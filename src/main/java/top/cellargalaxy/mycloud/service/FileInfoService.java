package top.cellargalaxy.mycloud.service;

import top.cellargalaxy.mycloud.model.bo.FileInfoBo;
import top.cellargalaxy.mycloud.model.po.FileInfoPo;
import top.cellargalaxy.mycloud.model.query.FileInfoQuery;
import top.cellargalaxy.mycloud.model.vo.FileInfoOwnVo;

import java.util.List;

/**
 * @author cellargalaxy
 * @time 2018/7/20
 */
public interface FileInfoService {
//	String addFileInfo(FileInfoPo fileInfoPo);

//	String removeFileInfo(FileInfoQuery fileInfoQuery);

	FileInfoBo getFileInfo(FileInfoQuery fileInfoQuery);

	List<FileInfoBo> listFileInfo(FileInfoQuery fileInfoQuery);

	FileInfoOwnVo getFileInfoOwn(FileInfoQuery fileInfoQuery);

	List<FileInfoOwnVo> listFileInfoOwn(FileInfoQuery fileInfoQuery);

	List<String> listContentType();

//	String changeFileInfo(FileInfoPo fileInfoPo);

	String checkAddFileInfo(FileInfoPo fileInfoPo);

	String checkChangeFileInfo(FileInfoPo fileInfoPo);
}