package com.dev.hh.aspectj.annotation

/**
 * Package: com.dev.hh.aspectj.annotation
 * User: hehao3
 * Email: hehao3@jd.com
 * Date: 2021/4/23
 * Time: 下午6:27
 * Description:
 */
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class PerformanceAnnotation(val value: String)
