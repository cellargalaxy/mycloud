package top.cellargalaxy.mycloud.dao.cache;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Repository;
import top.cellargalaxy.mycloud.dao.BlockDao;
import top.cellargalaxy.mycloud.dao.mapper.BlockMapper;
import top.cellargalaxy.mycloud.model.bo.BlockBo;
import top.cellargalaxy.mycloud.model.po.BlockPo;
import top.cellargalaxy.mycloud.model.query.BlockQuery;

import java.util.List;

/**
 * @author cellargalaxy
 * @time 2018/7/16
 */
@Repository
@CacheConfig(cacheNames = "block")
public class BlockCache implements BlockDao {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private BlockMapper blockMapper;

	@Cacheable(key = "'selectOne'+#p0.blockId", condition = "true")
	public BlockBo selectOne(BlockPo blockPo) {
		logger.debug("selectOne:{}", blockPo);
		return blockMapper.selectOne(blockPo);
	}

	@Cacheable(key = "'selectOne'+#p0.blockId", condition = "true")
	public List<BlockBo> selectSome(BlockQuery blockQuery) {
		logger.debug("selectSome:{}", blockQuery);
		return blockMapper.selectSome(blockQuery);
	}

	@Cacheable(key = "'selectOne'+#p0.blockId", condition = "true")
	public int selectCount(BlockQuery blockQuery) {
		logger.debug("selectCount:{}", blockQuery);
		return blockMapper.selectCount(blockQuery);
	}

	@Cacheable(key = "'selectAll'", condition = "true")
	public List<BlockBo> selectAll() {
		logger.debug("selectAll");
		return blockMapper.selectAll();
	}

	//
	@Caching(evict = {
			@CacheEvict(key = "'selectAll'"),
	})
	public int insert(BlockPo blockPo) {
		logger.debug("insert:{}", blockPo);
		return blockMapper.insert(blockPo);
	}

	@Caching(evict = {
			@CacheEvict(key = "'selectAll'"),
	})
	public int delete(BlockPo blockPo) {
		logger.debug("delete:{}", blockPo);
		return blockMapper.delete(blockPo);
	}

	@Caching(evict = {
			@CacheEvict(key = "'selectAll'"),
	})
	public int update(BlockPo blockPo) {
		logger.debug("update:{}", blockPo);
		return blockMapper.update(blockPo);
	}
}
