package top.cellargalaxy.mycloud.dao.mapper;

import org.apache.ibatis.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.cellargalaxy.mycloud.dao.UserDao;
import top.cellargalaxy.mycloud.model.bo.UserBo;
import top.cellargalaxy.mycloud.model.po.UserPo;
import top.cellargalaxy.mycloud.model.query.UserQuery;
import top.cellargalaxy.mycloud.util.SqlUtil;
import top.cellargalaxy.mycloud.util.StringUtil;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import static org.apache.ibatis.type.JdbcType.TIMESTAMP;

/**
 * Created by cellargalaxy on 18-7-12.
 */
@Mapper
public interface UserMapper {
	@InsertProvider(type = UserProvider.class, method = "insert")
	@Options(useGeneratedKeys = true, keyProperty = "userId")
	int insert(UserPo userPo);

	@DeleteProvider(type = UserProvider.class, method = "delete")
	int delete(UserQuery userQuery);

	@Results(id = "userResult", value = {
			@Result(property = "userId", column = "user_id", id = true),
			@Result(property = "username", column = "username"),
			@Result(property = "userPassword", column = "user_password"),
			@Result(property = "createTime", column = "create_time", javaType = Date.class, jdbcType = TIMESTAMP),
			@Result(property = "updateTime", column = "update_time", javaType = Date.class, jdbcType = TIMESTAMP)
	})
	@SelectProvider(type = UserProvider.class, method = "selectOne")
	UserBo selectOne(UserQuery userQuery);

	@ResultMap(value = "userResult")
	@SelectProvider(type = UserProvider.class, method = "selectSome")
	List<UserBo> selectSome(UserQuery userQuery);

	@SelectProvider(type = UserProvider.class, method = "selectCount")
	int selectCount(UserQuery userQuery);

	@UpdateProvider(type = UserProvider.class, method = "update")
	int update(UserPo userPo);

	class UserProvider {
		private static final String TABLE_NAME = UserDao.TABLE_NAME;
		private static final String userId = TABLE_NAME + ".user_id=#{userId}";
		private static final String username = TABLE_NAME + ".username=#{username}";
		private static final String userPassword = TABLE_NAME + ".user_password=#{userPassword}";
		private static final String createTime = TABLE_NAME + ".create_time=#{createTime,jdbcType=TIMESTAMP}";
		private static final String updateTime = TABLE_NAME + ".update_time=#{updateTime,jdbcType=TIMESTAMP}";

		public static final String insert(UserPo userPo) {
			init(userPo);
			Date date = new Date();
			userPo.setCreateTime(date);
			userPo.setUpdateTime(date);
			String string = "insert into " + TABLE_NAME + "(username,user_password,create_time,update_time) " +
					"values(#{username},#{userPassword},#{createTime,jdbcType=TIMESTAMP},#{updateTime,jdbcType=TIMESTAMP})";
			return string;
		}

		public static final String delete(UserQuery userQuery) {
			List<String> wheres = new LinkedList<>();
			wheresAll(userQuery, wheres);
			StringBuilder sql = SqlUtil.createDeleteSql(TABLE_NAME, wheres);
			String string = sql.toString();
			return string;
		}

		public static final String selectOne(UserQuery userQuery) {
			List<String> wheres = new LinkedList<>();
			wheresKey(userQuery, wheres);
			StringBuilder sql = SqlUtil.createSelectSql(null, TABLE_NAME, wheres);
			String string = sql.append(" limit 1").toString();
			return string;
		}

		public static final String selectSome(UserQuery userQuery) {
			SqlUtil.initPageQuery(userQuery);
			List<String> wheres = new LinkedList<>();
			wheresAll(userQuery, wheres);
			StringBuilder sql = SqlUtil.createSelectSql(null, TABLE_NAME, wheres);
			String string = sql.append(" limit #{off},#{len}").toString();
			return string;
		}

		public static final String selectCount(UserQuery userQuery) {
			SqlUtil.initPageQuery(userQuery);
			List<String> selects = new LinkedList<>();
			selects.add("count(*)");
			List<String> wheres = new LinkedList<>();
			wheresAll(userQuery, wheres);
			StringBuilder sql = SqlUtil.createSelectSql(selects, TABLE_NAME, wheres);
			String string = sql.toString();
			return string;
		}

		public static final String update(UserPo userPo) {
			init(userPo);
			userPo.setCreateTime(null);
			userPo.setUpdateTime(new Date());
			List<String> sets = new LinkedList<>();
			sets(userPo, sets);
			if (sets.size() == 0) {
				return "update " + TABLE_NAME + " set user_id=#{userId} where false";
			}
			List<String> wheres = new LinkedList<>();
			wheresKey(userPo, wheres);
			String string = SqlUtil.createUpdateSql(TABLE_NAME, sets, wheres).append(" limit 1").toString();
			return string;
		}

		private static final void wheresAll(UserQuery userQuery, List<String> wheres) {
			if (userQuery.getUserId() > 0) {
				wheres.add(userId);
			}
			if (!StringUtil.isBlank(userQuery.getUsername())) {
				wheres.add(username);
			}
			if (!StringUtil.isBlank(userQuery.getUserPassword())) {
				wheres.add(userPassword);
			}
			if (userQuery.getCreateTime() != null) {
				wheres.add(createTime);
			}
			if (userQuery.getUpdateTime() != null) {
				wheres.add(updateTime);
			}
		}

		private static final void wheresKey(UserPo userPo, List<String> wheres) {
			if (userPo.getUserId() > 0) {
				wheres.add(userId);
			} else if (!StringUtil.isBlank(userPo.getUsername())) {
				wheres.add(username);
			}
		}

		private static final void sets(UserPo userPo, List<String> sets) {
			if (!StringUtil.isBlank(userPo.getUsername())) {
				sets.add(username);
			}
			if (!StringUtil.isBlank(userPo.getUserPassword())) {
				sets.add(userPassword);
			}
			if (userPo.getUpdateTime() != null) {
				sets.add(updateTime);
			}
		}

		private static final void init(UserPo userPo) {
			if (userPo.getUsername() != null) {
				userPo.setUsername(userPo.getUsername().trim());
			}
			if (userPo.getUserPassword() != null) {
				userPo.setUserPassword(userPo.getUserPassword().trim());
			}
		}
	}
}
