package top.cellargalaxy.newcloud.dao.cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;
import top.cellargalaxy.newcloud.dao.PermissionDao;
import top.cellargalaxy.newcloud.dao.db.PermissionMapper;
import top.cellargalaxy.newcloud.model.bo.PermissionBo;
import top.cellargalaxy.newcloud.model.po.PermissionPo;
import top.cellargalaxy.newcloud.model.query.PermissionQuery;

import java.util.List;

/**
 * @author cellargalaxy
 * @time 2018/7/13
 */
@Repository
@CacheConfig(cacheNames = "permission")
public class PermissionCache implements PermissionDao {
	@Autowired
	private PermissionMapper permissionMapper;

	@CacheEvict(key = "#p0.permissionId")
	public int insert(PermissionPo permissionPo) {
		return permissionMapper.insert(permissionPo);
	}

	@CacheEvict(key = "#p0.permissionId")
	public int delete(PermissionQuery permissionQuery) {
		return permissionMapper.delete(permissionQuery);
	}

	@Cacheable(key = "#p0.permissionId", condition = "#p0.permissionId>0")
	public PermissionBo selectOne(PermissionQuery permissionQuery) {
		return permissionMapper.selectOne(permissionQuery);
	}

	public List<PermissionBo> selectSome(PermissionQuery permissionQuery) {
		return permissionMapper.selectSome(permissionQuery);
	}

	@CacheEvict(key = "#p0.permissionId")
	public int upldate(PermissionPo permissionPo) {
		return permissionMapper.upldate(permissionPo);
	}


}
