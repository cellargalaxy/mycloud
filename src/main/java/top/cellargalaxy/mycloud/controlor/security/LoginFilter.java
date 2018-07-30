package top.cellargalaxy.mycloud.controlor.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

/**
 * @author cellargalaxy
 * @time 2018/7/30
 */
public class LoginFilter extends AbstractAuthenticationProcessingFilter {
	private Logger logger = LoggerFactory.getLogger(LoginFilter.class);
	public static final String USERNAME_KEY = "username";
	public static final String PASSWORD_KEY = "password";
	public static final String TOKEN_KEY = "Authorization";
	public static final String TOKEN_PREFIX = "Bearer ";
	public static final int EXPIRATION_TIME = 1000 * 60 * 60 * 6;
	public static final String AUTHORITIE_KEY = "authorities";
	private final String secret;
	private final UserDetailsService userDetailsService;
	private final ObjectMapper objectMapper;

	public LoginFilter(String defaultFilterProcessesUrl, AuthenticationManager authenticationManager, String secret, UserDetailsService userDetailsService) {
		super(new AntPathRequestMatcher(defaultFilterProcessesUrl));
		setAuthenticationManager(authenticationManager);
		this.secret = secret;
		this.userDetailsService = userDetailsService;
		objectMapper = new ObjectMapper();
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws AuthenticationException, IOException, ServletException {
		httpServletResponse.setCharacterEncoding("utf-8");
		String username = httpServletRequest.getParameter(USERNAME_KEY);
		String password = httpServletRequest.getParameter(PASSWORD_KEY);
		logger.info("检验登录,username:{}", username);
		return getAuthenticationManager().authenticate(new UsernamePasswordAuthenticationToken(username, password));
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
		String username = request.getParameter(USERNAME_KEY);
		UserDetails userDetails = userDetailsService.loadUserByUsername(username);
		List<String> authorities = new LinkedList<>();
		for (GrantedAuthority authority : userDetails.getAuthorities()) {
			authorities.add(authority.getAuthority());
		}
		String jwt = Jwts.builder()
				//保存权限/角色
				.claim(AUTHORITIE_KEY, objectMapper.writeValueAsString(authorities))
				//用户名写入标题
				.setSubject(username)
				//有效期设置
				.setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
				//签名设置
				.signWith(SignatureAlgorithm.HS512, secret)
				.compact();

		logger.info("登录成功,username:{},token:{}", username, jwt);

		Cookie cookie = new Cookie(TOKEN_KEY, jwt);
		response.addCookie(cookie);

		jwt = TOKEN_PREFIX + jwt;
		response.setHeader(TOKEN_KEY, jwt);

		Map<String, Object> vo = new HashMap<>();
		vo.put("status", 1);
		vo.put("massage", null);
		vo.put("data", jwt);
		response.setContentType("application/json");
		response.setStatus(HttpServletResponse.SC_OK);
		response.getWriter().write(objectMapper.writeValueAsString(vo));
	}

	@Override
	protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
		logger.info("账号或密码错误");
		Map<String, Object> vo = new HashMap<>();
		vo.put("status", 0);
		vo.put("massage", "账号或密码错误");
		vo.put("data", null);
		response.setContentType("application/json");
		response.setStatus(HttpServletResponse.SC_OK);
		response.getWriter().write(objectMapper.writeValueAsString(vo));
	}
}
