//package top.cellargalaxy.mycloud.controlor.security;
//
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//import top.cellargalaxy.mycloud.model.po.UserPo;
//
//import java.util.Collection;
//
///**
// * @author cellargalaxy
// * @time 2018/7/23
// */
//public class UserPoDetails implements UserDetails {
//	private final UserPo userPo;
//	private final Collection<? extends GrantedAuthority> authorities;
//
//	public UserPoDetails(UserPo userPo, Collection<? extends GrantedAuthority> authorities) {
//		this.userPo = userPo;
//		this.authorities = authorities;
//	}
//
//	@Override
//	public Collection<? extends GrantedAuthority> getAuthorities() {
//		return null;
//	}
//
//	@Override
//	public String getPassword() {
//		return userPo.getUserPassword();
//	}
//
//	@Override
//	public String getUsername() {
//		return userPo.getUsername();
//	}
//
//	@Override
//	public boolean isAccountNonExpired() {
//		return true;
//	}
//
//	@Override
//	public boolean isAccountNonLocked() {
//		return true;
//	}
//
//	@Override
//	public boolean isCredentialsNonExpired() {
//		return true;
//	}
//
//	@Override
//	public boolean isEnabled() {
//		return true;
//	}
//}
