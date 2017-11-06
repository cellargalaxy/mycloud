package controlor;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by cellargalaxy on 17-10-15.
 */
public class LoginFilter implements Filter {
	private FilterConfig config;
	
	public void init(FilterConfig filterConfig) throws ServletException {
		config = filterConfig;
	}
	
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
		HttpServletResponse response = (HttpServletResponse) servletResponse;
		HttpServletRequest request = (HttpServletRequest) servletRequest;
		
		String disableLoginFilter = config.getInitParameter("disable");
		if (disableLoginFilter.toUpperCase().equals("Y")) {
			filterChain.doFilter(request, response);
			return;
		}
		
		Object token = request.getParameter("token");
		if (token == null) {
			token = request.getSession().getAttribute("token");
		}
		if (token == null || !LoginServlet.getToken().equals(token.toString())) {
			response.sendRedirect(config.getInitParameter("loginUrl"));
			return;
		}
		filterChain.doFilter(request, response);
	}
	
	public void destroy() {
	
	}
}
