package com.alurkerja.pajak.approve;

import com.alurkerja.pajak.Pajak;
import com.alurkerja.spec.entity.BaseDto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApproveDto extends BaseDto<Pajak, ApproveDto> {
    private String noResi;
    private String status;
}
