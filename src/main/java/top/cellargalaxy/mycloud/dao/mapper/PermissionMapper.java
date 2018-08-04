package top.cellargalaxy.mycloud.dao.mapper;

import org.apache.ibatis.annotations.*;
import top.cellargalaxy.mycloud.dao.PermissionDao;
import top.cellargalaxy.mycloud.model.bo.PermissionBo;
import top.cellargalaxy.mycloud.model.po.PermissionPo;
import top.cellargalaxy.mycloud.model.query.PermissionQuery;
import top.cellargalaxy.mycloud.util.ProviderUtil;
import top.cellargalaxy.mycloud.util.StringUtil;

import java.util.Date;
import java.util.List;
import java.util.Set;

import static org.apache.ibatis.type.JdbcType.TIMESTAMP;

/**
 * @author cellargalaxy
 * @time 2018/7/12
 */
@Mapper
public interface PermissionMapper {
	@InsertProvider(type = PermissionProviderUtil.class, method = "insert")
	int insert(PermissionPo permissionPo);

	@DeleteProvider(type = PermissionProviderUtil.class, method = "delete")
	int delete(PermissionPo permissionPo);

	@Results(id = "permissionResult", value = {
			@Result(property = "permissionId", column = "permission_id", id = true),
			@Result(property = "permissionMark", column = "permission_mark"),
			@Result(property = "createTime", column = "create_time", javaType = Date.class, jdbcType = TIMESTAMP),
			@Result(property = "updateTime", column = "update_time", javaType = Date.class, jdbcType = TIMESTAMP)
	})
	@SelectProvider(type = PermissionProviderUtil.class, method = "selectOne")
	PermissionBo selectOne(PermissionPo permissionPo);

	@ResultMap(value = "permissionResult")
	@SelectProvider(type = PermissionProviderUtil.class, method = "selectSome")
	List<PermissionBo> selectSome(PermissionQuery permissionQuery);

	@SelectProvider(type = PermissionProviderUtil.class, method = "selectCount")
	int selectCount(PermissionQuery permissionQuery);

	@UpdateProvider(type = PermissionProviderUtil.class, method = "update")
	int update(PermissionPo permissionPo);

	class PermissionProviderUtil {
		private String tableName = PermissionDao.TABLE_NAME;
		private String permissionId = tableName + ".permission_id=#{permissionId}";
		private String permissionMark = tableName + ".permission_mark like CONCAT(CONCAT('%', #{permissionMark}),'%')";
		private String permissionMarkSet = tableName + ".permission_mark=#{permissionMark}";
		private String createTime = tableName + ".create_time=#{createTime,jdbcType=TIMESTAMP}";
		private String updateTime = tableName + ".update_time=#{updateTime,jdbcType=TIMESTAMP}";

		public String insert(PermissionPo permissionPo) {
			init(permissionPo);
			Date date = new Date();
			permissionPo.setCreateTime(date);
			permissionPo.setUpdateTime(date);

			String string = "insert into " + tableName + "(permission_id,permission_mark,create_time,update_time) " +
					"values(#{permissionId},#{permissionMark},#{createTime,jdbcType=TIMESTAMP},#{updateTime,jdbcType=TIMESTAMP})";
			return string;
		}

		public String delete(PermissionPo permissionPo) {
			return ProviderUtil.delete(tableName, permissionPo, this::wheresKey);
		}

		public String selectOne(PermissionPo permissionPo) {
			return ProviderUtil.selectOne(tableName, permissionPo, this::wheresKey);
		}

		public String selectSome(PermissionQuery permissionQuery) {
			return ProviderUtil.selectSome(tableName, permissionQuery, this::wheresAll);
		}

		public String selectCount(PermissionQuery permissionQuery) {
			return ProviderUtil.selectCount(tableName, permissionQuery, this::wheresAll);
		}

		public String update(PermissionPo permissionPo) {
			init(permissionPo);
			permissionPo.setCreateTime(null);
			permissionPo.setUpdateTime(new Date());

			return ProviderUtil.update(tableName, permissionPo, permissionId, this::sets, this::wheresKey);
		}

		private void wheresAll(PermissionQuery permissionQuery, Set<String> wheres) {
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

		private void wheresKey(PermissionPo permissionPo, Set<String> wheres) {
			if (permissionPo.getPermissionId() > 0) {
				wheres.add(permissionId);
			}
		}

		private void sets(PermissionPo permissionPo, Set<String> sets) {
			if (!StringUtil.isBlank(permissionPo.getPermissionMark())) {
				sets.add(permissionMarkSet);
			}
			if (permissionPo.getUpdateTime() != null) {
				sets.add(updateTime);
			}
		}

		private void init(PermissionPo permissionPo) {
			if (permissionPo.getPermissionMark() != null) {
				permissionPo.setPermissionMark(permissionPo.getPermissionMark().trim());
			}
		}
	}
}
