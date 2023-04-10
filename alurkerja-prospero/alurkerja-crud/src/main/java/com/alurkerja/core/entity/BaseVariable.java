package com.alurkerja.core.entity;

import com.alurkerja.spec.spec.FormSpec;
import com.alurkerja.spec.spec.SpecService;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter
public abstract class BaseVariable implements Serializable  {
    protected String decision;
    public Map<String, Object> asMap(){
        HashMap<String, Object> asMap = new HashMap<>();

        Class<?> clazz = this.getClass();
        for(Field field : clazz.getDeclaredFields()){
            field.setAccessible(true);
            try {
                asMap.put(field.getName(), field.get(this));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return asMap;
    }

    public List<FormSpec> specifications() throws ClassNotFoundException {
        return SpecService.specifications(this.getClass());
    }

    public String getDecision() {
        return decision;
    }

    public void setDecision(String decision) {
        this.decision = decision;
    }

}
