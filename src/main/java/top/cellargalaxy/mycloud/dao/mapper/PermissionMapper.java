package top.cellargalaxy.mycloud.dao.mapper;

import org.apache.ibatis.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.cellargalaxy.mycloud.dao.PermissionDao;
import top.cellargalaxy.mycloud.model.bo.PermissionBo;
import top.cellargalaxy.mycloud.model.po.PermissionPo;
import top.cellargalaxy.mycloud.model.query.PermissionQuery;
import top.cellargalaxy.mycloud.util.SqlUtil;
import top.cellargalaxy.mycloud.util.StringUtil;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import static org.apache.ibatis.type.JdbcType.TIMESTAMP;

/**
 * @author cellargalaxy
 * @time 2018/7/12
 */
@Mapper
public interface PermissionMapper {
	@InsertProvider(type = PermissionProvider.class, method = "insert")
	int insert(PermissionPo permissionPo);

	@DeleteProvider(type = PermissionProvider.class, method = "delete")
	int delete(PermissionQuery permissionQuery);

	@Results(id = "permissionResult", value = {
			@Result(property = "permissionId", column = "permission_id", id = true),
			@Result(property = "permissionMark", column = "permission_mark"),
			@Result(property = "createTime", column = "create_time", javaType = Date.class, jdbcType = TIMESTAMP),
			@Result(property = "updateTime", column = "update_time", javaType = Date.class, jdbcType = TIMESTAMP)
	})
	@SelectProvider(type = PermissionProvider.class, method = "selectOne")
	PermissionBo selectOne(PermissionQuery permissionQuery);

	@ResultMap(value = "permissionResult")
	@SelectProvider(type = PermissionProvider.class, method = "selectSome")
	List<PermissionBo> selectSome(PermissionQuery permissionQuery);

	@UpdateProvider(type = PermissionProvider.class, method = "upldate")
	int upldate(PermissionPo permissionPo);

	class PermissionProvider {
		private static final Logger logger = LoggerFactory.getLogger(PermissionProvider.class);
		private static final String TABLE_NAME = PermissionDao.TABLE_NAME;
		private static final String permissionId = TABLE_NAME + ".permission_id=#{permissionId}";
		private static final String permissionMark = TABLE_NAME + ".permission_mark like CONCAT(CONCAT('%', #{permissionMark}),'%')";
		private static final String permissionMarkSet = TABLE_NAME + ".permission_mark=#{permissionMark}";
		private static final String createTime = TABLE_NAME + ".create_time=#{createTime,jdbcType=TIMESTAMP}";
		private static final String updateTime = TABLE_NAME + ".update_time=#{updateTime,jdbcType=TIMESTAMP}";

		public static final String insert(PermissionPo permissionPo) {
			Date date = new Date();
			permissionPo.setCreateTime(date);
			permissionPo.setUpdateTime(date);
			String string = "insert into " + TABLE_NAME + "(permission_id,permission_mark,create_time,update_time) " +
					"values(#{permissionId},#{permissionMark},#{createTime,jdbcType=TIMESTAMP},#{updateTime,jdbcType=TIMESTAMP})";
			logger.debug("insert:{}, sql:{}", permissionPo, string);
			return string;
		}

		public static final String delete(PermissionQuery permissionQuery) {
			List<String> wheres = new LinkedList<>();
			wheresAll(permissionQuery, wheres);
			StringBuilder sql = SqlUtil.createDeleteSql(TABLE_NAME, wheres);
			String string = sql.toString();
			logger.debug("delete:{}, sql:{}", permissionQuery, string);
			return string;
		}

		public static final String selectOne(PermissionQuery permissionQuery) {
			List<String> wheres = new LinkedList<>();
			wheresKey(permissionQuery, wheres);
			StringBuilder sql = SqlUtil.createSelectSql(null, TABLE_NAME, wheres);
			String string = sql.append(" limit 1").toString();
			logger.debug("selectOne:{}, sql:{}", permissionQuery, string);
			return string;
		}

		public static final String selectSome(PermissionQuery permissionQuery) {
			SqlUtil.initPageQuery(permissionQuery);
			List<String> wheres = new LinkedList<>();
			wheresAll(permissionQuery, wheres);
			if (permissionQuery.isPage()) {
				wheres.add("true");
			}
			StringBuilder sql = SqlUtil.createSelectSql(null, TABLE_NAME, wheres);
			if (permissionQuery.isPage()) {
				sql.append(" limit #{off},#{len}");
			}
			String string = sql.toString();
			logger.debug("selectSome:{}, sql:{}", permissionQuery, string);
			return string;
		}

		public static final String upldate(PermissionPo permissionPo) {
			if (PermissionDao.checkUpdate(permissionPo) != null) {
				return "update " + TABLE_NAME + " set permission_id=#{permissionId} where false";
			}
			permissionPo.setCreateTime(null);
			permissionPo.setUpdateTime(new Date());
			List<String> sets = new LinkedList<>();
			sets(permissionPo, sets);
			List<String> wheres = new LinkedList<>();
			wheresKey(permissionPo, wheres);
			String string = SqlUtil.createUpdateSql(TABLE_NAME, sets, wheres).append(" limit 1").toString();
			logger.debug("update:{}, sql:{}", permissionPo, string);
			return string;
		}

		private static final void wheresAll(PermissionQuery permissionQuery, List<String> wheres) {
			if (permissionQuery.getPermissionId() > 0) {
				wheres.add(permissionId);
			}
			if (!StringUtil.isBlank(permissionQuery.getPermissionMark())) {
				wheres.add(permissionMark);
			}
			if (permissionQuery.getCreateTime() != null) {
				wheres.add(createTime);
			}
			if (permissionQuery.getUpdateTime() != null) {
				wheres.add(updateTime);
			}
		}

		private static final void wheresKey(PermissionPo permissionPo, List<String> wheres) {
			if (permissionPo.getPermissionId() > 0) {
				wheres.add(permissionId);
			}
		}

		private static final void sets(PermissionPo permissionPo, List<String> sets) {
			if (!StringUtil.isBlank(permissionPo.getPermissionMark())) {
				sets.add(permissionMarkSet);
			}
			if (permissionPo.getUpdateTime() != null) {
				sets.add(updateTime);
			}
		}

	}
}
