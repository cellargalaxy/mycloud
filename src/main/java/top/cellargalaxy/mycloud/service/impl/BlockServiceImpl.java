package top.cellargalaxy.mycloud.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import top.cellargalaxy.mycloud.dao.BlockDao;
import top.cellargalaxy.mycloud.model.bo.BlockBo;
import top.cellargalaxy.mycloud.model.po.BlockPo;
import top.cellargalaxy.mycloud.service.BlockService;

/**
 * Created by cellargalaxy on 18-8-5.
 */
@Transactional
@Service
public class BlockServiceImpl implements BlockService {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private BlockDao blockDao;

	@Override
	public String addBlock(BlockPo blockPo) {
		logger.debug("addBlock:{}", blockPo);
		String string = checkAddBlock(blockPo);
		if (string != null) {
			return string;
		}
		int i = blockDao.insert(blockPo);
		if (i == 0) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return "块空新增";
		}
		return null;
	}

	@Override
	public String removeBlock(BlockPo blockPo) {
		logger.debug("removeBlock:{}", blockPo);
		int i = blockDao.delete(blockPo);
		if (i == 0) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return "块空删除";
		}
		return null;
	}

	@Override
	public BlockBo getBlock(BlockPo blockPo) {
		logger.debug("getBlock:{}", blockPo);
		return blockDao.selectOne(blockPo);
	}

	@Override
	public String changeBlock(BlockPo blockPo) {
		logger.debug("changeBlock:{}", blockPo);
		String string = checkChangeBlock(blockPo);
		if (string != null) {
			return string;
		}
		int i = blockDao.update(blockPo);
		if (i == 0) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return "块空更新";
		}
		return null;
	}

	@Override
	public String checkAddBlock(BlockPo blockPo) {
		logger.debug("checkAddBlock:{}", blockPo);
		return BlockDao.checkInsert(blockPo);
	}

	@Override
	public String checkChangeBlock(BlockPo blockPo) {
		logger.debug("checkChangeBlock:{}", blockPo);
		return BlockDao.checkUpdate(blockPo);
	}
}
