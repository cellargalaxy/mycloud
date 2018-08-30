package top.cellargalaxy.mycloud.controlor.admin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import top.cellargalaxy.mycloud.model.po.UserPo;
import top.cellargalaxy.mycloud.model.query.UserQuery;
import top.cellargalaxy.mycloud.model.vo.Vo;
import top.cellargalaxy.mycloud.service.UserService;

/**
 * Created by cellargalaxy on 18-7-19.
 */
@PreAuthorize("hasAuthority('ADMIN')")
@RestController
@RequestMapping(UserAdminController.URL)
public class UserAdminController {
	public static final String URL = "/admin/user";
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private UserService userService;

	@PreAuthorize("hasAuthority('ROOT')")
	@PostMapping("/addUser")
	public Vo addUser(UserPo userPo) {
		String string = userService.addUser(userPo);
		logger.info("addUser:{}", string);
		return new Vo(string, null);
	}

	@PreAuthorize("hasAuthority('ROOT')")
	@PostMapping("/removeUser")
	public Vo removeUser(UserQuery userQuery) {
		String string = userService.removeUser(userQuery);
		logger.info("removeUser:{}", string);
		return new Vo(string, null);
	}

	@GetMapping("/getUser")
	public Vo getUser(UserQuery userQuery) {
		logger.info("getUser");
		return new Vo(null, userService.getUser(userQuery));
	}

	@GetMapping("/getUserCount")
	public Vo getUserCount(UserQuery userQuery) {
		logger.info("getUserCount");
		return new Vo(null, userService.getUserCount(userQuery));
	}

	@GetMapping("/listUser")
	public Vo listUser(UserQuery userQuery) {
		logger.info("listUser");
		return new Vo(null, userService.listUser(userQuery));
	}

	@GetMapping("/getUserAuthorization")
	public Vo getUserAuthorization(UserQuery userQuery) {
		logger.info("getUserAuthorization");
		return new Vo(null, userService.getUserAuthorization(userQuery));
	}

	@GetMapping("/listUserAuthorization")
	public Vo listUserAuthorization(UserQuery userQuery) {
		logger.info("listUserAuthorization");
		return new Vo(null, userService.listUserAuthorization(userQuery));
	}

	@GetMapping("/getUserOwn")
	public Vo getUserOwn(UserQuery userQuery) {
		logger.info("getUserOwn");
		return new Vo(null, userService.getUserOwn(userQuery));
	}

	@GetMapping("/listUserOwn")
	public Vo listUserOwn(UserQuery userQuery) {
		logger.info("listUserOwn");
		return new Vo(null, userService.listUserOwn(userQuery));
	}

	@PreAuthorize("hasAuthority('ROOT')")
	@PostMapping("/changeUser")
	public Vo changeUser(UserQuery userQuery) {
		String string = userService.changeUser(userQuery);
		logger.info("changeUser:{}", string);
		return new Vo(string, null);
	}

	@GetMapping("/checkAddUser")
	public Vo checkAddUser(UserPo userPo) {
		String string = userService.checkAddUser(userPo);
		logger.info("checkAddUser:{}", string);
		return new Vo(string, null);
	}

	@GetMapping("/checkChangeUser")
	public Vo checkChangeUser(UserPo userPo) {
		String string = userService.checkChangeUser(userPo);
		logger.info("checkChangeUser:{}", string);
		return new Vo(string, null);
	}
}
