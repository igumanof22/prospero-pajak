package com.alurkerja.pajak.check;

import com.alurkerja.core.controller.CrudController;
import com.alurkerja.pajak.Pajak;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pajak/check")
public class CheckController extends CrudController<Pajak, CheckDto, CheckService, CheckRepository> {
    protected CheckController(CheckService checkService) {
        super(checkService);
    }
}
