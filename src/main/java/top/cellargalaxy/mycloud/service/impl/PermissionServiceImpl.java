package top.cellargalaxy.mycloud.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.cellargalaxy.mycloud.dao.PermissionDao;
import top.cellargalaxy.mycloud.model.bo.PermissionBo;
import top.cellargalaxy.mycloud.model.po.PermissionPo;
import top.cellargalaxy.mycloud.model.query.PermissionQuery;
import top.cellargalaxy.mycloud.service.PermissionService;

import java.util.List;

/**
 * @author cellargalaxy
 * @time 2018/7/17
 */
@Service
public class PermissionServiceImpl implements PermissionService {
	@Autowired
	private PermissionDao permissionDao;

	@Override
	public void addPermission(PermissionPo permissionPo) {
		permissionDao.insert(permissionPo);
	}

	@Override
	public void removePermission(PermissionQuery permissionQuery) {
		permissionDao.delete(permissionQuery);
	}

	@Override
	public PermissionBo getPermission(PermissionQuery permissionQuery) {
		return permissionDao.selectOne(permissionQuery);
	}

	@Override
	public List<PermissionBo> listPermission(PermissionQuery permissionQuery) {
		return permissionDao.selectSome(permissionQuery);
	}

	@Override
	public void changePermission(PermissionPo permissionPo) {
		permissionDao.upldate(permissionPo);
	}

	@Override
	public String checkAddPermission(PermissionPo permissionPo) {
		return PermissionDao.checkInsert(permissionPo);
	}

	@Override
	public String checkChangePermission(PermissionPo permissionPo) {
		return PermissionDao.checkUpdate(permissionPo);
	}
}
