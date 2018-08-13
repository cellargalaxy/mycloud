package top.cellargalaxy.mycloud.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import top.cellargalaxy.mycloud.util.StringUtil;

import java.io.File;

/**
 * @author cellargalaxy
 * @time 2018/7/17
 */
@Component
public class MycloudConfiguration {
	@Value("${blobLength:65535}")
	private int blobLength;
	@Value("${secret:mycloud}")
	private String secret;
	@Value("${webUploadMaxFileSize:1024MB}")
	private String webUploadMaxFileSize;
	@Value("${webUploadMaxRequestSize:1024MB}")
	private String webUploadMaxRequestSize;
	@Value("${domain:http://drive.cellargalaxy.top}")
	private String domain;
	@Value("${mycloudPath:}")
	private String mycloudPath;
	@Value("${mycloudTmpPath:}")
	private String mycloudTmpPath;
	@Value("${mycloudDrivePath:}")
	private String mycloudDrivePath;
	@Value("${taskDetailLength:1024}")
	private int taskDetailLength;
	@Value("${toLocal:false}")
	private boolean restoreFileToLocal;

	public int getBlobLength() {
		return blobLength;
	}

	public void setBlobLength(int blobLength) {
		this.blobLength = blobLength;
	}

	public String getSecret() {
		return secret;
	}

	public void setSecret(String secret) {
		this.secret = secret;
	}

	public String getMycloudPath() {
		if (!StringUtil.isBlank(mycloudPath)) {
			return mycloudPath;
		}
		return System.getProperty("user.dir") + File.separator + "mycloud";
	}

	public void setMycloudPath(String mycloudPath) {
		this.mycloudPath = mycloudPath;
	}

	public String getMycloudTmpPath() {
		if (!StringUtil.isBlank(mycloudTmpPath)) {
			return mycloudTmpPath;
		}
		return getMycloudPath() + File.separator + "tmp";
	}

	public void setMycloudTmpPath(String mycloudTmpPath) {
		this.mycloudTmpPath = mycloudTmpPath;
	}

	public String getMycloudDrivePath() {
		if (!StringUtil.isBlank(mycloudDrivePath)) {
			return mycloudDrivePath;
		}
		return getMycloudPath() + File.separator + "drive";
	}

	public void setMycloudDrivePath(String mycloudDrivePath) {
		this.mycloudDrivePath = mycloudDrivePath;
	}

	public String getWebUploadMaxFileSize() {
		return webUploadMaxFileSize;
	}

	public void setWebUploadMaxFileSize(String webUploadMaxFileSize) {
		this.webUploadMaxFileSize = webUploadMaxFileSize;
	}

	public String getWebUploadMaxRequestSize() {
		return webUploadMaxRequestSize;
	}

	public void setWebUploadMaxRequestSize(String webUploadMaxRequestSize) {
		this.webUploadMaxRequestSize = webUploadMaxRequestSize;
	}

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public int getTaskDetailLength() {
		return taskDetailLength;
	}

	public void setTaskDetailLength(int taskDetailLength) {
		this.taskDetailLength = taskDetailLength;
	}

	public boolean isRestoreFileToLocal() {
		return restoreFileToLocal;
	}

	public void setRestoreFileToLocal(boolean restoreFileToLocal) {
		this.restoreFileToLocal = restoreFileToLocal;
	}
}
