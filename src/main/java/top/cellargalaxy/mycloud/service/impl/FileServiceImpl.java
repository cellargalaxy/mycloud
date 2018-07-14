package top.cellargalaxy.mycloud.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.cellargalaxy.mycloud.dao.FileInfoDao;
import top.cellargalaxy.mycloud.model.po.FileInfoPo;
import top.cellargalaxy.mycloud.model.query.FileInfoQuery;
import top.cellargalaxy.mycloud.service.FileService;

import java.io.File;
import java.util.Date;
import java.util.List;

/**
 * Created by cellargalaxy on 18-7-14.
 */
@Service
public class FileServiceImpl implements FileService {
	@Autowired
	private FileInfoDao fileInfoDao;

	@Override
	public int addFile(FileInfoPo fileInfoPo, File file) {
		return fileInfoDao.insert( fileInfoPo);
	}

	@Override
	public int deleteFile(FileInfoQuery fileInfoQuery) {
		return fileInfoDao.delete( fileInfoQuery);
	}

	@Override
	public FileInfoPo getOneFileInfo(FileInfoQuery fileInfoQuery) {
		return fileInfoDao.selectOne( fileInfoQuery);
	}

	@Override
	public List<FileInfoPo> getSomeFileInfo(FileInfoQuery fileInfoQuery) {
		return fileInfoDao.selectSome( fileInfoQuery);
	}

	@Override
	public List<Date> getAllFileCreateTime() {
		return fileInfoDao.selectCreateTime();
	}

	@Override
	public List<String> getAllFileContentType() {
		return fileInfoDao.selectContentType();
	}

	@Override
	public List<String> getAllFileSort() {
		return fileInfoDao.selectSort();
	}

	@Override
	public int updateFile(FileInfoPo fileInfoPo) {
		return fileInfoDao.update( fileInfoPo);
	}
}
