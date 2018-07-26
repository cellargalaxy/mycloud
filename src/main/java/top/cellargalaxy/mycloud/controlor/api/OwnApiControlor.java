package top.cellargalaxy.mycloud.controlor.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
@RestController
@RequestMapping(OwnApiControlor.URL)
public class OwnApiControlor {
	public static final String URL = "/api/own";
	private Logger logger = LoggerFactory.getLogger(OwnApiControlor.class);
	@Autowired
	private OwnService ownService;

	@PostMapping("/addOwn")
	public Vo addOwn(OwnPo ownPo) {
		try {
			String string = ownService.addOwn(ownPo);
			logger.info("addOwn:{}", string);
			return new Vo(string, null);
		} catch (Exception e) {
			e.printStackTrace();
			GlobalException.add(e);
			return new Vo(e);
		}
	}

	@PostMapping("/removeOwn")
	public Vo removeOwn(OwnQuery ownQuery) {
		try {
			String string = ownService.removeOwn(ownQuery);
			logger.info("removeOwn:{}", string);
			return new Vo(string, null);
		} catch (Exception e) {
			e.printStackTrace();
			GlobalException.add(e);
			return new Vo(e);
		}
	}

	@GetMapping("/getOwn")
	public Vo getOwn(OwnQuery ownQuery) {
		try {
			logger.info("ownQuery");
			return new Vo(null, ownService.getOwn(ownQuery));
		} catch (Exception e) {
			e.printStackTrace();
			GlobalException.add(e);
			return new Vo(e);
		}
	}

	@GetMapping("/getOwnCount")
	public Vo getOwnCount(OwnQuery ownQuery) {
		try {
			logger.info("getOwnCount");
			return new Vo(null, ownService.getOwnCount(ownQuery));
		} catch (Exception e) {
			e.printStackTrace();
			GlobalException.add(e);
			return new Vo(e);
		}
	}

	@GetMapping("/  ")
	public Vo listOwn(OwnQuery ownQuery) {
		try {
			logger.info("listOwn");
			return new Vo(null, ownService.listOwn(ownQuery));
		} catch (Exception e) {
			e.printStackTrace();
			GlobalException.add(e);
			return new Vo(e);
		}
	}

	@GetMapping("/listSort")
	public Vo listSort(OwnQuery ownQuery) {
		try {
			logger.info("listSort");
			return new Vo(null, ownService.listSort(ownQuery));
		} catch (Exception e) {
			e.printStackTrace();
			GlobalException.add(e);
			return new Vo(e);
		}
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
		try {
			String string = ownService.checkAddOwn(ownPo);
			logger.info("checkAddOwn:{}", string);
			return new Vo(string, null);
		} catch (Exception e) {
			e.printStackTrace();
			GlobalException.add(e);
			return new Vo(e);
		}
	}

	@GetMapping("/checkChangeOwn")
	public Vo checkChangeOwn(OwnPo ownPo) {
		try {
			String string = ownService.checkChangeOwn(ownPo);
			logger.info("checkChangeOwn:{}", string);
			return new Vo(string, null);
		} catch (Exception e) {
			e.printStackTrace();
			GlobalException.add(e);
			return new Vo(e);
		}
	}
}
