package com.alurkerja.core.support;

import javax.persistence.criteria.AbstractQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.From;
import javax.persistence.criteria.Predicate;
import java.sql.Time;

public class TimePropertyGreaterThan extends AbstractPropertyPredicateBuilder {

    private final String propertyName;
    private final String value;
    private final boolean orEqualTo;

    TimePropertyGreaterThan(String propertyName, Time value, boolean orEqualTo) {
        this.propertyName = propertyName;
        this.value = value.toString();
        this.orEqualTo = orEqualTo;
    }

    @Override
    public Predicate build(From<?, ?> from, AbstractQuery<?> query, CriteriaBuilder cb) {
        String[] props = split(propertyName);
        if (orEqualTo) {
            return cb.greaterThanOrEqualTo(navigate(from, props).as(String.class), value);
        } else {
            return cb.greaterThan(navigate(from, props).as(String.class), value);
        }
    }

}
