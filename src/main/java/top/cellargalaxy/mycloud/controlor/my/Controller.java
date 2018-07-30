package top.cellargalaxy.mycloud.controlor.my;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author cellargalaxy
 * @time 2018/7/27
 */
@org.springframework.stereotype.Controller
@RequestMapping
public class Controller {
@Autowired
private UserDetailsService userDetailsService;

	@ResponseBody
	@GetMapping("/")
	public String home() {
		return "不需要权限";
	}

//	@ResponseBody
//	@RequestMapping("/login")
//	public String login(User user) {
//		return "获取token: "+user;
//	}

	@PreAuthorize("hasRole('USER')")
	@ResponseBody
	@GetMapping("/user")
	public String user() {
		System.out.println(userDetailsService);
		return "USER权限";
	}

	@PreAuthorize("hasAuthority('ADMIN')")
	@ResponseBody
	@GetMapping("/admin")
	public String admin() {
		System.out.println(userDetailsService);
		return "ADMIN权限";
	}


}

