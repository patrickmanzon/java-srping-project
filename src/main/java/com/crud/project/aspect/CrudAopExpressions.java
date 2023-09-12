package com.crud.project.aspect;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class CrudAopExpressions {

	@Pointcut("execution(* com.crud.project.dao.*.*(..))")
	public void forDaoPackage() {
	}
}
