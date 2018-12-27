package top.cellargalaxy.mycloud.configuration;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;
import top.cellargalaxy.mycloud.util.StringUtils;

/**
 * @author cellargalaxy
 * @time 18-12-27
 */
public class RedisCondition implements Condition {
	@Override
	public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
		String enable = context.getEnvironment().getProperty("mycloud.redis.enable");
		return !StringUtils.isBlank(enable) && enable.trim().toLowerCase().equals("true");
	}
}
