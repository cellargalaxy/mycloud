package top.cellargalaxy.mycloud.service;

import top.cellargalaxy.mycloud.model.bo.UserBo;
import top.cellargalaxy.mycloud.model.po.UserPo;
import top.cellargalaxy.mycloud.model.query.UserQuery;
import top.cellargalaxy.mycloud.model.vo.UserAuthorizationVo;
import top.cellargalaxy.mycloud.model.vo.UserOwnVo;

import java.util.List;

/**
 * @author cellargalaxy
 * @time 2018/7/17
 */
public interface UserService {
	void addUser(UserPo userPo);

	void removeUser(UserQuery userQuery);

	UserBo getUser(UserQuery userQuery);

	List<UserBo> listUser(UserQuery userQuery);

	UserAuthorizationVo getUserAuthorization(UserQuery userQuery);

	List<UserAuthorizationVo> listUserAuthorization(UserQuery userQuery);

	UserOwnVo getUserOwn(UserQuery userQuery);

	List<UserOwnVo> listUserOwn(UserQuery userQuery);

	void changeUser(UserPo userPo);

	String checkAddUser(UserPo userPo);

	String checkChangeUser(UserPo userPo);
}
