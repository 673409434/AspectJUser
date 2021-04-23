package com.dev.hh.aspectj.aspect;

import android.util.Log;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;

/**
 * Package: com.dev.hh.aspectj.aspect
 * User: hehao3
 * Email: hehao3@jd.com
 * Date: 2021/4/23
 * Time: 下午6:47
 * Description:  函数精准匹配
 */
@Aspect
public class FunctionAspect {
    /**
     * 匹配com.dev.hh.ajuser.Greeter.**()中的方法执行
     * @param joinPoint
     * @throws Throwable
     */
    @After("execution(* com.dev.hh.ajuser.Greeter.**())")
    public void greeterAdvice(JoinPoint joinPoint) throws Throwable {
        Log.i("helloAOP", "aspect:::" + joinPoint.getSignature());
    }

}
