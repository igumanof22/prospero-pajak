package com.alurkerja.core.support;

import javax.persistence.criteria.AbstractQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;


public class SubqueryPredicateBuilder {

    private final Class<?> type;
    private String selectProperty;
    private final PropertyPredicateBuilder builder;

    public SubqueryPredicateBuilder(Class<?> type, String selectProperty, PropertyPredicateBuilder builder) {
        this.type = type;
        this.selectProperty = selectProperty;
        this.builder = builder;
    }

    public Subquery build(AbstractQuery<?> query, CriteriaBuilder cb) {
        Subquery<?> subquery = query.subquery(type);
        Root<?> fromType = subquery.from(type);
        String[] selectProps = FindByPropertyValueSpecifications.split(selectProperty);
        return subquery.select(FindByPropertyValueSpecifications.navigate(fromType, selectProps))
                .where(builder.build(fromType, subquery, cb));
    }

}
