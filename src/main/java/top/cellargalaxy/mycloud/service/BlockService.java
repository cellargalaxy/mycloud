package top.cellargalaxy.mycloud.service;

import top.cellargalaxy.mycloud.model.bo.BlockBo;
import top.cellargalaxy.mycloud.model.po.BlockPo;

/**
 * Created by cellargalaxy on 18-8-5.
 */
public interface BlockService {
	String addBlock(BlockPo blockPo);

	String removeBlock(BlockPo blockPo);

	BlockBo getBlock(BlockPo blockPo);

	String changeBlock(BlockPo blockPo);

	String checkAddBlock(BlockPo blockPo);

	String checkChangeBlock(BlockPo blockPo);
}
