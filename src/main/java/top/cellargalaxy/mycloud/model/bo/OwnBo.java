package top.cellargalaxy.mycloud.model.bo;

import top.cellargalaxy.mycloud.model.po.OwnPo;

import java.util.Objects;

/**
 * @author cellargalaxy
 * @time 2018/7/16
 */
public class OwnBo extends OwnPo {
	private String username;
	private String md5;
	private long fileLength;
	private String contentType;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
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

	@Override
	public String toString() {
		return "OwnBo{" +
				"super=" + super.toString() +
				", username='" + username + '\'' +
				", md5='" + md5 + '\'' +
				", fileLength=" + fileLength +
				", contentType='" + contentType + '\'' +
				'}';
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		if (!super.equals(o)) return false;
		OwnBo ownBo = (OwnBo) o;
		return fileLength == ownBo.fileLength &&
				Objects.equals(username, ownBo.username) &&
				Objects.equals(md5, ownBo.md5) &&
				Objects.equals(contentType, ownBo.contentType);
	}

	@Override
	public int hashCode() {
		return Objects.hash(super.hashCode(), username, md5, fileLength, contentType);
	}
}
