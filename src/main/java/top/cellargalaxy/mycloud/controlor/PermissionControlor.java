package top.cellargalaxy.mycloud.controlor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.cellargalaxy.mycloud.exception.GlobalException;
import top.cellargalaxy.mycloud.model.po.PermissionPo;
import top.cellargalaxy.mycloud.model.query.PermissionQuery;
import top.cellargalaxy.mycloud.model.vo.Vo;
import top.cellargalaxy.mycloud.service.PermissionService;

/**
 * @author cellargalaxy
 * @time 2018/7/20
 */
@RestController
@RequestMapping(PermissionControlor.URL)
public class PermissionControlor {
	public static final String URL = "/api/permission";
	private Logger logger = LoggerFactory.getLogger(PermissionControlor.class);
	@Autowired
	private PermissionService permissionService;

	@PostMapping("/addPermission")
	public Vo addPermission(PermissionPo permissionPo) {
		try {
			String string = permissionService.addPermission(permissionPo);
			logger.info("addPermission:{}", string);
			return new Vo(string, null);
		} catch (Exception e) {
			e.printStackTrace();
			GlobalException.add(e);
			return new Vo(e);
		}
	}

	@PostMapping("/removePermission")
	public Vo removePermission(PermissionQuery permissionQuery) {
		try {
			String string = permissionService.removePermission(permissionQuery);
			logger.info("removePermission:{}", string);
			return new Vo(string, null);
		} catch (Exception e) {
			e.printStackTrace();
			GlobalException.add(e);
			return new Vo(e);
		}
	}

	@GetMapping("/getPermission")
	public Vo getPermission(PermissionQuery permissionQuery) {
		try {
			logger.info("getPermission");
			return new Vo(null, permissionService.getPermission(permissionQuery));
		} catch (Exception e) {
			e.printStackTrace();
			GlobalException.add(e);
			return new Vo(e);
		}
	}

	@GetMapping("/listPermission")
	public Vo listPermission(PermissionQuery permissionQuery) {
		try {
			logger.info("listPermission");
			return new Vo(null, permissionService.listPermission(permissionQuery));
		} catch (Exception e) {
			e.printStackTrace();
			GlobalException.add(e);
			return new Vo(e);
		}
	}

	@GetMapping("/getPermissionAuthorization")
	public Vo getPermissionAuthorization(PermissionQuery permissionQuery) {
		try {
			logger.info("getPermissionAuthorization");
			return new Vo(null, permissionService.getPermissionAuthorization(permissionQuery));
		} catch (Exception e) {
			e.printStackTrace();
			GlobalException.add(e);
			return new Vo(e);
		}
	}

	@GetMapping("/listPermissionAuthorization")
	public Vo listPermissionAuthorization(PermissionQuery permissionQuery) {
		try {
			logger.info("listPermissionAuthorization");
			return new Vo(null, permissionService.listPermissionAuthorization(permissionQuery));
		} catch (Exception e) {
			e.printStackTrace();
			GlobalException.add(e);
			return new Vo(e);
		}
	}

	@PostMapping("/changePermission")
	public Vo changePermission(PermissionPo permissionPo) {
		try {
			String string = permissionService.changePermission(permissionPo);
			logger.info("changePermission:{}", string);
			return new Vo(string, null);
		} catch (Exception e) {
			e.printStackTrace();
			GlobalException.add(e);
			return new Vo(e);
		}
	}

	@GetMapping("/checkAddPermission")
	public Vo checkAddPermission(PermissionPo permissionPo) {
		try {
			String string = permissionService.checkAddPermission(permissionPo);
			logger.info("checkAddPermission:{}", string);
			return new Vo(string, null);
		} catch (Exception e) {
			e.printStackTrace();
			GlobalException.add(e);
			return new Vo(e);
		}
	}

	@GetMapping("/checkChangePermission")
	public Vo checkChangePermission(PermissionPo permissionPo) {
		try {
			String string = permissionService.checkChangePermission(permissionPo);
			logger.info("checkChangePermission:{}", string);
			return new Vo(string, null);
		} catch (Exception e) {
			e.printStackTrace();
			GlobalException.add(e);
			return new Vo(e);
		}
	}
}
