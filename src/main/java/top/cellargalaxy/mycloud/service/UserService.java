package top.cellargalaxy.mycloud.service;

import top.cellargalaxy.mycloud.model.bo.UserBo;
import top.cellargalaxy.mycloud.model.po.Permission;
import top.cellargalaxy.mycloud.model.po.UserPo;
import top.cellargalaxy.mycloud.model.vo.UserVo;

import java.util.List;

/**
 * @author cellargalaxy
 * @time 2018/7/17
 */
public interface UserService {
	String addUser(UserPo userPo);

	String removeUser(UserPo userPo);

	String changeUser(UserPo userPo);

	String changeUser(UserPo userPo, UserPo newUserPo);

	String checkAddUser(UserPo userPo);

	String checkChangeUser(UserPo userPo);

	UserBo getUser(UserPo userPo);

	UserVo getUserVo(UserPo userPo);

	UserBo getUserByUsername(UserPo userPo);

	UserVo getUserVoByUsername(UserPo userPo);

	List<UserBo> listAllUser();

	List<UserVo> listAllUserVo();

	Permission[] listAllPermission();
}
