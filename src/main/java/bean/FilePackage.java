package bean;


import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by cellargalaxy on 17-10-15.
 */
public class FilePackage {
	private final static DateFormat DATE_FORMAT = new SimpleDateFormat("yyyyMMdd");
	private final static File ROOT_FOLDER = new File(new File(FilePackage.class.getResource("").getPath()).getParentFile().getParentFile().getParentFile().getAbsolutePath() + File.separator + "drive");
	
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
	
	public static final File createFile(String fileName, Date uploadDate) {
		return new File(ROOT_FOLDER.getAbsolutePath() + File.separator + DATE_FORMAT.format(uploadDate) + File.separator + fileName);
	}
	
	public static final String createUrl(String fileName, Date uploadDate) {
		return ROOT_FOLDER.getName() + File.separator + DATE_FORMAT.format(uploadDate) + File.separator + fileName;
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
			file = FilePackage.createFile(fileName, uploadDate);
		}
		return file;
	}
	
	public String getUrl() {
		if (url == null) {
			url = FilePackage.createUrl(fileName, uploadDate);
		}
		return url;
	}
	
	public void setFile(File file) {
		this.file = file;
	}
	
	public void setUrl(String url) {
		this.url = url;
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
