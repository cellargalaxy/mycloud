package top.cellargalaxy.mycloud.model.bo.schedule;

import top.cellargalaxy.mycloud.model.po.OwnPo;
import top.cellargalaxy.mycloud.model.po.UserPo;

import java.io.File;

/**
 * @author cellargalaxy
 * @time 2018/7/17
 */
public class UploadFileTask extends Task {
	private final OwnPo ownPo;
	private final File file;
	private final String contentType;

	public UploadFileTask(UserPo userPo, OwnPo ownPo, File file, String contentType) {
		super(userPo);
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
}
