package top.cellargalaxy.mycloud.controlor;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author cellargalaxy
 * @time 2018/7/23
 */
@Controller
@RequestMapping("/page")
//@PreAuthorize("hasRole('USER')")
public class AdminControlor {
	@GetMapping("/exceptionInfo")
	public String exceptionInfo() {
		return "exceptionInfo";
	}

	@GetMapping("/user")
	public String user() {
		return "user";
	}

	@GetMapping("/permission")
	public String permission() {
		return "permission";
	}

	@GetMapping("/authorization")
	public String authorization() {
		return "authorization";
	}

	@GetMapping("/own")
	public String own() {
		return "own";
	}
}
