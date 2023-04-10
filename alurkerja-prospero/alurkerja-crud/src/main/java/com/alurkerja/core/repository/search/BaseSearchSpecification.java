package com.alurkerja.core.repository.search;

import com.alurkerja.core.entity.CrudEntity;
import com.alurkerja.spec.entity.BaseDto;
import com.alurkerja.spec.spec.FormSpec;
import lombok.SneakyThrows;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.ObjectUtils;

import javax.persistence.Transient;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public abstract class BaseSearchSpecification<E, D> implements Specification<E> {
    protected List<String> processInstanceIds;

    protected D dto;


    @Transient
    protected List<SearchCriteria> searchCriteriaList = new ArrayList<>();

    protected BaseSearchSpecification(D dto) {
        this.dto = dto;
        buildSpecification(dto);
    }

    protected BaseSearchSpecification(List<String> processInstanceIds, D dto) {
        this.dto = dto;
        this.processInstanceIds = processInstanceIds;
        buildSpecification(dto);
    }

    @Override
    public Predicate toPredicate(Root<E> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
        BasePredicate basePredicate = new BasePredicate();
        if (this.processInstanceIds != null) {
            addCriteria(new SearchCriteria("processInstanceId", this.processInstanceIds, SearchOperation.IN));
        }

        BaseDto baseDto = (BaseDto) dto;
        List<Predicate> predicates = basePredicate.predicate(root, criteriaQuery, criteriaBuilder, searchCriteriaList);
        if (baseDto.createCustomPredicate((Root<CrudEntity>) root, (CriteriaQuery<CrudEntity>) criteriaQuery, criteriaBuilder, searchCriteriaList) != null) {
            predicates.addAll(baseDto.createCustomPredicate((Root<CrudEntity>) root, (CriteriaQuery<CrudEntity>) criteriaQuery, criteriaBuilder, searchCriteriaList));
        }

        Predicate predicate = null;
        if (!ObjectUtils.isEmpty(predicates)) {
            predicate = criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        }

        List<Predicate> customPredicates =  baseDto.createCustomPredicate((Root<CrudEntity>) root, (CriteriaQuery<CrudEntity>) criteriaQuery, criteriaBuilder,  searchCriteriaList);

        if( !ObjectUtils.isEmpty(customPredicates)) {
            List<Predicate> orPredicates = new ArrayList<>();
            if (predicate != null) {
                orPredicates.add(predicate);
            }
            orPredicates.add(criteriaBuilder.or(customPredicates.toArray(new Predicate[0])));
            return criteriaBuilder.and(orPredicates.toArray(new Predicate[0]));
        }

        return predicate;
    }

    public void buildSpecification(D dto) {
        this.defaultSpecification(dto);
    }

    @SneakyThrows
    public void defaultSpecification(Object dto) {
        Class<?> clazzEntity = dto.getClass();
        List<String> skippedFieldName = new ArrayList<>();


        skippedFieldName.add("ANNOTATION");
        skippedFieldName.add("SYNTHETIC");
        skippedFieldName.add("ENUM");
        skippedFieldName.add("classRedefinedCount");
        skippedFieldName.add("serialVersionUID");

        for (Field field : clazzEntity.getDeclaredFields()) {
            Boolean skipped =  false ;
            if (skippedFieldName.contains(field.getName())) {
                continue;
            }
            if (field.getType().isPrimitive()) {
                System.err.println("Cant Use Field : " + field.getName() + "  to filter , Type Primitive");
                continue;
            }


            Object value = this.getPropertyValue(field.getName(), dto);
            if (value != null) {
                FormSpec form = new FormSpec();
                form.setName(field.getName());
                form.setLabel(com.alurkerja.core.util.StringUtil.convertFieldToLabel(field.getName()));
//            String[] constraints = new String[field.getDeclaredAnnotations().length];
                List<Map<String, Object>> constraints = new ArrayList<>();
                form.setType(field.getType().getSimpleName().toUpperCase(Locale.ROOT));
                if (field.getDeclaredAnnotations().length > 0) {
                    SearchQueryCriteria criteriaAnotation = field.getAnnotation(SearchQueryCriteria.class);
                    if (criteriaAnotation != null) {
                        if (criteriaAnotation.ignoreGenerated()) {
                            skipped =  true;
                        }
                    }
                }

                if (field.getType().getName().contains("com.alurkerja") && field.getType().getDeclaredFields().length > 0) {
                    for (Field parentField : field.getType().getDeclaredFields()) {
                        this.addCriteria(field.getName() + "." + parentField.getName(), this.getPropertyValue(field.getName() + "." + parentField.getName(), dto), skipped);
                    }
                } else {
                    this.addCriteria(field.getName(), value ,  skipped);
                }
            }
        }


    }



    public void addCriteria(SearchCriteria criteria) {
        this.searchCriteriaList.add(criteria);
    }

    @Deprecated
    public void addCriteria(String fieldName, Object value) {
        this.addCriteria(fieldName , value, false);
    }

    public void addCriteria(String fieldName, Object  value, Boolean skipped) {
        String[] properties = fieldName.split("\\.");
        boolean isId = (properties.length == 1 && properties[0].equalsIgnoreCase("id")) || (properties.length == 2 && properties[1].equalsIgnoreCase("id"));
        if (value != null) {
            if (!isId && value.getClass().getSimpleName().equalsIgnoreCase("String")) {
                this.addCriteria(new SearchCriteria(fieldName, value, SearchOperation.MATCH, skipped));
            } else {
                this.addCriteria(new SearchCriteria(fieldName, value, SearchOperation.EQUAL, skipped));
            }
        }
    }


    public Object getPropertyValue(String property, Object dto) {
        String[] strLevels = property.split("\\.");
        try {
            Class<?> clazzDto = dto.getClass();
            Field fieldLevel0 = clazzDto.getDeclaredField(strLevels[0]);
            fieldLevel0.setAccessible(true);
            Object level0 = fieldLevel0.get(dto);
            if (strLevels.length == 1) {
                return level0;
            } else if (strLevels.length == 2 && level0 != null) {
                return this.getPropertyValue(strLevels[1], this.getPropertyValue(strLevels[0], dto));
            }
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }

        return null;

    }
}
