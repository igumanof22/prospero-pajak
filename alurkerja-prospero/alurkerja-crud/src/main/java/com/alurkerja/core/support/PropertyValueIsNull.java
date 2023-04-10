package com.alurkerja.core.support;

import javax.persistence.criteria.AbstractQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.From;
import javax.persistence.criteria.Predicate;

public class PropertyValueIsNull extends AbstractPropertyPredicateBuilder {

    private final String propertyName;

    PropertyValueIsNull(String propertyName) {
        this.propertyName = propertyName;
    }

    @Override
    public Predicate build(From<?, ?> from, AbstractQuery<?> query, CriteriaBuilder cb) {
        String[] props = split(propertyName);
        return cb.isNull(navigate(from, props));
    }

}
