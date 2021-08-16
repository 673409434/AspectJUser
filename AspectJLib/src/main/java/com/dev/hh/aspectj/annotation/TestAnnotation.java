package com.dev.hh.aspectj.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Package: com.dev.hh.aspectj.annotation
 * User: hehao3
 * Email: hehao3@jd.com
 * Date: 2021/4/23
 * Time: 下午6:36
 * Description:
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface TestAnnotation {
    String value() default "";
}