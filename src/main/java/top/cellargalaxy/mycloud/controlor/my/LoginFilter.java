package top.cellargalaxy.mycloud.controlor.my;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Base64;
import java.util.Date;
import java.util.Iterator;

/**
 * Created by cellargalaxy on 18-7-28.
 */
public class LoginFilter extends AbstractAuthenticationProcessingFilter {
	protected LoginFilter(String defaultFilterProcessesUrl, AuthenticationManager authManager) {
		super(new AntPathRequestMatcher(defaultFilterProcessesUrl));
		setAuthenticationManager(authManager);
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws AuthenticationException, IOException, ServletException {
		String username = httpServletRequest.getParameter("username");
		String password = httpServletRequest.getParameter("password");
		System.out.println("登录, username: " + username + ", password: " + password);
		return getAuthenticationManager().authenticate(new UsernamePasswordAuthenticationToken(username, password));
	}

	//验证成功后调用
	@Override
	protected void successfulAuthentication(HttpServletRequest req, HttpServletResponse res, FilterChain chain, Authentication auth) throws IOException, ServletException {
		System.out.println("登录验证成功");
		System.out.println(auth);
		String authorities = "";
		Iterator<? extends GrantedAuthority> iterator = auth.getAuthorities().iterator();
		if (iterator.hasNext()) {
			authorities += iterator.next().getAuthority();
		}
		while (iterator.hasNext()) {
			authorities += "," + iterator.next().getAuthority();
		}

		String jwt = Jwts.builder()
				// 有效期设置
				.setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60))
				// 用户名写入标题
				.setSubject(auth.getName())
				// 保存权限（角色）
				.claim("authorities", authorities)
				// 签名设置
				.signWith(SignatureAlgorithm.HS512, CheckFilter.secret)
				.compact();
		jwt = Base64.getEncoder().encodeToString((CheckFilter.tokenStartWith + jwt).getBytes("utf-8"));

		Cookie cookie = new Cookie(CheckFilter.tokenHeadKey, jwt);
		System.out.println("cookie: " + cookie.getValue());
		res.addCookie(cookie);
		res.setHeader(CheckFilter.tokenHeadKey, jwt);
		res.setContentType("application/json");
		res.setStatus(HttpServletResponse.SC_OK);
		res.getOutputStream().println("{massage:'" + jwt + "'}");
	}

	//验证失败后调用，这里直接灌入500错误返回，由于同一JSON返回，HTTP就都返回200了
	@Override
	protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
		response.setContentType("application/json");
		response.setStatus(HttpServletResponse.SC_OK);
		response.getOutputStream().println("{massage:'my Internal Server Error!!!'}");
	}
}
