package com.nujabness.basicapi.api.logging;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAdvice {

	Logger log = LoggerFactory.getLogger(LoggingAdvice.class);

	@Pointcut(value="execution(* com.nujabness.basicapi.api.*.*.*(..) )")
	public void myPointcut() {}

	@Around("myPointcut()")
	public Object applicationLogger(ProceedingJoinPoint pjp) throws Throwable {
		ObjectMapper mapper = new ObjectMapper();
		String methodName = pjp.getSignature().getName();
		String className = pjp.getTarget().getClass().toString();

		// Measure method execution time
		long startTime = System.currentTimeMillis();
		Object[] array = pjp.getArgs();
		log.info("method invoked {} : {}() arguments : {}", className, methodName, mapper.writeValueAsString(array));
		Object object = pjp.proceed();
		long endtime = System.currentTimeMillis();
		log.info("{} : {}()" + "Response : {} in {} ms", className, methodName, mapper.writeValueAsString(object), (endtime-startTime));
		return object;
	}

}
