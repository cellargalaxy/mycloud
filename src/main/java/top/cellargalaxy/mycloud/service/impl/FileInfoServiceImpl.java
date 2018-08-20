package top.cellargalaxy.mycloud.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import top.cellargalaxy.mycloud.configuration.MycloudConfiguration;
import top.cellargalaxy.mycloud.dao.FileInfoDao;
import top.cellargalaxy.mycloud.model.bo.FileBlockBo;
import top.cellargalaxy.mycloud.model.bo.FileInfoBo;
import top.cellargalaxy.mycloud.model.bo.OwnBo;
import top.cellargalaxy.mycloud.model.po.*;
import top.cellargalaxy.mycloud.model.query.BlockQuery;
import top.cellargalaxy.mycloud.model.query.FileBlockQuery;
import top.cellargalaxy.mycloud.model.query.FileInfoQuery;
import top.cellargalaxy.mycloud.model.query.OwnQuery;
import top.cellargalaxy.mycloud.model.vo.FileInfoOwnVo;
import top.cellargalaxy.mycloud.service.BlockService;
import top.cellargalaxy.mycloud.service.FileBlockService;
import top.cellargalaxy.mycloud.service.FileInfoService;
import top.cellargalaxy.mycloud.service.OwnService;
import top.cellargalaxy.mycloud.util.FileBlocks;
import top.cellargalaxy.mycloud.util.SqlUtil;
import top.cellargalaxy.mycloud.util.StringUtil;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

/**
 * @author cellargalaxy
 * @time 2018/7/20
 */
@Transactional
@Service
public class FileInfoServiceImpl implements FileInfoService {
	private Logger logger = LoggerFactory.getLogger(FileInfoServiceImpl.class);
	@Autowired
	private FileInfoDao fileInfoDao;
	@Autowired
	private OwnService ownService;
	@Autowired
	private MycloudConfiguration mycloudConfiguration;

	@Override
	public String addFileInfo(FileInfoPo fileInfoPo) {
		logger.info("addFileInfo, fileInfoPo:{}", fileInfoPo);
		String string = checkAddFileInfo(fileInfoPo);
		if (string != null) {
			return string;
		}
		int i = fileInfoDao.insert(fileInfoPo);
		if (i == 0) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return "文件信息空新增";
		}
		return null;
	}

	@Override
	public String removeFileInfo(FileInfoPo fileInfoPo) {
		logger.info("removeFileInfo:{}", fileInfoPo);
		int i = fileInfoDao.delete(fileInfoPo);
		if (i == 0) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return "文件信息空删除";
		}
		return null;
	}

	@Override
	public FileInfoBo getFileInfo(FileInfoPo fileInfoPo) {
		logger.info("getFileInfo:{}", fileInfoPo);
		return setUrl(fileInfoDao.selectOne(fileInfoPo));
	}

	@Override
	public FileInfoBo getFileInfo(UserPo userPo, FileInfoQuery fileInfoQuery) {
		return getFileInfo(fileInfoQuery);
	}

	@Override
	public int getFileInfoCount(FileInfoQuery fileInfoQuery) {
		logger.info("getFileInfoCount:{}", fileInfoQuery);
		return fileInfoDao.selectCount(fileInfoQuery);
	}

	@Override
	public List<FileInfoBo> listFileInfo(FileInfoQuery fileInfoQuery) {
		logger.info("listFileInfo:{}", fileInfoQuery);
		return setUrl(fileInfoDao.selectSome(fileInfoQuery));
	}

	@Override
	public List<FileInfoBo> listAllFileInfo() {
		logger.info("listAllFileInfo");
		return setUrl(fileInfoDao.selectAll());
	}

	@Override
	public FileInfoOwnVo getFileInfoOwn(FileInfoQuery fileInfoQuery) {
		logger.info("getFileInfoOwn:{}", fileInfoQuery);
		FileInfoBo fileInfoBo = setUrl(fileInfoDao.selectOne(fileInfoQuery));
		if (fileInfoBo == null) {
			return null;
		}
		OwnQuery ownQuery = new OwnQuery();
		ownQuery.setFileId(fileInfoBo.getFileId());
		List<OwnBo> ownBos = ownService.listOwn(ownQuery);
		return new FileInfoOwnVo(fileInfoBo, ownBos);
	}

	@Override
	public List<FileInfoOwnVo> listFileInfoOwn(FileInfoQuery fileInfoQuery) {
		logger.info("listFileInfoOwn:{}", fileInfoQuery);
		List<FileInfoBo> fileInfoBos = setUrl(fileInfoDao.selectSome(fileInfoQuery));
		if (fileInfoBos == null) {
			return null;
		}
		List<FileInfoOwnVo> fileInfoOwnVos = new LinkedList<>();
		for (FileInfoBo fileInfoBo : fileInfoBos) {
			OwnQuery ownQuery = new OwnQuery();
			ownQuery.setFileId(fileInfoBo.getFileId());
			ownQuery.setPage(1);
			ownQuery.setPageSize(SqlUtil.MAX_PAGE_SIZE);
			List<OwnBo> ownBos = ownService.listOwn(ownQuery);
			fileInfoOwnVos.add(new FileInfoOwnVo(fileInfoBo, ownBos));
		}
		return fileInfoOwnVos;
	}

	@Override
	public List<String> listContentType() {
		logger.info("listContentType");
		return fileInfoDao.selectContentType();
	}

	@Override
	public String changeFileInfo(FileInfoPo fileInfoPo) {
		logger.info("changeFileInfo:{}", fileInfoPo);
		String string = checkChangeFileInfo(fileInfoPo);
		if (string != null) {
			return string;
		}
		int i = fileInfoDao.update(fileInfoPo);
		if (i == 0) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return "文件信息空更新";
		}
		return null;
	}

	@Override
	public String checkAddFileInfo(FileInfoPo fileInfoPo) {
		String string = FileInfoDao.checkInsert(fileInfoPo);
		if (string != null) {
			return string;
		}
		FileInfoBo fileInfoBo = fileInfoDao.selectOne(fileInfoPo);
		if (fileInfoBo != null) {
			return "文件已存在,MD5:" + fileInfoBo.getMd5();
		}
		return null;
	}

	@Override
	public String checkChangeFileInfo(FileInfoPo fileInfoPo) {
		String string = FileInfoDao.checkUpdate(fileInfoPo);
		if (string != null) {
			return string;
		}
		FileInfoBo fileInfoBo = fileInfoDao.selectOne(fileInfoPo);
		if (fileInfoBo == null) {
			return "文件不存在";
		}
		return null;
	}

	private List<FileInfoBo> setUrl(List<FileInfoBo> fileInfoBos) {
		if (fileInfoBos == null) {
			return null;
		}
		for (FileInfoBo fileInfoBo : fileInfoBos) {
			setUrl(fileInfoBo);
		}
		return fileInfoBos;
	}

	private FileInfoBo setUrl(FileInfoBo fileInfoBo) {
		if (fileInfoBo == null) {
			return null;
		}
		fileInfoBo.setUrl(StringUtil.createUrl(mycloudConfiguration.getDomain(), fileInfoBo.getMd5()));
		return fileInfoBo;
	}
}
