package top.cellargalaxy.mycloud.dao;

import top.cellargalaxy.mycloud.model.bo.BlockBo;
import top.cellargalaxy.mycloud.model.po.BlockPo;
import top.cellargalaxy.mycloud.model.query.BlockQuery;

/**
 * @author cellargalaxy
 * @time 2018/7/16
 */
public interface BlockDao extends AbstractDao<BlockPo,BlockBo,BlockQuery> {
	String TABLE_NAME = "block";

	static String checkInsert(BlockPo blockPo) {
		if (blockPo.getBlock() == null) {
			return "数据块不得为空";
		}
		return null;
	}

	static String checkUpdate(BlockPo blockPo) {
		if (blockPo.getBlockId() < 1) {
			return "数据块id不得为空";
		}
		if (blockPo.getBlock() == null) {
			return "数据块不得为空";
		}
		return null;
	}
}
