package top.cellargalaxy.mycloud.service;

import top.cellargalaxy.mycloud.model.po.FileInfoPo;
import top.cellargalaxy.mycloud.model.po.OwnPo;
import top.cellargalaxy.mycloud.model.po.UserPo;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

/**
 * @author cellargalaxy
 * @time 2018/7/17
 */
public interface ExecuteService {
	//不允许上传文件而没有task日志
//	String uploadFile(OwnPo ownPo, File file, String contentType) throws IOException;

	String executeUploadFileTask(UserPo userPo, OwnPo ownPo, File file, String contentType) throws Exception;

	void addUploadFileTask(UserPo userPo, OwnPo ownPo, File file, String contentType);

	String downloadFile(FileInfoPo fileInfoPo, File file) throws IOException;

	String downloadFile(FileInfoPo fileInfoPo, OutputStream outputStream) throws IOException;

	String executeDownloadFileTask(UserPo userPo, FileInfoPo fileInfoPo, File file) throws Exception;

	void addDownloadFileTask(UserPo userPo, FileInfoPo fileInfoPo, File file);

	//不允许删除文件而没有task日志
//	String removeFile(FileInfoPo fileInfoPo);

	String executeRemoveFileTask(UserPo userPo, FileInfoPo fileInfoPo) throws Exception;

	void addRemoveFileTask(UserPo userPo, FileInfoPo fileInfoPo);
}