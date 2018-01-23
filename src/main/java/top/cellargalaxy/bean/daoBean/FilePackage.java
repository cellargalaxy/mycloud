package top.cellargalaxy.bean.daoBean;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.File;
import java.util.Date;

/**
 * Created by cellargalaxy on 17-12-2.
 */
@JsonIgnoreProperties(value = {"fileBytes", "file"})
public class FilePackage {
	private String filename;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
	private Date date;
	private String description;
	private long fileLength;
	private String fileSha256;
	private byte[] fileBytes;
	
	private File file;
	private String url;
	
	public FilePackage() {
	}
	
	public FilePackage(String filename, Date date, String description, long fileLength, String fileSha256, byte[] fileBytes, File file, String url) {
		this.filename = filename;
		this.date = date;
		this.description = description;
		this.fileLength = fileLength;
		this.fileSha256 = fileSha256;
		this.fileBytes = fileBytes;
		this.file = file;
		this.url = url;
	}
	
	public String getFilename() {
		return filename;
	}
	
	public void setFilename(String filename) {
		this.filename = filename;
	}
	
	public Date getDate() {
		return date;
	}
	
	public void setDate(Date date) {
		this.date = date;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public long getFileLength() {
		return fileLength;
	}
	
	public void setFileLength(long fileLength) {
		this.fileLength = fileLength;
	}
	
	public String getFileSha256() {
		return fileSha256;
	}
	
	public void setFileSha256(String fileSha256) {
		this.fileSha256 = fileSha256;
	}
	
	public byte[] getFileBytes() {
		return fileBytes;
	}
	
	public void setFileBytes(byte[] fileBytes) {
		this.fileBytes = fileBytes;
	}
	
	public File getFile() {
		return file;
	}
	
	public void setFile(File file) {
		this.file = file;
	}
	
	public String getUrl() {
		return url;
	}
	
	public void setUrl(String url) {
		this.url = url;
	}
	
	@Override
	public String toString() {
		return "FilePackage{" +
				"filename='" + filename + '\'' +
				", date=" + date +
				", description='" + description + '\'' +
				", fileLength=" + fileLength +
				", fileSha256='" + fileSha256 + '\'' +
				", file=" + file +
				", url='" + url + '\'' +
				'}';
	}
}
