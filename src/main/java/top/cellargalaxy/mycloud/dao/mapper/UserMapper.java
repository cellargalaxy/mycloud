package top.cellargalaxy.mycloud.dao.mapper;

import org.apache.ibatis.annotations.*;
import top.cellargalaxy.mycloud.dao.UserDao;
import top.cellargalaxy.mycloud.model.bo.UserBo;
import top.cellargalaxy.mycloud.model.po.UserPo;
import top.cellargalaxy.mycloud.model.query.UserQuery;
import top.cellargalaxy.mycloud.util.ProviderUtil;
import top.cellargalaxy.mycloud.util.StringUtil;

import java.util.Date;
import java.util.List;
import java.util.Set;

import static org.apache.ibatis.type.JdbcType.TIMESTAMP;

/**
 * Created by cellargalaxy on 18-7-12.
 */
@Mapper
public interface UserMapper {
	@InsertProvider(type = UserProviderUtil.class, method = "insert")
	@Options(useGeneratedKeys = true, keyProperty = "userId")
	int insert(UserPo userPo);

	@DeleteProvider(type = UserProviderUtil.class, method = "delete")
	int delete(UserPo userPo);

	@Results(id = "userResult", value = {
			@Result(property = "userId", column = "user_id", id = true),
			@Result(property = "username", column = "username"),
			@Result(property = "userPassword", column = "user_password"),
			@Result(property = "createTime", column = "create_time", javaType = Date.class, jdbcType = TIMESTAMP),
			@Result(property = "updateTime", column = "update_time", javaType = Date.class, jdbcType = TIMESTAMP)
	})
	@SelectProvider(type = UserProviderUtil.class, method = "selectOne")
	UserBo selectOne(UserPo userPo);

	@ResultMap(value = "userResult")
	@SelectProvider(type = UserProviderUtil.class, method = "selectSome")
	List<UserBo> selectSome(UserQuery userQuery);

	@SelectProvider(type = UserProviderUtil.class, method = "selectCount")
	int selectCount(UserQuery userQuery);

	@UpdateProvider(type = UserProviderUtil.class, method = "update")
	int update(UserPo userPo);

	class UserProviderUtil {
		private String tableName = UserDao.TABLE_NAME;
		private String userId = tableName + ".user_id=#{userId}";
		private String username = tableName + ".username=#{username}";
		private String userPassword = tableName + ".user_password=#{userPassword}";
		private String createTime = tableName + ".create_time=#{createTime,jdbcType=TIMESTAMP}";
		private String updateTime = tableName + ".update_time=#{updateTime,jdbcType=TIMESTAMP}";

		public String insert(UserPo userPo) {
			init(userPo);
			Date date = new Date();
			userPo.setCreateTime(date);
			userPo.setUpdateTime(date);

			String string = "insert into " + tableName + "(username,user_password,create_time,update_time) " +
					"values(#{username},#{userPassword},#{createTime,jdbcType=TIMESTAMP},#{updateTime,jdbcType=TIMESTAMP})";
			return string;
		}

		public String delete(UserPo userPo) {
			return ProviderUtil.delete(tableName, userPo, this::wheresKey).append(" limit 1").toString();
		}

		public String selectOne(UserPo userPo) {
			return ProviderUtil.selectOne(tableName, userPo, this::wheresKey).append(" limit 1").toString();
		}

		public String selectSome(UserQuery userQuery) {
			return ProviderUtil.selectSome(tableName, userQuery, this::wheresAll).append(" limit #{off},#{len}").toString();
		}

		public String selectCount(UserQuery userQuery) {
			return ProviderUtil.selectCount(tableName, userQuery, this::wheresAll).append(" limit #{off},#{len}").toString();
		}

		public String update(UserPo userPo) {
			init(userPo);
			userPo.setCreateTime(null);
			userPo.setUpdateTime(new Date());

			return ProviderUtil.update(tableName, userPo, userId, this::sets, this::wheresKey).append(" limit 1").toString();
		}

		private void wheresAll(UserQuery userQuery, Set<String> wheres) {
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

		private void wheresKey(UserPo userPo, Set<String> wheres) {
			if (userPo.getUserId() > 0) {
				wheres.add(userId);
			} else if (!StringUtil.isBlank(userPo.getUsername())) {
				wheres.add(username);
			}
		}

		private void sets(UserPo userPo, Set<String> sets) {
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

		private void init(UserPo userPo) {
			if (userPo.getUsername() != null) {
				userPo.setUsername(userPo.getUsername().trim());
			}
			if (userPo.getUserPassword() != null) {
				userPo.setUserPassword(userPo.getUserPassword().trim());
			}
		}
	}
}
