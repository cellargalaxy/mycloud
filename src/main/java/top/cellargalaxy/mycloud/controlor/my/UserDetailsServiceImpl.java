package top.cellargalaxy.mycloud.controlor.my;

import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @author cellargalaxy
 * @time 2018/7/27
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	private static Map<String, User> map = new HashMap<String, User>() {{
		User user=new User("root", "$2a$04$pe.YFryLRMULXZwYNfTmSulxWEaLBFhcwIvpz3aVQKzsHivrp1rjC"){{
			setGrantedAuthorities(AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_USER"));
		}};
		put("root", user);
	}};

	@Override
	public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
		UserDetails userDetails = map.get(s);
		System.out.println("查询用户(" + s + "): " + userDetails);
		return userDetails;
	}
}
