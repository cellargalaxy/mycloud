package top.cellargalaxy.mycloud.dao.mapper;

import org.apache.ibatis.annotations.*;
import org.apache.ibatis.jdbc.SQL;
import top.cellargalaxy.mycloud.dao.AuthorizationDao;
import top.cellargalaxy.mycloud.dao.UserDao;
import top.cellargalaxy.mycloud.model.bo.AuthorizationBo;
import top.cellargalaxy.mycloud.model.po.AuthorizationPo;
import top.cellargalaxy.mycloud.model.query.AuthorizationQuery;
import top.cellargalaxy.mycloud.util.dao.IDao;
import top.cellargalaxy.mycloud.util.dao.ProviderUtils;
import top.cellargalaxy.mycloud.util.dao.SqlUtils;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author cellargalaxy
 * @time 2018/10/25
 */
@Mapper
public interface AuthorizationMapper extends IDao<AuthorizationPo, AuthorizationBo, AuthorizationQuery> {
	@Options(useGeneratedKeys = true, keyProperty = "authorizationId")
	@InsertProvider(type = AuthorizationProvider.class, method = "insert")
	int insert(AuthorizationPo authorizationPo);

	@DeleteProvider(type = AuthorizationProvider.class, method = "delete")
	int delete(AuthorizationPo authorizationPo);

	@UpdateProvider(type = AuthorizationProvider.class, method = "update")
	int update(AuthorizationPo authorizationPo);

	@Results(id = "authorizationResults", value = {
			@Result(property = "authorizationId", column = "authorization_id"),
			@Result(property = "userId", column = "user_id"),
			@Result(property = "permission", column = "permission"),
			@Result(property = "createTime", column = "create_time"),
			@Result(property = "updateTime", column = "update_time"),
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

	class AuthorizationProvider /*implements IProvider<AuthorizationPo,AuthorizationQuery>*/ {
		private final String tableName = AuthorizationDao.TABLE_NAME;

		public Set<String> wheresKey(AuthorizationPo authorizationPo) {
			Set<String> wheres = new HashSet<>();
			if (authorizationPo.getAuthorizationId() > 0) {
				wheres.add("authorizationId");
				return wheres;
			}
			if (authorizationPo.getUserId() > 0 && authorizationPo.getPermission() != null) {
				wheres.add("userId");
				wheres.add("permission");
				return wheres;
			}
			wheres.add("authorizationId");
			return wheres;
		}

		public Set<String> wheresAll(AuthorizationQuery authorizationQuery) {
			Set<String> wheres = new HashSet<>();
			if (authorizationQuery.getAuthorizationId() > 0) {
				wheres.add("authorizationId");
			}
			if (authorizationQuery.getUserId() > 0) {
				wheres.add("userId");
			}
			if (authorizationQuery.getPermission() != null) {
				wheres.add("permission");
			}
			return wheres;
		}

		/**
		 * 只有删除，没有修改
		 *
		 * @param authorizationPo
		 * @return
		 */
		public Set<String> sets(AuthorizationPo authorizationPo) {
			Set<String> sets = new HashSet<>();

			return sets;
		}

		public String insert(AuthorizationPo authorizationPo) {
			String string = ProviderUtils.insert(tableName, AuthorizationPo.class, "authorizationId").toString();
			return string;
		}

		public String delete(AuthorizationPo authorizationPo) {
			String string = ProviderUtils.delete(tableName, wheresKey(authorizationPo)).toString();
			return string;
		}

		public String update(AuthorizationPo authorizationPo) {
			String string = null;//ProviderUtils.limitOne(ProviderUtils.update(tableName, sets(authorizationPo), "defaultSet", wheresKey(authorizationPo))).toString();
			return string;
		}

		public String selectOne(AuthorizationPo authorizationPo) {
			SQL sql = ProviderUtils.select(new SQL(), tableName, AuthorizationPo.class);
			sql.SELECT(UserDao.TABLE_NAME + ".username");

			sql.FROM(tableName).LEFT_OUTER_JOIN(UserDao.TABLE_NAME + " on " + ProviderUtils.column(tableName, "userId") + "=" + ProviderUtils.column(UserDao.TABLE_NAME, "userId"));

			sql = ProviderUtils.whereTrue(sql, tableName, wheresKey(authorizationPo));

			String string = ProviderUtils.limitOne(sql).toString();
			return string;
		}

		public String selectPageSome(AuthorizationQuery authorizationQuery) {
			SqlUtils.initPageQuery(authorizationQuery);

			SQL sql = ProviderUtils.select(new SQL(), tableName, AuthorizationPo.class);
			sql.SELECT(UserDao.TABLE_NAME + ".username");

			sql.FROM(tableName).LEFT_OUTER_JOIN(UserDao.TABLE_NAME + " on " + ProviderUtils.column(tableName, "userId") + "=" + ProviderUtils.column(UserDao.TABLE_NAME, "userId"));

			sql = ProviderUtils.whereTrue(sql, tableName, wheresAll(authorizationQuery));

			String string = ProviderUtils.limitSome(sql).toString();
			return string;
		}

		public String selectAllSome(AuthorizationQuery authorizationQuery) {
			SQL sql = ProviderUtils.select(new SQL(), tableName, AuthorizationPo.class);
			sql.SELECT(UserDao.TABLE_NAME + ".username");

			sql.FROM(tableName).LEFT_OUTER_JOIN(UserDao.TABLE_NAME + " on " + ProviderUtils.column(tableName, "userId") + "=" + ProviderUtils.column(UserDao.TABLE_NAME, "userId"));

			sql = ProviderUtils.whereTrue(sql, tableName, wheresAll(authorizationQuery));

			String string = sql.toString();
			return string;
		}

		public String selectCount(AuthorizationQuery authorizationQuery) {
			String string = ProviderUtils.selectCount(tableName, wheresAll(authorizationQuery)).toString();
			return string;
		}

		public String selectAll() {
			SQL sql = ProviderUtils.select(new SQL(), tableName, AuthorizationPo.class);
			sql.SELECT(UserDao.TABLE_NAME + ".username");

			sql.FROM(tableName).LEFT_OUTER_JOIN(UserDao.TABLE_NAME + " on " + ProviderUtils.column(tableName, "userId") + "=" + ProviderUtils.column(UserDao.TABLE_NAME, "userId"));

			String string = sql.toString();
			return string;
		}
	}
}
