package com.alurkerja.spec.spec;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter
public class FormSpec {
    private String name;
    private String label;
    private Boolean required = false;
    private String type;
    private List<Map<String, Object>> constraints;
    private HashMap<String, Object> metadata;

    public FormSpec(){

    }

    public FormSpec(String name, String label, Boolean required, String type) {
        this.name = name;
        this.label = label;
        this.required = required;
        this.type = type;
    }

    public FormSpec(String name, String label, Boolean required, String type, String url) {
        this.name = name;
        this.label = label;
        this.required = required;
        this.type = type;
    }
}
