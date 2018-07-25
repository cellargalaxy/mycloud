package top.cellargalaxy.mycloud.model.vo;

import top.cellargalaxy.mycloud.model.bo.FileInfoBo;
import top.cellargalaxy.mycloud.model.bo.OwnBo;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

/**
 * @author cellargalaxy
 * @time 2018/7/17
 */
public class FileInfoOwnVo implements Serializable {
	private static final long serialVersionUID = -1087172801966336462L;
	private final FileInfoBo fileInfo;
	private final List<OwnBo> owns;

	public FileInfoOwnVo(FileInfoBo fileInfo, List<OwnBo> owns) {
		this.fileInfo = fileInfo;
		this.owns = owns;
	}

	public FileInfoBo getFileInfo() {
		return fileInfo;
	}

	public List<OwnBo> getOwns() {
		return owns;
	}

	@Override
	public String toString() {
		return "FileInfoOwnVo{" +
				"fileInfo=" + fileInfo +
				", owns=" + owns +
				'}';
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		FileInfoOwnVo that = (FileInfoOwnVo) o;
		return Objects.equals(fileInfo, that.fileInfo) &&
				Objects.equals(owns, that.owns);
	}

	@Override
	public int hashCode() {
		return Objects.hash(fileInfo, owns);
	}
}
