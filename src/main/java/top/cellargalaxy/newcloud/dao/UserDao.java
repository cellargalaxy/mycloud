package top.cellargalaxy.newcloud.dao;

import org.apache.ibatis.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.cellargalaxy.newcloud.model.po.UserPo;
import top.cellargalaxy.newcloud.model.query.UserQuery;
import top.cellargalaxy.newcloud.util.SqlUtil;
import top.cellargalaxy.newcloud.util.StringUtil;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import static org.apache.ibatis.type.JdbcType.DATE;

/**
 * Created by cellargalaxy on 18-7-12.
 */
@Mapper
public interface UserDao {
	@InsertProvider(type = UserDaoProvider.class, method = "insert")
	int insert(UserPo userPo);

	@DeleteProvider(type = UserDaoProvider.class, method = "delete")
	int delete(UserQuery userQuery);

	@Results(id = "userResult", value = {
			@Result(property = "userId", column = "user_id", id = true),
			@Result(property = "userName", column = "user_name"),
			@Result(property = "userPassword", column = "user_password"),
			@Result(property = "createTime", column = "create_time", javaType = Date.class, jdbcType = DATE),
			@Result(property = "updateTime", column = "update_time", javaType = Date.class, jdbcType = DATE)
	})
	@SelectProvider(type = UserDaoProvider.class, method = "selectOne")
	UserPo selectOne(UserQuery userQuery);

	@ResultMap(value = "userResult")
	@SelectProvider(type = UserDaoProvider.class, method = "selectSome")
	List<UserPo> selectSome(UserQuery userQuery);

	@UpdateProvider(type = UserDaoProvider.class, method = "update")
	int update(UserPo userPo);

	class UserDaoProvider {
		public static final String TABLE_NAME = "user";
		private static final Logger logger = LoggerFactory.getLogger(UserDaoProvider.class);
		private static final String userId = "user_id=#{userId}";
		private static final String userName = "user_name=#{userName}";
		private static final String userPassword = "user_password=#{userPassword}";
		private static final String createTime = "create_time=#{createTime,jdbcType=DATE}";
		private static final String updateTime = "update_time=#{updateTime,jdbcType=DATE}";

		public static final String insert(UserPo userPo) {
			Date date = new Date();
			userPo.setCreateTime(date);
			userPo.setUpdateTime(date);
			String string = "insert into " + TABLE_NAME + "(user_name,user_password,create_time,update_time) " +
					"values(#{userName},#{userPassword},#{createTime,jdbcType=DATE},#{updateTime,jdbcType=DATE})";
			logger.debug("insert:{}, sql:{}", userPo, string);
			return string;
		}

		public static final String delete(UserQuery userQuery) {
			List<String> wheres = new LinkedList<>();
			wheresAll(userQuery, wheres);
			StringBuilder sql = SqlUtil.createDeleteSql(TABLE_NAME, wheres);
			String string = sql.toString();
			logger.debug("delete:{}, sql:{}", userQuery, string);
			return string;
		}

		public static final String selectOne(UserQuery userQuery) {
			List<String> wheres = new LinkedList<>();
			wheresKey(userQuery, wheres);
			StringBuilder sql = SqlUtil.createSelectSql(null, TABLE_NAME, wheres);
			String string = sql.append(" limit 1").toString();
			logger.debug("selectOne:{}, sql:{}", userQuery, string);
			return string;
		}

		public static final String selectSome(UserQuery userQuery) {
			List<String> wheres = new LinkedList<>();
			wheresAll(userQuery, wheres);
			StringBuilder sql = SqlUtil.createSelectSql(null, TABLE_NAME, wheres);
			SqlUtil.initPageQuery(userQuery);
			if (userQuery.getPageSize() > 0 && userQuery.getPage() > 0) {
				sql.append(" limit #{off},#{len}");
			}
			String string = sql.toString();
			logger.debug("selectSome:{}, sql:{}", userQuery, string);
			return string;
		}

		public static final String update(UserPo userPo) {
			userPo.setCreateTime(null);
			userPo.setUpdateTime(new Date());
			if (checkUpload(userPo) != null) {
				return "update " + TABLE_NAME + " set user_id=#{userId} where false";
			}
			List<String> sets = new LinkedList<>();
			wheresAll(userPo, sets);
			List<String> wheres = new LinkedList<>();
			wheresKey(userPo, wheres);
			String string = SqlUtil.createUpdateSql(TABLE_NAME, sets, wheres).append(" limit 1").toString();
			logger.debug("update:{}, sql:{}", userPo, string);
			return string;
		}

		private static final void wheresAll(UserPo userPo, List<String> wheres) {
			if (userPo.getUserId() > 0) {
				wheres.add(userId);
			}
			if (!StringUtil.isBlank(userPo.getUserName())) {
				wheres.add(userName);
			}
			if (!StringUtil.isBlank(userPo.getUserPassword())) {
				wheres.add(userPassword);
			}
			if (userPo.getCreateTime() != null) {
				wheres.add(createTime);
			}
			if (userPo.getUpdateTime() != null) {
				wheres.add(updateTime);
			}
		}

		private static final void wheresKey(UserPo userPo, List<String> wheres) {
			if (userPo.getUserId() > 0) {
				wheres.add(userId);
			}
			if (!StringUtil.isBlank(userPo.getUserName())) {
				wheres.add(userName);
			}
		}

		public static String checkInsert(UserPo userPo) {
			if (StringUtil.isBlank(userPo.getUserName())) {
				return "用户名不得为空";
			}
			if (StringUtil.isBlank(userPo.getUserPassword())) {
				return "用户密码不得为空";
			}
			return null;
		}

		public static String checkUpload(UserPo userPo) {
			if (userPo.getUserId() < 1 && StringUtil.isBlank(userPo.getUserName())) {
				return "用户id或者用户名不得为空";
			}
			return null;
		}
	}
}
