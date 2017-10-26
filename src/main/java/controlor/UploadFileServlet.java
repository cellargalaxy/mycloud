package controlor;

import bean.FilePackage;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.json.JSONObject;
import service.FileBackup;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Date;
import java.util.List;

/**
 * Created by cellargalaxy on 17-10-15.
 */
public class UploadFileServlet extends HttpServlet {
	private String rootPath;
	private FileBackup fileBackup;
	private DiskFileItemFactory factory;
	
	@Override
	public void init() throws ServletException {
		fileBackup = FileBackupThreadListener.FILE_BACKUP_THREAD;
		
		//获得磁盘文件条目工厂
		factory = new DiskFileItemFactory();
		//获取文件需要上传到的路径
		rootPath = getServletContext().getRealPath("/upload");
		File repository = new File(rootPath);
		repository.mkdirs();
		//设置存储室
		factory.setRepository(repository);
		//设置缓存的大小，当上传文件的容量超过该缓存时，直接放到暂时存储室
		factory.setSizeThreshold(1024 * 1024);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		resp.setContentType("application/json;charset=utf-8");
		Writer writer = resp.getWriter();
		JSONObject jsonObject = new JSONObject();
		
		String token = null;
		String description = null;
		
		//文件上传处理
		ServletFileUpload upload = new ServletFileUpload(factory);
		File file = null;
		try {
			List<FileItem> list = upload.parseRequest(req);
			//可以上传多个文件
			for (FileItem item : list) {
				//获取表单的属性名字
				String name = item.getFieldName();
				if (item.isFormField()) {//如果是普通的文本信息
					String value = item.getString(CodingFilter.getCoding());
					if (name.equals("description")) {
						description = value;
					} else if (name.equals("token")) {
						token = value;
					}
				} else {//对传入的非简单的字符串进行处理
					if (file != null) {//只接受第一个文件
						continue;
					}
					//获取路径名
					String filename = item.getName();
					if (filename == null || filename.length() == 0) {
						do {
							filename = rootPath + "/" + (int) (Math.random() * 100000000) + ".noname";
							file = new File(filename);
						} while (file == null || file.exists());
					} else {
						//索引到最后一个反斜杠
						int start = filename.lastIndexOf("\\");
						//截取上传文件的字符串名字，加1是去掉反斜杠
						filename = rootPath + "/" + filename.substring(start + 1);
						file = new File(filename);
					}
					file.getParentFile().mkdirs();
					InputStream inputStream = null;
					OutputStream outputStream = null;
					try {
						inputStream = new BufferedInputStream(item.getInputStream());
						outputStream = new BufferedOutputStream(new FileOutputStream(file));
						int len;
						byte[] bytes = new byte[1024];
						while ((len = inputStream.read(bytes)) != -1) {
							outputStream.write(bytes, 0, len);
						}
					} catch (IOException e) {
						e.printStackTrace();
					} finally {
						if (inputStream != null) {
							try {
								inputStream.close();
							} catch (IOException e) {
								e.printStackTrace();
							}
						}
						if (outputStream != null) {
							try {
								outputStream.close();
							} catch (IOException e) {
								e.printStackTrace();
							}
						}
					}
				}
			}
			
			if (file == null) {
				jsonObject.put("result", false);
				jsonObject.put("info", "not find file");
			}
			Date date = new Date();
			FilePackage filePackage = new FilePackage(file.getName(), date, description);
			File reFile = filePackage.getFile();
			if (token == null || !token.equals(LoginServlet.getToken())) {
				file.delete();
				jsonObject.put("result", false);
				jsonObject.put("info", "unauthorized access");
			} else if ((reFile.getParentFile().exists() || reFile.getParentFile().mkdirs()) && file.renameTo(reFile)) {
				fileBackup.backup(filePackage);
				jsonObject.put("result", true);
				jsonObject.put("info", filePackage.getUrl());
			} else {
				jsonObject.put("result", false);
				jsonObject.put("info", "move file fail");
			}
		} catch (FileUploadException | UnsupportedEncodingException e) {
			e.printStackTrace();
			jsonObject.put("result", false);
			jsonObject.put("info", "upload fail");
		} finally {
			writer.write(jsonObject.toString());
			writer.close();
		}
	}
}
