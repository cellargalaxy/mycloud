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
	String addUser(UserPo userPo);

	String removeUser(UserPo userPo);

	UserBo getUser(UserPo userPo);

	UserBo getUser(UserPo userPo, UserQuery userQuery);

	int getUserCount(UserQuery userQuery);

	List<UserBo> listUser(UserQuery userQuery);

	UserAuthorizationVo getUserAuthorization(UserQuery userQuery);

	UserAuthorizationVo getUserAuthorization(UserPo userPo, UserQuery userQuery);

	List<UserAuthorizationVo> listUserAuthorization(UserQuery userQuery);

	UserOwnVo getUserOwn(UserQuery userQuery);

	List<UserOwnVo> listUserOwn(UserQuery userQuery);

	String changeUser(UserPo userPo);

	String changeUser(UserPo odUserPo, UserPo newUserPo);

	String checkAddUser(UserPo userPo);

	String checkChangeUser(UserPo userPo);
}
