package top.cellargalaxy.mycloud.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.cellargalaxy.mycloud.configuration.MycloudConfiguration;
import top.cellargalaxy.mycloud.dao.BlockDao;
import top.cellargalaxy.mycloud.dao.FileBlockDao;
import top.cellargalaxy.mycloud.dao.FileInfoDao;
import top.cellargalaxy.mycloud.dao.OwnDao;
import top.cellargalaxy.mycloud.model.bo.FileBlockBo;
import top.cellargalaxy.mycloud.model.bo.FileInfoBo;
import top.cellargalaxy.mycloud.model.bo.OwnBo;
import top.cellargalaxy.mycloud.model.po.BlockPo;
import top.cellargalaxy.mycloud.model.po.FileBlockPo;
import top.cellargalaxy.mycloud.model.po.FileInfoPo;
import top.cellargalaxy.mycloud.model.po.UserPo;
import top.cellargalaxy.mycloud.model.query.BlockQuery;
import top.cellargalaxy.mycloud.model.query.FileBlockQuery;
import top.cellargalaxy.mycloud.model.query.FileInfoQuery;
import top.cellargalaxy.mycloud.model.query.OwnQuery;
import top.cellargalaxy.mycloud.model.vo.FileInfoOwnVo;
import top.cellargalaxy.mycloud.service.FileInfoService;
import top.cellargalaxy.mycloud.util.FileBlocks;
import top.cellargalaxy.mycloud.util.SqlUtil;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

/**
 * @author cellargalaxy
 * @time 2018/7/20
 */
@Service
public class FileInfoServiceImpl implements FileInfoService {
	private Logger logger = LoggerFactory.getLogger(FileInfoServiceImpl.class);
	@Autowired
	private FileInfoDao fileInfoDao;
	@Autowired
	private OwnDao ownDao;
	@Autowired
	private FileBlockDao fileBlockDao;
	@Autowired
	private BlockDao blockDao;
	@Autowired
	private MycloudConfiguration mycloudConfiguration;

	@Override
	public String addFileInfo(FileInfoPo fileInfoPo, File file) throws IOException {
		logger.info("addFileInfo, fileInfoPo:{}, file:{}", fileInfoPo, file);
		String string = checkAddFileInfo(fileInfoPo);
		if (string != null) {
			return string;
		}
		int i = fileInfoDao.insert(fileInfoPo);
		if (i == 0) {
			return "文件信息空新增";
		}

		//写数据块
		int[] blockIds;
		try (FileBlocks fileBlocks = new FileBlocks(file, mycloudConfiguration.getBlobLength())) {
			blockIds = new int[fileBlocks.getBlockCount()];
			int blockIndex = 0;
			byte[] block;
			while ((block = fileBlocks.next()) != null) {
				BlockPo blockPo = new BlockPo();
				blockPo.setBlock(block);
				blockDao.insert(blockPo);
				blockIds[blockIndex] = blockPo.getBlockId();
				blockIndex++;
			}
		}

		//添加文件信息与数据块对应数据
		for (int blockId : blockIds) {
			FileBlockPo fileBlockPo = new FileBlockPo();
			fileBlockPo.setFileId(fileInfoPo.getFileId());
			fileBlockPo.setBlockId(blockId);
			fileBlockDao.insert(fileBlockPo);
		}

		file.delete();

		return null;
	}

	@Override
	public String removeFileInfo(FileInfoQuery fileInfoQuery) {
		logger.info("removeFileInfo:{}", fileInfoQuery);
		int i = fileInfoDao.delete(fileInfoQuery);
		if (i == 0) {
			return "文件信息空删除";
		}

		FileBlockQuery fileBlockQuery = new FileBlockQuery();
		fileBlockQuery.setFileId(fileInfoQuery.getFileId());
		List<FileBlockBo> fileBlockBos = fileBlockDao.selectSome(fileBlockQuery);
		fileBlockDao.delete(fileBlockQuery);

		for (FileBlockBo fileBlockBo : fileBlockBos) {
			BlockQuery blockQuery = new BlockQuery();
			blockQuery.setBlockId(fileBlockBo.getBlockId());
			blockDao.delete(blockQuery);
		}
		return null;
	}

	@Override
	public FileInfoBo getFileInfo(FileInfoQuery fileInfoQuery) {
		logger.info("getFileInfo:{}", fileInfoQuery);
		return fileInfoDao.selectOne(fileInfoQuery);
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
		return fileInfoDao.selectSome(fileInfoQuery);
	}

	@Override
	public FileInfoOwnVo getFileInfoOwn(FileInfoQuery fileInfoQuery) {
		logger.info("getFileInfoOwn:{}", fileInfoQuery);
		FileInfoBo fileInfoBo = fileInfoDao.selectOne(fileInfoQuery);
		if (fileInfoBo == null) {
			return null;
		}
		OwnQuery ownQuery = new OwnQuery();
		ownQuery.setFileId(fileInfoBo.getFileId());
		List<OwnBo> ownBos = ownDao.selectSome(ownQuery);
		return new FileInfoOwnVo(fileInfoBo, ownBos);
	}

	@Override
	public List<FileInfoOwnVo> listFileInfoOwn(FileInfoQuery fileInfoQuery) {
		logger.info("listFileInfoOwn:{}", fileInfoQuery);
		List<FileInfoBo> fileInfoBos = fileInfoDao.selectSome(fileInfoQuery);
		if (fileInfoBos == null) {
			return null;
		}
		List<FileInfoOwnVo> fileInfoOwnVos = new LinkedList<>();
		for (FileInfoBo fileInfoBo : fileInfoBos) {
			OwnQuery ownQuery = new OwnQuery();
			ownQuery.setFileId(fileInfoBo.getFileId());
			ownQuery.setPage(1);
			ownQuery.setPageSize(SqlUtil.MAX_PAGE_SIZE);
			List<OwnBo> ownBos = ownDao.selectSome(ownQuery);
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
		FileInfoQuery fileInfoQuery = new FileInfoQuery();
		fileInfoQuery.setFileId(fileInfoPo.getFileId());
		fileInfoQuery.setMd5(fileInfoPo.getMd5());
		FileInfoPo fileInfo = fileInfoDao.selectOne(fileInfoQuery);
		if (fileInfo != null) {
			return "文件已存在";
		}
		return null;
	}

	@Override
	public String checkChangeFileInfo(FileInfoPo fileInfoPo) {
		String string = FileInfoDao.checkUpdate(fileInfoPo);
		if (string != null) {
			return string;
		}
		FileInfoQuery fileInfoQuery = new FileInfoQuery();
		fileInfoQuery.setFileId(fileInfoPo.getFileId());
		fileInfoQuery.setMd5(fileInfoPo.getMd5());
		FileInfoPo fileInfo = fileInfoDao.selectOne(fileInfoQuery);
		if (fileInfo == null) {
			return "文件不存在";
		}
		return null;
	}
}
