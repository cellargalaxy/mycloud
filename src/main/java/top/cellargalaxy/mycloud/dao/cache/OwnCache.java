package top.cellargalaxy.mycloud.dao.cache;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private OwnMapper ownMapper;

	@Cacheable(key = "'selectOne'+(#p0.ownId>0?#p0.ownId:(#p0.userId+'-'+#p0.fileId))", condition = "true")
	public OwnBo selectOne(OwnPo ownPo) {
		logger.info("selectOne:{}", ownPo);
		return ownMapper.selectOne(ownPo);
	}

	@Cacheable(key = "'selectSome'+#p0", condition = "true")
	public List<OwnBo> selectSome(OwnQuery ownQuery) {
		logger.info("selectSome:{}", ownQuery);
		return ownMapper.selectSome(ownQuery);
	}

	@Cacheable(key = "'selectCount'+#p0", condition = "true")
	public int selectCount(OwnQuery ownQuery) {
		logger.info("selectCount:{}", ownQuery);
		return ownMapper.selectCount(ownQuery);
	}

	@Cacheable(key = "'selectAll'", condition = "true")
	public List<OwnBo> selectAll() {
		logger.info("selectAll");
		return ownMapper.selectAll();
	}

	@Cacheable(key = "'selectSort'+#p0", condition = "true")
	public List<String> selectSort(OwnQuery ownQuery) {
		logger.info("selectSort:{}", ownQuery);
		return ownMapper.selectSort(ownQuery);
	}

	//
	@Caching(evict = {
			@CacheEvict(key = "'selectOne'+#p0.ownId"),
			@CacheEvict(key = "'selectOne'+(#p0.userId+'-'+#p0.fileId)"),
			@CacheEvict(key = "'selectAll'"),
	})
	public int insert(OwnPo ownPo) {
		logger.info("insert:{}", ownPo);
		return ownMapper.insert(ownPo);
	}

	@Caching(evict = {
			@CacheEvict(key = "'selectOne'+#p0.ownId"),
			@CacheEvict(key = "'selectOne'+(#p0.userId+'-'+#p0.fileId)"),
			@CacheEvict(key = "'selectAll'"),
	})
	public int delete(OwnPo ownPo) {
		logger.info("delete:{}", ownPo);
		return ownMapper.delete(ownPo);
	}

	@Caching(evict = {
			@CacheEvict(key = "'selectOne'+#p0.ownId"),
			@CacheEvict(key = "'selectOne'+(#p0.userId+'-'+#p0.fileId)"),
			@CacheEvict(key = "'selectAll'"),
	})
	public int update(OwnPo ownPo) {
		logger.info("update:{}", ownPo);
		return ownMapper.update(ownPo);
	}
}
