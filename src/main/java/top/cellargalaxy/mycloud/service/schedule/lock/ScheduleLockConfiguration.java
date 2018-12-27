package top.cellargalaxy.mycloud.service.schedule.lock;

import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.context.annotation.Conditional;
import org.springframework.core.type.AnnotatedTypeMetadata;
import top.cellargalaxy.mycloud.configuration.RedisCondition;

/**
 * @author cellargalaxy
 * @time 18-12-27
 */
@SpringBootConfiguration
public class ScheduleLockConfiguration {
	private static final RedisCondition REDIS_CONDITION = new RedisCondition();

	@Bean
	@Conditional(ScheduleLocalLockCondition.class)
	public ScheduleLock scheduleLocalLock() {
		return new ScheduleLocalLock();
	}

	@Bean
	@Conditional(ScheduleDistributedLockCondition.class)
	public ScheduleLock scheduleDistributedLock(DistributedLock distributedLock) {
		return new ScheduleDistributedLock(distributedLock);
	}

	static class ScheduleLocalLockCondition implements Condition {
		@Override
		public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
			return !REDIS_CONDITION.matches(context, metadata);
		}
	}

	static class ScheduleDistributedLockCondition implements Condition {
		@Override
		public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
			return REDIS_CONDITION.matches(context, metadata);
		}
	}
}
