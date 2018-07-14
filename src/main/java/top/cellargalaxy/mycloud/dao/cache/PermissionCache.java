package top.cellargalaxy.mycloud.dao.cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;
import top.cellargalaxy.mycloud.dao.PermissionDao;
import top.cellargalaxy.mycloud.dao.db.PermissionMapper;
import top.cellargalaxy.mycloud.model.po.PermissionPo;
import top.cellargalaxy.mycloud.model.query.PermissionQuery;

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
	public int delete(PermissionPo permissionPo) {
		return permissionMapper.delete(permissionPo);
	}

	@Cacheable(key = "#p0.permissionId", condition = "#p0.permissionId>0")
	public PermissionPo selectOne(PermissionQuery permissionQuery) {
		return permissionMapper.selectOne(permissionQuery);
	}

	public List<PermissionPo> selectSome(PermissionQuery permissionQuery) {
		return permissionMapper.selectSome(permissionQuery);
	}

	@CacheEvict(key = "#p0.permissionId")
	public int upload(PermissionPo permissionPo) {
		return permissionMapper.upload(permissionPo);
	}
}
