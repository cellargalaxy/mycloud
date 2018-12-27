package top.cellargalaxy.mycloud.service.schedule;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import top.cellargalaxy.mycloud.model.bo.OwnExpireBo;
import top.cellargalaxy.mycloud.model.po.OwnPo;
import top.cellargalaxy.mycloud.service.FileService;
import top.cellargalaxy.mycloud.service.OwnExpireService;
import top.cellargalaxy.mycloud.service.OwnService;
import top.cellargalaxy.mycloud.service.schedule.lock.ScheduleLock;

import java.io.IOException;
import java.util.List;

/**
 * @author cellargalaxy
 * @time 2018/12/19
 */
@Component
public class TmpFileCleanSchedule {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private ScheduleLock scheduleLock;
    @Autowired
    private OwnExpireService ownExpireService;
    @Autowired
    private OwnService ownService;
    @Autowired
    private FileService fileService;

    @Scheduled(initialDelay = 1000 * 10, fixedDelay = 1000 * 60 * 5)
    public void cleanTmpFile() throws IOException {
        try {
            if (!scheduleLock.tryTmpFileCleanLock(1000 * 10)) {
                logger.debug("清理临时文件定时任务没有获取到锁，取消清理");
                return;
            }
            logger.debug("清理临时文件定时任务获取到锁，开始清理");
            List<OwnExpireBo> ownExpireBos = ownExpireService.listExpireOwnExpire();
            for (OwnExpireBo ownExpireBo : ownExpireBos) {
                OwnPo ownPo = new OwnPo();
                ownPo.setOwnId(ownExpireBo.getOwnId());
                fileService.removeFile(ownPo);
                ownExpireService.removeOwnExpire(ownExpireBo);
                ownService.removeOwn(ownPo);
            }
            logger.debug("清理临时文件定时任务完成");
        } finally {
            scheduleLock.unTmpFileCleanLock();
        }
    }
}
