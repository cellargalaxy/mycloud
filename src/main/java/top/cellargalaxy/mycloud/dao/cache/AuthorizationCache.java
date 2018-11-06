package top.cellargalaxy.mycloud.dao.cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
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

import java.util.Date;
import java.util.List;

/**
 * @author cellargalaxy
 * @time 2018/7/13
 */
@ConditionalOnBean(AuthorizationMapper.class)
@Repository
@CacheConfig
public class AuthorizationCache implements AuthorizationDao {
	@Autowired
	private AuthorizationMapper authorizationMapper;

	@Cacheable(key = "'AuthorizationCache-selectAll'", cacheNames = "5m")
	public List<AuthorizationBo> selectAll() {
		return authorizationMapper.selectAll();
	}

	//

	@Caching(evict = {
			@CacheEvict(key = "'AuthorizationCache-selectAll'", cacheNames = "5m"),
	})
	public int insert(AuthorizationPo authorizationPo) {
		Date date = new Date();
		authorizationPo.setCreateTime(date);
		authorizationPo.setUpdateTime(date);
		return authorizationMapper.insert(authorizationPo);
	}

	@Caching(evict = {
			@CacheEvict(key = "'AuthorizationCache-selectAll'", cacheNames = "5m"),
	})
	public int delete(AuthorizationPo authorizationPo) {
		return authorizationMapper.delete(authorizationPo);
	}

	@Caching(evict = {
			@CacheEvict(key = "'AuthorizationCache-selectAll'", cacheNames = "5m"),
	})
	public int update(AuthorizationPo authorizationPo) {
		authorizationPo.setCreateTime(null);
		authorizationPo.setUpdateTime(new Date());
		return authorizationMapper.update(authorizationPo);
	}

	//

	@Cacheable(key = "'AuthorizationCache-selectOne-'+#p0", cacheNames = "5m")
	public AuthorizationBo selectOne(AuthorizationPo authorizationPo) {
		return authorizationMapper.selectOne(authorizationPo);
	}

	@Cacheable(key = "'AuthorizationCache-selectPageSome-'+#p0", cacheNames = "5m")
	public List<AuthorizationBo> selectPageSome(AuthorizationQuery authorizationQuery) {
		return authorizationMapper.selectPageSome(authorizationQuery);
	}

	@Cacheable(key = "'AuthorizationCache-selectAllSome-'+#p0", cacheNames = "5m")
	public List<AuthorizationBo> selectAllSome(AuthorizationQuery authorizationQuery) {
		return authorizationMapper.selectAllSome(authorizationQuery);
	}

	@Cacheable(key = "'AuthorizationCache-selectCount-'+#p0", cacheNames = "5m")
	public int selectCount(AuthorizationQuery authorizationQuery) {
		return authorizationMapper.selectCount(authorizationQuery);
	}
}
