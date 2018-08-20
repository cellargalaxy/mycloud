package top.cellargalaxy.mycloud.model.bo.schedule;

import top.cellargalaxy.mycloud.exception.GlobalException;
import top.cellargalaxy.mycloud.model.po.FileInfoPo;
import top.cellargalaxy.mycloud.model.po.UserPo;

import java.io.IOException;
import java.util.Objects;

/**
 * @author cellargalaxy
 * @time 2018/8/20
 */
public class RemoveFileTask extends Task {
	public static final String TASK_SORT = "删除文件";
	private final FileInfoPo fileInfoPo;

	public RemoveFileTask(UserPo userPo, FileInfoPo fileInfoPo) {
		super(userPo, TASK_SORT);
		this.fileInfoPo = fileInfoPo;
	}

	public static final FileInfoPo deserialization(String json) {
		try {
			return OBJECT_MAPPER.readValue(json, FileInfoPo.class);
		} catch (IOException e) {
			e.printStackTrace();
			GlobalException.add(e);
		}
		return null;
	}

	public FileInfoPo getFileInfoPo() {
		return fileInfoPo;
	}

	@Override
	public String toString() {
		return "RemoveFileTask{" +
				"super=" + super.toString() +
				", fileInfoPo=" + fileInfoPo +
				'}';
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		if (!super.equals(o)) return false;
		RemoveFileTask that = (RemoveFileTask) o;
		return Objects.equals(fileInfoPo, that.fileInfoPo);
	}

	@Override
	public int hashCode() {

		return Objects.hash(super.hashCode(), fileInfoPo);
	}

	@Override
	public String serializationTaskDetail() {
		return serialization2Json(fileInfoPo);
	}
}
