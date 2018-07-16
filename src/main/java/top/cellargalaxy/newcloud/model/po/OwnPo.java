package top.cellargalaxy.newcloud.model.po;

import java.io.Serializable;
import java.util.Date;

/**
 * @author cellargalaxy
 * @time 2018/7/16
 */
public class OwnPo implements Serializable {
	private static final long serialVersionUID = -1711152764871510164L;
	private int ownId;
	private int userId;
	private int fileId;
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
	public String toString() {
		return "OwnPo{" +
				"ownId=" + ownId +
				", userId=" + userId +
				", fileId=" + fileId +
				", fileName='" + fileName + '\'' +
				", sort='" + sort + '\'' +
				", description='" + description + '\'' +
				", createTime=" + createTime +
				", updateTime=" + updateTime +
				'}';
	}
}
