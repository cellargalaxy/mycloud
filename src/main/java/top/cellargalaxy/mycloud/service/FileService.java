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
public interface FileService {
	void addUploadFileTask(UserPo userPo, OwnPo ownPo, File file, String contentType);

	String uploadFile(UserPo userPo, OwnPo ownPo, File file, String contentType) throws IOException;

	void addDownloadFileTask(UserPo userPo, FileInfoPo fileInfoPo, File file);

	String downloadFile(FileInfoPo fileInfoPo, OutputStream outputStream) throws IOException;

	String restoreAllFileToLocal(UserPo userPo);
}
