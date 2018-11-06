package top.cellargalaxy.mycloud.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author cellargalaxy
 * @time 2018/7/17
 */
@Component
@ConfigurationProperties(prefix = "mycloud")
public class MycloudConfiguration {

	private volatile String webUploadMaxFileSize;
	private volatile String webUploadMaxRequestSize;

	private volatile String mycloudPath;
	private volatile int downloadUrlConnectTimeout;
	private volatile double localFileMaxSpaceRate;
	private volatile String secret;
	private volatile String domain;

	private String mycloudUsername;
	private String mycloudPassword;

	private volatile String hdfsUrl;

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

	public String getMycloudPath() {
		return mycloudPath;
	}

	public void setMycloudPath(String mycloudPath) {
		this.mycloudPath = mycloudPath;
	}

	public int getDownloadUrlConnectTimeout() {
		return downloadUrlConnectTimeout;
	}

	public void setDownloadUrlConnectTimeout(int downloadUrlConnectTimeout) {
		this.downloadUrlConnectTimeout = downloadUrlConnectTimeout;
	}

	public double getLocalFileMaxSpaceRate() {
		return localFileMaxSpaceRate;
	}

	public void setLocalFileMaxSpaceRate(double localFileMaxSpaceRate) {
		this.localFileMaxSpaceRate = localFileMaxSpaceRate;
	}

	public String getSecret() {
		return secret;
	}

	public void setSecret(String secret) {
		this.secret = secret;
	}

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public String getMycloudUsername() {
		return mycloudUsername;
	}

	public void setMycloudUsername(String mycloudUsername) {
		this.mycloudUsername = mycloudUsername;
	}

	public String getMycloudPassword() {
		return mycloudPassword;
	}

	public void setMycloudPassword(String mycloudPassword) {
		this.mycloudPassword = mycloudPassword;
	}

	public String getHdfsUrl() {
		return hdfsUrl;
	}

	public void setHdfsUrl(String hdfsUrl) {
		this.hdfsUrl = hdfsUrl;
	}
}
