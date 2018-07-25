package top.cellargalaxy.mycloud.model.po;

import java.io.Serializable;
import java.util.Objects;

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

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		FileBlockPo that = (FileBlockPo) o;
		return fileId == that.fileId &&
				blockId == that.blockId;
	}

	@Override
	public int hashCode() {
		return Objects.hash(fileId, blockId);
	}
}
