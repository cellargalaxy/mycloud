package top.cellargalaxy.controlor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import top.cellargalaxy.service.MycloudService;

import javax.servlet.http.HttpSession;

/**
 * Created by cellargalaxy on 17-12-2.
 */
@Controller
@RequestMapping(RootControlor.ROOT_CONTROLOR_URL)
public class RootControlor {
	public static final String ROOT_CONTROLOR_URL = "";
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private MycloudService service;
	
	@GetMapping("/")
	public String login(Model model, HttpSession session) {
		Object object = session.getAttribute("info");
		if (object != null) {
			session.setAttribute("info", null);
			model.addAttribute("info", object.toString());
		}
		return "login";
	}
	
	@PostMapping("/")
	public String login(Model model, HttpSession session, String token) {
		if (!service.checkToken(token)) {
			model.addAttribute("info", "口令错误");
			logger.info("口令错误");
			return "login";
		}
		session.setAttribute("token", token);
		logger.info("登录成功");
		return "redirect:/admin/1";
	}
}
