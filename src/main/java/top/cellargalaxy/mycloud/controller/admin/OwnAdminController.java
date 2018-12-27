package top.cellargalaxy.mycloud.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.cellargalaxy.mycloud.model.po.OwnPo;
import top.cellargalaxy.mycloud.model.query.OwnQuery;
import top.cellargalaxy.mycloud.service.OwnService;
import top.cellargalaxy.mycloud.util.model.Vo;

/**
 * @author cellargalaxy
 * @time 2018/7/20
 */
@PreAuthorize("hasAuthority('ADMIN')")
@RestController
@RequestMapping(OwnAdminController.URL)
public class OwnAdminController {
	public static final String URL = "/admin/own";
	@Autowired
	private OwnService ownService;

	@PostMapping("/addOwn")
	public Vo addOwn(OwnPo ownPo) {
		return new Vo(ownService.addOwn(ownPo), null);
	}

	@PostMapping("/removeOwn")
	public Vo removeOwn(OwnPo ownPo) {
		return new Vo(ownService.removeOwn(ownPo), null);
	}

	@PostMapping("/changeOwn")
	public Vo changeOwn(OwnPo ownPo) {
		return new Vo(ownService.changeOwn(ownPo), null);
	}

	@GetMapping("/getOwn")
	public Vo getOwn(OwnPo ownPo) {
		return new Vo(null, ownService.getOwn(ownPo));
	}

	@GetMapping("/listOwn")
	public Vo listOwn(OwnQuery ownQuery) {
		return new Vo(null, ownService.listPageOwn(ownQuery));
	}

	@GetMapping("/getOwnCount")
	public Vo getOwnCount(OwnQuery ownQuery) {
		return new Vo(null, ownService.getOwnCount(ownQuery));
	}

	@GetMapping("/listSort")
	public Vo listSort() {
		return new Vo(null, ownService.listSort());
	}
}
