package top.cellargalaxy.mycloud.dao;

import top.cellargalaxy.mycloud.model.bo.UserBo;
import top.cellargalaxy.mycloud.model.po.UserPo;
import top.cellargalaxy.mycloud.model.query.UserQuery;
import top.cellargalaxy.mycloud.util.StringUtil;

import java.util.List;

/**
 * Created by cellargalaxy on 18-7-12.
 */
public interface UserDao extends AbstractDao<UserPo, UserBo, UserQuery> {
	String TABLE_NAME = "user";

	static String checkInsert(UserPo userPo) {
		if (StringUtil.isBlank(userPo.getUsername())) {
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
