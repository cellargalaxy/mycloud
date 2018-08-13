package top.cellargalaxy.mycloud.service.schedule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import top.cellargalaxy.mycloud.configuration.MycloudConfiguration;
import top.cellargalaxy.mycloud.model.po.UserPo;
import top.cellargalaxy.mycloud.service.FileService;

/**
 * @author cellargalaxy
 * @time 2018/8/13
 */
@Component
public class RestoreFileToLocalSchedule {
	private UserPo userPo;
	@Autowired
	private MycloudConfiguration mycloudConfiguration;
	@Autowired
	private FileService fileService;

	@Scheduled(fixedDelay = 1000 * 60 * 60 * 3)
	public void schedule() {
		if (mycloudConfiguration.isRestoreFileToLocal()) {
			fileService.restoreAllFileToLocal(userPo);
		}
	}

	public UserPo getUserPo() {
		return userPo;
	}

	public void setUserPo(UserPo userPo) {
		this.userPo = userPo;
	}
}
