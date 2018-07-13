package top.cellargalaxy.newcloud.dao;

import top.cellargalaxy.newcloud.model.po.PermissionPo;
import top.cellargalaxy.newcloud.model.query.PermissionQuery;

import java.util.List;

/**
 * @author cellargalaxy
 * @time 2018/7/12
 */
public interface PermissionDao {
	int insert(PermissionPo permissionPo);

	int delete(PermissionPo permissionPo);

	PermissionPo selectOne(PermissionQuery permissionQuery);

	List<PermissionPo> selectSome(PermissionQuery permissionQuery);

	int upload(PermissionPo permissionPo);

}
