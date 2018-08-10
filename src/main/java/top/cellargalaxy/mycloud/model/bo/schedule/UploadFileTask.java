package top.cellargalaxy.mycloud.model.bo.schedule;

import top.cellargalaxy.mycloud.model.po.OwnPo;
import top.cellargalaxy.mycloud.model.po.UserPo;

import java.io.File;
import java.util.Objects;

/**
 * @author cellargalaxy
 * @time 2018/7/17
 */
public class UploadFileTask extends Task {
	public static final String TASK_SORT = "上传文件";
	private final OwnPo ownPo;
	private final File file;
	private final String contentType;

	public UploadFileTask(UserPo userPo, OwnPo ownPo, File file, String contentType) {
		super(userPo, TASK_SORT, "添加所属: " + ownPo + ", 上传文件: " + file + ", 文件类型: " + contentType);
		this.ownPo = ownPo;
		this.file = file;
		this.contentType = contentType;
	}

	public OwnPo getOwnPo() {
		return ownPo;
	}

	public File getFile() {
		return file;
	}

	public String getContentType() {
		return contentType;
	}

	@Override
	public String toString() {
		return "UploadFileTask{" +
				"super=" + super.toString() +
				", ownPo=" + ownPo +
				", file=" + file +
				", contentType='" + contentType + '\'' +
				'}';
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		UploadFileTask that = (UploadFileTask) o;
		return Objects.equals(ownPo, that.ownPo) &&
				Objects.equals(file, that.file) &&
				Objects.equals(contentType, that.contentType);
	}

	@Override
	public int hashCode() {
		return Objects.hash(ownPo, file, contentType);
	}
}
