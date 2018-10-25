package top.cellargalaxy.mycloud.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.MultipartConfigElement;
import java.io.File;

/**
 * Created by cellargalaxy on 18-8-4.
 */
@Configuration
public class WebUploadConfigBeans {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Bean
	MultipartConfigElement multipartConfigElement(MycloudConfiguration mycloudConfiguration) {
		String mycloudTmpPath = mycloudConfiguration.getMycloudPath() + File.separator + "tmp";
		String webUploadMaxFileSize = mycloudConfiguration.getWebUploadMaxFileSize();
		String webUploadMaxRequestSize = mycloudConfiguration.getWebUploadMaxRequestSize();
		logger.info("mycloudTmpPath: {}; webUploadMaxFileSize: {}; webUploadMaxRequestSize: {}", mycloudTmpPath, webUploadMaxFileSize, webUploadMaxRequestSize);

		MultipartConfigFactory factory = new MultipartConfigFactory();
		File folder = new File(mycloudTmpPath);
		if (!folder.exists()) {
			folder.mkdirs();
		}
		factory.setLocation(mycloudTmpPath);

		factory.setMaxFileSize(webUploadMaxFileSize);
		factory.setMaxRequestSize(webUploadMaxRequestSize);
		return factory.createMultipartConfig();
	}
}
