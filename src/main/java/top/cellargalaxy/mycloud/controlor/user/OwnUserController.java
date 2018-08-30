package top.cellargalaxy.mycloud.controlor.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import top.cellargalaxy.mycloud.model.po.OwnPo;
import top.cellargalaxy.mycloud.model.po.UserPo;
import top.cellargalaxy.mycloud.model.query.OwnQuery;
import top.cellargalaxy.mycloud.model.vo.Vo;
import top.cellargalaxy.mycloud.service.OwnService;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by cellargalaxy on 18-8-4.
 */
@PreAuthorize("hasAuthority('USER')")
@RestController
@RequestMapping(OwnUserController.URL)
public class OwnUserController {
	public static final String URL = "/user/own";
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private OwnService ownService;

	@PostMapping("/addOwn")
	public Vo addOwn(HttpServletRequest request, OwnPo ownPo) {
		UserPo userPo = (UserPo) request.getAttribute(UserUserController.USER_KEY);
		String string = ownService.addOwn(userPo, ownPo);
		logger.info("addOwn:{}, userPo:{}", string, userPo);
		return new Vo(string, null);
	}

	@PostMapping("/removeOwn")
	public Vo removeOwn(HttpServletRequest request, OwnQuery ownQuery) {
		UserPo userPo = (UserPo) request.getAttribute(UserUserController.USER_KEY);
		String string = ownService.removeOwn(userPo, ownQuery);
		logger.info("removeOwn:{}, userPo:{}", string, userPo);
		return new Vo(string, null);
	}

	@GetMapping("/getOwn")
	public Vo getOwn(HttpServletRequest request, OwnQuery ownQuery) {
		UserPo userPo = (UserPo) request.getAttribute(UserUserController.USER_KEY);
		logger.info("getOwn:{}", userPo);
		return new Vo(null, ownService.getOwn(userPo, ownQuery));
	}

	@GetMapping("/getOwnCount")
	public Vo getOwnCount(HttpServletRequest request, OwnQuery ownQuery) {
		UserPo userPo = (UserPo) request.getAttribute(UserUserController.USER_KEY);
		logger.info("getOwnCount:{}", userPo);
		return new Vo(null, ownService.getOwnCount(userPo, ownQuery));
	}

	@GetMapping("/listOwn")
	public Vo listOwn(HttpServletRequest request, OwnQuery ownQuery) {
		UserPo userPo = (UserPo) request.getAttribute(UserUserController.USER_KEY);
		logger.info("listOwn:{}", userPo);
		return new Vo(null, ownService.listOwn(userPo, ownQuery));
	}

	@GetMapping("/listSort")
	public Vo listSort(HttpServletRequest request, OwnQuery ownQuery) {
		UserPo userPo = (UserPo) request.getAttribute(UserUserController.USER_KEY);
		logger.info("listSort:{}", userPo);
		return new Vo(null, ownService.listSort(userPo, ownQuery));
	}

	@PostMapping("/changeOwn")
	public Vo changeOwn(HttpServletRequest request, OwnPo ownPo) {
		UserPo userPo = (UserPo) request.getAttribute(UserUserController.USER_KEY);
		String string = ownService.changeOwn(userPo, ownPo);
		logger.info("changeOwn:{}, userPo:{}", string, userPo);
		return new Vo(string, null);
	}

	@GetMapping("/checkAddOwn")
	public Vo checkAddOwn(OwnPo ownPo) {
		String string = ownService.checkAddOwn(ownPo);
		logger.info("checkAddOwn:{}", string);
		return new Vo(string, null);
	}

	@GetMapping("/checkChangeOwn")
	public Vo checkChangeOwn(OwnPo ownPo) {
		String string = ownService.checkChangeOwn(ownPo);
		logger.info("checkChangeOwn:{}", string);
		return new Vo(string, null);
	}
}
