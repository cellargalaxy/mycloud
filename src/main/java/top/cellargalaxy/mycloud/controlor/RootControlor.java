package top.cellargalaxy.mycloud.controlor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by cellargalaxy on 18-7-14.
 */
@Controller
@RequestMapping(RootControlor.URL)
public class RootControlor {
	public static final String URL = "";
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@GetMapping("/")
	public String login() {
		return "global";
	}
}
