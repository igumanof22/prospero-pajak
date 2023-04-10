package com.alurkerja.core.support;

import javax.persistence.criteria.AbstractQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.From;
import javax.persistence.criteria.Predicate;
import java.sql.Time;

public class TimePropertyBetween extends AbstractPropertyPredicateBuilder {

    private final String propertyName;
    private final String fromTime;
    private final String toTime;

    TimePropertyBetween(String propertyName, Time fromTime, Time toTime) {
        this.propertyName = propertyName;
        this.fromTime = fromTime.toString();
        this.toTime = toTime.toString();
    }

    @Override
    public Predicate build(From<?, ?> from, AbstractQuery<?> query, CriteriaBuilder cb) {
        String[] props = split(propertyName);
        return cb.between(navigate(from, props).as(String.class), fromTime, toTime);
    }

}
