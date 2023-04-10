package com.alurkerja.pajak;

import com.alurkerja.core.controller.CrudController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pajak")
public class PajakController extends CrudController<Pajak, PajakDto, PajakService, PajakRepository> {
    protected PajakController(PajakService pajakService) {
        super(pajakService);
    }
}
