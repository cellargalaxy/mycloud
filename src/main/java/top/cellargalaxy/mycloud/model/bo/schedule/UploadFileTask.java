package top.cellargalaxy.mycloud.model.bo.schedule;

import com.fasterxml.jackson.databind.JavaType;
import top.cellargalaxy.mycloud.exception.GlobalException;
import top.cellargalaxy.mycloud.model.po.OwnPo;
import top.cellargalaxy.mycloud.model.po.UserPo;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author cellargalaxy
 * @time 2018/7/17
 */
public class UploadFileTask extends Task {
	public static final String TASK_SORT = "上传文件";
	public static final String OWN_PO_KEY = "ownPo";
	public static final String CONTENT_TYPE_KEY = "contentType";
	private final OwnPo ownPo;
	private final File file;
	private final String contentType;

	public UploadFileTask(UserPo userPo, OwnPo ownPo, File file, String contentType) {
		super(userPo, TASK_SORT);
		this.ownPo = ownPo;
		this.file = file;
		this.contentType = contentType;
	}

	public static final Map<String, Object> deserialization(String json) {
		try {
			JavaType javaType = OBJECT_MAPPER.getTypeFactory().constructParametricType(HashMap.class, String.class, Object.class);
			Map<String, Object> map = OBJECT_MAPPER.readValue(json, javaType);
			map.put(OWN_PO_KEY, OBJECT_MAPPER.convertValue(map.get(OWN_PO_KEY), OwnPo.class));
			return map;
		} catch (IOException e) {
			e.printStackTrace();
			GlobalException.add(e);
		}
		return null;
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

	@Override
	public String serializationTaskDetail() {
		return serialization2Json(new HashMap<String, Object>() {{
			put(OWN_PO_KEY, ownPo);
			put(CONTENT_TYPE_KEY, contentType);
		}});
	}
}
