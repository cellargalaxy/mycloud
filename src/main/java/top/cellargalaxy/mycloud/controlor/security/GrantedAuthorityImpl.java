package top.cellargalaxy.mycloud.controlor.security;

import org.springframework.security.core.GrantedAuthority;

/**
 * @author cellargalaxy
 * @time 2018/7/30
 */
public class GrantedAuthorityImpl implements GrantedAuthority {
	private final String permission;

	public GrantedAuthorityImpl(String permission) {
		this.permission = permission;
	}

	@Override
	public String getAuthority() {
		return permission;
	}
}
