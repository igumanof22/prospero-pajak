package com.alurkerja.core.support;

import javax.persistence.criteria.AbstractQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.From;
import javax.persistence.criteria.Predicate;

public class PropertyValueNot extends AbstractPropertyPredicateBuilder {


    private PropertyPredicateBuilder builder;

    public PropertyValueNot(PropertyPredicateBuilder builder) {
        this.builder = builder;
    }

    @Override
    public Predicate build(From<?, ?> from, AbstractQuery<?> query, CriteriaBuilder cb) {
        return cb.not(builder.build(from, query, cb));
    }

}
