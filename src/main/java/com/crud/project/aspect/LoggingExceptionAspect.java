package com.crud.project.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingExceptionAspect {

	private Logger logger = LoggerFactory.getLogger(getClass().getName());

	@AfterThrowing(pointcut = "com.crud.project.aspect.CrudAopExpressions.forDaoPackage()", throwing = "theException")
	public void logException(JoinPoint theJoinPoint, Throwable theException) {

		String method = theJoinPoint.getSignature().toShortString();

		logger.error("Encountered exception on method: " + method);

	}
}
