package top.cellargalaxy.mycloud.dao.cache;

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
	@Autowired
	private BlockMapper blockMapper;

	@Cacheable(key = "'selectOne'+#p0.blockId", condition = "true")
	public BlockBo selectOne(BlockPo blockPo) {
		return blockMapper.selectOne(blockPo);
	}

	@Cacheable(key = "'selectOne'+#p0.blockId", condition = "true")
	public List<BlockBo> selectSome(BlockQuery blockQuery) {
		return blockMapper.selectSome(blockQuery);
	}

	@Cacheable(key = "'selectOne'+#p0.blockId", condition = "true")
	public int selectCount(BlockQuery blockQuery) {
		return blockMapper.selectCount(blockQuery);
	}

	@Cacheable(key = "'selectAll'", condition = "true")
	public List<BlockBo> selectAll() {
		return blockMapper.selectAll();
	}

	//
	@Caching(evict = {
			@CacheEvict(key = "'selectAll'"),
	})
	public int insert(BlockPo blockPo) {
		return blockMapper.insert(blockPo);
	}

	@Caching(evict = {
			@CacheEvict(key = "'selectAll'"),
	})
	public int delete(BlockPo blockPo) {
		return blockMapper.delete(blockPo);
	}

	public int update(BlockPo blockPo) {
		return blockMapper.update(blockPo);
	}
}
