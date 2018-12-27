package top.cellargalaxy.mycloud.configuration;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

/**
 * @author cellargalaxy
 * @time 2018/8/21
 */
@SpringBootConfiguration
public class CacheConfiguration {
	private static final int second = 1;
	private static final int minute = 60 * second;
	private static final int hour = 60 * minute;
	private static final int day = 24 * hour;
	private static final int month = 30 * day;
	private static final int year = 12 * month;

	@Bean
	@Primary
	public CacheManager caffeineCacheManager() {
		SimpleCacheManager cacheManager = new SimpleCacheManager();

		ArrayList<CaffeineCache> caches = new ArrayList<>();

		for (int i = 1; i < 60; i++) {
			caches.add(
					new CaffeineCache(
							i + "s",
							Caffeine.newBuilder().recordStats()
									.expireAfterWrite(i, TimeUnit.SECONDS)
									.maximumSize(100)
									.build())
			);
		}
		for (int i = 1; i < 60; i++) {
			caches.add(
					new CaffeineCache(
							i + "m",
							Caffeine.newBuilder().recordStats()
									.expireAfterWrite(i, TimeUnit.MINUTES)
									.maximumSize(100)
									.build())
			);
		}
		for (int i = 1; i < 24; i++) {
			caches.add(
					new CaffeineCache(
							i + "h",
							Caffeine.newBuilder().recordStats()
									.expireAfterWrite(i, TimeUnit.HOURS)
									.maximumSize(100)
									.build())
			);
		}
		for (int i = 1; i < 3650; i++) {
			caches.add(
					new CaffeineCache(
							i + "d",
							Caffeine.newBuilder().recordStats()
									.expireAfterWrite(i, TimeUnit.DAYS)
									.maximumSize(100)
									.build())
			);
		}

		cacheManager.setCaches(caches);

		return cacheManager;
	}

//	@Bean
//	public CacheManager cacheManager(RedisConnectionFactory redisConnectionFactory) {
//		return new RedisCacheManager(
//				RedisCacheWriter.nonLockingRedisCacheWriter(redisConnectionFactory),
//				this.createRedisCacheConfigurationWithTtl(minute), // 默认策略，未配置的 key 会使用这个
//				this.createRedisCacheConfigurationMap() // 指定 key 策略
//		);
//	}
//
//	private Map<String, RedisCacheConfiguration> createRedisCacheConfigurationMap() {
//		Map<String, RedisCacheConfiguration> redisCacheConfigurationMap = new HashMap<>();
//		//秒s
//		for (int i = 1; i < 60; i++) {
//			redisCacheConfigurationMap.put(i + "s", createRedisCacheConfigurationWithTtl(i * second));
//		}
//		//分m
//		for (int i = 1; i < 60; i++) {
//			redisCacheConfigurationMap.put(i + "m", createRedisCacheConfigurationWithTtl(i * minute));
//		}
//		//时h
//		for (int i = 1; i < 24; i++) {
//			redisCacheConfigurationMap.put(i + "h", createRedisCacheConfigurationWithTtl(i * hour));
//		}
//		//日d
//		for (int i = 1; i < 30; i++) {
//			redisCacheConfigurationMap.put(i + "d", createRedisCacheConfigurationWithTtl(i * day));
//		}
//		//月M
//		for (int i = 1; i < 12; i++) {
//			redisCacheConfigurationMap.put(i + "M", createRedisCacheConfigurationWithTtl(i * month));
//		}
//		//年y
//		for (int i = 1; i < 10; i++) {
//			redisCacheConfigurationMap.put(i + "y", createRedisCacheConfigurationWithTtl(i * year));
//		}
//		return redisCacheConfigurationMap;
//	}
//
//	private RedisCacheConfiguration createRedisCacheConfigurationWithTtl(int seconds) {
//		RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig();
//		redisCacheConfiguration = redisCacheConfiguration.entryTtl(Duration.ofSeconds(seconds));
//		return redisCacheConfiguration;
//	}

}
