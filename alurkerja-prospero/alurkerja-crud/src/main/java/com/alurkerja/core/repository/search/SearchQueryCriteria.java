package com.alurkerja.core.repository.search;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface SearchQueryCriteria {
    boolean ignoreGenerated() default false;
//    TODO:  check apakaha operator bisa di gunakan di sini
//    SearchOperation operator() default SearchOperation.EQUAL;
}

