package controlor;

import service.FileBackupThread;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by cellargalaxy on 17-10-15.
 */
public class AdminServlet extends HttpServlet {
	private DateFormat dateFormat;
	private String adminJsp;
	
	@Override
	public void init() throws ServletException {
		dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		adminJsp = getInitParameter("adminJsp");
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String dbName = req.getParameter("dbName");
		String uploadDateString=req.getParameter("uploadDate");
		Date uploadDate=null;
		try{
			if (uploadDateString!=null) {
				uploadDate=dateFormat.parse(uploadDateString);
			}
		}catch (Exception e){
			e.printStackTrace();
		}
		
		FileBackupThread fileBackupThread = FileBackupThreadListener.FILE_BACKUP_THREAD;
		req.setAttribute("token", LoginServlet.getToken());
		req.setAttribute("dbs", fileBackupThread.getDbs());
		req.setAttribute("backupCount", fileBackupThread.getBackupCount());
		req.setAttribute("yetBackup", fileBackupThread.getYetBackup());
		req.setAttribute("restoreCount", fileBackupThread.getRestoreCount());
		req.setAttribute("yetRestore", fileBackupThread.getYetRestore());
		if (uploadDate==null) {
			req.setAttribute("DBPackage", fileBackupThread.findDB(dbName));
		}else {
			req.setAttribute("DBPackage", fileBackupThread.findDBByDate(dbName,uploadDate));
		}
		req.getRequestDispatcher(adminJsp).forward(req, resp);
	}
	
	
}
