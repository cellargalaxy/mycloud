package top.cellargalaxy.mycloud.service.fileDeal;

import top.cellargalaxy.mycloud.model.po.FileInfoPo;
import top.cellargalaxy.mycloud.model.po.OwnPo;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public interface FileDeal {
	String addFile(InputStream inputStream, FileInfoPo fileInfoPo) throws IOException;

	String addFile(InputStream inputStream, OwnPo ownPo) throws IOException;

	String addFile(String urlString, FileInfoPo fileInfoPo) throws IOException;

	String addFile(String urlString, OwnPo ownPo) throws IOException;

	String removeFile(FileInfoPo fileInfoPo) throws IOException;

	String removeFile(OwnPo ownPo) throws IOException;

	String getFile(FileInfoPo fileInfoPo, OutputStream outputStream) throws IOException;

	String getFile(OwnPo ownPo, OutputStream outputStream) throws IOException;
}
