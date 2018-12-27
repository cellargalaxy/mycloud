package top.cellargalaxy.mycloud.configuration;

import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @author cellargalaxy
 * @time 18-12-22
 */
@SpringBootConfiguration
public class JedisConfiguration {

	@Bean
	@Conditional(RedisCondition.class)
	public JedisPool jedisPool(RedisProperties properties) {
		JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
		//最多有多少个jedis实例，-1表示不限制，默认8
		jedisPoolConfig.setMaxTotal(properties.getJedis().getPool().getMaxActive());
		//最多有多少个空闲jedis实例，默认8
		jedisPoolConfig.setMaxIdle(properties.getJedis().getPool().getMaxIdle());
		//至少有多少个空闲jedis实例，默认0
		jedisPoolConfig.setMinIdle(properties.getJedis().getPool().getMinIdle());
		//获取jedis实例的最大的等待时间，单位毫秒。小于零：阻塞任意时间。超时抛出JedisConnectionException。默认-1
		jedisPoolConfig.setMaxWaitMillis(properties.getJedis().getPool().getMaxWait().toMillis());
		//获取jedis实例时是否检查可用性，以确保获取到的jedis实例是可用的
		jedisPoolConfig.setTestOnBorrow(true);
		//返回jedis实例给pool时是否检查可用性
		jedisPoolConfig.setTestOnReturn(true);
		//connectionTimeout：连接超时。默认2000ms
		//soTimeout：响应超时。默认2000ms
		JedisPool jedisPool = new JedisPool(jedisPoolConfig, properties.getHost(), properties.getPort(), (int) properties.getTimeout().toMillis(), properties.getPassword());
		return jedisPool;
	}
}
