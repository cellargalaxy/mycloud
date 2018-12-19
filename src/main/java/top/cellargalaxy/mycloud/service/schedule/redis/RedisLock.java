package top.cellargalaxy.mycloud.service.schedule.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import top.cellargalaxy.mycloud.util.StringUtils;

import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @author cellargalaxy
 * @time 2018/12/19
 */
@Service
public class RedisLock {
    public static final String VALUE = UUID.randomUUID().toString();
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    public boolean tryLock(String key, int expire) {
        try {
            if (stringRedisTemplate.opsForValue().setIfAbsent(key, VALUE)) {
                return stringRedisTemplate.expire(key, expire, TimeUnit.SECONDS);
            }
            return false;
        } catch (Exception e) {
            unLock(key);
        }
    }

    public boolean unLock(String key) {
        String value = stringRedisTemplate.opsForValue().get(key);
        if (!StringUtils.isBlank(value) && value.equals(VALUE)) {
            return stringRedisTemplate.opsForValue().getOperations().delete(key);
        }
        return false;
    }

}
