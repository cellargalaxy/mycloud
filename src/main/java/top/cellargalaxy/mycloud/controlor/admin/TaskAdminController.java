package top.cellargalaxy.mycloud.controlor.admin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.cellargalaxy.mycloud.model.po.TaskPo;
import top.cellargalaxy.mycloud.model.query.TaskQuery;
import top.cellargalaxy.mycloud.model.vo.Vo;
import top.cellargalaxy.mycloud.service.TaskService;

/**
 * @author cellargalaxy
 * @time 2018/8/10
 */
@PreAuthorize("hasAuthority('ADMIN')")
@RestController
@RequestMapping(TaskAdminController.URL)
public class TaskAdminController {
	public static final String URL = "/admin/task";
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private TaskService taskService;

	@PostMapping("/removeWaitTask")
	public Vo removeWaitTask(TaskPo taskPo) {
		String string = taskService.removeWaitTask(taskPo);
		logger.info("removeWaitTask:{}", string);
		return new Vo(string, null);
	}

	@GetMapping("/getCurrentTask")
	public Vo getCurrentTask() {
		logger.info("getCurrentTask");
		return new Vo(null, taskService.getCurrentTask());
	}

	@GetMapping("/listWaitTask")
	public Vo listWaitTask() {
		logger.info("listWaitTask");
		return new Vo(null, taskService.listWaitTask());
	}

	@GetMapping("/getWaitTaskCount")
	public Vo getWaitTaskCount() {
		logger.info("getWaitTaskCount");
		return new Vo(null, taskService.getWaitTaskCount());
	}

	@PostMapping("/removeTask")
	public Vo removeTask(TaskPo taskPo) {
		String string = taskService.removeTask(taskPo);
		logger.info("removeTask,{}", taskPo);
		return new Vo(string, null);
	}

	@GetMapping("/getTask")
	public Vo getTask(TaskPo taskPo) {
		logger.info("getTask");
		return new Vo(null, taskService.getTask(taskPo));
	}

	@GetMapping("/listTask")
	public Vo listTask(TaskQuery taskQuery) {
		logger.info("listTask");
		return new Vo(null, taskService.listTask(taskQuery));
	}

	@GetMapping("/getTaskCount")
	public Vo getTaskCount(TaskQuery taskQuery) {
		logger.info("getTaskCount");
		return new Vo(null, taskService.getTaskCount(taskQuery));
	}
}
