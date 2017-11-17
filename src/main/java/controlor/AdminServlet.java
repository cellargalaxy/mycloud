package controlor;

import bean.DBPackage;
import bean.FilePackage;
import configuration.Configuration;
import service.FileService;
import service.FileServiceThread;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
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
		dateFormat = Configuration.getRequestDateFormat();
		adminJsp = getInitParameter("adminJsp");
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String dbName = req.getParameter("dbName");
		String uploadDateString = req.getParameter("uploadDate");
		String description = req.getParameter("description");
		Date uploadDate = null;
		if (uploadDateString != null && uploadDateString.length() > 0) {
			try {
				uploadDate = dateFormat.parse(uploadDateString);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		
		FileService fileService = FileBackupThreadListener.FILE_BACKUP_THREAD;
		DBPackage dbPackage;
		if (dbName != null && uploadDate != null) {
			dbPackage = fileService.findDBByDate(dbName, uploadDate);
		} else if (dbName != null && description != null) {
			dbPackage = fileService.findDBByDescription(dbName, description);
		} else {
			dbPackage = new DBPackage(dbName, new FilePackage[0]);
		}
		
		FileServiceThread fileServiceThread = FileBackupThreadListener.FILE_BACKUP_THREAD;
		req.setAttribute("token", LoginServlet.getToken());
		req.setAttribute("dbs", fileServiceThread.getDbs());
		req.setAttribute("backupCount", fileServiceThread.getBackupCount());
		req.setAttribute("yetBackup", fileServiceThread.getYetBackup());
		req.setAttribute("restoreCount", fileServiceThread.getRestoreCount());
		req.setAttribute("yetRestore", fileServiceThread.getYetRestore());
		req.setAttribute("DBPackage", dbPackage);
		req.getRequestDispatcher(adminJsp).forward(req, resp);
	}
}
