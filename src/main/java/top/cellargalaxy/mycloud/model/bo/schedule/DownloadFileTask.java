package top.cellargalaxy.mycloud.model.bo.schedule;

import top.cellargalaxy.mycloud.model.po.FileInfoPo;
import top.cellargalaxy.mycloud.model.po.UserPo;

import java.io.File;
import java.util.Objects;

/**
 * @author cellargalaxy
 * @time 2018/7/17
 */
public class DownloadFileTask extends Task {
	public static final String TASK_SORT = "下载文件";
	private final FileInfoPo fileInfoPo;
	private final File file;

	public DownloadFileTask(UserPo userPo, FileInfoPo fileInfoPo, File file) {
		super(userPo, TASK_SORT,"查询下载文件: " + fileInfoPo + ", 写入文件到: " + file);
		this.fileInfoPo = fileInfoPo;
		this.file = file;
	}

	public FileInfoPo getFileInfoPo() {
		return fileInfoPo;
	}

	public File getFile() {
		return file;
	}

	@Override
	public String toString() {
		return "DownloadFileTask{" +
				"super=" + super.toString() +
				", fileInfoPo=" + fileInfoPo +
				", file=" + file +
				'}';
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		if (!super.equals(o)) return false;
		DownloadFileTask that = (DownloadFileTask) o;
		return Objects.equals(fileInfoPo, that.fileInfoPo) &&
				Objects.equals(file, that.file);
	}

	@Override
	public int hashCode() {

		return Objects.hash(super.hashCode(), fileInfoPo, file);
	}
}
