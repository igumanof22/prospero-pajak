package com.alurkerja.spec.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface FormReference {
    boolean required() default false;
    String label() default "";
    String type() default "";
    String tooltips() default  "";
    String format() default "";
    String placeholder() default "";
    boolean canFilter() default true;
    boolean canSort() default true;
}
