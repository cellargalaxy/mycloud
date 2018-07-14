package top.cellargalaxy.mycloud.service;

import top.cellargalaxy.mycloud.model.po.FileInfoPo;
import top.cellargalaxy.mycloud.model.query.FileInfoQuery;

import java.io.File;
import java.util.Date;
import java.util.List;

public interface FileService {
	int addFile(FileInfoPo fileInfoPo, File file);

	int deleteFile(FileInfoQuery fileInfoQuery);

	FileInfoPo getOneFileInfo(FileInfoQuery fileInfoQuery);

	List<FileInfoPo> getSomeFileInfo(FileInfoQuery fileInfoQuery);

	List<Date> getAllFileCreateTime();

	List<String> getAllFileContentType();

	List<String> getAllFileSort();

	int updateFile(FileInfoPo fileInfoPo);
}
