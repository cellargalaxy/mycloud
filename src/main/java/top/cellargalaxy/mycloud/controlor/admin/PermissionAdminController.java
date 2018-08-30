package top.cellargalaxy.mycloud.controlor.admin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import top.cellargalaxy.mycloud.model.po.PermissionPo;
import top.cellargalaxy.mycloud.model.query.PermissionQuery;
import top.cellargalaxy.mycloud.model.vo.Vo;
import top.cellargalaxy.mycloud.service.PermissionService;

/**
 * @author cellargalaxy
 * @time 2018/7/20
 */
@PreAuthorize("hasAuthority('ROOT')")
@RestController
@RequestMapping(PermissionAdminController.URL)
public class PermissionAdminController {
	public static final String URL = "/admin/permission";
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private PermissionService permissionService;

	@PostMapping("/addPermission")
	public Vo addPermission(PermissionPo permissionPo) {
		String string = permissionService.addPermission(permissionPo);
		logger.info("addPermission:{}", string);
		return new Vo(string, null);
	}

	@PostMapping("/removePermission")
	public Vo removePermission(PermissionQuery permissionQuery) {
		String string = permissionService.removePermission(permissionQuery);
		logger.info("removePermission:{}", string);
		return new Vo(string, null);
	}

	@GetMapping("/getPermission")
	public Vo getPermission(PermissionQuery permissionQuery) {
		logger.info("getPermission");
		return new Vo(null, permissionService.getPermission(permissionQuery));
	}

	@GetMapping("/getPermissionCount")
	public Vo getPermissionCount(PermissionQuery permissionQuery) {
		logger.info("getPermissionCount");
		return new Vo(null, permissionService.getPermissionCount(permissionQuery));
	}

	@GetMapping("/listPermission")
	public Vo listPermission(PermissionQuery permissionQuery) {
		logger.info("listPermission");
		return new Vo(null, permissionService.listPermission(permissionQuery));
	}

	@GetMapping("/getPermissionAuthorization")
	public Vo getPermissionAuthorization(PermissionQuery permissionQuery) {
		logger.info("getPermissionAuthorization");
		return new Vo(null, permissionService.getPermissionAuthorization(permissionQuery));
	}

	@GetMapping("/listPermissionAuthorization")
	public Vo listPermissionAuthorization(PermissionQuery permissionQuery) {
		logger.info("listPermissionAuthorization");
		return new Vo(null, permissionService.listPermissionAuthorization(permissionQuery));
	}

	@PostMapping("/changePermission")
	public Vo changePermission(PermissionPo permissionPo) {
		String string = permissionService.changePermission(permissionPo);
		logger.info("changePermission:{}", string);
		return new Vo(string, null);
	}

	@GetMapping("/checkAddPermission")
	public Vo checkAddPermission(PermissionPo permissionPo) {
		String string = permissionService.checkAddPermission(permissionPo);
		logger.info("checkAddPermission:{}", string);
		return new Vo(string, null);
	}

	@GetMapping("/checkChangePermission")
	public Vo checkChangePermission(PermissionPo permissionPo) {
		String string = permissionService.checkChangePermission(permissionPo);
		logger.info("checkChangePermission:{}", string);
		return new Vo(string, null);
	}
}
