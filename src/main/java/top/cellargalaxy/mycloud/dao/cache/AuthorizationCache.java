package top.cellargalaxy.mycloud.dao.cache;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Repository;
import top.cellargalaxy.mycloud.dao.AuthorizationDao;
import top.cellargalaxy.mycloud.dao.mapper.AuthorizationMapper;
import top.cellargalaxy.mycloud.model.bo.AuthorizationBo;
import top.cellargalaxy.mycloud.model.po.AuthorizationPo;
import top.cellargalaxy.mycloud.model.query.AuthorizationQuery;

import java.util.List;

/**
 * @author cellargalaxy
 * @time 2018/7/13
 */
@Repository
@CacheConfig(cacheNames = "authorization")
public class AuthorizationCache implements AuthorizationDao {
	private Logger logger = LoggerFactory.getLogger(AuthorizationCache.class);
	@Autowired
	private AuthorizationMapper authorizationMapper;

	@Cacheable(key = "'selectOne'+(#p0.authorizationId>0?#p0.authorizationId:(#p0.userId+'-'+#p0.permissionId))", condition = "true")
	public AuthorizationBo selectOne(AuthorizationPo authorizationPo) {
		logger.info("selectOne:{}", authorizationPo);
		return authorizationMapper.selectOne(authorizationPo);
	}

	@Cacheable(key = "'selectSome'+#p0", condition = "true")
	public List<AuthorizationBo> selectSome(AuthorizationQuery authorizationQuery) {
		logger.info("selectSome:{}", authorizationQuery);
		return authorizationMapper.selectSome(authorizationQuery);
	}

	@Cacheable(key = "'selectCount'+#p0", condition = "true")
	public int selectCount(AuthorizationQuery authorizationQuery) {
		logger.info("selectCount:{}", authorizationQuery);
		return authorizationMapper.selectCount(authorizationQuery);
	}

	@Cacheable(key = "'selectAll'", condition = "true")
	public List<AuthorizationBo> selectAll() {
		logger.info("selectAll");
		return authorizationMapper.selectAll();
	}

	//
	@Caching(evict = {
			@CacheEvict(key = "'selectAll'"),
	})
	public int insert(AuthorizationPo authorizationPo) {
		logger.info("insert:{}", authorizationPo);
		return authorizationMapper.insert(authorizationPo);
	}

	@Caching(evict = {
			@CacheEvict(key = "'selectOne'+#p0.authorizationId"),
			@CacheEvict(key = "'selectOne'+(#p0.userId+'-'+#p0.permissionId)"),
			@CacheEvict(key = "'selectAll'"),
	})
	public int delete(AuthorizationPo authorizationPo) {
		logger.info("delete:{}", authorizationPo);
		return authorizationMapper.delete(authorizationPo);
	}

	@Caching(evict = {
			@CacheEvict(key = "'selectOne'+#p0.authorizationId"),
			@CacheEvict(key = "'selectOne'+(#p0.userId+'-'+#p0.permissionId)"),
			@CacheEvict(key = "'selectAll'"),
	})
	public int update(AuthorizationPo authorizationPo) {
		logger.info("update:{}", authorizationPo);
		return authorizationMapper.update(authorizationPo);
	}
}
