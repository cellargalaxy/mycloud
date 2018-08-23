package top.cellargalaxy.mycloud.configuration;

import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

/**
 * @author cellargalaxy
 * @time 2018/8/21
 */
@Configuration
public class CacheConfiguration {
	private static final int minute = 60;
	private static final int hour = 60 * minute;
	private static final int day = 24 * hour;
	private static final int month = 30 * day;
	private static final int year = 12 * month;

	@Bean
	public CacheManager cacheManager(RedisConnectionFactory redisConnectionFactory) {
		return new RedisCacheManager(
				RedisCacheWriter.nonLockingRedisCacheWriter(redisConnectionFactory),
				this.createRedisCacheConfigurationWithTtl(minute), // 默认策略，未配置的 key 会使用这个
				this.createRedisCacheConfigurationMap() // 指定 key 策略
		);
	}

	private Map<String, RedisCacheConfiguration> createRedisCacheConfigurationMap() {
		Map<String, RedisCacheConfiguration> redisCacheConfigurationMap = new HashMap<>();
		redisCacheConfigurationMap.put("block", createRedisCacheConfigurationWithTtl(year));
		redisCacheConfigurationMap.put("fileBlock", createRedisCacheConfigurationWithTtl(year));
		redisCacheConfigurationMap.put("fileInfo", createRedisCacheConfigurationWithTtl(minute));
		redisCacheConfigurationMap.put("task", createRedisCacheConfigurationWithTtl(minute));
		redisCacheConfigurationMap.put("own", createRedisCacheConfigurationWithTtl(minute));
		redisCacheConfigurationMap.put("user", createRedisCacheConfigurationWithTtl(minute));
		redisCacheConfigurationMap.put("authorization", createRedisCacheConfigurationWithTtl(minute));
		redisCacheConfigurationMap.put("permission", createRedisCacheConfigurationWithTtl(minute));
		return redisCacheConfigurationMap;
	}

	private RedisCacheConfiguration createRedisCacheConfigurationWithTtl(int seconds) {
		RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig();
		redisCacheConfiguration = redisCacheConfiguration.entryTtl(Duration.ofSeconds(seconds));
		return redisCacheConfiguration;
	}

}
