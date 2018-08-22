package top.cellargalaxy.mycloud.dao;

import top.cellargalaxy.mycloud.model.bo.PermissionBo;
import top.cellargalaxy.mycloud.model.po.PermissionPo;
import top.cellargalaxy.mycloud.model.query.PermissionQuery;
import top.cellargalaxy.mycloud.util.StringUtil;

/**
 * @author cellargalaxy
 * @time 2018/7/12
 */
public interface PermissionDao extends AbstractDao<PermissionPo, PermissionBo, PermissionQuery> {
	String TABLE_NAME = "permission";

	static String checkInsert(PermissionPo permissionPo) {
		if (permissionPo.getPermissionId() < 1) {
			return "权限id不得为空";
		}
		if (StringUtil.isBlank(permissionPo.getPermissionName())) {
			return "权限描述不得为空";
		}
		return null;
	}

	static String checkUpdate(PermissionPo permissionPo) {
		if (permissionPo.getPermissionId() < 1) {
			return "权限id不得为空";
		}
		return null;
	}
}
