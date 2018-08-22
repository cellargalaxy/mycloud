package top.cellargalaxy.mycloud.dao.mapper;

import org.apache.ibatis.annotations.*;
import top.cellargalaxy.mycloud.dao.AbstractDao;
import top.cellargalaxy.mycloud.dao.AuthorizationDao;
import top.cellargalaxy.mycloud.dao.PermissionDao;
import top.cellargalaxy.mycloud.dao.UserDao;
import top.cellargalaxy.mycloud.model.bo.AuthorizationBo;
import top.cellargalaxy.mycloud.model.po.AuthorizationPo;
import top.cellargalaxy.mycloud.model.query.AuthorizationQuery;
import top.cellargalaxy.mycloud.util.ProviderUtil;
import top.cellargalaxy.mycloud.util.SqlUtil;

import java.util.*;

import static org.apache.ibatis.type.JdbcType.TIMESTAMP;

/**
 * @author cellargalaxy
 * @time 2018/7/12
 */
@Mapper
public interface AuthorizationMapper extends AbstractDao<AuthorizationPo, AuthorizationBo, AuthorizationQuery> {
	@Options(useGeneratedKeys = true, keyProperty = "authorizationId")
	@InsertProvider(type = AuthorizationProvider.class, method = "insert")
	int insert(AuthorizationPo authorizationPo);

	@DeleteProvider(type = AuthorizationProvider.class, method = "delete")
	int delete(AuthorizationPo authorizationPo);

	@Results(id = "authorizationResult", value = {
			@Result(property = "authorizationId", column = "authorization_id", id = true),
			@Result(property = "userId", column = "user_id"),
			@Result(property = "permissionId", column = "permission_id"),
			@Result(property = "createTime", column = "create_time", javaType = Date.class, jdbcType = TIMESTAMP),
			@Result(property = "updateTime", column = "update_time", javaType = Date.class, jdbcType = TIMESTAMP),
			@Result(property = "username", column = "username"),
			@Result(property = "permissionName", column = "permission_name")
	})
	@SelectProvider(type = AuthorizationProvider.class, method = "selectOne")
	AuthorizationBo selectOne(AuthorizationPo authorizationPo);

	@ResultMap(value = "authorizationResult")
	@SelectProvider(type = AuthorizationProvider.class, method = "selectSome")
	List<AuthorizationBo> selectSome(AuthorizationQuery authorizationQuery);

	@SelectProvider(type = AuthorizationProvider.class, method = "selectCount")
	int selectCount(AuthorizationQuery authorizationQuery);

	@ResultMap(value = "authorizationResult")
	@SelectProvider(type = AuthorizationProvider.class, method = "selectAll")
	List<AuthorizationBo> selectAll();

	@UpdateProvider(type = AuthorizationProvider.class, method = "update")
	int update(AuthorizationPo authorizationPo);

	class AuthorizationProvider /*implements AbstractProvider<AuthorizationPo, AuthorizationQuery>*/ {
		private String tableName = AuthorizationDao.TABLE_NAME;
		private String authorizationId = tableName + ".authorization_id=#{authorizationId}";
		private String userId = tableName + ".user_id=#{userId}";
		private String permissionId = tableName + ".permission_id=#{permissionId}";
		private String createTime = tableName + ".create_time=#{createTime,jdbcType=TIMESTAMP}";
		private String updateTime = tableName + ".update_time=#{updateTime,jdbcType=TIMESTAMP}";

		public String insert(AuthorizationPo authorizationPo) {
			Date date = new Date();
			authorizationPo.setCreateTime(date);
			authorizationPo.setUpdateTime(date);

			String string = "insert into " + tableName + "(user_id,permission_id,create_time,update_time) " +
					"values(#{userId},#{permissionId},#{createTime,jdbcType=TIMESTAMP},#{updateTime,jdbcType=TIMESTAMP})";
			return string;
		}

		public String delete(AuthorizationPo authorizationPo) {
			return ProviderUtil.delete(tableName, authorizationPo, this::wheresKey).append(" limit 1").toString();
		}

		public String selectOne(AuthorizationPo authorizationPo) {
			List<String> selects = new LinkedList<>();
			selects.add(tableName + ".authorization_id");
			selects.add(tableName + ".user_id");
			selects.add(tableName + ".permission_id");
			selects.add(tableName + ".create_time");
			selects.add(tableName + ".update_time");
			selects.add(UserDao.TABLE_NAME + ".username");
			selects.add(PermissionDao.TABLE_NAME + ".permission_name");

			Set<String> wheres = new HashSet<>();
			wheres.add(tableName + ".user_id=" + UserDao.TABLE_NAME + ".user_id");
			wheres.add(tableName + ".permission_id=" + PermissionDao.TABLE_NAME + ".permission_id");
			wheresKey(authorizationPo, wheres);

			StringBuilder sql = SqlUtil.createSelectSql(selects, tableName + "," + UserDao.TABLE_NAME + "," + PermissionDao.TABLE_NAME, wheres);
			String string = sql.append(" limit 1").toString();
			return string;
		}

		public String selectSome(AuthorizationQuery authorizationQuery) {
			SqlUtil.initPageQuery(authorizationQuery);

			List<String> selects = new LinkedList<>();
			selects.add(tableName + ".authorization_id");
			selects.add(tableName + ".user_id");
			selects.add(tableName + ".permission_id");
			selects.add(tableName + ".create_time");
			selects.add(tableName + ".update_time");
			selects.add(UserDao.TABLE_NAME + ".username");
			selects.add(PermissionDao.TABLE_NAME + ".permission_name");

			Set<String> wheres = new HashSet<>();
			wheres.add(tableName + ".user_id=" + UserDao.TABLE_NAME + ".user_id");
			wheres.add(tableName + ".permission_id=" + PermissionDao.TABLE_NAME + ".permission_id");
			wheresAll(authorizationQuery, wheres);

			StringBuilder sql = SqlUtil.createSelectSql(selects, tableName + "," + UserDao.TABLE_NAME + "," + PermissionDao.TABLE_NAME, wheres);
			String string = sql.append(" limit #{off},#{len}").toString();
			return string;
		}

		public String selectCount(AuthorizationQuery authorizationQuery) {
			SqlUtil.initPageQuery(authorizationQuery);

			List<String> selects = new LinkedList<>();
			selects.add("count(*)");

			Set<String> wheres = new HashSet<>();
			wheres.add(tableName + ".user_id=" + UserDao.TABLE_NAME + ".user_id");
			wheres.add(tableName + ".permission_id=" + PermissionDao.TABLE_NAME + ".permission_id");
			wheresAll(authorizationQuery, wheres);

			StringBuilder sql = SqlUtil.createSelectSql(selects, tableName + "," + UserDao.TABLE_NAME + "," + PermissionDao.TABLE_NAME, wheres);
			String string = sql.toString();
			return string;
		}

		public String selectAll() {
			List<String> selects = new LinkedList<>();
			selects.add(tableName + ".authorization_id");
			selects.add(tableName + ".user_id");
			selects.add(tableName + ".permission_id");
			selects.add(tableName + ".create_time");
			selects.add(tableName + ".update_time");
			selects.add(UserDao.TABLE_NAME + ".username");
			selects.add(PermissionDao.TABLE_NAME + ".permission_name");

			Set<String> wheres = new HashSet<>();
			wheres.add(tableName + ".user_id=" + UserDao.TABLE_NAME + ".user_id");
			wheres.add(tableName + ".permission_id=" + PermissionDao.TABLE_NAME + ".permission_id");

			StringBuilder sql = SqlUtil.createSelectSql(selects, tableName + "," + UserDao.TABLE_NAME + "," + PermissionDao.TABLE_NAME, wheres);
			String string = sql.toString();
			return string;
		}

		public String update(AuthorizationPo authorizationPo) {
			authorizationPo.setCreateTime(null);
			authorizationPo.setUpdateTime(new Date());

			return ProviderUtil.update(tableName, authorizationPo, authorizationId, this::sets, this::wheresKey).append(" limit 1").toString();
		}

		private void wheresAll(AuthorizationQuery authorizationQuery, Set<String> wheres) {
			if (authorizationQuery.getAuthorizationId() > 0) {
				wheres.add(authorizationId);
			}
			if (authorizationQuery.getUserId() > 0) {
				wheres.add(userId);
			}
			if (authorizationQuery.getPermissionId() > 0) {
				wheres.add(permissionId);
			}
			if (authorizationQuery.getCreateTime() != null) {
				wheres.add(createTime);
			}
			if (authorizationQuery.getUpdateTime() != null) {
				wheres.add(updateTime);
			}
		}

		private void wheresKey(AuthorizationPo authorizationPo, Set<String> wheres) {
			if (authorizationPo.getAuthorizationId() > 0) {
				wheres.add(authorizationId);
			} else if (authorizationPo.getUserId() > 0 && authorizationPo.getPermissionId() > 0) {
				wheres.add(userId);
				wheres.add(permissionId);
			}
		}

		private void sets(AuthorizationPo authorizationPo, Set<String> sets) {
			if (authorizationPo.getUserId() > 0) {
				sets.add(userId);
			}
			if (authorizationPo.getPermissionId() > 0) {
				sets.add(permissionId);
			}
			if (authorizationPo.getUpdateTime() != null) {
				sets.add(updateTime);
			}
		}
	}
}
