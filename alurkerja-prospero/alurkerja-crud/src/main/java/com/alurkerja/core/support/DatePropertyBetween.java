package com.alurkerja.core.support;

import javax.persistence.criteria.AbstractQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.From;
import javax.persistence.criteria.Predicate;
import java.util.Date;

public class DatePropertyBetween extends AbstractPropertyPredicateBuilder {

    private final String propertyName;
    private final Date fromDate;
    private final Date toDate;

    public DatePropertyBetween(String propertyName, Date fromDate, Date toDate) {
        this.propertyName = propertyName;
        this.fromDate = fromDate;
        this.toDate = toDate;
    }

    @Override
    public Predicate build(From<?, ?> from, AbstractQuery<?> query, CriteriaBuilder cb) {
        String[] props = split(propertyName);
        return cb.between(navigate(from, props).as(Date.class), fromDate, toDate);
    }

}
