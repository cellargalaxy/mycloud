package controlor;

import bean.FilePackage;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by cellargalaxy on 17-10-15.
 */
public class DeleteFileServlet extends HttpServlet {
	private DateFormat dateFormat;
	
	@Override
	public void init() throws ServletException {
		dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("application/json;charset=utf-8");
		Writer writer = resp.getWriter();
		JSONObject jsonObject = new JSONObject();
		
		String dbName = req.getParameter("dbName");
		String fileName = req.getParameter("fileName");
		String uploadDateString = req.getParameter("uploadDate");
		Date date = null;
		try {
			date = dateFormat.parse(uploadDateString);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		if (dbName == null || fileName == null || date == null) {
			jsonObject.put("result", false);
			jsonObject.put("info", "信息缺失");
		} else {
			FilePackage filePackage = new FilePackage(fileName, date, null);
			if (FileBackupThreadListener.FILE_BACKUP_THREAD.deleteFile(dbName, filePackage)) {
				jsonObject.put("result", true);
				jsonObject.put("info", "删除成功");
			} else {
				jsonObject.put("result", false);
				jsonObject.put("info", "删除失败");
			}
		}
		writer.write(jsonObject.toString());
		writer.close();
	}
}
