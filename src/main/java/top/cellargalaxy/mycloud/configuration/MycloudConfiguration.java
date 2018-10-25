package top.cellargalaxy.mycloud.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author cellargalaxy
 * @time 2018/7/17
 */
@Component
public class MycloudConfiguration {
	@Value("${secret}")
	private volatile String secret;
	@Value("${webUploadMaxFileSize}")
	private volatile String webUploadMaxFileSize;
	@Value("${webUploadMaxRequestSize}")
	private volatile String webUploadMaxRequestSize;
	@Value("${domain}")
	private volatile String domain;
	@Value("${mycloudPath}")
	private volatile String mycloudPath;

	@Value("${installationType}")
	private volatile String installationType;

	@Value("${localFileMaxSpaceRate}")
	private volatile double localFileMaxSpaceRate;

	public String getSecret() {
		return secret;
	}

	public void setSecret(String secret) {
		this.secret = secret;
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

	public String getMycloudPath() {
		return mycloudPath;
	}

	public void setMycloudPath(String mycloudPath) {
		this.mycloudPath = mycloudPath;
	}

	public String getInstallationType() {
		return installationType;
	}

	public void setInstallationType(String installationType) {
		this.installationType = installationType;
	}

	public double getLocalFileMaxSpaceRate() {
		return localFileMaxSpaceRate;
	}

	public void setLocalFileMaxSpaceRate(double localFileMaxSpaceRate) {
		this.localFileMaxSpaceRate = localFileMaxSpaceRate;
	}
}
