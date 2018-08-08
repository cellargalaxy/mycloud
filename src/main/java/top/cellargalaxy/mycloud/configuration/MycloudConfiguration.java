package top.cellargalaxy.mycloud.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

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
	@Value("${domain:http://127.0.0.1:8080}")
	private String domain;

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

	public String getTmpPath() {
		return System.getProperty("user.dir") + File.separator + ".mycloud";
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
}
