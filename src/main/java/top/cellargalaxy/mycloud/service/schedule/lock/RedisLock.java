package top.cellargalaxy.mycloud.service.schedule.lock;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.params.SetParams;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author cellargalaxy
 * @time 2018/12/19
 */
@ConditionalOnBean(JedisPool.class)
@Service
public final class RedisLock implements DistributedLock {
	//加锁操作成功返回的字符串
	private static final String LOCK_SUCCESS = "OK";
	//释放锁的Lua脚本
	private static final String UN_LOCK_SCRIPT = "if lock.call('get', KEYS[1]) ~= ARGV[1] then return 1 else return lock.call('del', KEYS[1]) end";
	//加锁操作成功返回的long
	private static final Long RELEASE_SUCCESS = 1L;
	//每一个服务实例的每一个线程都有一个唯一的id
	private final ThreadLocal<String> id = new ThreadLocal<>();
	//当前线程是否已经获取了锁，默认为false
	private final ThreadLocal<Map<String, Boolean>> hasLock = ThreadLocal.withInitial(() -> new HashMap<>());
	private final JedisPool jedisPool;

	@Autowired
	public RedisLock(JedisPool jedisPool) {
		this.jedisPool = jedisPool;
	}

	public boolean tryLock(String lockKey, int expireTime) {
		if (id.get() == null) {
			//初始化线程id
			id.set(UUID.randomUUID().toString());
		}
		try (Jedis jedis = jedisPool.getResource()) {
			//SetParams是除了key-value，redis操作的其他参数
			//nx意思的SET IF NOT EXIST，即key不存在才操作
			//px是如果进行操作，这个key的过期时间
			SetParams setParams = new SetParams().nx().px(expireTime);
			//虽然这里检查了key是否存在，设置了value以及过期时间三个操作
			//但是这里调用了一次set方法，redis保证这三个操作的原子性
			String result = jedis.set(lockKey, id.get(), setParams);
			if (LOCK_SUCCESS.equals(result)) {
				hasLock.get().put(lockKey, true);
				return true;
			}
			return false;
		}
	}

	public boolean unLock(String lockKey) {
		if (id.get() == null) {
			//初始化线程id
			id.set(UUID.randomUUID().toString());
		}
		try (Jedis jedis = jedisPool.getResource()) {
			//这里让redis执行Lua脚本，redis也保证Lua脚本的执行的原子性
			Object result = jedis.eval(UN_LOCK_SCRIPT, Collections.singletonList(lockKey), Collections.singletonList(id.get()));
			if (RELEASE_SUCCESS.equals(result)) {
				hasLock.get().remove(lockKey);
				return true;
			}
			return false;
		}
	}

	public boolean hasLock(String lockKey) {
		Boolean b = hasLock.get().get(lockKey);
		if (b == null) {
			return false;
		}
		return b;
	}

	public String getId() {
		if (id.get() == null) {
			//初始化线程id
			id.set(UUID.randomUUID().toString());
		}
		return id.get();
	}
}
