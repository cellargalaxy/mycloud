package controlor;

import service.FileBackupThread;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by cellargalaxy on 17-10-15.
 */
public class AdminServlet extends HttpServlet {
	private String adminJsp;
	
	@Override
	public void init() throws ServletException {
		adminJsp = getInitParameter("adminJsp");
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String dbName = req.getParameter("dbName");
		
		FileBackupThread fileBackupThread = FileBackupThreadListener.FILE_BACKUP_THREAD;
		req.setAttribute("token", LoginServlet.getToken());
		req.setAttribute("dbs", fileBackupThread.getDbs());
		req.setAttribute("backupCount", fileBackupThread.getBackupCount());
		req.setAttribute("yetBackup", fileBackupThread.getYetBackup());
		req.setAttribute("restoreCount", fileBackupThread.getRestoreCount());
		req.setAttribute("yetRestore", fileBackupThread.getYetRestore());
		req.setAttribute("DBPackage", fileBackupThread.findDB(dbName));
		req.getRequestDispatcher(adminJsp).forward(req, resp);
	}
	
	
}
