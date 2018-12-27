package top.cellargalaxy.mycloud.service.schedule;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import top.cellargalaxy.mycloud.configuration.MycloudConfiguration;
import top.cellargalaxy.mycloud.dao.OwnExpireDao;
import top.cellargalaxy.mycloud.model.bo.OwnExpireBo;
import top.cellargalaxy.mycloud.model.po.OwnPo;
import top.cellargalaxy.mycloud.service.FileService;
import top.cellargalaxy.mycloud.service.OwnExpireService;
import top.cellargalaxy.mycloud.service.OwnService;
import top.cellargalaxy.mycloud.service.schedule.lock.ScheduleLock;
import top.cellargalaxy.mycloud.util.StringUtils;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author cellargalaxy
 * @time 2018/12/19
 */
@Component
public class TmpFileCleanSchedule {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	private final String dbType;
	@Autowired
	private ScheduleLock scheduleLock;
	@Autowired
	private OwnExpireService ownExpireService;
	@Autowired
	private OwnService ownService;
	@Autowired
	private FileService fileService;

	@Autowired
	private OwnExpireDao ownExpireDao;

	@Autowired
	public TmpFileCleanSchedule(MycloudConfiguration mycloudConfiguration) {
		dbType = mycloudConfiguration.getDbType();
	}

	@Scheduled(initialDelay = 1000 * 10, fixedDelay = 1000 * 60 * 5)
	public void cleanTmpFile() throws IOException {
		if (!StringUtils.isBlank(dbType) && dbType.trim().toLowerCase().equals("sqlite")) {
			logger.debug("sqlite数据库，开始清理临时文件");
			List<OwnExpireBo> ownExpireBos = ownExpireService.listAllOwnExpire();
			ownExpireBos = ownExpireBos.stream().filter(ownExpireBo -> ownExpireBo.getOwnExpireTime().getTime() <= System.currentTimeMillis()).collect(Collectors.toList());
			cleanTmpFile(ownExpireBos);
			logger.debug("清理临时文件定时任务完成，共清理：" + ownExpireBos.size());
			return;
		}
		try {
			if (!scheduleLock.tryTmpFileCleanLock(1000 * 10)) {
				logger.debug("清理临时文件定时任务没有获取到锁，取消清理");
				return;
			}
			logger.debug("清理临时文件定时任务获取到锁，开始清理");
			List<OwnExpireBo> ownExpireBos = ownExpireService.listExpireOwnExpire();
			cleanTmpFile(ownExpireBos);
			logger.debug("清理临时文件定时任务完成，共清理：" + ownExpireBos.size());
		} finally {
			scheduleLock.unTmpFileCleanLock();
		}
	}

	private void cleanTmpFile(List<OwnExpireBo> ownExpireBos) throws IOException {
		for (OwnExpireBo ownExpireBo : ownExpireBos) {
			OwnPo ownPo = new OwnPo();
			ownPo.setOwnId(ownExpireBo.getOwnId());
			fileService.removeFile(ownPo);
			ownExpireService.removeOwnExpire(ownExpireBo);
			ownService.removeOwn(ownPo);
		}
	}
}
