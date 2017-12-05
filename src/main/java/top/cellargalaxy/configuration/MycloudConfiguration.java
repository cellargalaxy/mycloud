package top.cellargalaxy.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Created by cellargalaxy on 17-12-2.
 */
@Component
public class MycloudConfiguration {
	@Value("${blobMaxLength:16777215}")
	private int blobMaxLength;
	@Value("${token:mycloud}")
	private String token;
	@Value("${listFileLength:10}")
	private int listFileLength;
	@Value("${fileDriveRootPath:src/main/resources/static/drive}")
	private String fileDriveRootPath;
	@Value("${fileServerRootPath:/drive}")
	private String fileServerRootPath;
	@Value("${dataFormat:yyyyMM/dd}")
	private String dataFormat;
	
	public int getBlobMaxLength() {
		return blobMaxLength;
	}
	
	public void setBlobMaxLength(int blobMaxLength) {
		this.blobMaxLength = blobMaxLength;
	}
	
	public String getToken() {
		return token;
	}
	
	public void setToken(String token) {
		this.token = token;
	}
	
	public int getListFileLength() {
		return listFileLength;
	}
	
	public void setListFileLength(int listFileLength) {
		this.listFileLength = listFileLength;
	}
	
	public String getFileDriveRootPath() {
		return fileDriveRootPath;
	}
	
	public void setFileDriveRootPath(String fileDriveRootPath) {
		this.fileDriveRootPath = fileDriveRootPath;
	}
	
	public String getFileServerRootPath() {
		return fileServerRootPath;
	}
	
	public void setFileServerRootPath(String fileServerRootPath) {
		this.fileServerRootPath = fileServerRootPath;
	}
	
	public String getDataFormat() {
		return dataFormat;
	}
	
	public void setDataFormat(String dataFormat) {
		this.dataFormat = dataFormat;
	}
}
