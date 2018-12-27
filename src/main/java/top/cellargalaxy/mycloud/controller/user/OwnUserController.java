package top.cellargalaxy.mycloud.controller.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.cellargalaxy.mycloud.model.po.OwnPo;
import top.cellargalaxy.mycloud.model.po.UserPo;
import top.cellargalaxy.mycloud.model.query.OwnQuery;
import top.cellargalaxy.mycloud.service.OwnService;
import top.cellargalaxy.mycloud.service.security.SecurityServiceImpl;
import top.cellargalaxy.mycloud.util.model.Vo;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by cellargalaxy on 18-8-4.
 */
@PreAuthorize("hasAuthority('USER')")
@RestController
@RequestMapping(OwnUserController.URL)
public class OwnUserController {
	public static final String URL = "/user/own";
	@Autowired
	private OwnService ownService;

	@PostMapping("/addOwn")
	public Vo addOwn(HttpServletRequest request, OwnPo ownPo) {
		UserPo userPo = SecurityServiceImpl.getSecurityUser(request);
		return new Vo(ownService.addOwn(userPo, ownPo), null);
	}

	@PostMapping("/changeOwn")
	public Vo changeOwn(HttpServletRequest request, OwnPo ownPo) {
		UserPo userPo = SecurityServiceImpl.getSecurityUser(request);
		return new Vo(ownService.changeOwn(userPo, ownPo), null);
	}

	@GetMapping("/getOwn")
	public Vo getOwn(HttpServletRequest request, OwnPo ownPo) {
		UserPo userPo = SecurityServiceImpl.getSecurityUser(request);
		return new Vo(null, ownService.getOwn(userPo, ownPo));
	}

	@GetMapping("/listOwn")
	public Vo listOwn(HttpServletRequest request, OwnQuery ownQuery) {
		UserPo userPo = SecurityServiceImpl.getSecurityUser(request);
		return new Vo(null, ownService.listPageOwn(userPo, ownQuery));
	}

	@GetMapping("/getOwnCount")
	public Vo getOwnCount(OwnQuery ownQuery) {
		return new Vo(null, ownService.getOwnCount(ownQuery));
	}

	@GetMapping("/listSort")
	public Vo listSort(HttpServletRequest request) {
		UserPo userPo = SecurityServiceImpl.getSecurityUser(request);
		return new Vo(null, ownService.listSort(userPo));
	}
}
