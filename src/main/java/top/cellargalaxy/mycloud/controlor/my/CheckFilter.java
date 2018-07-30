package top.cellargalaxy.mycloud.controlor.my;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
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
import java.io.IOException;
import java.util.Base64;
import java.util.List;

/**
 * @author cellargalaxy
 * @time 2018/7/27
 */
public class CheckFilter extends GenericFilterBean {
	public static final String tokenHeadKey = "Authorization";
	public static final String secret = "secret";
	public static final String tokenStartWith = "Bearer ";

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) servletRequest;
		String token = null;
//		token = request.getHeader(tokenHeadKey);
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals(tokenHeadKey)) {
					token = cookie.getValue();
					break;
				}
			}
		}

		if (token==null) {
			System.out.println("error token: " + token);
			filterChain.doFilter(servletRequest, servletResponse);
			return;
		}

		try {
			token = new String(Base64.getDecoder().decode(token), "utf-8");
		}catch (Exception e){
			e.printStackTrace();
		}

		if (token == null || !token.startsWith(tokenStartWith)) {
			System.out.println("error token: " + token);
			filterChain.doFilter(servletRequest, servletResponse);
			return;
		} else {
			token = token.substring(tokenStartWith.length());
		}
		System.out.println("token: " + token);

		Claims claims;
		try {
			claims = Jwts.parser()
					.setSigningKey(secret)
					.parseClaimsJws(token)
					.getBody();
		} catch (SignatureException e) {
			System.out.println("伪造token");
			filterChain.doFilter(servletRequest, servletResponse);
			return;
		} catch (ExpiredJwtException e) {
			System.out.println("token超时");
			filterChain.doFilter(servletRequest, servletResponse);
			return;
		}

//		List<GrantedAuthorityImpl> list = new LinkedList<GrantedAuthorityImpl>() {{
//			add(new GrantedAuthorityImpl("ROLE_USER"));
//			add(new GrantedAuthorityImpl("ADMIN"));
//		}};
//		User user = new User("root", "pw") {{
//			setGrantedAuthorities(list);
//		}};


		List<GrantedAuthority> authorities = AuthorityUtils.commaSeparatedStringToAuthorityList(claims.get("authorities", String.class));
		UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
				new UsernamePasswordAuthenticationToken(null, null, authorities);
		SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);


		filterChain.doFilter(servletRequest, servletResponse);
	}
}
