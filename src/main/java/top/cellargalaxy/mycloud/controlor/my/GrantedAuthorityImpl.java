package top.cellargalaxy.mycloud.controlor.my;

import org.springframework.security.core.GrantedAuthority;

/**
 * @author cellargalaxy
 * @time 2018/7/27
 */
public class GrantedAuthorityImpl implements GrantedAuthority {
	private String rule;

	public GrantedAuthorityImpl() {
	}

	public GrantedAuthorityImpl(String rule) {
		this.rule = rule;
	}

	@Override
	public String getAuthority() {
		return rule;
	}

	public String getRule() {
		return rule;
	}

	public void setRule(String rule) {
		this.rule = rule;
	}

	@Override
	public String toString() {
		return "GrantedAuthorityImpl{" +
				"rule='" + rule + '\'' +
				'}';
	}
}
