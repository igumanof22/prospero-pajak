package com.alurkerja.core.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;


@MappedSuperclass
@Getter
@Setter
public abstract class CrudEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    protected String createdBy;
    protected String updatedBy;

    public Date getCreatedDate() {
        return null;
    }

    public void setCreatedDate(Date createdDate) {
    }

    public Date getUpdatedDate() {
        return null;
    }

    public void setUpdatedDate(Date updatedDate) {
    }
}
