package top.cellargalaxy.mycloud.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.cellargalaxy.mycloud.dao.AuthorizationDao;
import top.cellargalaxy.mycloud.model.bo.AuthorizationBo;
import top.cellargalaxy.mycloud.model.po.AuthorizationPo;
import top.cellargalaxy.mycloud.model.query.AuthorizationQuery;
import top.cellargalaxy.mycloud.service.AuthorizationService;

import java.util.List;

/**
 * @author cellargalaxy
 * @time 2018/7/17
 */
@Service
public class AuthorizationServiceImpl implements AuthorizationService {
	@Autowired
	private AuthorizationDao authorizationDao;

	@Override
	public void addAuthorization(AuthorizationPo authorizationPo) {
		authorizationDao.insert(authorizationPo);
	}

	@Override
	public void removeAuthorization(AuthorizationQuery authorizationQuery) {
		authorizationDao.delete(authorizationQuery);
	}

	@Override
	public AuthorizationBo getAuthorization(AuthorizationQuery authorizationQuery) {
		return authorizationDao.selectOne(authorizationQuery);
	}

	@Override
	public List<AuthorizationBo> listAuthorization(AuthorizationQuery authorizationQuery) {
		return authorizationDao.selectSome(authorizationQuery);
	}

	@Override
	public void changeAuthorization(AuthorizationPo authorizationPo) {
		authorizationDao.update(authorizationPo);
	}

	@Override
	public String checkAddAuthorization(AuthorizationPo authorizationPo) {
		return AuthorizationDao.checkInsert(authorizationPo);
	}

	@Override
	public String checkChangeAuthorization(AuthorizationPo authorizationPo) {
		return AuthorizationDao.checkUpdate(authorizationPo);
	}
}
