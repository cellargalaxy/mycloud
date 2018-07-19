package top.cellargalaxy.mycloud.model.vo;

import top.cellargalaxy.mycloud.model.bo.FileInfoBo;
import top.cellargalaxy.mycloud.model.bo.OwnBo;

import java.io.Serializable;
import java.util.List;

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
}
