package com.alurkerja.core.support;

import javax.persistence.criteria.AbstractQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.From;
import javax.persistence.criteria.Predicate;

public class PropertyValueLike extends AbstractPropertyPredicateBuilder {

    private final String propertyName;
    private final String value;
    private final boolean ignoreCase;

    PropertyValueLike(String propertyName, String value, boolean ignoreCase) {
        this.propertyName = propertyName;
        this.value = value;
        this.ignoreCase = ignoreCase;
    }

    @Override
    public Predicate build(From<?, ?> from, AbstractQuery<?> query, CriteriaBuilder cb) {
        String[] props = split(propertyName);
        if (ignoreCase) {
            return cb.like(cb.upper(navigate(from, props)), value.toUpperCase());
        } else {
            return cb.like(navigate(from, props), value);
        }
    }
}
