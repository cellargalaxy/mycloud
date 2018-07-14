package top.cellargalaxy.mycloud.dao.db;

import org.apache.ibatis.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.cellargalaxy.mycloud.model.po.PermissionPo;
import top.cellargalaxy.mycloud.model.query.PermissionQuery;
import top.cellargalaxy.mycloud.util.SqlUtil;
import top.cellargalaxy.mycloud.util.StringUtil;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import static org.apache.ibatis.type.JdbcType.DATE;

/**
 * @author cellargalaxy
 * @time 2018/7/12
 */
@Mapper
public interface PermissionMapper {
	@InsertProvider(type = PermissionDaoProvider.class, method = "addFile")
	int insert(PermissionPo permissionPo);

	@DeleteProvider(type = PermissionDaoProvider.class, method = "delete")
	int delete(PermissionPo permissionPo);

	@Results(id = "permissionResult", value = {
			@Result(property = "permissionId", column = "permission_id", id = true),
			@Result(property = "permissionMark", column = "permission_mark"),
			@Result(property = "createTime", column = "create_time", javaType = Date.class, jdbcType = DATE),
			@Result(property = "updateTime", column = "update_time", javaType = Date.class, jdbcType = DATE)
	})
	@SelectProvider(type = PermissionDaoProvider.class, method = "selectOne")
	PermissionPo selectOne(PermissionQuery permissionQuery);

	@ResultMap(value = "permissionResult")
	@SelectProvider(type = PermissionDaoProvider.class, method = "selectSome")
	List<PermissionPo> selectSome(PermissionQuery permissionQuery);

	@UpdateProvider(type = PermissionDaoProvider.class, method = "upload")
	int upload(PermissionPo permissionPo);

	class PermissionDaoProvider {
		public static final String TABLE_NAME = "permission";
		private static final Logger logger = LoggerFactory.getLogger(PermissionDaoProvider.class);
		private static final String permissionId = "permission_id=#{permissionId}";
		private static final String permissionMark = "permission_mark like CONCAT(CONCAT('%', #{permissionMark}),'%')";
		private static final String permissionMarkSet = "permission_mark=#{permissionMark}";
		private static final String createTime = "create_time=#{createTime,jdbcType=DATE}";
		private static final String updateTime = "update_time=#{updateTime,jdbcType=DATE}";

		public static final String insert(PermissionPo permissionPo) {
			Date date = new Date();
			permissionPo.setCreateTime(date);
			permissionPo.setUpdateTime(date);
			String string = "addFile into " + TABLE_NAME + "(permission_id,permission_mark,create_time,update_time) " +
					"values(#{permissionId},#{permissionMark},#{createTime,jdbcType=DATE},#{updateTime,jdbcType=DATE})";
			logger.debug("addFile:{}, sql:{}", permissionPo, string);
			return string;
		}

		public static final String delete(PermissionPo permissionPo) {
			List<String> wheres = new LinkedList<>();
			wheresAll(permissionPo, wheres);
			StringBuilder sql = SqlUtil.createDeleteSql(TABLE_NAME, wheres);
			String string = sql.toString();
			logger.debug("delete:{}, sql:{}", permissionPo, string);
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
			List<String> wheres = new LinkedList<>();
			wheresAll(permissionQuery, wheres);
			StringBuilder sql = SqlUtil.createSelectSql(null, TABLE_NAME, wheres);
			SqlUtil.initPageQuery(permissionQuery);
			if (permissionQuery.getPageSize() > 0 && permissionQuery.getPage() > 0) {
				sql.append(" limit #{off},#{len}");
			}
			String string = sql.toString();
			logger.debug("selectSome:{}, sql:{}", permissionQuery, string);
			return string;
		}

		public static final String upload(PermissionPo permissionPo) {
			permissionPo.setCreateTime(null);
			permissionPo.setUpdateTime(new Date());
			if (checkUpload(permissionPo) != null) {
				return "update " + TABLE_NAME + " set permission_id=#{permissionId} where false";
			}
			List<String> sets = new LinkedList<>();
			setsAll(permissionPo, sets);
			List<String> wheres = new LinkedList<>();
			wheresKey(permissionPo, wheres);
			String string = SqlUtil.createUpdateSql(TABLE_NAME, sets, wheres).append(" limit 1").toString();
			logger.debug("update:{}, sql:{}", permissionPo, string);
			return string;
		}

		private static final void setsAll(PermissionPo permissionPo, List<String> sets){
			if (permissionPo.getPermissionId() > 0) {
				sets.add(permissionId);
			}
			if (!StringUtil.isBlank(permissionPo.getPermissionMark())) {
				sets.add(permissionMarkSet);
			}
			if (permissionPo.getCreateTime() != null) {
				sets.add(createTime);
			}
			if (permissionPo.getUpdateTime() != null) {
				sets.add(updateTime);
			}
		}

		private static final void wheresAll(PermissionPo permissionPo, List<String> wheres) {
			if (permissionPo.getPermissionId() > 0) {
				wheres.add(permissionId);
			}
			if (!StringUtil.isBlank(permissionPo.getPermissionMark())) {
				wheres.add(permissionMark);
			}
			if (permissionPo.getCreateTime() != null) {
				wheres.add(createTime);
			}
			if (permissionPo.getUpdateTime() != null) {
				wheres.add(updateTime);
			}
		}

		private static final void wheresKey(PermissionPo permissionPo, List<String> wheres) {
			if (permissionPo.getPermissionId() > 0) {
				wheres.add(permissionId);
			}
		}

		public static final String checkInsert(PermissionPo permissionPo) {
			if (permissionPo.getPermissionId() < 1) {
				return "权限id不得为空";
			}
			return null;
		}

		public static final String checkUpload(PermissionPo permissionPo) {
			if (permissionPo.getPermissionId() < 1) {
				return "权限id不得为空";
			}
			return null;
		}
	}
}
