package com.alurkerja.pajak.check;

import com.alurkerja.pajak.Pajak;
import com.alurkerja.spec.entity.BaseDto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CheckDto extends BaseDto<Pajak, CheckDto> {
    private String noResi;
    private String status;
}
