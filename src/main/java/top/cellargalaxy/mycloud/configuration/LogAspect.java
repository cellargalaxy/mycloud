package top.cellargalaxy.mycloud.configuration;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * Created by cellargalaxy on 18-9-26.
 */
@Aspect
@Component
public class LogAspect {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	//

	@Before("execution(public * top.cellargalaxy.mycloud.dao.mapper.*.*(..))")
	public void mapperBefore(JoinPoint joinPoint) {
		beforeDebug(joinPoint);
	}

	@AfterReturning(pointcut = "execution(public * top.cellargalaxy.mycloud.dao.mapper.*.*(..))", returning = "object")
	public void mapperAfterReturning(JoinPoint joinPoint, Object object) {
		afterReturningDebug(joinPoint, object);
	}

	//
	@Before("execution(public * top.cellargalaxy.mycloud.dao.cache.*.*(..))")
	public void cacheBefore(JoinPoint joinPoint) {
		beforeDebug(joinPoint);
	}

	@AfterReturning(pointcut = "execution(public * top.cellargalaxy.mycloud.dao.cache.*.*(..))", returning = "object")
	public void cacheAfterReturning(JoinPoint joinPoint, Object object) {
		afterReturningDebug(joinPoint, object);
	}

	//

	@Before("execution(public * top.cellargalaxy.mycloud.service.impl.*.*(..))")
	public void implBefore(JoinPoint joinPoint) {
		beforeDebug(joinPoint);
	}

	@AfterReturning(pointcut = "execution(public * top.cellargalaxy.mycloud.service.impl.*.*(..))", returning = "object")
	public void implAfterReturning(JoinPoint joinPoint, Object object) {
		afterReturningDebug(joinPoint, object);
	}

	//

	@Before("execution(public * top.cellargalaxy.mycloud.controller.*.*(..))")
	public void controllerBefore(JoinPoint joinPoint) {
		beforeInfo(joinPoint);
	}

	@AfterReturning(pointcut = "execution(public * top.cellargalaxy.mycloud.controller.*.*(..))", returning = "object")
	public void controllerAfterReturning(JoinPoint joinPoint, Object object) {
		afterReturningInfo(joinPoint, object);
	}

	//

	@Before("execution(public * top.cellargalaxy.mycloud.controller.admin.*.*(..))")
	public void adminBefore(JoinPoint joinPoint) {
		beforeInfo(joinPoint);
	}

	@AfterReturning(pointcut = "execution(public * top.cellargalaxy.mycloud.controller.admin.*.*(..))", returning = "object")
	public void adminAfterReturning(JoinPoint joinPoint, Object object) {
		afterReturningInfo(joinPoint, object);
	}

	//

	@Before("execution(public * top.cellargalaxy.mycloud.controller.user.*.*(..))")
	public void guestBefore(JoinPoint joinPoint) {
		beforeInfo(joinPoint);
	}

	@AfterReturning(pointcut = "execution(public * top.cellargalaxy.mycloud.controller.user.*.*(..))", returning = "object")
	public void guestAfterReturning(JoinPoint joinPoint, Object object) {
		afterReturningInfo(joinPoint, object);
	}

	//

	private final void beforeInfo(JoinPoint joinPoint) {
		logger.info("{}: {}; {}", joinPoint.getSignature().getDeclaringTypeName(), joinPoint.getSignature().getName(), Arrays.toString(joinPoint.getArgs()));
	}

	private final void afterReturningInfo(JoinPoint joinPoint, Object object) {
		logger.info("{}: {}; {}", joinPoint.getSignature().getDeclaringTypeName(), joinPoint.getSignature().getName(), object);
	}

	private final void beforeDebug(JoinPoint joinPoint) {
		logger.debug("{}: {}; {}", joinPoint.getSignature().getDeclaringTypeName(), joinPoint.getSignature().getName(), Arrays.toString(joinPoint.getArgs()));
	}

	private final void afterReturningDebug(JoinPoint joinPoint, Object object) {
		logger.debug("{}: {}; {}", joinPoint.getSignature().getDeclaringTypeName(), joinPoint.getSignature().getName(), object);
	}
}