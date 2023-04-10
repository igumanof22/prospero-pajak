package com.alurkerja.core.repository.search;

import java.util.List;

public class GenericSearchSpecification<E, D> extends BaseSearchSpecification<E, D>{
    public GenericSearchSpecification(D dto) {
        super(dto);
    }
    public GenericSearchSpecification(List<String> processInstanceIds, D dto) {
        super(processInstanceIds, dto);
    }
}
