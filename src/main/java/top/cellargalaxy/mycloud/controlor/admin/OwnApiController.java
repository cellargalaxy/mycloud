package top.cellargalaxy.mycloud.controlor.admin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.cellargalaxy.mycloud.exception.GlobalException;
import top.cellargalaxy.mycloud.model.po.OwnPo;
import top.cellargalaxy.mycloud.model.query.OwnQuery;
import top.cellargalaxy.mycloud.model.vo.Vo;
import top.cellargalaxy.mycloud.service.OwnService;

/**
 * @author cellargalaxy
 * @time 2018/7/20
 */
@PreAuthorize("hasAuthority('ADMIN')")
@RestController
@RequestMapping(OwnApiController.URL)
public class OwnApiController {
	public static final String URL = "/admin/own";
	private Logger logger = LoggerFactory.getLogger(OwnApiController.class);
	@Autowired
	private OwnService ownService;

	@PostMapping("/addOwn")
	public Vo addOwn(OwnPo ownPo) {
		String string = ownService.addOwn(ownPo);
		logger.info("addOwn:{}", string);
		return new Vo(string, null);
	}

	@PostMapping("/removeOwn")
	public Vo removeOwn(OwnQuery ownQuery) {
		String string = ownService.removeOwn(ownQuery);
		logger.info("removeOwn:{}", string);
		return new Vo(string, null);
	}

	@GetMapping("/getOwn")
	public Vo getOwn(OwnQuery ownQuery) {
		logger.info("ownQuery");
		return new Vo(null, ownService.getOwn(ownQuery));
	}

	@GetMapping("/getOwnCount")
	public Vo getOwnCount(OwnQuery ownQuery) {
		logger.info("getOwnCount");
		return new Vo(null, ownService.getOwnCount(ownQuery));
	}

	@GetMapping("/  ")
	public Vo listOwn(OwnQuery ownQuery) {
		logger.info("listOwn");
		return new Vo(null, ownService.listOwn(ownQuery));
	}

	@GetMapping("/listSort")
	public Vo listSort(OwnQuery ownQuery) {
		logger.info("listSort");
		return new Vo(null, ownService.listSort(ownQuery));
	}

	@PostMapping("/changeOwn")
	public Vo changeOwn(OwnPo ownPo) {
		try {
			String string = ownService.changeOwn(ownPo);
			logger.info("changeOwn:{}", string);
			return new Vo(string, null);
		} catch (Exception e) {
			e.printStackTrace();
			GlobalException.add(e);
			return new Vo(e);
		}
	}

	@GetMapping("/checkAddOwn")
	public Vo checkAddOwn(OwnPo ownPo) {
		String string = ownService.checkAddOwn(ownPo);
		logger.info("checkAddOwn:{}", string);
		return new Vo(string, null);
	}

	@GetMapping("/checkChangeOwn")
	public Vo checkChangeOwn(OwnPo ownPo) {
		String string = ownService.checkChangeOwn(ownPo);
		logger.info("checkChangeOwn:{}", string);
		return new Vo(string, null);
	}
}
