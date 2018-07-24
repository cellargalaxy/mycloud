package top.cellargalaxy.mycloud.dao;

import top.cellargalaxy.mycloud.model.po.BlockPo;
import top.cellargalaxy.mycloud.model.query.BlockQuery;
import top.cellargalaxy.mycloud.model.bo.BlockBo;
import top.cellargalaxy.mycloud.util.StringUtil;

/**
 * @author cellargalaxy
 * @time 2018/7/16
 */
public interface BlockDao {
	String TABLE_NAME = "block";

	int insert(BlockPo blockPo);

	int delete(BlockQuery blockQuery);

	BlockBo selectOne(BlockQuery blockQuery);

	int update(BlockPo blockPo);

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