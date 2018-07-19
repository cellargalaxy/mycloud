package top.cellargalaxy.mycloud.controlor;

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
@RequestMapping("")
public class UserControlor {
	@Autowired
	private UserService userService;

	@PostMapping("/addUser")
	public Vo addUser(UserPo userPo) {
		Vo vo;
		try {
			userService.addUser(userPo);
			vo = new Vo("添加成功", null);
		} catch (Exception e) {
			GlobalException.add(e);
			vo = new Vo(e, "添加失败");
		}
		return vo;
	}

	@PostMapping("/removeUser")
	public Vo removeUser(UserQuery userQuery){
		Vo vo;
		try {
			userService.removeUser(userQuery);
			vo = new Vo("删除成功", null);
		} catch (Exception e) {
			GlobalException.add(e);
			vo = new Vo(e, "删除失败");
		}
		return vo;
	}

	@GetMapping("/getUser")
	private Vo getUser(UserQuery userQuery){
		Vo vo;
		try {
			vo = new Vo("获取成功", userService.getUser(userQuery));
		} catch (Exception e) {
			GlobalException.add(e);
			vo = new Vo(e, "获取失败");
		}
		return vo;
	}

	@GetMapping("/listUser")
	private Vo listUser(UserQuery userQuery){
		Vo vo;
		try {
			vo = new Vo("获取成功", userService.listUser(userQuery));
		} catch (Exception e) {
			GlobalException.add(e);
			vo = new Vo(e, "获取失败");
		}
		return vo;
	}

	@PostMapping("/changeUser")
	public Vo changeUser(UserQuery userQuery){
		Vo vo;
		try {
			userService.changeUser(userQuery);
			vo = new Vo("修改成功", null);
		} catch (Exception e) {
			GlobalException.add(e);
			vo = new Vo(e, "修改失败");
		}
		return vo;
	}
}
