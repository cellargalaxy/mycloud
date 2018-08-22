package top.cellargalaxy.mycloud.dao.cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Repository;
import top.cellargalaxy.mycloud.dao.PermissionDao;
import top.cellargalaxy.mycloud.dao.mapper.PermissionMapper;
import top.cellargalaxy.mycloud.model.bo.PermissionBo;
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

	@Cacheable(key = "'selectOne'+#p0", condition = "true")
	public PermissionBo selectOne(PermissionPo permissionPo) {
		return permissionMapper.selectOne(permissionPo);
	}

	@Cacheable(key = "'selectSome'+#p0", condition = "true")
	public List<PermissionBo> selectSome(PermissionQuery permissionQuery) {
		return permissionMapper.selectSome(permissionQuery);
	}

	@Cacheable(key = "'selectCount'+#p0", condition = "true")
	public int selectCount(PermissionQuery permissionQuery) {
		return permissionMapper.selectCount(permissionQuery);
	}

	@Cacheable(key = "'selectAll'", condition = "true")
	public List<PermissionBo> selectAll() {
		return permissionMapper.selectAll();
	}

	//
	@Caching(evict = {
			@CacheEvict(key = "'selectAll'"),
	})
	public int insert(PermissionPo permissionPo) {
		return permissionMapper.insert(permissionPo);
	}

	@Caching(evict = {
			@CacheEvict(key = "'selectAll'"),
	})
	public int delete(PermissionPo permissionPo) {
		return permissionMapper.delete(permissionPo);
	}

	public int update(PermissionPo permissionPo) {
		return permissionMapper.update(permissionPo);
	}


}
