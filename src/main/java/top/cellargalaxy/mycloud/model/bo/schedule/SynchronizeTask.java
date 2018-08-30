package top.cellargalaxy.mycloud.model.bo.schedule;

/**
 * @author cellargalaxy
 * @time 2018/8/21
 */
public class SynchronizeTask extends Task {
	public static final String TASK_SORT = "同步数据";

	public SynchronizeTask() {
		super(TASK_SORT);
	}

	@Override
	public String serializationTaskDetail() {
		return null;
	}
}
