package top.cellargalaxy.mycloud.controller.guest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.cellargalaxy.mycloud.service.OwnExpireService;
import top.cellargalaxy.mycloud.util.model.Vo;

/**
 * @author cellargalaxy
 * @time 18-12-28
 */
@RestController
@RequestMapping(OwnExpireController.URL)
public class OwnExpireController {
	public static final String URL = "/guest/ownExpire";

	@Autowired
	private OwnExpireService ownExpireService;

	@GetMapping("/listRecentExpireOwn")
	public Vo listRecentExpireOwn() {
		return new Vo(null, ownExpireService.listRecentExpireOwn());
	}
}
