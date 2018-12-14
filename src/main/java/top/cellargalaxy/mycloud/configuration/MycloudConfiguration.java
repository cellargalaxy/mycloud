package top.cellargalaxy.mycloud.configuration;

import lombok.Data;
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
    private volatile int downloadUrlConnectTimeout;
    private volatile double localFileMaxSpaceRate;
    private volatile String secret;
    private volatile String domain;

    private String mycloudUsername;
    private String mycloudPassword;

    private volatile String hdfsUrl;
}
