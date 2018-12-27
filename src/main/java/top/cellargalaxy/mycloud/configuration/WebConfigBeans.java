package top.cellargalaxy.mycloud.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.support.GenericConversionService;
import org.springframework.web.bind.support.ConfigurableWebBindingInitializer;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
import top.cellargalaxy.mycloud.service.PathService;

import javax.annotation.PostConstruct;
import javax.servlet.MultipartConfigElement;
import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author cellargalaxy
 * @time 2018/7/20
 */
@SpringBootConfiguration
public class WebConfigBeans {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private RequestMappingHandlerAdapter handlerAdapter;

	/**
	 * http参数的日期字符串转Date对象
	 */
	@PostConstruct
	public void dataConfig() {
		ConfigurableWebBindingInitializer initializer = (ConfigurableWebBindingInitializer) handlerAdapter.getWebBindingInitializer();
		if (initializer.getConversionService() != null) {
			GenericConversionService genericConversionService = (GenericConversionService) initializer.getConversionService();
			genericConversionService.addConverter(new Converter<String, Date>() {
				private final DateFormat DATE_FORMAT_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				private final DateFormat SHORT_DATE_FORMAT_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

				@Override
				public Date convert(String string) {
					if (string == null || (string = string.trim()).length() == 0) {
						return null;
					}
					try {
						if (string.matches("^\\d+$")) {
							return new Date(Long.valueOf(string) * 1000);
						}
						if (string.contains(":")) {
							return DATE_FORMAT_FORMAT.parse(string);
						} else {
							return SHORT_DATE_FORMAT_FORMAT.parse(string);
						}
					} catch (Exception e) {
						return null;
					}
				}
			});
		}
	}

	@Bean
	public MultipartConfigElement multipartConfigElement(MycloudConfiguration mycloudConfiguration, PathService pathService) {
		File mycloudTmpFolder = pathService.getTmpFolder();
		if (!mycloudTmpFolder.exists()) {
			mycloudTmpFolder.mkdirs();
		}
		String webUploadMaxFileSize = mycloudConfiguration.getWebUploadMaxFileSize();
		String webUploadMaxRequestSize = mycloudConfiguration.getWebUploadMaxRequestSize();
		logger.info("webUploadMaxFileSize: {}; webUploadMaxRequestSize: {}; mycloudTmpPath: {}", webUploadMaxFileSize, webUploadMaxRequestSize, mycloudTmpFolder);

		MultipartConfigFactory factory = new MultipartConfigFactory();
		factory.setLocation(mycloudTmpFolder.getAbsolutePath());
		factory.setMaxFileSize(webUploadMaxFileSize);
		factory.setMaxRequestSize(webUploadMaxRequestSize);
		return factory.createMultipartConfig();
	}
}
