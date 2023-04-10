package com.alurkerja.spec.spec;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JenisKelaminSelectItem  extends EmptyValueSelectItem {
    @Override
    public List<Map<String, String>> getValue() {
        ArrayList<Map<String,  String>> value =  new ArrayList<>();

        Map<String, String> items1 = new HashMap<>();
        items1.put("id", "P");
        items1.put("value", "Perempuan");
        value.add(items1);
        Map<String, String> items2 = new HashMap<>();
        items2.put("id", "L");
        items2.put("value", "Laki Laki");
        value.add(items2);
        return value;
    }
}
