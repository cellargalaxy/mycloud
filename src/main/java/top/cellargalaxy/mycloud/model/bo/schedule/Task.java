package top.cellargalaxy.mycloud.model.bo.schedule;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import top.cellargalaxy.mycloud.exception.GlobalException;
import top.cellargalaxy.mycloud.model.po.TaskPo;
import top.cellargalaxy.mycloud.model.po.UserPo;
import top.cellargalaxy.mycloud.model.vo.TaskVo;

import java.io.Closeable;
import java.util.Date;

/**
 * @author cellargalaxy
 * @time 2018/7/17
 */
public abstract class Task extends TaskVo implements Closeable {
	private static final long serialVersionUID = 7062287537522505438L;
	protected static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
	private final UserPo userPo;

	public Task(UserPo userPo, String taskSort) {
		this.userPo = userPo;
		setUserId(userPo.getUserId());
		setTaskSort(taskSort);
		setCreateTime(new Date());
		setStatus(TaskPo.WAIT_STATUS);
	}

	public abstract String serializationTaskDetail();

	protected static final String serialization2Json(Object object) {
		try {
			return OBJECT_MAPPER.writeValueAsString(object);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			GlobalException.add(e);
		}
		return "{}";
	}

	public UserPo getUserPo() {
		return userPo;
	}

	@Override
	public void close() {
		setFinishTime(new Date());
		setTaskDetail(serializationTaskDetail());
	}
}
