package top.cellargalaxy.mycloud.service.impl;

import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.cellargalaxy.mycloud.dao.AuthorizationDao;
import top.cellargalaxy.mycloud.dao.OwnDao;
import top.cellargalaxy.mycloud.dao.UserDao;
import top.cellargalaxy.mycloud.model.bo.AuthorizationBo;
import top.cellargalaxy.mycloud.model.bo.OwnBo;
import top.cellargalaxy.mycloud.model.bo.UserBo;
import top.cellargalaxy.mycloud.model.po.UserPo;
import top.cellargalaxy.mycloud.model.query.AuthorizationQuery;
import top.cellargalaxy.mycloud.model.query.OwnQuery;
import top.cellargalaxy.mycloud.model.query.UserQuery;
import top.cellargalaxy.mycloud.model.vo.UserAuthorizationVo;
import top.cellargalaxy.mycloud.model.vo.UserOwnVo;
import top.cellargalaxy.mycloud.service.UserService;
import top.cellargalaxy.mycloud.util.SqlUtil;

import java.util.LinkedList;
import java.util.List;

/**
 * @author cellargalaxy
 * @time 2018/7/17
 */
@Service
public class UserServiceImpl implements UserService {
	private Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
	@Autowired
	private UserDao userDao;
	@Autowired
	private AuthorizationDao authorizationDao;
	@Autowired
	private OwnDao ownDao;

	@Override
	public UserPo login(UserQuery userQuery) {
		UserPo userPo = userDao.selectOne(userQuery);
		if (userPo == null || !userPo.getUserPassword().equals(DigestUtils.md5Hex(userQuery.getUserPassword()))) {
			return null;
		}
		return userPo;
	}

	@Override
	public String addUser(UserPo userPo) {
		logger.info("addUser:{}", userPo);
		String string = checkAddUser(userPo);
		if (string != null) {
			return string;
		}
		if (userPo.getUserPassword() != null) {
			userPo.setUserPassword(DigestUtils.md5Hex(userPo.getUserPassword()));
		}
		int i = userDao.insert(userPo);
		if (i == 0) {
			return "用户空新增";
		}
		return null;
	}

	@Override
	public String removeUser(UserQuery userQuery) {
		logger.info("removeUser:{}", userQuery);
		int i = userDao.delete(userQuery);
		if (i == 0) {
			return "用户空删除";
		}
		return null;
	}

	@Override
	public UserBo getUser(UserQuery userQuery) {
		logger.info("getUser:{}", userQuery);
		return userDao.selectOne(userQuery);
	}

	@Override
	public List<UserBo> listUser(UserQuery userQuery) {
		logger.info("listUser:{}", userQuery);
		return userDao.selectSome(userQuery);
	}

	@Override
	public UserAuthorizationVo getUserAuthorization(UserQuery userQuery) {
		logger.info("getUserAuthorization:{}", userQuery);
		UserBo userBo = userDao.selectOne(userQuery);
		if (userBo == null) {
			return null;
		}
		AuthorizationQuery authorizationQuery = new AuthorizationQuery();
		authorizationQuery.setUserId(userBo.getUserId());
		List<AuthorizationBo> authorizationBos = authorizationDao.selectSome(authorizationQuery);
		return new UserAuthorizationVo(userBo, authorizationBos);
	}

	@Override
	public List<UserAuthorizationVo> listUserAuthorization(UserQuery userQuery) {
		logger.info("listUserAuthorization:{}", userQuery);
		List<UserBo> userBos = userDao.selectSome(userQuery);
		if (userBos == null) {
			return null;
		}
		List<UserAuthorizationVo> userAuthorizationVos = new LinkedList<>();
		for (UserBo userBo : userBos) {
			AuthorizationQuery authorizationQuery = new AuthorizationQuery();
			authorizationQuery.setUserId(userBo.getUserId());
			authorizationQuery.setPage(1);
			authorizationQuery.setPageSize(SqlUtil.MAX_PAGE_SIZE);
			List<AuthorizationBo> authorizationBos = authorizationDao.selectSome(authorizationQuery);
			userAuthorizationVos.add(new UserAuthorizationVo(userBo, authorizationBos));
		}
		return userAuthorizationVos;
	}

	@Override
	public UserOwnVo getUserOwn(UserQuery userQuery) {
		logger.info("getUserOwn:{}", userQuery);
		UserBo userBo = userDao.selectOne(userQuery);
		if (userBo == null) {
			return null;
		}
		OwnQuery ownQuery = new OwnQuery();
		ownQuery.setUserId(userBo.getUserId());
		List<OwnBo> ownBos = ownDao.selectSome(ownQuery);
		return new UserOwnVo(userBo, ownBos);
	}

	@Override
	public List<UserOwnVo> listUserOwn(UserQuery userQuery) {
		logger.info("listUserOwn:{}", userQuery);
		List<UserBo> userBos = userDao.selectSome(userQuery);
		if (userBos == null) {
			return null;
		}
		List<UserOwnVo> userOwnVos = new LinkedList<>();
		for (UserBo userBo : userBos) {
			OwnQuery ownQuery = new OwnQuery();
			ownQuery.setUserId(userBo.getUserId());
			ownQuery.setPage(1);
			ownQuery.setPageSize(SqlUtil.MAX_PAGE_SIZE);
			List<OwnBo> ownBos = ownDao.selectSome(ownQuery);
			userOwnVos.add(new UserOwnVo(userBo, ownBos));
		}
		return userOwnVos;
	}

	@Override
	public String changeUser(UserPo userPo) {
		logger.info("changeUser:{}", userPo);
		String string = checkChangeUser(userPo);
		if (string != null) {
			return string;
		}
		if (userPo.getUserPassword() != null) {
			userPo.setUserPassword(DigestUtils.md5Hex(userPo.getUserPassword()));
		}
		int i = userDao.update(userPo);
		if (i == 0) {
			return "用户空更新";
		}
		return null;
	}

	@Override
	public String checkAddUser(UserPo userPo) {
		logger.info("checkAddUser:{}", userPo);
		String string = UserDao.checkInsert(userPo);
		if (string != null) {
			return string;
		}
		UserQuery userQuery = new UserQuery();
		userQuery.setUserId(userPo.getUserId());
		userQuery.setUsername(userPo.getUsername());
		UserPo user = userDao.selectOne(userQuery);
		if (user != null) {
			return "账号已存在";
		}
		return null;
	}

	@Override
	public String checkChangeUser(UserPo userPo) {
		logger.info("checkChangeUser:{}", userPo);
		String string = UserDao.checkUpdate(userPo);
		if (string != null) {
			return string;
		}
		UserQuery userQuery = new UserQuery();
		userQuery.setUserId(userPo.getUserId());
		userQuery.setUsername(userPo.getUsername());
		UserPo user = userDao.selectOne(userQuery);
		if (user == null) {
			return "账号不存在";
		}
		return null;
	}
}
