package top.cellargalaxy.controlor;

import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by cellargalaxy on 17-12-5.
 */
@WebFilter(filterName = "adminFilter", urlPatterns = "/admin/*")
public class AdminFilter implements Filter {
	private FilterConfig filterConfig;
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		this.filterConfig=filterConfig;
	}
	
	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
		SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this, filterConfig.getServletContext());
		
		HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
		HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;
		
		Object object = httpServletRequest.getSession().getAttribute("token");
		if (object == null) {
			httpServletRequest.getSession().setAttribute("info", "请登录");
			httpServletRequest.getRequestDispatcher("/").forward(httpServletRequest, httpServletResponse);
			return;
		}
		filterChain.doFilter(httpServletRequest, httpServletResponse);
	}
	
	@Override
	public void destroy() {
	
	}
}
