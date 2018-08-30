package top.cellargalaxy.mycloud.controlor.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import top.cellargalaxy.mycloud.model.po.UserPo;
import top.cellargalaxy.mycloud.model.query.UserQuery;
import top.cellargalaxy.mycloud.model.vo.Vo;
import top.cellargalaxy.mycloud.service.UserService;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by cellargalaxy on 18-8-4.
 */
@PreAuthorize("hasAuthority('USER')")
@RestController
@RequestMapping(UserUserController.URL)
public class UserUserController {
	public static final String URL = "/user/user";
	public static final String USER_KEY = "user";
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private UserService userService;

	@GetMapping("/getUser")
	public Vo getUser(HttpServletRequest request, UserQuery userQuery) {
		UserPo userPo = (UserPo) request.getAttribute(UserUserController.USER_KEY);
		logger.info("getUser:{}", userPo);
		return new Vo(null, userService.getUser(userPo, userQuery));
	}

	@GetMapping("/getUserAuthorization")
	public Vo getUserAuthorization(HttpServletRequest request, UserQuery userQuery) {
		UserPo userPo = (UserPo) request.getAttribute(UserUserController.USER_KEY);
		logger.info("getUserAuthorization:{}", userPo);
		return new Vo(null, userService.getUserAuthorization(userPo, userQuery));
	}

	@PostMapping("/changeUser")
	public Vo changeUser(HttpServletRequest request, UserQuery userQuery) {
		UserPo userPo = (UserPo) request.getAttribute(UserUserController.USER_KEY);
		String string = userService.changeUser(userPo, userQuery);
		logger.info("changeUser:{}, userPo:{}", string, userPo);
		return new Vo(string, null);
	}

	@GetMapping("/checkChangeUser")
	public Vo checkChangeUser(UserPo userPo) {
		String string = userService.checkChangeUser(userPo);
		logger.info("checkChangeUser:{}", string);
		return new Vo(string, null);
	}
}
