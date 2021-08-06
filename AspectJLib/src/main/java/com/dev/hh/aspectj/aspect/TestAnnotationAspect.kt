package com.dev.hh.aspectj.aspect

import android.util.Log
import com.dev.hh.aspectj.annotation.TestAnnotation
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Pointcut
import org.aspectj.lang.reflect.MethodSignature
import java.util.*

/**
 * Package: com.dev.hh.aspectj.aspect
 * User: hehao3
 * Email: hehao3@jd.com
 * Date: 2021/4/23
 * Time: 下午6:29
 * Description:
 */
@Aspect
class TestAnnotationAspect {

    companion object {
        val TAG = TestAnnotationAspect::class.java.simpleName
    }

    /**
     * 匹配被@TestAnnotation注解的方法
     */
    @Pointcut("execution(@com.dev.hh.aspectj.annotation.TestAnnotation * *(..))")
    fun TestPointcut1() {
    }

    /**
     * 统计被@PerformanceAnnotation注解的方法的耗时
     */
    @Around("TestPointcut1()")
    fun waveTestAnnotationPointcut1(joinPoint: ProceedingJoinPoint) {
        val methodSignature = joinPoint.signature as MethodSignature
        // 类名
        val className = methodSignature.declaringType.simpleName
        // 方法名
        val methodName = methodSignature.name
        // 功能名
        val behaviorTrace = methodSignature.method.getAnnotation(TestAnnotation::class.java)
        val value = behaviorTrace.value
        val start = System.currentTimeMillis()

        joinPoint.proceed(joinPoint.args)


        Log.e(TAG, "${className}类中${methodName}方法执行${value}功能,参数：" + Arrays.toString(joinPoint.args))

        val duration = System.currentTimeMillis() - start
        Log.e(TAG, "${className}类中${methodName}方法执行${value}功能,耗时：${duration}ms")
    }

}