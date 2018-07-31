package top.cellargalaxy.mycloud.controlor.admin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import top.cellargalaxy.mycloud.exception.GlobalException;
import top.cellargalaxy.mycloud.model.vo.Vo;
import top.cellargalaxy.mycloud.service.LogService;
import top.cellargalaxy.mycloud.service.impl.LogServiceImpl;

/**
 * @author cellargalaxy
 * @time 2018/7/20
 */
@PreAuthorize("hasAuthority('ADMIN')")
@RestController
@RequestMapping(LogApiController.URL)
public class LogApiController {
	public static final String URL = "/admin/log";
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

	@GetMapping("/getExceptionInfoCount")
	public Vo getExceptionInfoCount() {
		try {
			logger.info("getExceptionInfoCount");
			return new Vo(null, logService.getExceptionInfoCount());
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
