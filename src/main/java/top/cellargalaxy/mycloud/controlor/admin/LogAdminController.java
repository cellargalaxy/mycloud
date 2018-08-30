package top.cellargalaxy.mycloud.controlor.admin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import top.cellargalaxy.mycloud.model.vo.Vo;
import top.cellargalaxy.mycloud.service.ExceptionLogService;

/**
 * @author cellargalaxy
 * @time 2018/7/20
 */
@PreAuthorize("hasAuthority('ADMIN')")
@RestController
@RequestMapping(LogAdminController.URL)
public class LogAdminController {
	public static final String URL = "/admin/log";
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private ExceptionLogService exceptionLogService;

	@PostMapping("/clearExceptionInfo")
	public Vo clearExceptionInfo() {
		logger.info("clearExceptionInfo");
		return new Vo(null, exceptionLogService.clearExceptionInfo());
	}

	@GetMapping("/getExceptionInfoCount")
	public Vo getExceptionInfoCount() {
		logger.info("getExceptionInfoCount");
		return new Vo(null, exceptionLogService.getExceptionInfoCount());
	}

	@GetMapping("/listExceptionInfo")
	public Vo listExceptionInfo() {
		logger.info("listExceptionInfo");
		return new Vo(null, exceptionLogService.listExceptionInfo());
	}


}
