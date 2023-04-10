package com.alurkerja.spec.entity;

import com.alurkerja.spec.annotation.FormReference;
import com.alurkerja.spec.annotation.ListOption;
import com.alurkerja.spec.spec.JenisKelaminSelectItem;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;


public class TestDto  extends BaseDto<Test, TestDto >{


    private String username;
    private String password;
    @NotNull
    @Max(30)
    @FormReference(label="Ini Lablenya" ,  type = "Select")
    @ListOption(url = "api/kong/inventory/product",  key= "id", value = "item" ,  values =  JenisKelaminSelectItem.class)
    private String jenis_kelamin;
    @FormReference(type = "Select" ,  canSort = false)
    @ListOption(url = "https:/example.com/api/kong/inventory/product",  key= "id", value = "item" )
    private String lama;

}
