package top.cellargalaxy.mycloud.service.schedule;

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
public class TmpFileClean {
    @Autowired
    private ScheduleLock scheduleLock;
    @Autowired
    private OwnExpireService ownExpireService;
    @Autowired
    private FileService fileService;
    @Autowired
    private OwnService ownService;

    @Scheduled(initialDelay = 1000 * 10, fixedDelay = 1000 * 60 * 5)
    public void cleanTmpFile() throws IOException {
        if (!scheduleLock.tryTmpFileCleanLock()) {
            return;
        }
        List<OwnExpireBo> ownExpireBos = ownExpireService.listExpireOwnExpire();
        for (OwnExpireBo ownExpireBo : ownExpireBos) {
            OwnPo ownPo = new OwnPo();
            ownPo.setOwnId(ownExpireBo.getOwnId());
            fileService.removeFile(ownPo);
            ownExpireService.removeOwnExpire(ownExpireBo);
            ownService.removeOwn(ownPo);
        }
    }
}
