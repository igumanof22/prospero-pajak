package com.alurkerja.core.repository.search;

import com.alurkerja.core.entity.CrudEntity;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BasePredicate {

    public List<Predicate> predicate(Root<?> root, CriteriaQuery<?> query, CriteriaBuilder builder, List<SearchCriteria> list) {
        List<Predicate> predicates = new ArrayList<>();
        for (SearchCriteria criteria : list) {
//            Skip generated Criteria Whenk Ignore Generated True
            if(criteria.getIgnoreGenerated() != null && criteria.getIgnoreGenerated() ) {
                continue;
            }

            Expression currentCriteria = getCriteria(root, criteria.getKey());
            if (builder != null && currentCriteria != null) {
                if (criteria.getOperation().equals(SearchOperation.GREATER_THAN)) {
                    predicates.add(builder.greaterThan(
                            currentCriteria, criteria.getValue().toString()));
                }
                if (criteria.getOperation().equals(SearchOperation.LESS_THAN)) {
                    predicates.add(builder.lessThan(
                            currentCriteria, criteria.getValue().toString()));
                }
                if (criteria.getOperation().equals(SearchOperation.GREATER_THAN_EQUAL)) {
                    predicates.add(builder.greaterThanOrEqualTo(
                            currentCriteria, criteria.getValue().toString()));
                }
                if (criteria.getOperation().equals(SearchOperation.LESS_THAN_EQUAL)) {
                    predicates.add(builder.lessThanOrEqualTo(
                            currentCriteria, criteria.getValue().toString()));
                }
                if (criteria.getOperation().equals(SearchOperation.NOT_EQUAL)) {
                    predicates.add(builder.notEqual(
                            currentCriteria, criteria.getValue()));
                }
                if (criteria.getOperation().equals(SearchOperation.EQUAL)) {
                    predicates.add(builder.equal(
                            currentCriteria, criteria.getValue()));
                }
                if (criteria.getOperation().equals(SearchOperation.MATCH)) {
                    predicates.add(builder.like(
                            builder.lower(getCriteria(root, criteria.getKey())),
                            "%" + criteria.getValue().toString().toLowerCase() + "%"));
                } else if (criteria.getOperation().equals(SearchOperation.MATCH_END)) {
                    predicates.add(builder.like(
                            builder.lower(getCriteria(root, criteria.getKey())),
                            criteria.getValue().toString().toLowerCase() + "%"));
                }
                if (criteria.getOperation().equals(SearchOperation.MATCH_START)) {
                    predicates.add(builder.like(
                            builder.lower(getCriteria(root, criteria.getKey())),
                            "%" + criteria.getValue().toString().toLowerCase()));
                }
                if (criteria.getOperation().equals(SearchOperation.IN)) {
                    predicates.add(builder.in(getCriteria(root, criteria.getKey())).value(criteria.getValue()));
                }
                if (criteria.getOperation().equals(SearchOperation.NOT_IN)) {
                    predicates.add(builder.not(getCriteria(root, criteria.getKey())).in(criteria.getValue()));
                }
                if (criteria.getOperation().equals(SearchOperation.DATE_GREATER_EQUAL)) {
                    predicates.add(builder.greaterThanOrEqualTo(currentCriteria,
                            (Date) criteria.getValue()));
                }
                if (criteria.getOperation().equals(SearchOperation.DATE_LESS_EQUAL)) {
                    predicates.add(builder.lessThanOrEqualTo(
                            currentCriteria, (Date) criteria.getValue()));
                }
                if (criteria.getOperation().equals(SearchOperation.DATE_EQUAL)) {
                    predicates.add(builder.equal(
                            currentCriteria, (Date) criteria.getValue()));
                }
                if (criteria.getOperation().equals(SearchOperation.NOT_NULL)) {
                    predicates.add(builder.isNotNull(
                            getCriteria(root, criteria.getKey())));
                }
            }
        }

        return predicates;
    }

    private <Y> Path<Y> getCriteria(Root<?> root, String criteria) {
//Skip Decision Remark
        if (criteria.equals("decisionRemark")) {
            return null;
        }
        String[] fields = criteria.split("\\.");
        try {
            Path<Y> path = root.get(fields[0]);
            for (int i = 1; i < fields.length; i++) {
                String field = fields[i];
                path = path.get(field);
            }
            return path;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }

    }
}
