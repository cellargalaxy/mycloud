package controlor;

import bean.FilePackage;
import configuration.Configuration;
import org.json.JSONObject;

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
 * Created by cellargalaxy on 17-10-16.
 */
public class RestoreFileServlet extends HttpServlet {
	private DateFormat dateFormat;
	
	@Override
	public void init() throws ServletException {
		dateFormat = Configuration.getRequestDateFormat();
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("application/json;charset=" + CodingFilter.getCoding());
		Writer writer = resp.getWriter();
		JSONObject jsonObject = new JSONObject();
		
		try {
			String dbName = req.getParameter("dbName");
			String fileName = req.getParameter("fileName");
			String uploadDateString = req.getParameter("uploadDate");
			Date uploadDate = null;
			if (uploadDateString != null && uploadDateString.length() > 0) {
				try {
					uploadDate = dateFormat.parse(uploadDateString);
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
			if (dbName == null || fileName == null || uploadDate == null) {
				jsonObject.put("result", false);
				jsonObject.put("info", "信息缺失");
			} else {
				FilePackage filePackage = new FilePackage(fileName, uploadDate, null);
				FileBackupThreadListener.FILE_BACKUP_THREAD.restore(dbName, filePackage);
				jsonObject.put("result", true);
				jsonObject.put("info", "恢复提交成功");
			}
		} finally {
			writer.write(jsonObject.toString());
			writer.close();
		}
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("application/json;charset=" + CodingFilter.getCoding());
		Writer writer = resp.getWriter();
		JSONObject jsonObject = new JSONObject();
		
		try {
			String dbName = req.getParameter("dbName");
			if (dbName == null) {
				jsonObject.put("result", false);
				jsonObject.put("info", "信息缺失");
			} else {
				FileBackupThreadListener.FILE_BACKUP_THREAD.restore(dbName);
				jsonObject.put("result", true);
				jsonObject.put("info", "恢复提交成功");
			}
		} finally {
			writer.write(jsonObject.toString());
			writer.close();
		}
	}
}
