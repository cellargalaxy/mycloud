package top.cellargalaxy.mycloud.service.schedule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * @author cellargalaxy
 * @time 2018/12/19
 */
@Service
public class ScheduleLockImpl implements ScheduleLock {
    private static final String TMP_FILE_CLEAN_LOCK_KEY = "TMP_FILE_CLEAN_LOCK_KEY";
    private static final String TMP_FILE_CLEAN_LOCK = "TMP_FILE_CLEAN_LOCK";
    //默认请求锁的超时时间(ms 毫秒)
    private static final long TIME_OUT = 100;
    //默认锁的有效时间(s)
    public static final int EXPIRE = 60;
    private static final String LOCK_SUCCESS = "OK";
    private static final String SET_IF_NOT_EXIST = "NX";
    private static final String SET_WITH_EXPIRE_TIME = "PX";

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public boolean tryTmpFileCleanLock() {
        if (stringRedisTemplate.opsForValue().setIfAbsent(TMP_FILE_CLEAN_LOCK_KEY, TMP_FILE_CLEAN_LOCK)) {
            // 设置锁的有效期，也是锁的自动释放时间，也是一个客户端在其他客户端能抢占锁之前可以执行任务的时间
            // 可以防止因异常情况无法释放锁而造成死锁情况的发生
            stringRedisTemplate.expire(TMP_FILE_CLEAN_LOCK_KEY, EXPIRE, TimeUnit.SECONDS);
        }
        return true;
    }
}
