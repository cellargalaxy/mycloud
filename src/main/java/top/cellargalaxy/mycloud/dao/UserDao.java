package top.cellargalaxy.mycloud.dao;

import top.cellargalaxy.mycloud.model.bo.UserBo;
import top.cellargalaxy.mycloud.model.po.UserPo;
import top.cellargalaxy.mycloud.model.query.UserQuery;
import top.cellargalaxy.mycloud.util.StringUtils;
import top.cellargalaxy.mycloud.util.dao.IDao;

/**
 * @author cellargalaxy
 * @time 2018/10/25
 */
public interface UserDao extends IDao<UserPo, UserBo, UserQuery> {
	String TABLE_NAME = "user";

	static String checkInsert(UserPo userPo) {
		if (StringUtils.isBlank(userPo.getUsername())) {
			return "账号不得为空";
		}
		if (StringUtils.isBlank(userPo.getPassword())) {
			return "密码不得为空";
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
