package controlor;

import bean.DBPackage;
import bean.FilePackage;
import org.json.JSONArray;
import org.json.JSONObject;
import service.FileBackupThread;

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
 * Created by cellargalaxy on 17-11-3.
 */
public class AdminJsonServlet extends HttpServlet {
	private DateFormat dateFormat;
	
	@Override
	public void init() throws ServletException {
		dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("application/json;charset=utf-8");
		Writer writer = resp.getWriter();
		JSONArray jsonArray=new JSONArray();
		
		try {
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
			DBPackage dbPackage;
			if (uploadDate==null) {
				dbPackage=fileBackupThread.findDB(dbName);
			}else {
				dbPackage=fileBackupThread.findDBByDate(dbName,uploadDate);
			}
			for (FilePackage filePackage : dbPackage.getFilePackages()) {
				JSONObject jsonObject=new JSONObject();
				jsonObject.put("fileName",filePackage.getFileName());
				jsonObject.put("uploadDate",dateFormat.format(filePackage.getUploadDate()));
				jsonObject.put("description",filePackage.getDescription());
				jsonObject.put("url",filePackage.getUrl());
				jsonArray.put(jsonObject);
			}
		} finally {
			writer.write(jsonArray.toString());
			writer.close();
		}
	}
}
