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

	String downloadFile(FileInfoPo fileInfoPo, OutputStream outputStream) throws IOException;

	String removeFile(FileInfoPo fileInfoPo);

	File createLocalFile(FileInfoPo fileInfoPo);

	String restoreAllFileToLocal(UserPo userPo);

	String startRestoreAllFileToLocal(UserPo userPo);

	String stopRestoreAllFileToLocal();

	String deleteAllFileFromLocal();
}
