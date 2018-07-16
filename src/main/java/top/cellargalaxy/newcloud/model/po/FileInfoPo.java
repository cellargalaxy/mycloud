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
	private String md5;
	private long fileLength;
	private String contentType;
	private Date createTime;

	public int getFileId() {
		return fileId;
	}

	public void setFileId(int fileId) {
		this.fileId = fileId;
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

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Override
	public String toString() {
		return "FileInfoPo{" +
				"fileId=" + fileId +
				", md5='" + md5 + '\'' +
				", fileLength=" + fileLength +
				", contentType='" + contentType + '\'' +
				", createTime=" + createTime +
				'}';
	}
}
