package top.cellargalaxy.mycloud.dao.cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
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

	@CacheEvict(key = "#p0.blockId")
	public int insert(BlockPo blockPo) {
		return blockMapper.insert(blockPo);
	}

	@CacheEvict(key = "#p0.blockId")
	public int delete(BlockPo blockPo) {
		return blockMapper.delete(blockPo);
	}

	@Cacheable(key = "#p0.blockId", condition = "#p0.blockId>0")
	public BlockBo selectOne(BlockPo blockPo) {
		return blockMapper.selectOne(blockPo);
	}

	public List<BlockBo> selectSome(BlockQuery blockQuery) {
		return blockMapper.selectSome(blockQuery);
	}

	public int selectCount(BlockQuery blockQuery) {
		return blockMapper.selectCount(blockQuery);
	}

	public List<BlockBo> selectAll() {
		return blockMapper.selectAll();
	}

	@CacheEvict(key = "#p0.blockId")
	public int update(BlockPo blockPo) {
		return blockMapper.update(blockPo);
	}
}
