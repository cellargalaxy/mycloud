package top.cellargalaxy.mycloud.controlor.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author cellargalaxy
 * @time 2018/7/30
 */
public class AuthenticationFilter extends GenericFilterBean {
	private Logger logger = LoggerFactory.getLogger(AuthenticationFilter.class);
	private final String secret;
	private final ObjectMapper objectMapper;

	public AuthenticationFilter(String secret) {
		this.secret = secret;
		objectMapper = new ObjectMapper();
	}

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
		HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
		HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;

		String jwt = httpServletRequest.getHeader(LoginFilter.TOKEN_KEY);
		if (jwt != null && jwt.startsWith(LoginFilter.TOKEN_PREFIX)) {
			jwt = jwt.substring(LoginFilter.TOKEN_PREFIX.length());
		} else {
			jwt = null;
		}

		if (jwt == null) {
			Cookie[] cookies = httpServletRequest.getCookies();
			if (cookies != null) {
				for (Cookie cookie : cookies) {
					if (cookie.getName().equals(LoginFilter.TOKEN_KEY)) {
						jwt = cookie.getValue();
						break;
					}
				}
			}
		}

		logger.info("检验token:{}", jwt);

		if (jwt == null) {
			Map<String, Object> vo = new HashMap<>();
			vo.put("status", 0);
			vo.put("massage", "非授权访问");
			vo.put("data", null);
			httpServletResponse.setCharacterEncoding("utf-8");
			httpServletResponse.setContentType("application/json");
			httpServletResponse.setStatus(HttpServletResponse.SC_FORBIDDEN);
			PrintWriter printWriter = httpServletResponse.getWriter();
			printWriter.write(objectMapper.writeValueAsString(vo));
			return;
		}

		try {
			Claims claims = Jwts.parser()
					.setSigningKey(secret)
					.parseClaimsJws(jwt)
					.getBody();

			String username = claims.getSubject();
			String[] strings = objectMapper.readValue(claims.get(LoginFilter.AUTHORITIE_KEY, String.class), String[].class);
			List<GrantedAuthority> authorities = AuthorityUtils.createAuthorityList(strings);
			Authentication authentication = new UsernamePasswordAuthenticationToken(username, null, authorities);
			SecurityContextHolder.getContext().setAuthentication(authentication);
			logger.info("过检token:{}", authentication);
			filterChain.doFilter(httpServletRequest, httpServletResponse);
		} catch (SignatureException e) {
			logger.info("非授权签发token");
			Map<String, Object> vo = new HashMap<>();
			vo.put("status", -1);
			vo.put("massage", "非授权签发token");
			vo.put("data", null);
			httpServletResponse.setCharacterEncoding("utf-8");
			httpServletResponse.setContentType("application/json");
			httpServletResponse.setStatus(HttpServletResponse.SC_OK);
			PrintWriter printWriter = httpServletResponse.getWriter();
			printWriter.write(objectMapper.writeValueAsString(vo));
		} catch (ExpiredJwtException e) {
			logger.info("过时token");
			Map<String, Object> vo = new HashMap<>();
			vo.put("status", 0);
			vo.put("massage", "token已过时，请重新获取");
			vo.put("data", null);
			httpServletResponse.setCharacterEncoding("utf-8");
			httpServletResponse.setContentType("application/json");
			httpServletResponse.setStatus(HttpServletResponse.SC_OK);
			PrintWriter printWriter = httpServletResponse.getWriter();
			printWriter.write(objectMapper.writeValueAsString(vo));
		}
	}
}
