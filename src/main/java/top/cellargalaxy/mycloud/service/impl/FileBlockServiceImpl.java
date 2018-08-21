package top.cellargalaxy.mycloud.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import top.cellargalaxy.mycloud.dao.FileBlockDao;
import top.cellargalaxy.mycloud.model.bo.FileBlockBo;
import top.cellargalaxy.mycloud.model.po.FileBlockPo;
import top.cellargalaxy.mycloud.model.query.FileBlockQuery;
import top.cellargalaxy.mycloud.service.FileBlockService;

import java.util.List;

/**
 * Created by cellargalaxy on 18-8-5.
 */
@Transactional
@Service
public class FileBlockServiceImpl implements FileBlockService {
	private Logger logger = LoggerFactory.getLogger(FileBlockServiceImpl.class);
	@Autowired
	private FileBlockDao fileBlockDao;

	@Override
	public String addFileBlock(FileBlockPo fileBlockPo) {
		logger.debug("addFileBlock:{}", fileBlockPo);
		String string = checkAddFileBlock(fileBlockPo);
		if (string != null) {
			return string;
		}
		int i = fileBlockDao.insert(fileBlockPo);
		if (i == 0) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return "文件块空新增";
		}
		return null;
	}

	@Override
	public String removeFileBlock(FileBlockPo fileBlockPo) {
		logger.debug("removeFileBlock:{}", fileBlockPo);
		int i = fileBlockDao.delete(fileBlockPo);
		if (i == 0) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return "文件块空删除";
		}
		return null;
	}

	@Override
	public FileBlockBo getFileBlock(FileBlockPo fileBlockPo) {
		logger.debug("getFileBlock:{}", fileBlockPo);
		return fileBlockDao.selectOne(fileBlockPo);
	}

	@Override
	public List<FileBlockBo> listFileBlock(FileBlockQuery fileBlockQuery) {
		logger.debug("listFileBlock:{}", fileBlockQuery);
		return fileBlockDao.selectSome(fileBlockQuery);
	}

	@Override
	public int getFileBlockCount(FileBlockQuery fileBlockQuery) {
		logger.debug("getFileBlockCount:{}", fileBlockQuery);
		return fileBlockDao.selectCount(fileBlockQuery);
	}

	@Override
	public String changeFileBlock(FileBlockPo fileBlockPo) {
		logger.debug("changeFileBlock:{}", fileBlockPo);
		String string = checkChangeFileBlock(fileBlockPo);
		if (string != null) {
			return string;
		}
		int i = fileBlockDao.update(fileBlockPo);
		if (i == 0) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return "文件块空更新";
		}
		return null;
	}

	@Override
	public String checkAddFileBlock(FileBlockPo fileBlockPo) {
		logger.debug("checkAddFileBlock:{}", fileBlockPo);
		String string = FileBlockDao.checkInsert(fileBlockPo);
		if (string != null) {
			return string;
		}
		FileBlockBo fileBlockBo = fileBlockDao.selectOne(fileBlockPo);
		if (fileBlockBo != null) {
			return "文件块已存在";
		}
		return null;
	}

	@Override
	public String checkChangeFileBlock(FileBlockPo fileBlockPo) {
		logger.debug("checkChangeFileBlock:{}", fileBlockPo);
		return FileBlockDao.checkUpdate(fileBlockPo);
	}
}
