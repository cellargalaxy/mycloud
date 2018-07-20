package top.cellargalaxy.mycloud.controlor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.cellargalaxy.mycloud.exception.GlobalException;
import top.cellargalaxy.mycloud.model.po.AuthorizationPo;
import top.cellargalaxy.mycloud.model.query.AuthorizationQuery;
import top.cellargalaxy.mycloud.model.vo.Vo;
import top.cellargalaxy.mycloud.service.AuthorizationService;

/**
 * @author cellargalaxy
 * @time 2018/7/20
 */
@RestController
@RequestMapping(AuthorizationControlor.URL)
public class AuthorizationControlor {
	public static final String URL = "/api/authorization";
	private Logger logger = LoggerFactory.getLogger(AuthorizationControlor.class);
	@Autowired
	private AuthorizationService authorizationService;

	@PostMapping("/addAuthorization")
	public Vo addAuthorization(AuthorizationPo authorizationPo) {
		try {
			String string = authorizationService.addAuthorization(authorizationPo);
			logger.info("addAuthorization:{}", string);
			return new Vo(string, null);
		} catch (Exception e) {
			e.printStackTrace();
			GlobalException.add(e);
			return new Vo(e);
		}
	}

	@PostMapping("/removeAuthorization")
	public Vo removeAuthorization(AuthorizationQuery authorizationQuery) {
		try {
			String string = authorizationService.removeAuthorization(authorizationQuery);
			logger.info("removeAuthorization:{}", string);
			return new Vo(string, null);
		} catch (Exception e) {
			e.printStackTrace();
			GlobalException.add(e);
			return new Vo(e);
		}
	}

	@GetMapping("/getAuthorization")
	public Vo getAuthorization(AuthorizationQuery authorizationQuery) {
		try {
			logger.info("getAuthorization");
			return new Vo(null, authorizationService.getAuthorization(authorizationQuery));
		} catch (Exception e) {
			e.printStackTrace();
			GlobalException.add(e);
			return new Vo(e);
		}
	}

	@GetMapping("/listAuthorization")
	public Vo listAuthorization(AuthorizationQuery authorizationQuery) {
		try {
			logger.info("listAuthorization");
			return new Vo(null, authorizationService.listAuthorization(authorizationQuery));
		} catch (Exception e) {
			e.printStackTrace();
			GlobalException.add(e);
			return new Vo(e);
		}
	}

	@PostMapping("/changeAuthorization")
	public Vo changeAuthorization(AuthorizationPo authorizationPo) {
		try {
			String string = authorizationService.changeAuthorization(authorizationPo);
			logger.info("changeAuthorization:{}", string);
			return new Vo(string, null);
		} catch (Exception e) {
			e.printStackTrace();
			GlobalException.add(e);
			return new Vo(e);
		}
	}

	@GetMapping("/checkAddAuthorization")
	public Vo checkAddAuthorization(AuthorizationPo authorizationPo) {
		try {
			String string = authorizationService.checkAddAuthorization(authorizationPo);
			logger.info("checkAddAuthorization:{}", string);
			return new Vo(string, null);
		} catch (Exception e) {
			e.printStackTrace();
			GlobalException.add(e);
			return new Vo(e);
		}
	}

	@GetMapping("/checkChangeAuthorization")
	public Vo checkChangeAuthorization(AuthorizationPo authorizationPo) {
		try {
			String string = authorizationService.checkChangeAuthorization(authorizationPo);
			logger.info("checkChangeAuthorization:{}", string);
			return new Vo(string, null);
		} catch (Exception e) {
			e.printStackTrace();
			GlobalException.add(e);
			return new Vo(e);
		}
	}
}
