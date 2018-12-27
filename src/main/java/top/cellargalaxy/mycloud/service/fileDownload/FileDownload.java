package top.cellargalaxy.mycloud.service.fileDownload;

import top.cellargalaxy.mycloud.model.po.FileInfoPo;
import top.cellargalaxy.mycloud.model.po.OwnPo;

import java.io.IOException;
import java.io.OutputStream;

public interface FileDownload {
	String downloadFile(String urlString, FileInfoPo fileInfoPo, OutputStream... outputStreams) throws IOException;

	String downloadFile(String urlString, OwnPo ownPo, OutputStream... outputStreams) throws IOException;
}
