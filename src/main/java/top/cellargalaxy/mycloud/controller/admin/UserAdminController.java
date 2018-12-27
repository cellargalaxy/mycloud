package top.cellargalaxy.mycloud.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.cellargalaxy.mycloud.model.po.UserPo;
import top.cellargalaxy.mycloud.service.UserService;
import top.cellargalaxy.mycloud.util.model.Vo;

/**
 * Created by cellargalaxy on 18-7-19.
 */
@PreAuthorize("hasAuthority('ADMIN')")
@RestController
@RequestMapping(UserAdminController.URL)
public class UserAdminController {
	public static final String URL = "/admin/user";
	@Autowired
	private UserService userService;

	@PostMapping("/addUser")
	public Vo addUser(UserPo userPo) {
		return new Vo(userService.addUser(userPo), null);
	}

	@PostMapping("/removeUser")
	public Vo removeUser(UserPo userPo) {
		return new Vo(userService.removeUser(userPo), null);
	}

	@PostMapping("/changeUser")
	public Vo changeUser(UserPo userPo) {
		return new Vo(userService.changeUser(userPo), null);
	}

	@GetMapping("/getUser")
	public Vo getUser(UserPo userPo) {
		return new Vo(null, userService.getUser(userPo));
	}

	@GetMapping("/getUserVo")
	public Vo getUserVo(UserPo userPo) {
		return new Vo(null, userService.getUserVo(userPo));
	}

	@GetMapping("/listAllUser")
	public Vo listAllUser() {
		return new Vo(null, userService.listAllUser());
	}

	@GetMapping("/listAllUserVo")
	public Vo listAllUserVo() {
		return new Vo(null, userService.listAllUserVo());
	}

	@GetMapping("/listAllPermission")
	public Vo listAllPermission() {
		return new Vo(null, userService.listAllPermission());
	}
}
