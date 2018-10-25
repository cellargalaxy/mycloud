package top.cellargalaxy.mycloud.model.po;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**
 * @author cellargalaxy
 * @time 2018/7/16
 */
public class OwnPo implements Serializable {
	private static final long serialVersionUID = -1711152764871510164L;
	private int ownId;
	private String ownUuid;
	private int userId;
	private int fileId;
	private long fileLength;
	private String contentType;
	private String fileName;
	private String sort;
	private String description;
	private Date createTime;
	private Date updateTime;

	public int getOwnId() {
		return ownId;
	}

	public void setOwnId(int ownId) {
		this.ownId = ownId;
	}

	public String getOwnUuid() {
		return ownUuid;
	}

	public void setOwnUuid(String ownUuid) {
		this.ownUuid = ownUuid;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getFileId() {
		return fileId;
	}

	public void setFileId(int fileId) {
		this.fileId = fileId;
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

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
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

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		OwnPo ownPo = (OwnPo) o;
		return ownId == ownPo.ownId &&
				userId == ownPo.userId &&
				fileId == ownPo.fileId &&
				fileLength == ownPo.fileLength &&
				Objects.equals(ownUuid, ownPo.ownUuid) &&
				Objects.equals(contentType, ownPo.contentType) &&
				Objects.equals(fileName, ownPo.fileName) &&
				Objects.equals(sort, ownPo.sort) &&
				Objects.equals(description, ownPo.description) &&
				Objects.equals(createTime, ownPo.createTime) &&
				Objects.equals(updateTime, ownPo.updateTime);
	}

	@Override
	public int hashCode() {

		return Objects.hash(ownId, ownUuid, userId, fileId, fileLength, contentType, fileName, sort, description, createTime, updateTime);
	}

	@Override
	public String toString() {
		return "OwnPo{" +
				"ownId=" + ownId +
				", ownUuid='" + ownUuid + '\'' +
				", userId=" + userId +
				", fileId=" + fileId +
				", fileLength=" + fileLength +
				", contentType='" + contentType + '\'' +
				", fileName='" + fileName + '\'' +
				", sort='" + sort + '\'' +
				'}';
	}
}
