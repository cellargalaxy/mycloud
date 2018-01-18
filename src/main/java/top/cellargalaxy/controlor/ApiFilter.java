package top.cellargalaxy.controlor;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;
import top.cellargalaxy.service.MycloudService;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by cellargalaxy on 17-12-5.
 */
@WebFilter(filterName = "apiFilter", urlPatterns = ApiControlor.API_CONTROLOR_URL + "/*")
public class ApiFilter implements Filter {
	@Autowired
	private MycloudService mycloudService;
	
	private FilterConfig filterConfig;
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		this.filterConfig = filterConfig;
	}
	
	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
		SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this, filterConfig.getServletContext());
		
		HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
		HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;
		
		String token = httpServletRequest.getParameter("token");
		if (!mycloudService.checkToken(token)) {
			JSONObject jsonObject = ApiControlor.createJSONObject(ApiControlor.UPLOAD_FILE_FAIL, "no authorization", null);
			httpServletResponse.getWriter().write(jsonObject.toString());
			return;
		}
		filterChain.doFilter(httpServletRequest, httpServletResponse);
	}
	
	@Override
	public void destroy() {
	
	}
}
