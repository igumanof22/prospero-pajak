package com.alurkerja.annotation;

import com.alurkerja.constant.ApplicationEnum;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Secured {
    ApplicationEnum.Group[] value() default {};
}
