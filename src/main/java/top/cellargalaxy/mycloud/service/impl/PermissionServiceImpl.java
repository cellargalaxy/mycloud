package top.cellargalaxy.mycloud.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import top.cellargalaxy.mycloud.dao.PermissionDao;
import top.cellargalaxy.mycloud.model.bo.AuthorizationBo;
import top.cellargalaxy.mycloud.model.bo.PermissionBo;
import top.cellargalaxy.mycloud.model.po.PermissionPo;
import top.cellargalaxy.mycloud.model.query.AuthorizationQuery;
import top.cellargalaxy.mycloud.model.query.PermissionQuery;
import top.cellargalaxy.mycloud.model.vo.PermissionAuthorizationVo;
import top.cellargalaxy.mycloud.service.AuthorizationService;
import top.cellargalaxy.mycloud.service.PermissionService;
import top.cellargalaxy.mycloud.util.SqlUtil;

import java.util.LinkedList;
import java.util.List;

/**
 * @author cellargalaxy
 * @time 2018/7/17
 */
@Transactional
@Service
public class PermissionServiceImpl implements PermissionService {
	private Logger logger = LoggerFactory.getLogger(PermissionServiceImpl.class);
	@Autowired
	private PermissionDao permissionDao;
	@Autowired
	private AuthorizationService authorizationService;

	@Override
	public String addPermission(PermissionPo permissionPo) {
		logger.info("addPermission:{}", permissionPo);
		String string = checkAddPermission(permissionPo);
		if (string != null) {
			return string;
		}
		int i = permissionDao.insert(permissionPo);
		if (i == 0) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return "权限空新增";
		}
		return null;
	}

	@Override
	public String removePermission(PermissionQuery permissionQuery) {
		logger.info("removePermission:{}", permissionQuery);
		int i = permissionDao.delete(permissionQuery);
		if (i == 0) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return "权限空删除";
		}
		return null;
	}

	@Override
	public PermissionBo getPermission(PermissionQuery permissionQuery) {
		logger.info("getPermission:{}", permissionQuery);
		return permissionDao.selectOne(permissionQuery);
	}

	@Override
	public int getPermissionCount(PermissionQuery permissionQuery) {
		logger.info("getPermissionCount:{}", permissionQuery);
		return permissionDao.selectCount(permissionQuery);
	}

	@Override
	public List<PermissionBo> listPermission(PermissionQuery permissionQuery) {
		logger.info("listPermission:{}", permissionQuery);
		return permissionDao.selectSome(permissionQuery);
	}

	@Override
	public PermissionAuthorizationVo getPermissionAuthorization(PermissionQuery permissionQuery) {
		logger.info("getPermissionAuthorization:{}", permissionQuery);
		PermissionBo permissionBo = permissionDao.selectOne(permissionQuery);
		if (permissionBo == null) {
			return null;
		}
		AuthorizationQuery authorizationQuery = new AuthorizationQuery();
		authorizationQuery.setPermissionId(permissionBo.getPermissionId());
		List<AuthorizationBo> authorizationBos = authorizationService.listAuthorization(authorizationQuery);
		return new PermissionAuthorizationVo(permissionBo, authorizationBos);
	}

	@Override
	public List<PermissionAuthorizationVo> listPermissionAuthorization(PermissionQuery permissionQuery) {
		logger.info("listPermissionAuthorization:{}", permissionQuery);
		List<PermissionBo> permissionBos = permissionDao.selectSome(permissionQuery);
		if (permissionBos == null) {
			return null;
		}
		List<PermissionAuthorizationVo> permissionAuthorizationVos = new LinkedList<>();
		for (PermissionBo permissionBo : permissionBos) {
			AuthorizationQuery authorizationQuery = new AuthorizationQuery();
			authorizationQuery.setPermissionId(permissionBo.getPermissionId());
			authorizationQuery.setPage(1);
			authorizationQuery.setPageSize(SqlUtil.MAX_PAGE_SIZE);
			List<AuthorizationBo> authorizationBos = authorizationService.listAuthorization(authorizationQuery);
			permissionAuthorizationVos.add(new PermissionAuthorizationVo(permissionBo, authorizationBos));
		}
		return permissionAuthorizationVos;
	}

	@Override
	public String changePermission(PermissionPo permissionPo) {
		logger.info("changePermission:{}", permissionPo);
		String string = checkChangePermission(permissionPo);
		if (string != null) {
			return string;
		}
		int i = permissionDao.update(permissionPo);
		if (i == 0) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return "权限空更新";
		}
		return null;
	}

	@Override
	public String checkAddPermission(PermissionPo permissionPo) {
		logger.info("checkAddPermission:{}", permissionPo);
		String string = PermissionDao.checkInsert(permissionPo);
		if (string != null) {
			return string;
		}
		PermissionBo permissionBo = permissionDao.selectOne(permissionPo);
		if (permissionBo != null) {
			return "权限已存在";
		}
		return null;
	}

	@Override
	public String checkChangePermission(PermissionPo permissionPo) {
		logger.info("checkChangePermission:{}", permissionPo);
		String string = PermissionDao.checkUpdate(permissionPo);
		if (string != null) {
			return string;
		}
		PermissionBo permissionBo = permissionDao.selectOne(permissionPo);
		if (permissionBo == null) {
			return "权限不存在";
		}
		return null;
	}
}
