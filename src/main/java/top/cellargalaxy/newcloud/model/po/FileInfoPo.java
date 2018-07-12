package top.cellargalaxy.newcloud.model.po;

import java.io.Serializable;
import java.util.Date;

/**
 * @author cellargalaxy
 * @time 2018/7/3
 */
public class FileInfoPo implements Serializable {
	private static final long serialVersionUID = 4632424672128158014L;
	private int fileId;
	private String fileName;
	private Date createTime;
	private String md5;
	private long fileLength;
	private String contentType;
	private int userId;
	private String sort;
	private String description;
	private Date updateTime;

	public int getFileId() {
		return fileId;
	}

	public void setFileId(int fileId) {
		this.fileId = fileId;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getMd5() {
		return md5;
	}

	public void setMd5(String md5) {
		this.md5 = md5;
	}

	public long getFileLength() {
		return fileLength;
	}

	public void setFileLength(long fileLength) {
		this.fileLength = fileLength;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	@Override
	public String toString() {
		return "FileInfoPo{" +
				"fileId=" + fileId +
				", fileName='" + fileName + '\'' +
				", createTime=" + createTime +
				", md5='" + md5 + '\'' +
				", fileLength=" + fileLength +
				", contentType='" + contentType + '\'' +
				", userId=" + userId +
				", sort='" + sort + '\'' +
				", description='" + description + '\'' +
				", updateTime=" + updateTime +
				'}';
	}
}
