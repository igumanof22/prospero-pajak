package com.alurkerja.core.entity;

import java.util.Date;


public interface CrudAble<ENTITY> {
    void setCreatedDate(Date date);
    void setUpdatedDate(Date date);

    void setCreatedBy(String id);
    void setUpdatedBy(String id);

    void setCreatedByName(String name);
    void setUpdatedByName(String name);
    
    void copy(ENTITY entity);
    
    String getBusinessKey();
}
