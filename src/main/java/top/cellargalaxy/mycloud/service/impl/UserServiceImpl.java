package top.cellargalaxy.mycloud.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
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
import top.cellargalaxy.mycloud.service.AuthorizationService;
import top.cellargalaxy.mycloud.service.OwnService;
import top.cellargalaxy.mycloud.service.UserService;
import top.cellargalaxy.mycloud.util.SqlUtil;

import java.util.LinkedList;
import java.util.List;

/**
 * @author cellargalaxy
 * @time 2018/7/17
 */
@Transactional
@Service
public class UserServiceImpl implements UserService {
	private Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
	private final String passwordHead = "{bcrypt}";
	private final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
	@Autowired
	private UserDao userDao;
	@Autowired
	private AuthorizationService authorizationService;
	@Autowired
	private OwnService ownService;

	@Override
	public String addUser(UserPo userPo) {
		logger.info("addUser:{}", userPo);
		String string = checkAddUser(userPo);
		if (string != null) {
			return string;
		}
		if (userPo.getUserPassword() != null) {
			userPo.setUserPassword(passwordHead + bCryptPasswordEncoder.encode(userPo.getUserPassword()));
		}
		int i = userDao.insert(userPo);
		if (i == 0) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return "用户空新增";
		}
		return null;
	}

	@Override
	public String removeUser(UserQuery userQuery) {
		logger.info("removeUser:{}", userQuery);
		int i = userDao.delete(userQuery);
		if (i == 0) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
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
	public UserBo getUser(UserPo userPo, UserQuery userQuery) {
		userQuery.setUserId(userPo.getUserId());
		userQuery.setUsername(userPo.getUsername());
		return getUser(userQuery);
	}

	@Override
	public int getUserCount(UserQuery userQuery) {
		logger.info("getUserCount:{}", userQuery);
		return userDao.selectCount(userQuery);
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
		List<AuthorizationBo> authorizationBos = authorizationService.listAuthorization(authorizationQuery);
		return new UserAuthorizationVo(userBo, authorizationBos);
	}

	@Override
	public UserAuthorizationVo getUserAuthorization(UserPo userPo, UserQuery userQuery) {
		userQuery.setUserId(userPo.getUserId());
		userQuery.setUsername(userPo.getUsername());
		return getUserAuthorization(userQuery);
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
			List<AuthorizationBo> authorizationBos = authorizationService.listAuthorization(authorizationQuery);
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
		List<OwnBo> ownBos = ownService.listOwn(ownQuery);
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
			List<OwnBo> ownBos = ownService.listOwn(ownQuery);
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
			userPo.setUserPassword(passwordHead + bCryptPasswordEncoder.encode(userPo.getUserPassword()));
		}
		int i = userDao.update(userPo);
		if (i == 0) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return "用户空更新";
		}
		return null;
	}

	@Override
	public String changeUser(UserPo odUserPo, UserPo newUserPo) {
		if (odUserPo.getUserId() != newUserPo.getUserId()) {
			return "登录账号与被修改账号不相同";
		}
		return changeUser(newUserPo);
	}

	@Override
	public String checkAddUser(UserPo userPo) {
		logger.info("checkAddUser:{}", userPo);
		String string = UserDao.checkInsert(userPo);
		if (string != null) {
			return string;
		}
		UserBo userBo = userDao.selectOne(userPo);
		if (userBo != null) {
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
		UserBo userBo = userDao.selectOne(userPo);
		if (userBo == null) {
			return "账号不存在";
		}
		return null;
	}
}
