package top.cellargalaxy.mycloud.service.schedule;

/**
 * @author cellargalaxy
 * @time 2018/7/30
 */
public interface TaskExecute<Task> {
	void executeTask(Task task) throws Exception;
}
