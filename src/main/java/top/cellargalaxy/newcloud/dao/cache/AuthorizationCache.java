package top.cellargalaxy.newcloud.dao.cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;
import top.cellargalaxy.newcloud.dao.AuthorizationDao;
import top.cellargalaxy.newcloud.dao.db.AuthorizationMapper;
import top.cellargalaxy.newcloud.model.bo.AuthorizationBo;
import top.cellargalaxy.newcloud.model.po.AuthorizationPo;
import top.cellargalaxy.newcloud.model.query.AuthorizationQuery;

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

	@CacheEvict(key = "#p0.authorizationId")
	public int insert(AuthorizationPo authorizationPo) {
		return authorizationMapper.insert(authorizationPo);
	}

	@CacheEvict(key = "#p0.authorizationId")
	public int delete(AuthorizationQuery authorizationQuery) {
		return authorizationMapper.delete(authorizationQuery);
	}

	@Cacheable(key = "#p0.authorizationId", condition = "#p0.authorizationId>0")
	public AuthorizationBo selectOne(AuthorizationQuery authorizationQuery) {
		return authorizationMapper.selectOne(authorizationQuery);
	}

	public List<AuthorizationBo> selectSome(AuthorizationQuery authorizationQuery) {
		return authorizationMapper.selectSome(authorizationQuery);
	}

	@CacheEvict(key = "#p0.authorizationId")
	public int update(AuthorizationPo authorizationPo) {
		return authorizationMapper.update(authorizationPo);
	}
}
