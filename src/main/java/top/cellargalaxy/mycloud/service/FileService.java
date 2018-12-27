package top.cellargalaxy.mycloud.service;

import top.cellargalaxy.mycloud.model.bo.OwnBo;
import top.cellargalaxy.mycloud.model.po.FileInfoPo;
import top.cellargalaxy.mycloud.model.po.OwnExpirePo;
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
	String addTmpFile(InputStream inputStream, OwnBo ownBo, OwnExpirePo ownExpirePo) throws IOException;

	String addFile(InputStream inputStream, OwnBo ownBo, UserPo userPo) throws IOException;

	String addFile(String urlString, OwnBo ownBo, UserPo userPo) throws IOException;

	String removeFile(FileInfoPo fileInfoPo) throws IOException;

	String removeFile(OwnPo ownPo) throws IOException;

	String removeFile(OwnPo ownPo, UserPo userPo) throws IOException;

	FileInfoPo getFileInfoPoByMd5OrUuid(String md5OrUuid);

	String getFileByMd5OrUuid(String md5OrUuid, OutputStream outputStream) throws IOException;

	String getTar(OutputStream outputStream) throws IOException;

	String getTar(UserPo userPo, OutputStream outputStream) throws IOException;
}
