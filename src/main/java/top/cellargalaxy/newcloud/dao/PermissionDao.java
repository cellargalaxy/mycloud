package top.cellargalaxy.newcloud.dao;

import top.cellargalaxy.newcloud.model.po.PermissionPo;
import top.cellargalaxy.newcloud.model.query.PermissionQuery;
import top.cellargalaxy.newcloud.model.bo.PermissionBo;
import top.cellargalaxy.newcloud.util.StringUtil;

import java.util.List;

/**
 * @author cellargalaxy
 * @time 2018/7/12
 */
public interface PermissionDao {
	String TABLE_NAME = "permission";

	int insert(PermissionPo permissionPo);

	int delete(PermissionQuery permissionQuery);

	PermissionBo selectOne(PermissionQuery permissionQuery);

	List<PermissionBo> selectSome(PermissionQuery permissionQuery);

	int upldate(PermissionPo permissionPo);

	static String checkInsert(PermissionPo permissionPo) {
		if (StringUtil.isBlank(permissionPo.getPermissionMark())) {
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
