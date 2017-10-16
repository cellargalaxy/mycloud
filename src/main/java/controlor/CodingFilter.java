package controlor;

import javax.servlet.*;
import java.io.IOException;

/**
 * Created by cellargalaxy on 17-10-15.
 */
public class CodingFilter implements Filter {
	private static String coding;
	
	public void init(FilterConfig filterConfig) throws ServletException {
		coding = filterConfig.getInitParameter("coding");
	}
	
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
		servletRequest.setCharacterEncoding(coding);
		servletResponse.setCharacterEncoding(coding);
		filterChain.doFilter(servletRequest, servletResponse);
	}
	
	public void destroy() {
		coding = null;
	}
	
	public static String getCoding() {
		return coding;
	}
}
