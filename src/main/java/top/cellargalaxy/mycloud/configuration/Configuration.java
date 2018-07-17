package top.cellargalaxy.mycloud.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author cellargalaxy
 * @time 2018/7/17
 */
@Component
public class Configuration {
	@Value("${blobLength:16777215}")
	private int blobLength;

	public int getBlobLength() {
		return blobLength;
	}

	public void setBlobLength(int blobLength) {
		this.blobLength = blobLength;
	}
}
