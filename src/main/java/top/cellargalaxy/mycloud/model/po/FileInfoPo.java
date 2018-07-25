package top.cellargalaxy.mycloud.model.po;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

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

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		FileInfoPo that = (FileInfoPo) o;
		return fileId == that.fileId &&
				fileLength == that.fileLength &&
				Objects.equals(md5, that.md5) &&
				Objects.equals(contentType, that.contentType) &&
				Objects.equals(createTime, that.createTime);
	}

	@Override
	public int hashCode() {
		return Objects.hash(fileId, md5, fileLength, contentType, createTime);
	}
}
