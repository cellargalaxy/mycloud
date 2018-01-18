package top.cellargalaxy.bean;

import org.json.JSONObject;

import java.io.*;
import java.util.Date;

/**
 * Created by cellargalaxy on 17-12-2.
 */
public class FilePackage {
	private File file;
	private byte[] filebytes;
	private String filename;
	private Date date;
	private String description;
	private String url;
	private String fileSize;
	
	public FilePackage() {
	}
	
	public FilePackage(File file, Date date, String description, String url, String fileSize) {
		this.file = file;
		this.date = date;
		this.description = description;
		this.url = url;
		this.fileSize = fileSize;
		filename = file.getName();
	}
	
	public File getFile() {
		return file;
	}
	
	public void setFile(File file) {
		this.file = file;
	}
	
	public byte[] getFilebytes() {
		return filebytes;
	}
	
	public void setFilebytes(byte[] filebytes) {
		this.filebytes = filebytes;
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
	
	public String getUrl() {
		return url;
	}
	
	public void setUrl(String url) {
		this.url = url;
	}
	
	public String getFileSize() {
		return fileSize;
	}
	
	public void setFileSize(String fileSize) {
		this.fileSize = fileSize;
	}
	
	public JSONObject toJSONObject() {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("filename", filename);
		jsonObject.put("date", date);
		jsonObject.put("description", description);
		jsonObject.put("url", url);
		jsonObject.put("fileSize", fileSize);
		return jsonObject;
	}
	
	@Override
	public String toString() {
		return "FilePackage{" +
				"file=" + file +
				", filename='" + filename + '\'' +
				", date=" + date +
				", description='" + description + '\'' +
				", url='" + url + '\'' +
				", fileSize='" + fileSize + '\'' +
				'}';
	}
}
