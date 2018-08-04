package top.cellargalaxy.mycloud.controlor.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;
import top.cellargalaxy.mycloud.controlor.user.UserUserController;
import top.cellargalaxy.mycloud.model.security.SecurityUser;
import top.cellargalaxy.mycloud.service.security.SecurityService;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

/**
 * @author cellargalaxy
 * @time 2018/7/30
 */
public class AuthenticationFilter extends GenericFilterBean {
	private final Logger logger = LoggerFactory.getLogger(AuthenticationFilter.class);
	private final SecurityService securityService;

	public AuthenticationFilter(SecurityService securityService) {
		this.securityService = securityService;
	}

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
		HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
		HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;

		//从头，cookie，和参数里获取token
		String token = httpServletRequest.getHeader(LoginFilter.TOKEN_KEY);
		if (token == null) {
			Cookie[] cookies = httpServletRequest.getCookies();
			if (cookies != null) {
				for (Cookie cookie : cookies) {
					if (cookie.getName().equals(LoginFilter.TOKEN_KEY)) {
						token = cookie.getValue();
						break;
					}
				}
			}
		}
		if (token == null) {
			token = httpServletRequest.getParameter(LoginFilter.TOKEN_KEY);
		}

		logger.info("检验token:{}", token);

		//如果获取并解析token成功，就给他在SecurityContextHolder.getContext()里设置账号对象
		//这样之后的Filter发现他有账号对象，就会认为他已经登录，允许放行
		SecurityUser securityUser = securityService.checkToken(token);
		if (securityUser != null) {
			httpServletRequest.setAttribute(UserUserController.USER_KEY, securityUser);

			List<GrantedAuthority> authorities = new LinkedList<>();
			for (String permission : securityUser.getPermissions()) {
				authorities.add(new SimpleGrantedAuthority(permission));
			}
			Authentication authentication = new UsernamePasswordAuthenticationToken(securityUser.getUsername(), null, authorities);
			//SecurityContextHolder.getContext()可以获取当前会话的账号对象
			SecurityContextHolder.getContext().setAuthentication(authentication);
			logger.info("token过检,securityUser:{}", securityUser);
		} else {
			logger.info("空token或者非法token");
		}

		//由于可能是访问不需要登录的资源，所以即便没有token，也需要放行
		//至于是不是访问不需要登录的资源，则由之后的Filter判断处理
		filterChain.doFilter(httpServletRequest, httpServletResponse);
	}
}
