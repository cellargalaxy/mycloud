package top.cellargalaxy.mycloud.controller.guest;

import org.springframework.beans.BeanUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.cellargalaxy.mycloud.model.bo.AuthorizationBo;
import top.cellargalaxy.mycloud.model.bo.UserBo;
import top.cellargalaxy.mycloud.model.po.UserPo;
import top.cellargalaxy.mycloud.model.vo.UserVo;
import top.cellargalaxy.mycloud.service.security.SecurityServiceImpl;
import top.cellargalaxy.mycloud.util.model.Vo;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author cellargalaxy
 * @time 2018/12/19
 */
@PreAuthorize("hasAuthority('GUEST')")
@RestController
@RequestMapping(UserGuestController.URL)
public class UserGuestController {
	public static final String URL = "/guest/user";

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
}
