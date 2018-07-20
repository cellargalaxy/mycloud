package top.cellargalaxy.mycloud.service;

import top.cellargalaxy.mycloud.model.bo.PermissionBo;
import top.cellargalaxy.mycloud.model.po.PermissionPo;
import top.cellargalaxy.mycloud.model.query.PermissionQuery;
import top.cellargalaxy.mycloud.model.vo.PermissionAuthorizationVo;

import java.util.List;

/**
 * @author cellargalaxy
 * @time 2018/7/17
 */
public interface PermissionService {
	String addPermission(PermissionPo permissionPo);

	String removePermission(PermissionQuery permissionQuery);

	PermissionBo getPermission(PermissionQuery permissionQuery);

	List<PermissionBo> listPermission(PermissionQuery permissionQuery);

	PermissionAuthorizationVo getPermissionAuthorization(PermissionQuery permissionQuery);

	List<PermissionAuthorizationVo> listPermissionAuthorization(PermissionQuery permissionQuery);

	String changePermission(PermissionPo permissionPo);

	String checkAddPermission(PermissionPo permissionPo);

	String checkChangePermission(PermissionPo permissionPo);
}
