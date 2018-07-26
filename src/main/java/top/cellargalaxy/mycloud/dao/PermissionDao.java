package top.cellargalaxy.mycloud.dao;

import top.cellargalaxy.mycloud.model.po.PermissionPo;
import top.cellargalaxy.mycloud.model.query.PermissionQuery;
import top.cellargalaxy.mycloud.model.bo.PermissionBo;
import top.cellargalaxy.mycloud.util.StringUtil;

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

	int selectCount(PermissionQuery permissionQuery);

	int upldate(PermissionPo permissionPo);

	static String checkInsert(PermissionPo permissionPo) {
		if (permissionPo.getPermissionId() < 1) {
			return "权限id不得为空";
		}
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
