package top.cellargalaxy.mycloud.service;

import top.cellargalaxy.mycloud.model.bo.FileInfoBo;
import top.cellargalaxy.mycloud.model.po.FileInfoPo;
import top.cellargalaxy.mycloud.model.po.UserPo;
import top.cellargalaxy.mycloud.model.query.FileInfoQuery;
import top.cellargalaxy.mycloud.model.vo.FileInfoOwnVo;

import java.util.List;

/**
 * @author cellargalaxy
 * @time 2018/7/20
 */
public interface FileInfoService {
	String addFileInfo(FileInfoPo fileInfoPo);

	String removeFileInfo(FileInfoPo fileInfoPo);

	FileInfoBo getFileInfo(FileInfoPo fileInfoPo);

	FileInfoBo getFileInfo(UserPo userPo, FileInfoQuery fileInfoQuery);

	int getFileInfoCount(FileInfoQuery fileInfoQuery);

	List<FileInfoBo> listFileInfo(FileInfoQuery fileInfoQuery);

	List<FileInfoBo> listAllFileInfo();

	FileInfoOwnVo getFileInfoOwn(FileInfoQuery fileInfoQuery);

	List<FileInfoOwnVo> listFileInfoOwn(FileInfoQuery fileInfoQuery);

	List<String> listContentType();

	String changeFileInfo(FileInfoPo fileInfoPo);

	String checkAddFileInfo(FileInfoPo fileInfoPo);

	String checkChangeFileInfo(FileInfoPo fileInfoPo);
}
