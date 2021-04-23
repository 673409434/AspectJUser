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
public class FragmentAspect {

    @After("execution(* android.support.v4.app.Fragment.on**(..))")
    public void fragmentMethod(JoinPoint joinPoint) throws Throwable {
        Log.i("helloAOP", "aspect:::" + joinPoint.getSignature());
    }

}