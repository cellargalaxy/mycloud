package top.cellargalaxy.newcloud.dao;

import top.cellargalaxy.newcloud.model.po.UserPo;
import top.cellargalaxy.newcloud.model.query.UserQuery;
import top.cellargalaxy.newcloud.model.bo.UserBo;
import top.cellargalaxy.newcloud.util.StringUtil;

import java.util.List;

/**
 * Created by cellargalaxy on 18-7-12.
 */
public interface UserDao {
	String TABLE_NAME = "user";

	int insert(UserPo userPo);

	int delete(UserQuery userQuery);

	UserBo selectOne(UserQuery userQuery);

	List<UserBo> selectSome(UserQuery userQuery);

	int update(UserPo userPo);

	static String checkInsert(UserPo userPo) {
		if (StringUtil.isBlank(userPo.getUserName())) {
			return "用户名不得为空";
		}
		if (StringUtil.isBlank(userPo.getUserPassword())) {
			return "用户密码不得为空";
		}
		return null;
	}

	static String checkUpdate(UserPo userPo) {
		if (userPo.getUserId() < 1) {
			return "用户id不得为空";
		}
		return null;
	}
}
