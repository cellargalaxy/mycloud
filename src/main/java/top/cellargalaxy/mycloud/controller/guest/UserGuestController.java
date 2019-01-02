package top.cellargalaxy.mycloud.controller.guest;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.cellargalaxy.mycloud.model.bo.AuthorizationBo;
import top.cellargalaxy.mycloud.model.bo.UserBo;
import top.cellargalaxy.mycloud.model.po.UserPo;
import top.cellargalaxy.mycloud.model.vo.UserVo;
import top.cellargalaxy.mycloud.service.security.SecurityService;
import top.cellargalaxy.mycloud.service.security.SecurityServiceImpl;
import top.cellargalaxy.mycloud.util.model.SecurityUser;
import top.cellargalaxy.mycloud.util.model.Vo;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author cellargalaxy
 * @time 2018/12/19
 */
@RestController
@RequestMapping(UserGuestController.URL)
public class UserGuestController {
	public static final String URL = "/guest/user";

	@Autowired
	private SecurityService securityService;

	@GetMapping("/getUserVo")
	public Vo getUserVo(HttpServletRequest request) {
		UserPo userPo = SecurityServiceImpl.getSecurityUser(request);
		if (userPo != null) {
			return new Vo(null, userPo);
		}

		UserVo userVo = UserVo.GUEST;
		SecurityServiceImpl.SecurityUserImpl securityUser = new SecurityServiceImpl.SecurityUserImpl();

		UserBo userBo = userVo.getUser();
		BeanUtils.copyProperties(userBo, securityUser);

		List<AuthorizationBo> authorizationBos = userVo.getAuthorizations();
		authorizationBos.stream().forEach(authorizationBo -> securityUser.getPermissions().add(authorizationBo.getPermission().toString()));
		return new Vo(null, securityUser);
	}

	@GetMapping("/getGuestToken")
	public Vo getGuestToken() {
		UserVo userVo = UserVo.GUEST;
		SecurityUser securityUser = new SecurityServiceImpl.SecurityUserImpl();
		BeanUtils.copyProperties(userVo.getUser(), securityUser);
		securityUser.getPermissions().addAll(userVo.getAuthorizations().stream().map(authorizationBo -> authorizationBo.getPermission().toString()).collect(Collectors.toSet()));
		return new Vo(null, securityService.createToken(securityUser));
	}
}
