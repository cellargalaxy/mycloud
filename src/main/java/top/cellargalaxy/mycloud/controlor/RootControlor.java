package top.cellargalaxy.mycloud.controlor;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author cellargalaxy
 * @time 2018/7/23
 */
@Controller
@RequestMapping
public class RootControlor {

	@GetMapping
	public String root() {
		return "mycloud";
	}

	@GetMapping("/login")
	public String login() {
		return "login";
	}

	@ResponseBody
	@GetMapping("/{md5}")
	public void navbar(@PathVariable("md5") String md5, HttpServletResponse response) {
		try {
			response.getWriter().write(md5);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
