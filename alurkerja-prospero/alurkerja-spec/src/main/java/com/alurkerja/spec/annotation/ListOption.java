package com.alurkerja.spec.annotation;

import com.alurkerja.spec.spec.EmptyValueSelectItem;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface ListOption {
    String url() default "";
    Class values() default EmptyValueSelectItem.class;
    String key() default "";
    String value() default "";
}
