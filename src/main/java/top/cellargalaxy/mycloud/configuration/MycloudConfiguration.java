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
	@Value("${webUploadMaxFileSize:1000MB}")
	private String webUploadMaxFileSize;
	@Value("${webUploadMaxRequestSize:1000MB}")
	private String webUploadMaxRequestSize;

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
}
