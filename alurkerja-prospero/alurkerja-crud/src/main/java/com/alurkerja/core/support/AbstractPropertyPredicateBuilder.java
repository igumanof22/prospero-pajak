package com.alurkerja.core.support;

import javax.persistence.criteria.*;
import java.util.List;

public abstract class AbstractPropertyPredicateBuilder implements PropertyPredicateBuilder {

    protected String[] split(String propertyName) {
        return FindByPropertyValueSpecifications.split(propertyName);
    }

    public String[] testableSplit(String propertyName) {
        return this.split(propertyName);
    }



    protected Path navigate(From<?, ?> root, String[] props) {
        return FindByPropertyValueSpecifications.navigate(root, props);
    }

    protected Predicate[] toPredicates(List<PropertyPredicateBuilder> builders, From<?, ?> from,
                                        AbstractQuery<?> criteriaQuery, CriteriaBuilder cb) {
        Predicate[] predicates = new Predicate[builders.size()];
        for (int i = 0; i < builders.size(); i++) {
            PropertyPredicateBuilder spec = builders.get(i);
            predicates[i] = spec.build(from, criteriaQuery, cb);
        }
        return predicates;
    }
}