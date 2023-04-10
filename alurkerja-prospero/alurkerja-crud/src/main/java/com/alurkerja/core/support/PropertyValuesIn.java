package com.alurkerja.core.support;

import javax.persistence.criteria.AbstractQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.From;
import javax.persistence.criteria.Predicate;
import java.util.Set;

public class PropertyValuesIn extends AbstractPropertyPredicateBuilder {

    private final String propertyName;
    private final Set<? extends Object> values;

    PropertyValuesIn(String propertyName, Set<? extends Object> values) {
        this.propertyName = propertyName;
        this.values = values;
    }

    @Override
    public Predicate build(From<?, ?> from, AbstractQuery<?> query, CriteriaBuilder cb) {
        String[] props = split(propertyName);
        return navigate(from, props).in(values);
    }

}
