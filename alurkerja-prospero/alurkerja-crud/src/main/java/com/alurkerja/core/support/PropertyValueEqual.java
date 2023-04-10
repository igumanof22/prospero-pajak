package com.alurkerja.core.support;

import javax.persistence.criteria.AbstractQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.From;
import javax.persistence.criteria.Predicate;

public class PropertyValueEqual extends AbstractPropertyPredicateBuilder {

    private final String propertyName;
    private final Object value;

    PropertyValueEqual(String propertyName, Object value) {
        this.propertyName = propertyName;
        this.value = value;
    }

    @Override
    public Predicate build(From<?, ?> from, AbstractQuery<?> query, CriteriaBuilder cb) {
        String[] props = split(propertyName);
        return cb.equal(navigate(from, props), value);
    }

}
