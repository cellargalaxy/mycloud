package controlor;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by cellargalaxy on 17-10-15.
 */
public class IndexServlet extends HttpServlet {
	private String indexJsp;
	private String adminUrl;
	private static String token;
	
	@Override
	public void init() throws ServletException {
		indexJsp = getInitParameter("indexJsp");
		adminUrl = getInitParameter("adminUrl");
		token = getInitParameter("token");
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.getRequestDispatcher(indexJsp).forward(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String string = req.getParameter("token");
		if (string == null || !string.equals(token)) {
			req.setAttribute("info", "口令错误");
			doGet(req, resp);
			return;
		}
		req.getSession().setAttribute("token", token);
		resp.sendRedirect(adminUrl);
	}
	
	public static String getToken() {
		return token;
	}
	
	public static void setToken(String token) {
		IndexServlet.token = token;
	}
}
