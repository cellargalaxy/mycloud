package top.cellargalaxy.mycloud.model.po;

import java.io.Serializable;

/**
 * @author cellargalaxy
 * @time 2018/7/3
 */
public class FileDataPo implements Serializable {
	private static final long serialVersionUID = 4217431251828547844L;
	private int fileId;
	private byte[] fileData;

	public int getFileId() {
		return fileId;
	}

	public void setFileId(int fileId) {
		this.fileId = fileId;
	}

	public byte[] getFileData() {
		return fileData;
	}

	public void setFileData(byte[] fileData) {
		this.fileData = fileData;
	}

	@Override
	public String toString() {
		return "FileDataPo{" +
				"fileId=" + fileId +
				'}';
	}
}
