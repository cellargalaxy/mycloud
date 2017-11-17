package controlor;

import bean.DBPackage;
import bean.FilePackage;
import configuration.Configuration;
import org.json.JSONArray;
import service.FileService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;

/**
 * Created by cellargalaxy on 17-11-3.
 */
public class FindFileServlet extends HttpServlet {
	private DateFormat dateFormat;
	
	@Override
	public void init() throws ServletException {
		dateFormat = Configuration.getRequestDateFormat();
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("application/json;charset=" + CodingFilter.getCoding());
		Writer writer = resp.getWriter();
		JSONArray jsonArray = new JSONArray();
		
		try {
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
			
			for (FilePackage filePackage : dbPackage.getFilePackages()) {
				jsonArray.put(filePackage.toJSONObject());
			}
		} finally {
			writer.write(jsonArray.toString());
			writer.close();
		}
	}
}
