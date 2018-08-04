package top.cellargalaxy.mycloud.configuration;

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
	@Bean
	MultipartConfigElement multipartConfigElement(MycloudConfiguration mycloudConfiguration) {
		MultipartConfigFactory factory = new MultipartConfigFactory();
		File file = new File(mycloudConfiguration.getTmpPath());
		if (!file.exists()) {
			file.mkdirs();
		}
		factory.setLocation(mycloudConfiguration.getTmpPath());

		factory.setMaxFileSize(mycloudConfiguration.getWebUploadMaxFileSize());
		factory.setMaxRequestSize(mycloudConfiguration.getWebUploadMaxRequestSize());
		return factory.createMultipartConfig();
	}
}
