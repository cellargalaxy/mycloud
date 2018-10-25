package top.cellargalaxy.mycloud.model.vo;

import top.cellargalaxy.mycloud.model.bo.FileInfoBo;
import top.cellargalaxy.mycloud.model.bo.OwnBo;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

/**
 * @author cellargalaxy
 * @time 2018/10/25
 */
public class FileInfoVo implements Serializable {
	private static final long serialVersionUID = -8081110814279748765L;
	private FileInfoBo fileInfo;
	private List<OwnBo> owns;

	public FileInfoBo getFileInfo() {
		return fileInfo;
	}

	public void setFileInfo(FileInfoBo fileInfo) {
		this.fileInfo = fileInfo;
	}

	public List<OwnBo> getOwns() {
		return owns;
	}

	public void setOwns(List<OwnBo> owns) {
		this.owns = owns;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		FileInfoVo that = (FileInfoVo) o;
		return Objects.equals(fileInfo, that.fileInfo) &&
				Objects.equals(owns, that.owns);
	}

	@Override
	public int hashCode() {

		return Objects.hash(fileInfo, owns);
	}

	@Override
	public String toString() {
		return "FileInfoVo{" +
				"fileInfo=" + fileInfo +
				", owns=" + owns +
				'}';
	}
}
