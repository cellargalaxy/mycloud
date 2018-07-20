package top.cellargalaxy.mycloud.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.support.GenericConversionService;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.ConfigurableWebBindingInitializer;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

import javax.annotation.PostConstruct;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author cellargalaxy
 * @time 2018/7/20
 */
@Component
public class WebDateConfigBeans {
	@Autowired
	private RequestMappingHandlerAdapter handlerAdapter;

	/**
	 * 增加字符串转日期的功能
	 */

	@PostConstruct
	public void initEditableAvlidation() {

		ConfigurableWebBindingInitializer initializer = (ConfigurableWebBindingInitializer) handlerAdapter.getWebBindingInitializer();
		if (initializer.getConversionService() != null) {
			GenericConversionService genericConversionService = (GenericConversionService) initializer.getConversionService();
			genericConversionService.addConverter(new Converter<String, Date>() {
				DateFormat DATE_FORMAT_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				DateFormat SHORT_DATE_FORMAT_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

				@Override
				public Date convert(String s) {
					if (s == null) {
						return null;
					}
					s = s.trim();
					if (s.length() == 0) {
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
}
