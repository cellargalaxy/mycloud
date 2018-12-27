package top.cellargalaxy.mycloud.configuration;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author cellargalaxy
 * @time 2018/7/17
 */
@Data
@Component
@ConfigurationProperties(prefix = "mycloud")
public class MycloudConfiguration {

	private volatile String webUploadMaxFileSize;
	private volatile String webUploadMaxRequestSize;

	private volatile String mycloudPath;
	private volatile double localFileMaxSpaceRate;
	private volatile int downloadUrlConnectTimeout;
	private volatile long maxTmpFileSaveTime;
	private volatile String secret;
	private volatile String domain;

	@Value("${mycloud.db.type}")
	private volatile String dbType;
	@Value("${mycloud.db.sqlitePath}")
	private volatile String sqlitePath;

	private volatile String hdfsUrl;
}
