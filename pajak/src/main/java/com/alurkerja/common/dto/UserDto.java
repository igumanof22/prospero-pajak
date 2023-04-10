package com.alurkerja.common.dto;

import com.alurkerja.common.entity.User;
import com.alurkerja.core.entity.CrudEntity;
import com.alurkerja.spec.entity.BaseDto;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;

@Getter
@Setter
public class UserDto extends BaseDto<User, UserDto> {
    private String email;
}
