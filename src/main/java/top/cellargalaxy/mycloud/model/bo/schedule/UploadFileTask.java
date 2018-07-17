package top.cellargalaxy.mycloud.model.bo.schedule;

import top.cellargalaxy.mycloud.model.po.OwnPo;
import top.cellargalaxy.mycloud.model.po.UserPo;

import java.io.File;

/**
 * @author cellargalaxy
 * @time 2018/7/17
 */
public class UploadFileTask extends Task {
	private final File file;
	private final String contentType;
	private final OwnPo ownPo;

	public UploadFileTask(UserPo userPo, File file, String contentType, OwnPo ownPo) {
		super(userPo);
		this.file = file;
		this.contentType = contentType;
		this.ownPo = ownPo;
	}

	public File getFile() {
		return file;
	}

	public String getContentType() {
		return contentType;
	}

	public OwnPo getOwnPo() {
		return ownPo;
	}

	@Override
	public String toString() {
		return "UploadFileTask{" +
				"super=" + super.toString() +
				", file=" + file +
				", contentType='" + contentType + '\'' +
				", ownPo=" + ownPo +
				'}';
	}
}
