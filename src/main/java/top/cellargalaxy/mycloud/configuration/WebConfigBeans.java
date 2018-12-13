package top.cellargalaxy.mycloud.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.support.GenericConversionService;
import org.springframework.web.bind.support.ConfigurableWebBindingInitializer;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

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
@Configuration
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
                public Date convert(String s) {
                    if (s == null || (s = s.trim()).length() == 0) {
                        return null;
                    }
                    try {
                        if (s.matches("^\\d+$")) {
                            return new Date(Long.valueOf(s));
                        }
                        if (s.contains(":")) {
                            return DATE_FORMAT_FORMAT.parse(s);
                        } else {
                            return SHORT_DATE_FORMAT_FORMAT.parse(s);
                        }
                    } catch (Exception e) {
                        return null;
                    }
                }
            });
        }
    }

    @Bean
    public MultipartConfigElement multipartConfigElement(MycloudConfiguration mycloudConfiguration) {
        String mycloudTmpPath = mycloudConfiguration.getMycloudPath() + File.separator + "mycloud" + File.separator + "tmp";
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
