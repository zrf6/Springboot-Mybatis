/**
 * @Title: TheLogger.java
 * @Description: TODO
 * @author: bryant
 * @date: 2019年9月21日 上午10:26:12
 * @version: v1.0
 */
package com.utils;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

/**
 * @ClassName: TheLogger
 * @Description: TODO
 * @author: bryant
 */
@Aspect
public class TheLogger {

    private static final Logger LOG = LoggerFactory.getLogger(TheLogger.class);

    //定义切入表达式
    @Pointcut("execution (* com.service.serviceimp..*.*(..))")
    public void pointcut() {
    }

    ;

    //环绕增强
    @Around("pointcut()")
    public Object around(ProceedingJoinPoint joinpoint) throws Throwable {    //抛出异常，不侵入源码
//	public void around(ProceedingJoinPoint joinpoint) {
        //前置增强
        LOG.info("开始执行" + joinpoint.getTarget() + "类的"
                + joinpoint.getSignature().getName() + "方法，参数为"
                + Arrays.toString(joinpoint.getArgs()));
        Object returning = null;
        try {
            returning = joinpoint.proceed();
            //后置增强
            LOG.info("结束执行" + joinpoint.getTarget() + "类的"
                    + joinpoint.getSignature().getName() + "方法，返回值为"
                    + returning);
        } catch (Throwable e) {
            //异常抛出增强
            LOG.info("执行" + joinpoint.getTarget() + "类的"
                    + joinpoint.getSignature().getName() + "方法发生异常，抛出异常为"
                    + e);
            throw e;    //不侵入源代码，将异常抛出
        } finally {
            //最终增强
            LOG.info("最终结束执行" + joinpoint.getTarget() + "类的"
                    + joinpoint.getSignature().getName() + "方法，参数为"
                    + Arrays.toString(joinpoint.getArgs()));
        }
        return returning;
    }
}
