package top.cellargalaxy.mycloud.dao;

import org.junit.Test;
import top.cellargalaxy.mycloud.model.po.BlockPo;

import static org.junit.Assert.*;

public class BlockDaoTest {
	private final BlockDao blockDao;

	public BlockDaoTest(BlockDao blockDao) {
		this.blockDao = blockDao;
	}

	@Test
	public void insert() {
		byte[] bytes={0,1,2,3,4,5,6,7,8,9};
		BlockPo blockPo=new BlockPo();
		blockPo.setBlock(bytes);
		System.out.println(blockDao.insert(blockPo));
	}

	@Test
	public void delete() {
	}

	@Test
	public void selectOne() {
	}

	@Test
	public void update() {
	}

	@Test
	public void checkInsert() {
	}

	@Test
	public void checkUpdate() {
	}
}