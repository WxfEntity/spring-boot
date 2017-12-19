package com.wxf.aop;

import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

/**
 * 创建一个且面组件，就是一个普通的javaBean
 * 
 * @author soft01
 *
 */
@Component
@Aspect
public class DemoAspect {
	//声明test方法将在userService的全部方法之前运行
	/*@Before("bean(userService)")
	public void test(){
		System.out.println("hello word!");
	}
	@After("bean(userService)")
	public void test2(){
		System.out.println("after");
	}
	@AfterReturning("bean(userService)")
	public void test3(){
		System.out.println("AfterReturning");

	}
	@AfterThrowing("bean(userService)")
	public void test4(){
		System.out.println("AfterThrowing");

	}*/
	/**
	 * 环绕通知 方法：
	 * 1.必须有返回值Object
	 * 2.必须有参数 ProceedingJoinPoint
	 * 3.必须抛出异常
	 * 4.需要在方法中调用jp.proceed()
	 * 5.返回业务方法的放回值
	 * @param jp
	 * @return
	 * @throws Throwable
	 */
	/*@Around("bean(userService)")
	public Object test5(ProceedingJoinPoint jp) throws Throwable{
		Object val = jp.proceed();
		System.out.println(val);
		return val;
		
		
		
	}*/
}
