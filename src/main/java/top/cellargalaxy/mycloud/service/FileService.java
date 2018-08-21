package top.cellargalaxy.mycloud.service;

import top.cellargalaxy.mycloud.model.po.FileInfoPo;
import top.cellargalaxy.mycloud.model.po.OwnPo;
import top.cellargalaxy.mycloud.model.po.UserPo;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

/**
 * @author cellargalaxy
 * @time 2018/7/17
 */
public interface FileService {
	Map getDriveInfo();

	String uploadFile(OwnPo ownPo, File file, String contentType) throws IOException;

	String executeUploadFileTask(UserPo userPo, OwnPo ownPo, File file, String contentType);

	void addUploadFileTask(UserPo userPo, OwnPo ownPo, File file, String contentType);

	String downloadFile(FileInfoPo fileInfoPo, File file) throws IOException;

	String downloadFile(FileInfoPo fileInfoPo, OutputStream outputStream) throws IOException;

	String executeDownloadFileTask(UserPo userPo, FileInfoPo fileInfoPo, File file);

	void addDownloadFileTask(UserPo userPo, FileInfoPo fileInfoPo, File file);

	String removeFile(FileInfoPo fileInfoPo);

	String executeRemoveFileTask(UserPo userPo, FileInfoPo fileInfoPo);

	void addRemoveFileTask(UserPo userPo, FileInfoPo fileInfoPo);

	File createLocalFile(FileInfoPo fileInfoPo);

	String restoreAllFileToLocal(UserPo userPo);

	String startRestoreAllFileToLocal(UserPo userPo);

	String stopRestoreAllFileToLocal();

	String deleteAllFileFromLocal();
}
