package top.cellargalaxy.mycloud.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.cellargalaxy.mycloud.configuration.MycloudConfiguration;
import top.cellargalaxy.mycloud.dao.FileInfoDao;
import top.cellargalaxy.mycloud.model.bo.FileInfoBo;
import top.cellargalaxy.mycloud.model.bo.OwnBo;
import top.cellargalaxy.mycloud.model.po.FileInfoPo;
import top.cellargalaxy.mycloud.model.query.FileInfoQuery;
import top.cellargalaxy.mycloud.model.query.OwnQuery;
import top.cellargalaxy.mycloud.model.vo.FileInfoVo;
import top.cellargalaxy.mycloud.service.FileInfoService;
import top.cellargalaxy.mycloud.service.OwnService;
import top.cellargalaxy.mycloud.util.ServiceUtil;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author cellargalaxy
 * @time 2018/7/20
 */
@Transactional
@Service
public class FileInfoServiceImpl implements FileInfoService {
	private static final String NAME = "文件";
	@Autowired
	private FileInfoDao fileInfoDao;
	@Autowired
	private OwnService ownService;
	private final String domain;

	@Autowired
	public FileInfoServiceImpl(MycloudConfiguration mycloudConfiguration) {
		this.domain = mycloudConfiguration.getDomain();
	}

	@Override
	public String addFileInfo(FileInfoPo fileInfoPo) {
		return ServiceUtil.add(fileInfoPo, NAME, this::checkAddFileInfo, fileInfoDao);
	}

	@Override
	public String removeFileInfo(FileInfoPo fileInfoPo) {
		return ServiceUtil.remove(fileInfoPo, NAME, fileInfoDao);
	}

	@Override
	public String checkAddFileInfo(FileInfoPo fileInfoPo) {
		return ServiceUtil.checkAdd(fileInfoPo, NAME, FileInfoDao::checkInsert, fileInfoDao);
	}

	@Override
	public FileInfoBo getFileInfo(FileInfoPo fileInfoPo) {
		FileInfoBo fileInfoBo = fileInfoDao.selectOne(fileInfoPo);
		setUrl(fileInfoBo);
		return fileInfoBo;
	}

	@Override
	public FileInfoVo getFileInfoVo(FileInfoPo fileInfoPo) {
		FileInfoBo fileInfoBo = fileInfoDao.selectOne(fileInfoPo);
		setUrl(fileInfoBo);
		return bo2vo(fileInfoBo);
	}

	@Override
	public List<FileInfoBo> listFileInfo(FileInfoQuery fileInfoQuery) {
		List<FileInfoBo> fileInfoBos = fileInfoDao.selectPageSome(fileInfoQuery);
		fileInfoBos.stream().forEach(fileInfoBo -> setUrl(fileInfoBo));
		return fileInfoBos;
	}

	@Override
	public List<FileInfoVo> listFileInfoVo(FileInfoQuery fileInfoQuery) {
		List<FileInfoBo> fileInfoBos = fileInfoDao.selectPageSome(fileInfoQuery);
		fileInfoBos.stream().forEach(fileInfoBo -> setUrl(fileInfoBo));
		return bo2vo(fileInfoBos);
	}

	@Override
	public List<FileInfoBo> listAllFileInfo() {
		List<FileInfoBo> fileInfoBos = fileInfoDao.selectAll();
		fileInfoBos.stream().forEach(fileInfoBo -> setUrl(fileInfoBo));
		return fileInfoBos;
	}

	@Override
	public int getFileInfoCount(FileInfoQuery fileInfoQuery) {
		return fileInfoDao.selectCount(fileInfoQuery);
	}

	@Override
	public List<String> listContentType() {
		return fileInfoDao.selectAllContentType();
	}

	@Override
	public void setUrl(FileInfoBo fileInfoBo) {
		if (fileInfoBo == null) {
			return;
		}
		fileInfoBo.setMd5Url(domain + "/" + fileInfoBo.getMd5());
	}

	private FileInfoVo bo2vo(FileInfoBo fileInfoBo) {
		if (fileInfoBo == null) {
			return null;
		}
		OwnQuery ownQuery = new OwnQuery();
		ownQuery.setFileId(fileInfoBo.getFileId());
		List<OwnBo> ownBos = ownService.listAllOwn(ownQuery);
		FileInfoVo fileInfoVo = new FileInfoVo();
		fileInfoVo.setFileInfo(fileInfoBo);
		fileInfoVo.setOwns(ownBos);
		return fileInfoVo;
	}

	private List<FileInfoVo> bo2vo(List<FileInfoBo> fileInfoBos) {
		if (fileInfoBos == null) {
			return null;
		}
		return fileInfoBos.stream().map(fileInfoBo -> bo2vo(fileInfoBo)).collect(Collectors.toList());
	}
}
