package top.cellargalaxy.mycloud.service;

import top.cellargalaxy.mycloud.model.po.FileInfoPo;
import top.cellargalaxy.mycloud.model.po.OwnPo;
import top.cellargalaxy.mycloud.model.po.UserPo;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * @author cellargalaxy
 * @time 2018/10/25
 */
public interface FileService {
	String addFile(InputStream inputStream, FileInfoPo fileInfoPo) throws IOException;

	String addFile(InputStream inputStream,  OwnPo ownPo,UserPo userPo) throws IOException;

	String removeFile(FileInfoPo fileInfoPo) throws IOException;

	String removeFile(OwnPo ownPo) throws IOException;

	FileInfoPo getFileInfoPoByMd5OrUuid(String md5OrUuid);

	String getFileByMd5OrUuid(String md5OrUuid, OutputStream outputStream)throws IOException;

	String getFile(FileInfoPo fileInfoPo, OutputStream outputStream) throws IOException;

	String getFile(OwnPo ownPo, OutputStream outputStream) throws IOException;
}
