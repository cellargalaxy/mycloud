package top.cellargalaxy.mycloud.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.cellargalaxy.mycloud.dao.UserDao;
import top.cellargalaxy.mycloud.model.bo.AuthorizationBo;
import top.cellargalaxy.mycloud.model.bo.UserBo;
import top.cellargalaxy.mycloud.model.po.Permission;
import top.cellargalaxy.mycloud.model.po.UserPo;
import top.cellargalaxy.mycloud.model.query.AuthorizationQuery;
import top.cellargalaxy.mycloud.model.vo.UserVo;
import top.cellargalaxy.mycloud.service.AuthorizationService;
import top.cellargalaxy.mycloud.service.UserService;
import top.cellargalaxy.mycloud.util.ServiceUtil;
import top.cellargalaxy.mycloud.util.StringUtil;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author cellargalaxy
 * @time 2018/7/17
 */
@Transactional
@Service
public class UserServiceImpl implements UserService {
	private static final String NAME = "账号";
	@Autowired
	private UserDao userDao;
	@Autowired
	private AuthorizationService authorizationService;

	@Override
	public String addUser(UserPo userPo) {
		if (userPo.getPassword() != null) {
			userPo.setPassword(StringUtil.PASSWORD_HEAD + StringUtil.encoderPassword(userPo.getPassword()));
		}
		return ServiceUtil.add(userPo, NAME, this::checkAddUser, userDao);
	}

	@Override
	public String removeUser(UserPo userPo) {
		return ServiceUtil.remove(userPo, NAME, userDao);
	}

	@Override
	public String changeUser(UserPo userPo) {
		if (userPo.getPassword() != null) {
			userPo.setPassword(StringUtil.PASSWORD_HEAD + StringUtil.encoderPassword(userPo.getPassword()));
		}
		return ServiceUtil.change(userPo, NAME, this::checkChangeUser, userDao);
	}

	@Override
	public String changeUser(UserPo userPo, UserPo newUserPo) {
		if (userPo == null) {
			return "未登录";
		}
		if (userPo.getUserId() != newUserPo.getUserId()) {
			return "不得修改他人信息";
		}
		return ServiceUtil.change(newUserPo, NAME, this::checkChangeUser, userDao);
	}

	@Override
	public String checkAddUser(UserPo userPo) {
		return ServiceUtil.checkAdd(userPo, NAME, UserDao::checkInsert, userDao);
	}

	@Override
	public String checkChangeUser(UserPo userPo) {
		return UserDao.checkUpdate(userPo);
	}

	@Override
	public UserBo getUser(UserPo userPo) {
		return userDao.selectOne(userPo);
	}

	@Override
	public UserVo getUserVo(UserPo userPo) {
		UserBo userBo = userDao.selectOne(userPo);
		return bo2vo(userBo);
	}

	@Override
	public UserBo getUserByUsername(UserPo userPo) {
		String username = userPo.getUsername();

		userPo = new UserPo();
		userPo.setUsername(username);

		return userDao.selectOne(userPo);
	}

	@Override
	public UserVo getUserVoByUsername(UserPo userPo) {
		String username = userPo.getUsername();

		userPo = new UserPo();
		userPo.setUsername(username);

		UserBo userBo = userDao.selectOne(userPo);
		return bo2vo(userBo);
	}

	@Override
	public List<UserBo> listAllUser() {
		return userDao.selectAll();
	}

	@Override
	public List<UserVo> listAllUserVo() {
		List<UserBo> userBos = userDao.selectAll();
		return bo2vo(userBos);
	}

	@Override
	public Permission[] listAllPermission() {
		return Permission.values();
	}

	private List<UserVo> bo2vo(List<UserBo> userBos) {
		if (userBos == null) {
			return null;
		}
		return userBos.stream().map(userBo -> bo2vo(userBo)).collect(Collectors.toList());
	}

	private UserVo bo2vo(UserBo userBo) {
		if (userBo == null) {
			return null;
		}

		AuthorizationQuery authorizationQuery = new AuthorizationQuery();
		authorizationQuery.setUserId(userBo.getUserId());
		List<AuthorizationBo> authorizationBos = authorizationService.listAuthorizationByUserId(authorizationQuery).stream().collect(Collectors.toList());
		if (authorizationBos.size() == 0) {
			authorizationBos.add(AuthorizationBo.GUEST);
		}

		UserVo userVo = new UserVo();
		userVo.setUser(userBo);
		userVo.setAuthorizations(authorizationBos);
		return userVo;
	}
}
