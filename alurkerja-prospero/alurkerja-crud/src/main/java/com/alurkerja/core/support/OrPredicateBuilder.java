package com.alurkerja.core.support;

import com.google.common.collect.Lists;

import javax.persistence.criteria.AbstractQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.From;
import javax.persistence.criteria.Predicate;
import java.util.List;

public class OrPredicateBuilder extends AbstractPropertyPredicateBuilder {

    private List<PropertyPredicateBuilder> builders;

    public OrPredicateBuilder(PropertyPredicateBuilder... builders) {
        this.builders = Lists.newArrayList(builders);
    }

    public OrPredicateBuilder(List<PropertyPredicateBuilder> builders) {
        this.builders = builders;
    }

    @Override
    public Predicate build(From<?, ?> from, AbstractQuery<?> query, CriteriaBuilder cb) {
        return cb.or(toPredicates(builders, from, query, cb));
    }

}
