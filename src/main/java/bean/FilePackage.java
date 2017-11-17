package bean;

import configuration.Configuration;
import org.json.JSONObject;

import java.io.File;
import java.text.DateFormat;
import java.util.Date;

/**
 * Created by cellargalaxy on 17-10-15.
 */
public class FilePackage {
	private final static DateFormat DRIVE_PATH_DATE_FORMAT = Configuration.getDrivePathDateFormat();
	private final static File DRIVE_ROOT_FILE = Configuration.getDriveRootFile();
	private final static String DRIVE_URL = Configuration.getDriveUrl();
	
	private String fileName;
	private Date uploadDate;
	private String description;
	private File file;
	private String url;
	
	public FilePackage(String fileName, Date uploadDate, String description) {
		this.fileName = fileName;
		this.uploadDate = uploadDate;
		this.description = description;
	}
	
	public static final File createFile(Date uploadDate, String fileName) {
		return new File(DRIVE_ROOT_FILE.getAbsolutePath() + File.separator + DRIVE_PATH_DATE_FORMAT.format(uploadDate) + File.separator + fileName);
	}
	
	public static final String createUrl(Date uploadDate, String fileName) {
		return DRIVE_URL + File.separator + DRIVE_PATH_DATE_FORMAT.format(uploadDate) + File.separator + fileName;
	}
	
	public String getFileName() {
		return fileName;
	}
	
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	public Date getUploadDate() {
		return uploadDate;
	}
	
	public void setUploadDate(Date uploadDate) {
		this.uploadDate = uploadDate;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public File getFile() {
		if (file == null) {
			file = FilePackage.createFile(uploadDate, fileName);
		}
		return file;
	}
	
	public String getUrl() {
		if (url == null) {
			url = FilePackage.createUrl(uploadDate, fileName);
		}
		return url;
	}
	
	public void setFile(File file) {
		this.file = file;
	}
	
	public void setUrl(String url) {
		this.url = url;
	}
	
	public JSONObject toJSONObject(){
		JSONObject jsonObject=new JSONObject();
		jsonObject.put("fileName", fileName);
		jsonObject.put("uploadDate", uploadDate);
		jsonObject.put("description", description);
		jsonObject.put("url", url);
		return jsonObject;
	}
	
	@Override
	public String toString() {
		return "FilePackage{" +
				"fileName='" + fileName + '\'' +
				", uploadDate=" + uploadDate +
				", description='" + description + '\'' +
				", file=" + file +
				", url='" + url + '\'' +
				'}';
	}
}
