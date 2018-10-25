package top.cellargalaxy.mycloud.dao.mapper;

import org.apache.ibatis.annotations.*;
import top.cellargalaxy.mycloud.dao.AbstractDao;
import top.cellargalaxy.mycloud.dao.AuthorizationDao;
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
 * @time 2018/10/25
 */
@Mapper
public interface AuthorizationMapper extends AbstractDao<AuthorizationPo, AuthorizationBo, AuthorizationQuery> {
	@Options(useGeneratedKeys = true, keyProperty = "authorizationId")
	@InsertProvider(type = AuthorizationProvider.class, method = "insert")
	int insert(AuthorizationPo authorizationPo);

	@DeleteProvider(type = AuthorizationProvider.class, method = "delete")
	int delete(AuthorizationPo authorizationPo);

	@UpdateProvider(type = AuthorizationProvider.class, method = "update")
	int update(AuthorizationPo authorizationPo);

	@Results(id = "authorizationResults", value = {
			@Result(property = "authorizationId", column = "authorization_id", id = true),
			@Result(property = "userId", column = "user_id"),
			@Result(property = "permission", column = "permission"),
			@Result(property = "createTime", column = "create_time", javaType = Date.class, jdbcType = TIMESTAMP),
			@Result(property = "updateTime", column = "update_time", javaType = Date.class, jdbcType = TIMESTAMP),
			@Result(property = "username", column = "username"),
	})
	@SelectProvider(type = AuthorizationProvider.class, method = "selectOne")
	AuthorizationBo selectOne(AuthorizationPo authorizationPo);

	@ResultMap(value = "authorizationResults")
	@SelectProvider(type = AuthorizationProvider.class, method = "selectPageSome")
	List<AuthorizationBo> selectPageSome(AuthorizationQuery authorizationQuery);

	@ResultMap(value = "authorizationResults")
	@SelectProvider(type = AuthorizationProvider.class, method = "selectAllSome")
	List<AuthorizationBo> selectAllSome(AuthorizationQuery authorizationQuery);

	@SelectProvider(type = AuthorizationProvider.class, method = "selectCount")
	int selectCount(AuthorizationQuery authorizationQuery);

	@ResultMap(value = "authorizationResults")
	@SelectProvider(type = AuthorizationProvider.class, method = "selectAll")
	List<AuthorizationBo> selectAll();

	class AuthorizationProvider /*implements AbstractProvider<AuthorizationPo, AuthorizationQuery>*/ {
		private String tableName = AuthorizationDao.TABLE_NAME;
		private String authorizationId = tableName + ".authorization_id=#{authorizationId}";
		private String userId = tableName + ".user_id=#{userId}";
		private String permission = tableName + ".permission=#{permission}";
		private String createTime = tableName + ".create_time=#{createTime,jdbcType=TIMESTAMP}";
		private String updateTime = tableName + ".update_time=#{updateTime,jdbcType=TIMESTAMP}";

		private List<String> createSelects() {
			List<String> selects = new LinkedList<>();
			selects.add(tableName + ".authorization_id");
			selects.add(tableName + ".user_id");
			selects.add(tableName + ".permission");
			selects.add(tableName + ".create_time");
			selects.add(tableName + ".update_time");
			selects.add(UserDao.TABLE_NAME + ".username");
			return selects;
		}

		private Set<String> createWheres() {
			Set<String> wheres = new HashSet<>();
			wheres.add(tableName + ".user_id=" + UserDao.TABLE_NAME + ".user_id");
			return wheres;
		}

		public void wheresKey(AuthorizationPo authorizationPo, Set<String> wheres) {
			if (authorizationPo.getUserId() > 0 && authorizationPo.getPermission() != null) {
				wheres.add(userId);
				wheres.add(permission);
				return;
			}
			wheres.add(authorizationId);
		}

		public void wheresAll(AuthorizationQuery authorizationQuery, Set<String> wheres) {
			if (authorizationQuery.getAuthorizationId() > 0) {
				wheres.add(authorizationId);
			}
			if (authorizationQuery.getUserId() > 0) {
				wheres.add(userId);
			}
			if (authorizationQuery.getPermission() != null) {
				wheres.add(permission);
			}
		}

		public void sets(AuthorizationPo authorizationPo, Set<String> sets) {
			if (authorizationPo.getUserId() > 0) {
				sets.add(userId);
			}
			if (authorizationPo.getPermission() != null) {
				sets.add(permission);
			}
			if (authorizationPo.getUpdateTime() != null) {
				sets.add(updateTime);
			}
		}

		public String insert(AuthorizationPo authorizationPo) {
			String string = "insert into " + tableName + "(user_id,permission,create_time,update_time) " +
					"values(#{userId},#{permission},#{createTime,jdbcType=TIMESTAMP},#{updateTime,jdbcType=TIMESTAMP})";
			return string;
		}

		public String delete(AuthorizationPo authorizationPo) {
			return ProviderUtil.delete(tableName, authorizationPo, this::wheresKey).append(" limit 1").toString();
		}

		public String update(AuthorizationPo authorizationPo) {
			return ProviderUtil.update(tableName, authorizationPo, authorizationId, this::sets, this::wheresKey).append(" limit 1").toString();
		}

		public String selectOne(AuthorizationPo authorizationPo) {
			List<String> selects = createSelects();

			Set<String> wheres = createWheres();
			wheresKey(authorizationPo, wheres);

			StringBuilder sql = SqlUtil.createSelectSql(selects, tableName + "," + UserDao.TABLE_NAME, wheres);
			String string = sql.append(" limit 1").toString();
			return string;
		}

		public String selectPageSome(AuthorizationQuery authorizationQuery) {
			SqlUtil.initPageQuery(authorizationQuery);
			return selectSome(authorizationQuery).append(" limit #{off},#{len}").toString();
		}

		public String selectAllSome(AuthorizationQuery authorizationQuery) {
			return selectSome(authorizationQuery).toString();
		}

		private StringBuilder selectSome(AuthorizationQuery authorizationQuery) {
			List<String> selects = createSelects();

			Set<String> wheres = createWheres();
			wheresAll(authorizationQuery, wheres);

			StringBuilder sql = SqlUtil.createSelectSql(selects, tableName + "," + UserDao.TABLE_NAME, wheres);
			return sql;
		}

		public String selectCount(AuthorizationQuery authorizationQuery) {
			return ProviderUtil.selectCount(tableName, authorizationQuery, this::wheresAll).toString();
		}

		public String selectAll() {
			List<String> selects = createSelects();

			Set<String> wheres = createWheres();

			StringBuilder sql = SqlUtil.createSelectSql(selects, tableName + "," + UserDao.TABLE_NAME, wheres);
			String string = sql.toString();
			return string;
		}
	}
}
