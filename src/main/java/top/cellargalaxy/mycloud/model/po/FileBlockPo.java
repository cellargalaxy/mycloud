package top.cellargalaxy.mycloud.model.po;

import java.io.Serializable;

/**
 * @author cellargalaxy
 * @time 2018/7/16
 */
public class FileBlockPo implements Serializable {
	private static final long serialVersionUID = -5242431242741858420L;
	private int fileId;
	private int blockId;

	public int getFileId() {
		return fileId;
	}

	public void setFileId(int fileId) {
		this.fileId = fileId;
	}

	public int getBlockId() {
		return blockId;
	}

	public void setBlockId(int blockId) {
		this.blockId = blockId;
	}

	@Override
	public String toString() {
		return "FileBlockPo{" +
				"fileId=" + fileId +
				", blockId=" + blockId +
				'}';
	}
}
