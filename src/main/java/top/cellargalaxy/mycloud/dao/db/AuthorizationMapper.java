package top.cellargalaxy.mycloud.dao.db;

import org.apache.ibatis.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.cellargalaxy.mycloud.model.po.AuthorizationPo;
import top.cellargalaxy.mycloud.model.query.AuthorizationQuery;
import top.cellargalaxy.mycloud.util.SqlUtil;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import static org.apache.ibatis.type.JdbcType.DATE;

/**
 * @author cellargalaxy
 * @time 2018/7/12
 */
@Mapper
public interface AuthorizationMapper {
	@InsertProvider(type = AuthorizationProvider.class, method = "addFile")
	int insert(AuthorizationPo authorizationPo);

	@DeleteProvider(type = AuthorizationProvider.class, method = "delete")
	int delete(AuthorizationPo authorizationPo);

	@Results(id = "authorizationResult", value = {
			@Result(property = "authorization_id", column = "authorizationId", id = true),
			@Result(property = "user_id", column = "userId"),
			@Result(property = "permission_id", column = "permissionId"),
			@Result(property = "createTime", column = "create_time", javaType = Date.class, jdbcType = DATE),
			@Result(property = "updateTime", column = "update_time", javaType = Date.class, jdbcType = DATE)
	})
	@SelectProvider(type = AuthorizationProvider.class, method = "selectOne")
	AuthorizationPo selectOne(AuthorizationQuery authorizationQuery);

	@ResultMap(value = "authorizationResult")
	@SelectProvider(type = AuthorizationProvider.class, method = "selectSome")
	List<AuthorizationPo> selectSome(AuthorizationQuery authorizationQuery);

	@UpdateProvider(type = AuthorizationProvider.class, method = "update")
	int update(AuthorizationPo authorizationPo);

	class AuthorizationProvider {
		public static final String TABLE_NAME = "authorization";
		private static final Logger logger = LoggerFactory.getLogger(AuthorizationProvider.class);
		private static final String authorizationId = "authorization_id=#{authorizationId}";
		private static final String userId = "user_id=#{userId}";
		private static final String permissionId = "permission_id=#{permissionId}";
		private static final String createTime = "create_time=#{createTime,jdbcType=DATE}";
		private static final String updateTime = "update_time=#{updateTime,jdbcType=DATE}";

		public static final String insert(AuthorizationPo authorizationPo) {
			Date date = new Date();
			authorizationPo.setCreateTime(date);
			authorizationPo.setUpdateTime(date);
			String string = "addFile into " + TABLE_NAME + "(user_id,permission_id,create_time,update_time) " +
					"values(#{userId},#{permissionId},#{createTime,jdbcType=DATE},#{updateTime,jdbcType=DATE})";
			logger.debug("addFile:{}, sql:{}", authorizationPo, string);
			return string;
		}

		public static final String delete(AuthorizationPo authorizationPo) {
			List<String> wheres = new LinkedList<>();
			wheresAll(authorizationPo, wheres);
			StringBuilder sql = SqlUtil.createDeleteSql(TABLE_NAME, wheres);
			String string = sql.toString();
			logger.debug("delete:{}, sql:{}", authorizationPo, string);
			return string;
		}

		public static final String selectOne(AuthorizationQuery authorizationQuery) {
			List<String> wheres = new LinkedList<>();
			wheresKey(authorizationQuery, wheres);
			StringBuilder sql = SqlUtil.createSelectSql(null, TABLE_NAME, wheres);
			String string = sql.append(" limit 1").toString();
			logger.debug("selectOne:{}, sql:{}", authorizationQuery, string);
			return string;
		}

		public static final String selectSome(AuthorizationQuery authorizationQuery) {
			List<String> wheres = new LinkedList<>();
			wheresAll(authorizationQuery, wheres);
			StringBuilder sql = SqlUtil.createSelectSql(null, TABLE_NAME, wheres);
			SqlUtil.initPageQuery(authorizationQuery);
			if (authorizationQuery.getPageSize() > 0 && authorizationQuery.getPage() > 0) {
				sql.append(" limit #{off},#{len}");
			}
			String string = sql.toString();
			logger.debug("selectSome:{}, sql:{}", authorizationQuery, string);
			return string;
		}

		public static final String update(AuthorizationPo authorizationPo) {
			authorizationPo.setCreateTime(null);
			authorizationPo.setUpdateTime(new Date());
			if (checkUpload(authorizationPo) != null) {
				return "update " + TABLE_NAME + " set authorization_id=#{authorizationId} where false";
			}
			List<String> sets = new LinkedList<>();
			wheresAll(authorizationPo, sets);
			List<String> wheres = new LinkedList<>();
			wheresKey(authorizationPo, wheres);
			String string = SqlUtil.createUpdateSql(TABLE_NAME, sets, wheres).append(" limit 1").toString();
			logger.debug("update:{}, sql:{}", authorizationPo, string);
			return string;
		}

		private static final void wheresAll(AuthorizationPo authorizationPo, List<String> wheres) {
			if (authorizationPo.getAuthorizationId() > 0) {
				wheres.add(authorizationId);
			}
			if (authorizationPo.getUserId() > 0) {
				wheres.add(userId);
			}
			if (authorizationPo.getPermissionId() > 0) {
				wheres.add(permissionId);
			}
			if (authorizationPo.getCreateTime() != null) {
				wheres.add(createTime);
			}
			if (authorizationPo.getUpdateTime() != null) {
				wheres.add(updateTime);
			}
		}

		private static final void wheresKey(AuthorizationPo authorizationPo, List<String> wheres) {
			if (authorizationPo.getAuthorizationId() > 0) {
				wheres.add(authorizationId);
			}else if (authorizationPo.getUserId() > 0 && authorizationPo.getPermissionId() > 0) {
				wheres.add(userId);
				wheres.add(permissionId);
			}
		}

		public static final String checkInsert(AuthorizationPo authorizationPo) {
			if (authorizationPo.getUserId() < 1) {
				return "用户id不得为空";
			}
			if (authorizationPo.getPermissionId() < 1) {
				return "权限id不得为空";
			}
			return null;
		}

		public static final String checkUpload(AuthorizationPo authorizationPo) {
			if (authorizationPo.getUserId() < 0) {
				return "用户id不得为空";
			}
			return null;
		}
	}
}
