package top.cellargalaxy.mycloud.dao.cache;

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
	@Autowired
	private AuthorizationMapper authorizationMapper;

	@Cacheable(key = "'selectOne'+#p0", condition = "true")
	public AuthorizationBo selectOne(AuthorizationPo authorizationPo) {
		return authorizationMapper.selectOne(authorizationPo);
	}

	@Cacheable(key = "'selectSome'+#p0", condition = "true")
	public List<AuthorizationBo> selectSome(AuthorizationQuery authorizationQuery) {
		return authorizationMapper.selectSome(authorizationQuery);
	}

	@Cacheable(key = "'selectCount'+#p0", condition = "true")
	public int selectCount(AuthorizationQuery authorizationQuery) {
		return authorizationMapper.selectCount(authorizationQuery);
	}

	@Cacheable(key = "'selectAll'", condition = "true")
	public List<AuthorizationBo> selectAll() {
		return authorizationMapper.selectAll();
	}

	//
	@Caching(evict = {
			@CacheEvict(key = "'selectAll'"),
	})
	public int insert(AuthorizationPo authorizationPo) {
		return authorizationMapper.insert(authorizationPo);
	}

	@Caching(evict = {
			@CacheEvict(key = "'selectAll'"),
	})
	public int delete(AuthorizationPo authorizationPo) {
		return authorizationMapper.delete(authorizationPo);
	}

	public int update(AuthorizationPo authorizationPo) {
		return authorizationMapper.update(authorizationPo);
	}
}
