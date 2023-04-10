package com.alurkerja.core.support;

import javax.persistence.criteria.AbstractQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.From;
import javax.persistence.criteria.Predicate;

public class PropertyValuesInSubquery extends AbstractPropertyPredicateBuilder {

    private final String propertyName;
    private final SubqueryPredicateBuilder subqueryBuilder;

    public PropertyValuesInSubquery(String propertyName, SubqueryPredicateBuilder subqueryBuilder) {
        this.propertyName = propertyName;
        this.subqueryBuilder = subqueryBuilder;
    }

    public Predicate build(From<?, ?> from, AbstractQuery<?> query, CriteriaBuilder cb) {
        String[] props = split(propertyName);
        return cb.in(navigate(from, props)).value(subqueryBuilder.build(query, cb));
    }

}
