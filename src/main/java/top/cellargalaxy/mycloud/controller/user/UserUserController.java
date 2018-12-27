package top.cellargalaxy.mycloud.controller.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.cellargalaxy.mycloud.model.po.UserPo;
import top.cellargalaxy.mycloud.service.UserService;
import top.cellargalaxy.mycloud.service.security.SecurityServiceImpl;
import top.cellargalaxy.mycloud.util.model.Vo;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by cellargalaxy on 18-8-4.
 */
@PreAuthorize("hasAuthority('USER')")
@RestController
@RequestMapping(UserUserController.URL)
public class UserUserController {
	public static final String URL = "/user/user";
	@Autowired
	private UserService userService;

	@PostMapping("/changeUser")
	public Vo changeUser(HttpServletRequest request, UserPo newUserPo) {
		UserPo userPo = SecurityServiceImpl.getSecurityUser(request);
		return new Vo(userService.changeUser(userPo, newUserPo), null);
	}
}
