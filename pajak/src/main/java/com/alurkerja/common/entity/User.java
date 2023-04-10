package com.alurkerja.common.entity;

import com.alurkerja.core.entity.CrudEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;

@Entity
@Getter
@Setter
public class User extends CrudEntity {
    private String email;
}
