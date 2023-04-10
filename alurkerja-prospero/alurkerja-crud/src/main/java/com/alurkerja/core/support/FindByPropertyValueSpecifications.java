package com.alurkerja.core.support;

import com.google.common.collect.Lists;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.sql.Time;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Set;

public class FindByPropertyValueSpecifications {

    public static PropertyValueEqual equals(String propertyName, Object value) {
        return new PropertyValueEqual(propertyName, value);
    }

    public static PropertyValueNotEqual notEqual(String propertyName, Object value) {
        return new PropertyValueNotEqual(propertyName, value);
    }

    public static PropertyValueLike like(String propertyName, String value, boolean ignoreCase) {
        return new PropertyValueLike(propertyName, value, ignoreCase);
    }

    public static DatePropertyGreaterThan gt(String propertyName, Date dateValue) {
        return new DatePropertyGreaterThan(propertyName, dateValue, false);
    }

    public static DatePropertyLessThan lt(String propertyName, Date dateValue) {
        return new DatePropertyLessThan(propertyName, dateValue, false);
    }

    public static DatePropertyGreaterThan gte(String propertyName, Date dateValue) {
        return new DatePropertyGreaterThan(propertyName, dateValue, true);
    }

    public static DatePropertyLessThan lte(String propertyName, Date dateValue) {
        return new DatePropertyLessThan(propertyName, dateValue, true);
    }

    public static DatePropertyBetween between(String propertyName, Date fromDate, Date toDate) {
        return new DatePropertyBetween(propertyName, fromDate, toDate);
    }

    public static TimePropertyGreaterThan gt(String propertyName, Time timeValue) {
        return new TimePropertyGreaterThan(propertyName, timeValue, false);
    }

    public static TimePropertyLessThan lt(String propertyName, Time timeValue) {
        return new TimePropertyLessThan(propertyName, timeValue, false);
    }

    public static TimePropertyGreaterThan gte(String propertyName, Time timeValue) {
        return new TimePropertyGreaterThan(propertyName, timeValue, true);
    }

    public static TimePropertyLessThan lte(String propertyName, Time timeValue) {
        return new TimePropertyLessThan(propertyName, timeValue, true);
    }

    public static TimePropertyBetween between(String propertyName, Time fromTime, Time toTime) {
        return new TimePropertyBetween(propertyName, fromTime, toTime);
    }

    public static NumberPropertyGreaterThan gt(String propertyName, Number value) {
        return new NumberPropertyGreaterThan(propertyName, value, false);
    }

    public static NumberPropertyLessThan lt(String propertyName, Number value) {
        return new NumberPropertyLessThan(propertyName, value, false);
    }

    public static NumberPropertyGreaterThan gte(String propertyName, Number value) {
        return new NumberPropertyGreaterThan(propertyName, value, true);
    }

    public static NumberPropertyLessThan lte(String propertyName, Number value) {
        return new NumberPropertyLessThan(propertyName, value, true);
    }

    public static PropertyValueNot not(PropertyPredicateBuilder builder) {
        return new PropertyValueNot(builder);
    }

    public static PropertyValuesIn in(String propertyName, Set<? extends Object> values) {
        return new PropertyValuesIn(propertyName, values);
    }

    public static PropertyValuesInSubquery in(String propertyName,
                                              SubqueryPredicateBuilder subqueryBuilder) {
        return new PropertyValuesInSubquery(propertyName, subqueryBuilder);
    }

    public static SubqueryPredicateBuilder subquery(Class<?> type, String selectProperty, PropertyPredicateBuilder builders) {
        return new SubqueryPredicateBuilder(type, selectProperty, builders);
    }

    public static PropertyValueNotNull notNull(String propertyName) {
        return new PropertyValueNotNull(propertyName);
    }

    public static PropertyValueIsNull isNull(String propertyName) {
        return new PropertyValueIsNull(propertyName);
    }

    public static <T> Specification<T> collectionProperties(String pathToCollection,
                                                            PropertyPredicateBuilder... builders) {
        return (root, criteriaQuery, cb) -> {
            String[] colProps = split(pathToCollection);
            Join<?, ?> join = join(root, colProps);
            Predicate[] predicates = new Predicate[builders.length];
            for (int i = 0; i < builders.length; i++) {
                PropertyPredicateBuilder builder = builders[i];
                predicates[i] = builder.build(join, criteriaQuery, cb);
            }
            return cb.or(predicates);
        };
    }

    public static <T> Specification<T> distinct(Specification<T> spec) {
        return (root, criteriaQuery, cb) -> {
            criteriaQuery.distinct(true);
            return spec.toPredicate(root, criteriaQuery, cb);
        };
    }

    public static <T> Specification<T> and(Specification<T>... specs) {
        return (root, criteriaQuery, cb) -> cb.and(toPredicates(Lists.newArrayList(specs), root, criteriaQuery, cb));
    }

    public static PropertyPredicateBuilder and(PropertyPredicateBuilder... builders) {
        return new AndPredicateBuilder(builders);
    }

    public static <T> Specification<T> and(List<Specification<T>> specs) {
        return (root, criteriaQuery, cb) -> cb.and(toPredicates(specs, root, criteriaQuery, cb));
    }

    public static <T> Specification<T> or(Specification<T>... specs) {
        return (root, criteriaQuery, cb) -> cb.or(toPredicates(Lists.newArrayList(specs), root, criteriaQuery, cb));
    }

    public static PropertyPredicateBuilder or(PropertyPredicateBuilder... builders) {
        return new OrPredicateBuilder(builders);
    }

    public static <T> Specification<T> or(List<Specification<T>> specs) {
        return (root, criteriaQuery, cb) -> cb.or(toPredicates(specs, root, criteriaQuery, cb));
    }

    public static <T> Specification<T> properties(PropertyPredicateBuilder... builders) {
        return (root, criteriaQuery, cb) -> {
            Predicate[] predicates = new Predicate[builders.length];
            for (int i = 0; i < builders.length; i++) {
                PropertyPredicateBuilder builder = builders[i];
                predicates[i] = builder.build(root, criteriaQuery, cb);
            }
            return cb.or(predicates);
        };
    }

    public static <T> Specification<T> nonDeleted() {
        return properties(equals("deleted", Boolean.FALSE));
    }

    private static <T> Predicate[] toPredicates(List<Specification<T>> specs, Root<T> root, CriteriaQuery<?> criteriaQuery,
                                                CriteriaBuilder cb) {
        Predicate[] predicates = new Predicate[specs.size()];
        for (int i = 0; i < specs.size(); i++) {
            Specification<T> spec = specs.get(i);
            predicates[i] = spec.toPredicate(root, criteriaQuery, cb);
        }
        return predicates;
    }

    static String[] split(String propertyName) {
        return propertyName.split("\\.");
    }

    static Path navigate(From<?, ?> root, String[] props) {
        if (props.length == 1) {
            return root.get(props[0]);
        } else {
            String[] joinProps = Arrays.copyOfRange(props, 0, props.length - 1);
            Join<?, ?> joinedPath = join(root, joinProps);
            if (joinedPath == null) {
                return null;
            }
            return joinedPath.get(props[props.length - 1]);
        }
    }

    private static Join<?, ?> join(From<?, ?> root, String[] colProps) {
        Join<?, ?> lastJoin = null;
        for (String colProp : colProps) {
            if (lastJoin == null) {
                lastJoin = root.join(colProp);
            } else {
                lastJoin = lastJoin.join(colProp);
            }
        }
        return lastJoin;
    }

}
