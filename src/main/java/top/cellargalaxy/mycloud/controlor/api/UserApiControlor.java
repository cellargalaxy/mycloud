package top.cellargalaxy.mycloud.controlor.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.cellargalaxy.mycloud.exception.GlobalException;
import top.cellargalaxy.mycloud.model.po.UserPo;
import top.cellargalaxy.mycloud.model.query.UserQuery;
import top.cellargalaxy.mycloud.model.vo.Vo;
import top.cellargalaxy.mycloud.service.UserService;

/**
 * Created by cellargalaxy on 18-7-19.
 */
@RestController
@RequestMapping(UserApiControlor.URL)
public class UserApiControlor {
	public static final String URL = "/api/user";
	private Logger logger = LoggerFactory.getLogger(UserApiControlor.class);
	@Autowired
	private UserService userService;

	@PostMapping("/addUser")
	public Vo addUser(UserPo userPo) {
		try {
			String string = userService.addUser(userPo);
			logger.info("addUser:{}", string);
			return new Vo(string, null);
		} catch (Exception e) {
			e.printStackTrace();
			GlobalException.add(e);
			return new Vo(e);
		}
	}

	@PostMapping("/removeUser")
	public Vo removeUser(UserQuery userQuery) {
		try {
			String string = userService.removeUser(userQuery);
			logger.info("removeUser:{}", string);
			return new Vo(string, null);
		} catch (Exception e) {
			e.printStackTrace();
			GlobalException.add(e);
			return new Vo(e);
		}
	}

	@GetMapping("/getUser")
	private Vo getUser(UserQuery userQuery) {
		try {
			logger.info("getUser");
			return new Vo(null, userService.getUser(userQuery));
		} catch (Exception e) {
			e.printStackTrace();
			GlobalException.add(e);
			return new Vo(e);
		}
	}

	@GetMapping("/getUserCount")
	private Vo getUserCount(UserQuery userQuery) {
		try {
			logger.info("getUserCount");
			return new Vo(null, userService.getUserCount(userQuery));
		} catch (Exception e) {
			e.printStackTrace();
			GlobalException.add(e);
			return new Vo(e);
		}
	}

	@GetMapping("/listUser")
	private Vo listUser(UserQuery userQuery) {
		try {
			logger.info("listUser");
			return new Vo(null, userService.listUser(userQuery));
		} catch (Exception e) {
			e.printStackTrace();
			GlobalException.add(e);
			return new Vo(e);
		}
	}

	@GetMapping("/getUserAuthorization")
	private Vo getUserAuthorization(UserQuery userQuery) {
		try {
			logger.info("getUserAuthorization");
			return new Vo(null, userService.getUserAuthorization(userQuery));
		} catch (Exception e) {
			e.printStackTrace();
			GlobalException.add(e);
			return new Vo(e);
		}
	}

	@GetMapping("/listUserAuthorization")
	private Vo listUserAuthorization(UserQuery userQuery) {
		try {
			logger.info("listUserAuthorization");
			return new Vo(null, userService.listUserAuthorization(userQuery));
		} catch (Exception e) {
			e.printStackTrace();
			GlobalException.add(e);
			return new Vo(e);
		}
	}

	@GetMapping("/getUserOwn")
	private Vo getUserOwn(UserQuery userQuery) {
		try {
			logger.info("getUserOwn");
			return new Vo(null, userService.getUserOwn(userQuery));
		} catch (Exception e) {
			e.printStackTrace();
			GlobalException.add(e);
			return new Vo(e);
		}
	}

	@GetMapping("/listUserOwn")
	private Vo listUserOwn(UserQuery userQuery) {
		try {
			logger.info("listUserOwn");
			return new Vo(null, userService.listUserOwn(userQuery));
		} catch (Exception e) {
			e.printStackTrace();
			GlobalException.add(e);
			return new Vo(e);
		}
	}

	@PostMapping("/changeUser")
	public Vo changeUser(UserQuery userQuery) {
		try {
			String string = userService.changeUser(userQuery);
			logger.info("changeUser:{}", string);
			return new Vo(string, null);
		} catch (Exception e) {
			e.printStackTrace();
			GlobalException.add(e);
			return new Vo(e);
		}
	}

	@GetMapping("/checkAddUser")
	public Vo checkAddUser(UserPo userPo) {
		try {
			String string = userService.checkAddUser(userPo);
			logger.info("checkAddUser:{}", string);
			return new Vo(string, null);
		} catch (Exception e) {
			e.printStackTrace();
			GlobalException.add(e);
			return new Vo(e);
		}
	}

	@GetMapping("/checkChangeUser")
	public Vo checkChangeUser(UserPo userPo) {
		try {
			String string = userService.checkChangeUser(userPo);
			logger.info("checkChangeUser:{}", string);
			return new Vo(string, null);
		} catch (Exception e) {
			e.printStackTrace();
			GlobalException.add(e);
			return new Vo(e);
		}
	}
}
