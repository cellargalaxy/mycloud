package top.cellargalaxy.mycloud.dao.cache;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private PermissionMapper permissionMapper;

	@Cacheable(key = "'selectOne'+(#p0.permissionName!=null?#p0.permissionName:#p0.permissionId)", condition = "true")
	public PermissionBo selectOne(PermissionPo permissionPo) {
		logger.info("selectOne:{}", permissionPo);
		return permissionMapper.selectOne(permissionPo);
	}

	@Cacheable(key = "'selectSome'+#p0", condition = "true")
	public List<PermissionBo> selectSome(PermissionQuery permissionQuery) {
		logger.info("selectSome:{}", permissionQuery);
		return permissionMapper.selectSome(permissionQuery);
	}

	@Cacheable(key = "'selectCount'+#p0", condition = "true")
	public int selectCount(PermissionQuery permissionQuery) {
		logger.info("selectCount:{}", permissionQuery);
		return permissionMapper.selectCount(permissionQuery);
	}

	@Cacheable(key = "'selectAll'", condition = "true")
	public List<PermissionBo> selectAll() {
		logger.info("selectAll");
		return permissionMapper.selectAll();
	}

	//
	@Caching(evict = {
			@CacheEvict(key = "'selectOne'+#p0.permissionName"),
			@CacheEvict(key = "'selectOne'+#p0.permissionId"),
			@CacheEvict(key = "'selectAll'"),
	})
	public int insert(PermissionPo permissionPo) {
		logger.info("insert:{}", permissionPo);
		return permissionMapper.insert(permissionPo);
	}

	@Caching(evict = {
			@CacheEvict(key = "'selectOne'+#p0.permissionName"),
			@CacheEvict(key = "'selectOne'+#p0.permissionId"),
			@CacheEvict(key = "'selectAll'"),
	})
	public int delete(PermissionPo permissionPo) {
		logger.info("delete:{}", permissionPo);
		return permissionMapper.delete(permissionPo);
	}

	@Caching(evict = {
			@CacheEvict(key = "'selectOne'+#p0.permissionName"),
			@CacheEvict(key = "'selectOne'+#p0.permissionId"),
			@CacheEvict(key = "'selectAll'"),
	})
	public int update(PermissionPo permissionPo) {
		logger.info("update:{}", permissionPo);
		return permissionMapper.update(permissionPo);
	}


}
