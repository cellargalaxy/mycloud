package top.cellargalaxy.mycloud.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
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
@Transactional
@Service
public class AuthorizationServiceImpl implements AuthorizationService {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private AuthorizationDao authorizationDao;

	@Override
	public String addAuthorization(AuthorizationPo authorizationPo) {
		logger.info("addAuthorization:{}", authorizationPo);
		String string = checkAddAuthorization(authorizationPo);
		if (string != null) {
			return string;
		}
		int i = authorizationDao.insert(authorizationPo);
		if (i == 0) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return "授权空新增";
		}
		return null;
	}

	@Override
	public String removeAuthorization(AuthorizationPo authorizationPo) {
		logger.info("removeAuthorization:{}", authorizationPo);
		int i = authorizationDao.delete(authorizationPo);
		if (i == 0) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return "授权空删除";
		}
		return null;
	}

	@Override
	public AuthorizationBo getAuthorization(AuthorizationPo authorizationPo) {
		logger.info("getAuthorization:{}", authorizationPo);
		return authorizationDao.selectOne(authorizationPo);
	}

	@Override
	public int getAuthorizationCount(AuthorizationQuery authorizationQuery) {
		logger.info("getAuthorizationCount:{}", authorizationQuery);
		return authorizationDao.selectCount(authorizationQuery);
	}

	@Override
	public List<AuthorizationBo> listAuthorization(AuthorizationQuery authorizationQuery) {
		logger.info("listAuthorization:{}", authorizationQuery);
		return authorizationDao.selectSome(authorizationQuery);
	}

	@Override
	public String changeAuthorization(AuthorizationPo authorizationPo) {
		logger.info("changeAuthorization:{}", authorizationPo);
		String string = checkChangeAuthorization(authorizationPo);
		if (string != null) {
			return string;
		}
		int i = authorizationDao.update(authorizationPo);
		if (i == 0) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return "授权空更新";
		}
		return null;
	}

	@Override
	public String checkAddAuthorization(AuthorizationPo authorizationPo) {
		logger.info("checkAddAuthorization:{}", authorizationPo);
		String string = AuthorizationDao.checkInsert(authorizationPo);
		if (string != null) {
			return string;
		}
		AuthorizationBo authorizationBo = authorizationDao.selectOne(authorizationPo);
		if (authorizationBo != null) {
			return "授权已存在";
		}
		return null;
	}

	@Override
	public String checkChangeAuthorization(AuthorizationPo authorizationPo) {
		logger.info("checkChangeAuthorization:{}", authorizationPo);
		String string = AuthorizationDao.checkUpdate(authorizationPo);
		if (string != null) {
			return string;
		}
		AuthorizationBo authorizationBo = authorizationDao.selectOne(authorizationPo);
		if (authorizationBo == null) {
			return "授权不存在";
		}
		return null;
	}
}
