package com.alurkerja.core.support;

import javax.persistence.criteria.AbstractQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.From;
import javax.persistence.criteria.Predicate;

public interface PropertyPredicateBuilder {

    Predicate build(From<?, ?> from, AbstractQuery<?> query, CriteriaBuilder cb);

}
