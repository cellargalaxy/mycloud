package top.cellargalaxy.mycloud.dao.cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Repository;
import top.cellargalaxy.mycloud.dao.OwnDao;
import top.cellargalaxy.mycloud.dao.mapper.OwnMapper;
import top.cellargalaxy.mycloud.model.bo.OwnBo;
import top.cellargalaxy.mycloud.model.po.OwnPo;
import top.cellargalaxy.mycloud.model.query.OwnQuery;

import java.util.List;

/**
 * @author cellargalaxy
 * @time 2018/7/16
 */
@Repository
@CacheConfig(cacheNames = "own")
public class OwnCache implements OwnDao {
	@Autowired
	private OwnMapper ownMapper;

	@Cacheable(key = "'selectOne'+#p0", condition = "true")
	public OwnBo selectOne(OwnPo ownPo) {
		return ownMapper.selectOne(ownPo);
	}

	@Cacheable(key = "'selectSome'+#p0", condition = "true")
	public List<OwnBo> selectSome(OwnQuery ownQuery) {
		return ownMapper.selectSome(ownQuery);
	}

	@Cacheable(key = "'selectCount'+#p0", condition = "true")
	public int selectCount(OwnQuery ownQuery) {
		return ownMapper.selectCount(ownQuery);
	}

	@Cacheable(key = "'selectAll'", condition = "true")
	public List<OwnBo> selectAll() {
		return ownMapper.selectAll();
	}

	@Cacheable(key = "'selectSort'+#p0", condition = "true")
	public List<String> selectSort(OwnQuery ownQuery) {
		return ownMapper.selectSort(ownQuery);
	}

	//
	@Caching(evict = {
			@CacheEvict(key = "'selectAll'"),
	})
	public int insert(OwnPo ownPo) {
		return ownMapper.insert(ownPo);
	}

	@Caching(evict = {
			@CacheEvict(key = "'selectAll'"),
	})
	public int delete(OwnPo ownPo) {
		return ownMapper.delete(ownPo);
	}


	@Caching(evict = {
			@CacheEvict(key = "'selectAll'"),
	})
	public int update(OwnPo ownPo) {
		return ownMapper.update(ownPo);
	}
}
