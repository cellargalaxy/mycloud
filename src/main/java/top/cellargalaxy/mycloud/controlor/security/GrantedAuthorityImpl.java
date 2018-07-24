//package top.cellargalaxy.mycloud.controlor.security;
//
//import org.springframework.security.core.GrantedAuthority;
//import top.cellargalaxy.mycloud.model.po.AuthorizationPo;
//
///**
// * @author cellargalaxy
// * @time 2018/7/23
// */
//public class GrantedAuthorityImpl implements GrantedAuthority {
//	private final AuthorizationPo authorizationPo;
//
//	public GrantedAuthorityImpl(AuthorizationPo authorizationPo) {
//		this.authorizationPo = authorizationPo;
//	}
//
//	@Override
//	public String getAuthority() {
//		return String.valueOf(authorizationPo.getPermissionId());
//	}
//}
