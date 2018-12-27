package top.cellargalaxy.mycloud.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.cellargalaxy.mycloud.model.po.AuthorizationPo;
import top.cellargalaxy.mycloud.model.query.AuthorizationQuery;
import top.cellargalaxy.mycloud.service.AuthorizationService;
import top.cellargalaxy.mycloud.util.model.Vo;

/**
 * @author cellargalaxy
 * @time 2018/7/20
 */
@PreAuthorize("hasAuthority('ADMIN')")
@RestController
@RequestMapping(AuthorizationAdminController.URL)
public class AuthorizationAdminController {
	public static final String URL = "/admin/authorization";
	@Autowired
	private AuthorizationService authorizationService;

	@PostMapping("/addAuthorization")
	public Vo addAuthorization(AuthorizationPo authorizationPo) {
		return new Vo(authorizationService.addAuthorization(authorizationPo), null);
	}

	@PostMapping("/removeAuthorization")
	public Vo removeAuthorization(AuthorizationPo authorizationPo) {
		return new Vo(authorizationService.removeAuthorization(authorizationPo), null);
	}

	@GetMapping("/listAuthorizationByUserId")
	public Vo listAuthorizationByUserId(AuthorizationQuery authorizationQuery) {
		return new Vo(null, authorizationService.listAuthorizationByUserId(authorizationQuery));
	}
}
