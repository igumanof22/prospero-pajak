package com.alurkerja.pajak;

import com.alurkerja.common.entity.User;
import com.alurkerja.core.entity.CrudEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
@Getter
@Setter
public class Pajak extends CrudEntity {
    private String noResi;
    private String status;
    @ManyToOne
    private User user;
}
