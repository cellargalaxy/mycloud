package top.cellargalaxy.mycloud.controller.security;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import top.cellargalaxy.mycloud.util.model.SecurityUser;
import top.cellargalaxy.mycloud.service.security.SecurityService;

import java.util.UUID;

/**
 * @author cellargalaxy
 * @time 2018/7/30
 */
public class UserDetailsServiceImpl implements UserDetailsService {
    private final SecurityService securityService;

    public UserDetailsServiceImpl(SecurityService securityService) {
        this.securityService = securityService;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        final SecurityUser securityUser = securityService.getSecurityUser(s);
        if (securityUser == null) {
            //如果查找不到账号，他不允许返回null，否则会报错，说什么违法规定
            //我们可以在WebSecurityConfig里配置写死使用哪种密码加密
            //这时候下面返回的UserDetails对象的密码可以为null，起码不会报错
            //但是如果我们想使用更加灵活的方法：
            //在密码前使用大括号来表示该密码的加密方法，框架会自动识别并选择对应的密码加密器
            //就自然不能在WebSecurityConfig里配置写死
            //这时候如果UserDetails对象的密码为null，他就会报错
            //所以这里用个uuid作为账号密码，{noop}表示密码是明文
            String string = UUID.randomUUID().toString();
            return new UserDetailsImpl(string, "{noop}" + string);
        }
        return new UserDetailsImpl(securityUser.getUsername(), securityUser.getPassword()) {{
            for (String permission : securityUser.getPermissions()) {
                getAuthorities().add(new SimpleGrantedAuthority(permission));
            }
        }};
    }
}
