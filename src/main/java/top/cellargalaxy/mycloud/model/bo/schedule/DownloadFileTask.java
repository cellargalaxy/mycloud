package top.cellargalaxy.mycloud.model.bo.schedule;

import top.cellargalaxy.mycloud.model.po.UserPo;
import top.cellargalaxy.mycloud.model.query.FileInfoQuery;

import java.io.File;
import java.util.Objects;

/**
 * @author cellargalaxy
 * @time 2018/7/17
 */
public class DownloadFileTask extends Task {
	public static final String TASK_SORT = "下载文件";
	private final FileInfoQuery fileInfoQuery;
	private final File file;

	public DownloadFileTask(UserPo userPo, FileInfoQuery fileInfoQuery, File file) {
		super(userPo, TASK_SORT);
		this.fileInfoQuery = fileInfoQuery;
		this.file = file;
	}

	public FileInfoQuery getFileInfoQuery() {
		return fileInfoQuery;
	}

	public File getFile() {
		return file;
	}

	@Override
	public String getTaskDetail() {
		return "查询下载文件: " + fileInfoQuery + "\n写入文件到: " + file;
	}

	@Override
	public String toString() {
		return "DownloadFileTask{" +
				"super=" + super.toString() +
				", fileInfoQuery=" + fileInfoQuery +
				", file=" + file +
				'}';
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		DownloadFileTask that = (DownloadFileTask) o;
		return Objects.equals(fileInfoQuery, that.fileInfoQuery) &&
				Objects.equals(file, that.file);
	}

	@Override
	public int hashCode() {
		return Objects.hash(fileInfoQuery, file);
	}
}
