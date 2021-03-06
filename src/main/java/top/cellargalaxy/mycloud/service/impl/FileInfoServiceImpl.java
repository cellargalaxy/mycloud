package top.cellargalaxy.mycloud.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.cellargalaxy.mycloud.dao.FileInfoDao;
import top.cellargalaxy.mycloud.model.bo.FileInfoBo;
import top.cellargalaxy.mycloud.model.bo.OwnBo;
import top.cellargalaxy.mycloud.model.po.FileInfoPo;
import top.cellargalaxy.mycloud.model.query.FileInfoQuery;
import top.cellargalaxy.mycloud.model.query.OwnQuery;
import top.cellargalaxy.mycloud.model.vo.FileInfoVo;
import top.cellargalaxy.mycloud.service.FileInfoService;
import top.cellargalaxy.mycloud.service.OwnService;
import top.cellargalaxy.mycloud.service.PathService;
import top.cellargalaxy.mycloud.util.serivce.ServiceUtils;

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
	@Autowired
	private PathService pathService;

	@Override
	public String addFileInfo(FileInfoPo fileInfoPo) {
		return ServiceUtils.add(fileInfoPo, NAME, FileInfoDao::checkInsert, fileInfoDao);
	}

	@Override
	public String removeFileInfo(FileInfoPo fileInfoPo) {
		return ServiceUtils.remove(fileInfoPo, NAME, fileInfoDao);
	}

	@Override
	public FileInfoBo getFileInfo(FileInfoPo fileInfoPo) {
		FileInfoBo fileInfoBo = fileInfoDao.selectOne(fileInfoPo);
		pathService.setUrl(fileInfoBo);
		return fileInfoBo;
	}

	@Override
	public FileInfoVo getFileInfoVo(FileInfoPo fileInfoPo) {
		FileInfoBo fileInfoBo = fileInfoDao.selectOne(fileInfoPo);
		pathService.setUrl(fileInfoBo);
		return bo2vo(fileInfoBo);
	}

	@Override
	public List<FileInfoBo> listPageFileInfo(FileInfoQuery fileInfoQuery) {
		List<FileInfoBo> fileInfoBos = fileInfoDao.selectPageSome(fileInfoQuery);
		fileInfoBos.stream().forEach(fileInfoBo -> pathService.setUrl(fileInfoBo));
		return fileInfoBos;
	}

	@Override
	public List<FileInfoVo> listPageFileInfoVo(FileInfoQuery fileInfoQuery) {
		List<FileInfoBo> fileInfoBos = fileInfoDao.selectPageSome(fileInfoQuery);
		fileInfoBos.stream().forEach(fileInfoBo -> pathService.setUrl(fileInfoBo));
		return bo2vo(fileInfoBos);
	}

	@Override
	public int getFileInfoCount(FileInfoQuery fileInfoQuery) {
		return fileInfoDao.selectCount(fileInfoQuery);
	}

	@Override
	public List<String> listContentType() {
		return fileInfoDao.selectAllContentType();
	}

	private FileInfoVo bo2vo(FileInfoBo fileInfoBo) {
		if (fileInfoBo == null) {
			return null;
		}
		OwnQuery ownQuery = new OwnQuery();
		ownQuery.setFileId(fileInfoBo.getFileId());
		List<OwnBo> ownBos = ownService.listSomeOwn(ownQuery);
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
