package com.alurkerja.core.repository.search;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SearchCriteria {
    private String key;
    private Object value;
    private SearchOperation operation;
    private Boolean ignoreGenerated;

    /**
     * To search many to one relation, use dot as separator. For example, to search City that belongs to Province DKI Jakarta, add
     * new SearchCriteria("province.name", "DKI Jakarta", SearchOperation.EQUAL);
     *
     * @param key
     * @param value
     * @param operation
     */
    public SearchCriteria(String key, Object value, SearchOperation operation) {
        this.key = key;
        this.value = value;
        this.operation = operation;
    }

    public SearchCriteria(String key, Object value, SearchOperation operation, Boolean ignoreGenerated) {
        this.key = key;
        this.value = value;
        this.operation = operation;
        this.ignoreGenerated = ignoreGenerated;
    }
}
