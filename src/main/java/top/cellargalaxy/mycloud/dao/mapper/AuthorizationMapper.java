package top.cellargalaxy.mycloud.dao.mapper;

import org.apache.ibatis.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.cellargalaxy.mycloud.dao.AuthorizationDao;
import top.cellargalaxy.mycloud.dao.PermissionDao;
import top.cellargalaxy.mycloud.dao.UserDao;
import top.cellargalaxy.mycloud.model.bo.AuthorizationBo;
import top.cellargalaxy.mycloud.model.po.AuthorizationPo;
import top.cellargalaxy.mycloud.model.query.AuthorizationQuery;
import top.cellargalaxy.mycloud.util.SqlUtil;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import static org.apache.ibatis.type.JdbcType.TIMESTAMP;

/**
 * @author cellargalaxy
 * @time 2018/7/12
 */
@Mapper
public interface AuthorizationMapper {
	@InsertProvider(type = AuthorizationProvider.class, method = "insert")
	@Options(useGeneratedKeys = true, keyProperty = "authorizationId")
	int insert(AuthorizationPo authorizationPo);

	@DeleteProvider(type = AuthorizationProvider.class, method = "delete")
	int delete(AuthorizationQuery authorizationQuery);

	@Results(id = "authorizationResult", value = {
			@Result(property = "authorizationId", column = "authorization_id", id = true),
			@Result(property = "userId", column = "user_id"),
			@Result(property = "permissionId", column = "permission_id"),
			@Result(property = "createTime", column = "create_time", javaType = Date.class, jdbcType = TIMESTAMP),
			@Result(property = "updateTime", column = "update_time", javaType = Date.class, jdbcType = TIMESTAMP),
			@Result(property = "username", column = "username"),
			@Result(property = "permissionMark", column = "permission_mark")
	})
	@SelectProvider(type = AuthorizationProvider.class, method = "selectOne")
	AuthorizationBo selectOne(AuthorizationQuery authorizationQuery);

	@ResultMap(value = "authorizationResult")
	@SelectProvider(type = AuthorizationProvider.class, method = "selectSome")
	List<AuthorizationBo> selectSome(AuthorizationQuery authorizationQuery);

	@UpdateProvider(type = AuthorizationProvider.class, method = "update")
	int update(AuthorizationPo authorizationPo);

	class AuthorizationProvider {
		private static final Logger logger = LoggerFactory.getLogger(AuthorizationProvider.class);
		private static final String TABLE_NAME = AuthorizationDao.TABLE_NAME;
		private static final String authorizationId = TABLE_NAME + ".authorization_id=#{authorizationId}";
		private static final String userId = TABLE_NAME + ".user_id=#{userId}";
		private static final String permissionId = TABLE_NAME + ".permission_id=#{permissionId}";
		private static final String createTime = TABLE_NAME + ".create_time=#{createTime,jdbcType=TIMESTAMP}";
		private static final String updateTime = TABLE_NAME + ".update_time=#{updateTime,jdbcType=TIMESTAMP}";

		public static final String insert(AuthorizationPo authorizationPo) {
			Date date = new Date();
			authorizationPo.setCreateTime(date);
			authorizationPo.setUpdateTime(date);
			String string = "insert into " + TABLE_NAME + "(user_id,permission_id,create_time,update_time) " +
					"values(#{userId},#{permissionId},#{createTime,jdbcType=TIMESTAMP},#{updateTime,jdbcType=TIMESTAMP})";
			logger.debug("insert:{}, sql:{}", authorizationPo, string);
			return string;
		}

		public static final String delete(AuthorizationQuery authorizationQuery) {
			List<String> wheres = new LinkedList<>();
			wheresAll(authorizationQuery, wheres);
			StringBuilder sql = SqlUtil.createDeleteSql(TABLE_NAME, wheres);
			String string = sql.toString();
			logger.debug("delete:{}, sql:{}", authorizationQuery, string);
			return string;
		}

		public static final String selectOne(AuthorizationQuery authorizationQuery) {
			List<String> selects = new LinkedList<>();
			selects.add(TABLE_NAME + ".authorization_id");
			selects.add(TABLE_NAME + ".user_id");
			selects.add(TABLE_NAME + ".permission_id");
			selects.add(TABLE_NAME + ".create_time");
			selects.add(TABLE_NAME + ".update_time");
			selects.add(UserDao.TABLE_NAME + ".username");
			selects.add(PermissionDao.TABLE_NAME + ".permission_mark");
			List<String> wheres = new LinkedList<>();
			wheresKey(authorizationQuery, wheres);
			StringBuilder sql = SqlUtil.createSelectSql(selects, TABLE_NAME + "," + UserDao.TABLE_NAME + "," + PermissionDao.TABLE_NAME, wheres);
			String string = sql.append(" limit 1").toString();
			logger.debug("selectOne:{}, sql:{}", authorizationQuery, string);
			return string;
		}

		public static final String selectSome(AuthorizationQuery authorizationQuery) {
			List<String> selects = new LinkedList<>();
			selects.add(TABLE_NAME + ".authorization_id");
			selects.add(TABLE_NAME + ".user_id");
			selects.add(TABLE_NAME + ".permission_id");
			selects.add(TABLE_NAME + ".create_time");
			selects.add(TABLE_NAME + ".update_time");
			selects.add(UserDao.TABLE_NAME + ".username");
			selects.add(PermissionDao.TABLE_NAME + ".permission_mark");
			List<String> wheres = new LinkedList<>();
			wheresAll(authorizationQuery, wheres);
			StringBuilder sql = SqlUtil.createSelectSql(selects, TABLE_NAME + "," + UserDao.TABLE_NAME + "," + PermissionDao.TABLE_NAME, wheres);
			SqlUtil.initPageQuery(authorizationQuery);
			if (authorizationQuery.getPageSize() > 0 && authorizationQuery.getPage() > 0) {
				sql.append(" limit #{off},#{len}");
			}
			String string = sql.toString();
			logger.debug("selectSome:{}, sql:{}", authorizationQuery, string);
			return string;
		}

		public static final String update(AuthorizationPo authorizationPo) {
			if (AuthorizationDao.checkUpdate(authorizationPo) != null) {
				return "update " + TABLE_NAME + " set authorization_id=#{authorizationId} where false";
			}
			authorizationPo.setCreateTime(null);
			authorizationPo.setUpdateTime(new Date());
			List<String> sets = new LinkedList<>();
			sets(authorizationPo, sets);
			List<String> wheres = new LinkedList<>();
			wheresKey(authorizationPo, wheres);
			String string = SqlUtil.createUpdateSql(TABLE_NAME, sets, wheres).append(" limit 1").toString();
			logger.debug("update:{}, sql:{}", authorizationPo, string);
			return string;
		}

		private static final void wheresAll(AuthorizationQuery authorizationQuery, List<String> wheres) {
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

		private static final void wheresKey(AuthorizationPo authorizationPo, List<String> wheres) {
			if (authorizationPo.getAuthorizationId() > 0) {
				wheres.add(authorizationId);
			} else if (authorizationPo.getUserId() > 0 && authorizationPo.getPermissionId() > 0) {
				wheres.add(userId);
				wheres.add(permissionId);
			}
		}

		private static final void sets(AuthorizationPo authorizationPo, List<String> sets) {
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
