package top.cellargalaxy.mycloud.service.schedule.lock;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author cellargalaxy
 * @time 2018/12/19
 */
@Service
public class ScheduleLockImpl implements ScheduleLock {
    private static final String TMP_FILE_CLEAN_LOCK_KEY = "TMP_FILE_CLEAN_LOCK_KEY";
    private static final int TMP_FILE_CLEAN_LOCK_KEY_EXPIRE_TIME = 1000 * 10;

    @Autowired
    private DistributedLock distributedLock;

    @Override
    public boolean tryTmpFileCleanLock() {
        return distributedLock.tryLock(TMP_FILE_CLEAN_LOCK_KEY, TMP_FILE_CLEAN_LOCK_KEY_EXPIRE_TIME);
    }

    @Override
    public boolean unTmpFileCleanLock() {
        return distributedLock.unLock(TMP_FILE_CLEAN_LOCK_KEY);
    }

    @Override
    public boolean hasTmpFileCleanLock() {
        return distributedLock.hasLock(TMP_FILE_CLEAN_LOCK_KEY);
    }
}
