package top.cellargalaxy.mycloud.controlor.admin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import top.cellargalaxy.mycloud.model.po.AuthorizationPo;
import top.cellargalaxy.mycloud.model.query.AuthorizationQuery;
import top.cellargalaxy.mycloud.model.vo.Vo;
import top.cellargalaxy.mycloud.service.AuthorizationService;

/**
 * @author cellargalaxy
 * @time 2018/7/20
 */
@PreAuthorize("hasAuthority('ROOT')")
@RestController
@RequestMapping(AuthorizationAdminController.URL)
public class AuthorizationAdminController {
	public static final String URL = "/admin/authorization";
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private AuthorizationService authorizationService;

	@PostMapping("/addAuthorization")
	public Vo addAuthorization(AuthorizationPo authorizationPo) {
		String string = authorizationService.addAuthorization(authorizationPo);
		logger.info("addAuthorization:{}", string);
		return new Vo(string, null);
	}

	@PostMapping("/removeAuthorization")
	public Vo removeAuthorization(AuthorizationQuery authorizationQuery) {
		String string = authorizationService.removeAuthorization(authorizationQuery);
		logger.info("removeAuthorization:{}", string);
		return new Vo(string, null);
	}

	@GetMapping("/getAuthorization")
	public Vo getAuthorization(AuthorizationQuery authorizationQuery) {
		logger.info("getAuthorization");
		return new Vo(null, authorizationService.getAuthorization(authorizationQuery));
	}

	@GetMapping("/getAuthorizationCount")
	public Vo getAuthorizationCount(AuthorizationQuery authorizationQuery) {
		logger.info("getAuthorizationCount");
		return new Vo(null, authorizationService.getAuthorizationCount(authorizationQuery));
	}

	@GetMapping("/listAuthorization")
	public Vo listAuthorization(AuthorizationQuery authorizationQuery) {
		logger.info("listAuthorization");
		return new Vo(null, authorizationService.listAuthorization(authorizationQuery));
	}

	@PostMapping("/changeAuthorization")
	public Vo changeAuthorization(AuthorizationPo authorizationPo) {
		String string = authorizationService.changeAuthorization(authorizationPo);
		logger.info("changeAuthorization:{}", string);
		return new Vo(string, null);
	}

	@GetMapping("/checkAddAuthorization")
	public Vo checkAddAuthorization(AuthorizationPo authorizationPo) {
		String string = authorizationService.checkAddAuthorization(authorizationPo);
		logger.info("checkAddAuthorization:{}", string);
		return new Vo(string, null);
	}

	@GetMapping("/checkChangeAuthorization")
	public Vo checkChangeAuthorization(AuthorizationPo authorizationPo) {
		String string = authorizationService.checkChangeAuthorization(authorizationPo);
		logger.info("checkChangeAuthorization:{}", string);
		return new Vo(string, null);
	}
}
