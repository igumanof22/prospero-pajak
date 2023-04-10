package com.alurkerja.pajak;

import com.alurkerja.common.dto.UserDto;
import com.alurkerja.common.entity.User;
import com.alurkerja.spec.entity.BaseDto;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.BeanUtils;
import org.springframework.util.ObjectUtils;

import java.util.Date;

@Getter
@Setter
public class PajakDto extends BaseDto<Pajak, PajakDto> {
    private String noResi;
    private String status;
    private UserDto user;
    private Date createdDate;
    private String createdBy;

    @Override
    public Pajak fromDto() {
        Pajak pajak = new Pajak();
        BeanUtils.copyProperties(this, pajak, getNullPropertyNames(this));
        if (!ObjectUtils.isEmpty(user) && !ObjectUtils.isEmpty(user.getEmail())) {
            User user1 = user.fromDto();
            pajak.setUser(user1);
        }
        return pajak;
    }

    @Override
    public PajakDto toDto(Pajak entity) {
        BeanUtils.copyProperties(entity, this, getNullPropertyNames(entity));
        if (!ObjectUtils.isEmpty(entity.getUser())) {
            UserDto userDto = new UserDto().toDto(entity.getUser());
            this.setUser(userDto);
        }
        return this;
    }
}
