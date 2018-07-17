package top.cellargalaxy.mycloud.service;

import top.cellargalaxy.mycloud.model.bo.PermissionBo;
import top.cellargalaxy.mycloud.model.po.PermissionPo;
import top.cellargalaxy.mycloud.model.query.PermissionQuery;

import java.util.List;

/**
 * @author cellargalaxy
 * @time 2018/7/17
 */
public interface PermissionService {
	void addPermission(PermissionPo permissionPo);

	void removePermission(PermissionQuery permissionQuery);

	PermissionBo getPermission(PermissionQuery permissionQuery);

	List<PermissionBo> listPermission(PermissionQuery permissionQuery);

	void changePermission(PermissionPo permissionPo);

	String checkAddPermission(PermissionPo permissionPo);

	String checkChangePermission(PermissionPo permissionPo);
}
