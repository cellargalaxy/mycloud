package top.cellargalaxy.newcloud.dao;

import org.apache.ibatis.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.cellargalaxy.newcloud.model.po.UserPo;
import top.cellargalaxy.newcloud.model.query.UserQuery;
import top.cellargalaxy.newcloud.util.StringUtil;

import java.util.List;

/**
 * Created by cellargalaxy on 18-7-12.
 */
@Mapper
public interface UserDao {
	@InsertProvider(type = UserDaoProvider.class, method = "insert")
	int insert(UserPo userPo);

	@DeleteProvider(type = UserDaoProvider.class, method = "delete")
	int delete(UserQuery userQuery);

	@SelectProvider(type = UserDaoProvider.class, method = "selectOne")
	UserPo selectOne(UserQuery userQuery);

	@SelectProvider(type = UserDaoProvider.class, method = "selectSome")
	List<UserPo> selectSome(UserQuery userQuery);

	@UpdateProvider(type = UserDaoProvider.class, method = "update")
	int update(UserPo userPo);

	class UserDaoProvider {
		public static final String TABLE_NAME = "user";
		private Logger logger = LoggerFactory.getLogger(this.getClass());
		private static final String userId = "user_id=#{userId}";
		private static final String userName = "user_name=#{userName}";
		private static final String userPassword = "user_password=#{userPassword}";

		public String insert(UserPo userPo) {
			return "";
		}

		public String delete(UserQuery userQuery) {

		}

		public String selectOne(UserQuery userQuery) {

		}

		public String selectSome(UserQuery userQuery) {

		}

		public String update(UserPo userPo) {

		}

		private void wheres(UserPo userPo, List<String> wheres) {

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
			if (userPo.getUserId() < 1) {
				return "用户id不得为空";
			}
			return null;
		}
	}
}
