package com.alurkerja.pajak.approve;

import com.alurkerja.core.controller.CrudController;
import com.alurkerja.pajak.Pajak;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pajak/approve")
public class ApproveController extends CrudController<Pajak, ApproveDto, ApproveService, ApproveRepository> {
    protected ApproveController(ApproveService approveService) {
        super(approveService);
    }
}
