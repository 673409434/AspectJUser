/*
 * ActivityAspect      2016-03-04
 * Copyright (c) 2016 hujiang Co.Ltd. All right reserved(http://www.hujiang.com).
 * 
 */

package com.dev.hh.aspectj.aspect;

import android.util.Log;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;

/**
 * activity aspect
 *
 * @author simon
 * @version 1.0.0
 * @since 2016-03-04
 */
@Aspect
public class ActivityAspect {

    /**
     *  匹配被注解@TraceDelay的类中的所有（显式定义的）方法
     *
     */
    @After("within(@com.dev.hh.aspectj.annotation.TraceDelay *)")
    public void onUi(JoinPoint joinPoint) throws Throwable {
        Log.i("helloAOP", "" + joinPoint.getSignature());
    }

    /**
     * 匹配所有的Activity周期函数调用
     * @param joinPoint
     * @throws Throwable
     */
    @After("execution(* android.app.Activity.on**(..))")
    public void onResumeMethod(JoinPoint joinPoint) throws Throwable {
        Log.i("helloAOP", "aspect:::" + joinPoint.getSignature());
    }

}