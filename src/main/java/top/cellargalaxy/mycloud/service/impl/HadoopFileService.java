package top.cellargalaxy.mycloud.service.impl;

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
public class HadoopFileService {

	public String addFile(InputStream inputStream, FileInfoPo fileInfoPo) throws IOException {
		return null;
	}


	public String addFile(InputStream inputStream, OwnPo ownPo, UserPo userPo) throws IOException {
		return null;
	}


	public String removeFile(FileInfoPo fileInfoPo) throws IOException {
		return null;
	}


	public String removeFile(OwnPo ownPo) throws IOException {
		return null;
	}


	public FileInfoPo getFileInfoPoByMd5OrUuid(String md5OrUuid) {
		return null;
	}


	public String getFileByMd5OrUuid(String md5OrUuid, OutputStream outputStream) throws IOException {
		return null;
	}


	public String getFile(FileInfoPo fileInfoPo, OutputStream outputStream) throws IOException {
		return null;
	}


	public String getFile(OwnPo ownPo, OutputStream outputStream) throws IOException {
		return null;
	}
}
