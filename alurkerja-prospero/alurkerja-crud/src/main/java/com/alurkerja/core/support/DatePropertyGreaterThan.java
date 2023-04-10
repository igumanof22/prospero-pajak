package com.alurkerja.core.support;

import javax.persistence.criteria.AbstractQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.From;
import javax.persistence.criteria.Predicate;
import java.util.Date;

public class DatePropertyGreaterThan extends AbstractPropertyPredicateBuilder {

    private final String propertyName;
    private final Date value;
    private boolean orEqualTo;

    DatePropertyGreaterThan(String propertyName, Date value, boolean orEqualTo) {
        this.propertyName = propertyName;
        this.value = value;
        this.orEqualTo = orEqualTo;
    }

    @Override
    public Predicate build(From<?, ?> from, AbstractQuery<?> query, CriteriaBuilder cb) {
        String[] props = split(propertyName);
        if (orEqualTo) {
            return cb.greaterThanOrEqualTo(navigate(from, props).as(Date.class), value);
        } else {
            return cb.greaterThan(navigate(from, props).as(Date.class), value);
        }
    }

}
