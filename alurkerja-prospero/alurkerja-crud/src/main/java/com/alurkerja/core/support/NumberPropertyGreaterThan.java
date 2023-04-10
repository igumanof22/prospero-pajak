package com.alurkerja.core.support;

import javax.persistence.criteria.AbstractQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.From;
import javax.persistence.criteria.Predicate;

public class NumberPropertyGreaterThan<T extends Number> extends AbstractPropertyPredicateBuilder {

    private final String propertyName;
    private final T value;
    private boolean orEqualTo;

    NumberPropertyGreaterThan(String propertyName, T value, boolean orEqualTo) {
        this.propertyName = propertyName;
        this.value = value;
        this.orEqualTo = orEqualTo;
    }

    @Override
    public Predicate build(From<?, ?> from, AbstractQuery<?> query, CriteriaBuilder cb) {
        String[] props = split(propertyName);
        if (orEqualTo) {
            return cb.ge(navigate(from, props), value);
        } else {
            return cb.gt(navigate(from, props), value);
        }
    }

}
