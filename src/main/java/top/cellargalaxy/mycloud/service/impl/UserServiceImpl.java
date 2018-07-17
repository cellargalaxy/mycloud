package top.cellargalaxy.mycloud.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.cellargalaxy.mycloud.dao.UserDao;
import top.cellargalaxy.mycloud.model.bo.UserBo;
import top.cellargalaxy.mycloud.model.po.UserPo;
import top.cellargalaxy.mycloud.model.query.UserQuery;
import top.cellargalaxy.mycloud.service.UserService;

import java.util.List;

/**
 * @author cellargalaxy
 * @time 2018/7/17
 */
@Service
public class UserServiceImpl implements UserService {
	@Autowired
	private UserDao userDao;

	@Override
	public void addUser(UserPo userPo) {
		userDao.insert(userPo);
	}

	@Override
	public void removeUser(UserQuery userQuery) {
		userDao.delete(userQuery);
	}

	@Override
	public UserBo getUser(UserQuery userQuery) {
		return userDao.selectOne(userQuery);
	}

	@Override
	public List<UserBo> listUser(UserQuery userQuery) {
		return userDao.selectSome(userQuery);
	}

	@Override
	public void changeUser(UserPo userPo) {
		userDao.update(userPo);
	}

	@Override
	public String checkAddUser(UserPo userPo) {
		return UserDao.checkInsert(userPo);
	}

	@Override
	public String checkChangeUser(UserPo userPo) {
		return UserDao.checkUpdate(userPo);
	}
}
