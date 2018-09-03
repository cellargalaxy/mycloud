package top.cellargalaxy.mycloud.dao.mapper;

import org.apache.ibatis.annotations.*;
import top.cellargalaxy.mycloud.dao.AbstractDao;
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
public interface PermissionMapper extends AbstractDao<PermissionPo, PermissionBo, PermissionQuery> {
	@InsertProvider(type = PermissionProvider.class, method = "insert")
	int insert(PermissionPo permissionPo);

	@DeleteProvider(type = PermissionProvider.class, method = "delete")
	int delete(PermissionPo permissionPo);

	@Results(id = "permissionResult", value = {
			@Result(property = "permissionId", column = "permission_id", id = true),
			@Result(property = "permissionName", column = "permission_name"),
			@Result(property = "createTime", column = "create_time", javaType = Date.class, jdbcType = TIMESTAMP),
			@Result(property = "updateTime", column = "update_time", javaType = Date.class, jdbcType = TIMESTAMP)
	})
	@SelectProvider(type = PermissionProvider.class, method = "selectOne")
	PermissionBo selectOne(PermissionPo permissionPo);

	@ResultMap(value = "permissionResult")
	@SelectProvider(type = PermissionProvider.class, method = "selectSome")
	List<PermissionBo> selectSome(PermissionQuery permissionQuery);

	@SelectProvider(type = PermissionProvider.class, method = "selectCount")
	int selectCount(PermissionQuery permissionQuery);

	@ResultMap(value = "permissionResult")
	@SelectProvider(type = PermissionProvider.class, method = "selectAll")
	List<PermissionBo> selectAll();

	@UpdateProvider(type = PermissionProvider.class, method = "update")
	int update(PermissionPo permissionPo);

	class PermissionProvider /*implements AbstractProvider<PermissionPo, PermissionQuery>*/ {
		private String tableName = PermissionDao.TABLE_NAME;
		private String permissionId = tableName + ".permission_id=#{permissionId}";
		private String permissionName = tableName + ".permission_name like CONCAT(CONCAT('%', #{permissionName}),'%')";
		private String permissionNameSet = tableName + ".permission_name=#{permissionName}";
		private String createTime = tableName + ".create_time=#{createTime,jdbcType=TIMESTAMP}";
		private String updateTime = tableName + ".update_time=#{updateTime,jdbcType=TIMESTAMP}";

		public String insert(PermissionPo permissionPo) {
			init(permissionPo);
			Date date = new Date();
			permissionPo.setCreateTime(date);
			permissionPo.setUpdateTime(date);

			String string = "insert into " + tableName + "(permission_id,permission_name,create_time,update_time) " +
					"values(#{permissionId},#{permissionName},#{createTime,jdbcType=TIMESTAMP},#{updateTime,jdbcType=TIMESTAMP})";
			return string;
		}

		public String delete(PermissionPo permissionPo) {
			return ProviderUtil.delete(tableName, permissionPo, this::wheresKey).append(" limit 1").toString();
		}

		public String selectOne(PermissionPo permissionPo) {
			return ProviderUtil.selectOne(tableName, permissionPo, this::wheresKey).append(" limit 1").toString();
		}

		public String selectSome(PermissionQuery permissionQuery) {
			return ProviderUtil.selectSome(tableName, permissionQuery, this::wheresAll).append(" limit #{off},#{len}").toString();
		}

		public String selectCount(PermissionQuery permissionQuery) {
			return ProviderUtil.selectCount(tableName, permissionQuery, this::wheresAll).toString();
		}

		public String selectAll() {
			return ProviderUtil.selectAll(tableName).toString();
		}

		public String update(PermissionPo permissionPo) {
			init(permissionPo);
			permissionPo.setCreateTime(null);
			permissionPo.setUpdateTime(new Date());

			return ProviderUtil.update(tableName, permissionPo, permissionId, this::sets, this::wheresKey).append(" limit 1").toString();
		}

		private void wheresAll(PermissionQuery permissionQuery, Set<String> wheres) {
			if (permissionQuery.getPermissionId() > 0) {
				wheres.add(permissionId);
			}
			if (!StringUtil.isBlank(permissionQuery.getPermissionName())) {
				wheres.add(permissionName);
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
			if (!StringUtil.isBlank(permissionPo.getPermissionName())) {
				sets.add(permissionNameSet);
			}
			if (permissionPo.getUpdateTime() != null) {
				sets.add(updateTime);
			}
		}

		private void init(PermissionPo permissionPo) {
			if (permissionPo.getPermissionName() != null) {
				permissionPo.setPermissionName(permissionPo.getPermissionName().trim());
			}
		}
	}
}
