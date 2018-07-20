package top.cellargalaxy.mycloud.controlor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.cellargalaxy.mycloud.exception.GlobalException;
import top.cellargalaxy.mycloud.model.vo.Vo;
import top.cellargalaxy.mycloud.service.LogService;
import top.cellargalaxy.mycloud.service.impl.LogServiceImpl;

/**
 * @author cellargalaxy
 * @time 2018/7/20
 */
@RestController
@RequestMapping(LogControlor.URL)
public class LogControlor {
	public static final String URL = "/api/log";
	private Logger logger = LoggerFactory.getLogger(LogServiceImpl.class);
	@Autowired
	private LogService logService;

	@PostMapping("/clearExceptionInfo")
	public Vo clearExceptionInfo() {
		try {
			logger.info("clearExceptionInfo");
			return new Vo(null, logService.clearExceptionInfo());
		} catch (Exception e) {
			e.printStackTrace();
			GlobalException.add(e);
			return new Vo(e);
		}
	}

	@GetMapping("/listExceptionInfo")
	public Vo listExceptionInfo() {
		try {
			logger.info("listExceptionInfo");
			return new Vo(null, logService.listExceptionInfo());
		} catch (Exception e) {
			e.printStackTrace();
			GlobalException.add(e);
			return new Vo(e);
		}
	}
}
