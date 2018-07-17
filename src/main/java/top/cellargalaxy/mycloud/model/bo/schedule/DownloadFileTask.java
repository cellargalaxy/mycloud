package top.cellargalaxy.mycloud.model.bo.schedule;

import top.cellargalaxy.mycloud.model.query.FileInfoQuery;

import java.io.File;

/**
 * @author cellargalaxy
 * @time 2018/7/17
 */
public class DownloadFileTask extends Task {
	private final FileInfoQuery fileInfoQuery;
	private final File file;

	public DownloadFileTask(FileInfoQuery fileInfoQuery, File file) {
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
	public String toString() {
		return "DownloadFileTask{" +
				"fileInfoQuery=" + fileInfoQuery +
				", file=" + file +
				'}';
	}
}
