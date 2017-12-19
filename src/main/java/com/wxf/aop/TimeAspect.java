package com.wxf.aop;

import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class TimeAspect {
	/*@Around("bean(*Service)")
	public Object test(ProceedingJoinPoint jp) throws Throwable{
		Long t1 = System.currentTimeMillis();
		
		Object val = jp.proceed();
		Long t2 =System.currentTimeMillis();
		Long t= t2-t1;
		//JoinPoint 对象可以获取目标业务方法的详细信息：
		//方法签名，调用参数等
		Signature m =jp.getSignature();
		//Signature：签名，这里是方法名
		System.out.println(m+"用时："+t);
		return val;
		
	}*/
}
