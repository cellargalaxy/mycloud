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
	private static final int defaultSeconds = 60;

	@Bean
	public CacheManager cacheManager(RedisConnectionFactory redisConnectionFactory) {
		return new RedisCacheManager(
				RedisCacheWriter.nonLockingRedisCacheWriter(redisConnectionFactory),
				this.createRedisCacheConfigurationWithTtl(defaultSeconds), // 默认策略，未配置的 key 会使用这个
				this.createRedisCacheConfigurationMap() // 指定 key 策略
		);
	}

	private Map<String, RedisCacheConfiguration> createRedisCacheConfigurationMap() {
		Map<String, RedisCacheConfiguration> redisCacheConfigurationMap = new HashMap<>();
		redisCacheConfigurationMap.put("block", createRedisCacheConfigurationWithTtl(0));
		redisCacheConfigurationMap.put("fileBlock", createRedisCacheConfigurationWithTtl(0));
		redisCacheConfigurationMap.put("task", createRedisCacheConfigurationWithTtl(defaultSeconds));
		redisCacheConfigurationMap.put("fileInfo", createRedisCacheConfigurationWithTtl(defaultSeconds));
		redisCacheConfigurationMap.put("own", createRedisCacheConfigurationWithTtl(defaultSeconds));
		redisCacheConfigurationMap.put("user", createRedisCacheConfigurationWithTtl(defaultSeconds));
		redisCacheConfigurationMap.put("authorization", createRedisCacheConfigurationWithTtl(defaultSeconds));
		redisCacheConfigurationMap.put("permission", createRedisCacheConfigurationWithTtl(defaultSeconds));
		return redisCacheConfigurationMap;
	}

	private RedisCacheConfiguration createRedisCacheConfigurationWithTtl(int seconds) {
		RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig();
		redisCacheConfiguration = redisCacheConfiguration.entryTtl(Duration.ofSeconds(seconds));
		return redisCacheConfiguration;
	}

}
