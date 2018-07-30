package top.cellargalaxy.mycloud.controlor.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import top.cellargalaxy.mycloud.model.bo.AuthorizationBo;
import top.cellargalaxy.mycloud.model.query.UserQuery;
import top.cellargalaxy.mycloud.model.vo.UserAuthorizationVo;
import top.cellargalaxy.mycloud.service.UserService;

import java.util.LinkedList;
import java.util.List;

/**
 * @author cellargalaxy
 * @time 2018/7/30
 */
//@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	@Autowired
	private UserService userService;

	@Override
	public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
		UserAuthorizationVo userAuthorization = userService.getUserAuthorization(new UserQuery() {{
			setUsername(s);
		}});
		if (userAuthorization == null) {
			return new UserDetailsImpl(null, null, null);
		}
		List<GrantedAuthorityImpl> grantedAuthorities = new LinkedList<>();
		for (AuthorizationBo authorization : userAuthorization.getAuthorizations()) {
			grantedAuthorities.add(new GrantedAuthorityImpl(authorization.getPermissionMark()));
		}
		return new UserDetailsImpl(userAuthorization.getUser().getUsername(), userAuthorization.getUser().getUserPassword(), grantedAuthorities);
	}
}
