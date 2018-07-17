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
			@Result(property = "userName", column = "user_name"),
			@Result(property = "userPassword", column = "user_password"),
			@Result(property = "createTime", column = "create_time", javaType = Date.class, jdbcType = TIMESTAMP),
			@Result(property = "updateTime", column = "update_time", javaType = Date.class, jdbcType = TIMESTAMP)
	})
	@SelectProvider(type = UserProvider.class, method = "selectOne")
	UserBo selectOne(UserQuery userQuery);

	@ResultMap(value = "userResult")
	@SelectProvider(type = UserProvider.class, method = "selectSome")
	List<UserBo> selectSome(UserQuery userQuery);

	@UpdateProvider(type = UserProvider.class, method = "update")
	int update(UserPo userPo);

	class UserProvider {
		private static final Logger logger = LoggerFactory.getLogger(UserProvider.class);
		private static final String TABLE_NAME = UserDao.TABLE_NAME;
		private static final String userId = TABLE_NAME + ".user_id=#{userId}";
		private static final String userName = TABLE_NAME + ".user_name=#{userName}";
		private static final String userPassword = TABLE_NAME + ".user_password=#{userPassword}";
		private static final String createTime = TABLE_NAME + ".create_time=#{createTime,jdbcType=TIMESTAMP}";
		private static final String updateTime = TABLE_NAME + ".update_time=#{updateTime,jdbcType=TIMESTAMP}";

		public static final String insert(UserPo userPo) {
			Date date = new Date();
			userPo.setCreateTime(date);
			userPo.setUpdateTime(date);
			String string = "insert into " + TABLE_NAME + "(user_name,user_password,create_time,update_time) " +
					"values(#{userName},#{userPassword},#{createTime,jdbcType=TIMESTAMP},#{updateTime,jdbcType=TIMESTAMP})";
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
			if (UserDao.checkUpdate(userPo) != null) {
				return "update " + TABLE_NAME + " set user_id=#{userId} where false";
			}
			userPo.setCreateTime(null);
			userPo.setUpdateTime(new Date());
			List<String> sets = new LinkedList<>();
			sets(userPo, sets);
			List<String> wheres = new LinkedList<>();
			wheresKey(userPo, wheres);
			String string = SqlUtil.createUpdateSql(TABLE_NAME, sets, wheres).append(" limit 1").toString();
			logger.debug("update:{}, sql:{}", userPo, string);
			return string;
		}

		private static final void wheresAll(UserQuery userQuery, List<String> wheres) {
			if (userQuery.getUserId() > 0) {
				wheres.add(userId);
			}
			if (!StringUtil.isBlank(userQuery.getUserName())) {
				wheres.add(userName);
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
			}
		}

		private static final void sets(UserPo userPo, List<String> sets) {
			if (!StringUtil.isBlank(userPo.getUserName())) {
				sets.add(userName);
			}
			if (!StringUtil.isBlank(userPo.getUserPassword())) {
				sets.add(userPassword);
			}
			if (userPo.getUpdateTime() != null) {
				sets.add(updateTime);
			}
		}
	}
}
