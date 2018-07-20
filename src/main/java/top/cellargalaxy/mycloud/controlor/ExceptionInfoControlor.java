package top.cellargalaxy.mycloud.controlor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author cellargalaxy
 * @time 2018/7/20
 */
@RestController
@RequestMapping(ExceptionInfoControlor.URL)
public class ExceptionInfoControlor {
	public static final String URL="/api/exceptionInfo";
	private Logger logger = LoggerFactory.getLogger(PermissionControlor.class);
}
