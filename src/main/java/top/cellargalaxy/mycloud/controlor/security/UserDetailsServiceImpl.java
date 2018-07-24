//package top.cellargalaxy.mycloud.controlor.security;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//import top.cellargalaxy.mycloud.model.bo.AuthorizationBo;
//import top.cellargalaxy.mycloud.model.query.UserQuery;
//import top.cellargalaxy.mycloud.model.vo.UserAuthorizationVo;
//import top.cellargalaxy.mycloud.service.UserService;
//
//import java.util.LinkedList;
//import java.util.List;
//
///**
// * @author cellargalaxy
// * @time 2018/7/23
// */
////@Service
//public class UserDetailsServiceImpl implements UserDetailsService {
//	@Autowired
//	private UserService userService;
//
//	@Override
//	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//		UserQuery userQuery = new UserQuery();
//		userQuery.setUsername(username);
//		UserAuthorizationVo userAuthorization = userService.getUserAuthorization(userQuery);
//		if (userAuthorization == null) {
//			return null;
//		}
//		List<GrantedAuthorityImpl> grantedAuthorities = new LinkedList<>();
//		for (AuthorizationBo authorization : userAuthorization.getAuthorizations()) {
//			grantedAuthorities.add(new GrantedAuthorityImpl(authorization));
//		}
//		return new UserPoDetails(userAuthorization.getUser(), grantedAuthorities);
//	}
//}
