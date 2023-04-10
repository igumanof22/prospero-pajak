package com.alurkerja.spec.entity;


import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.util.*;

@Getter
@Setter
public abstract class BaseDtoGeneric<E, D> implements Serializable {
    public void copyFromDto(E t) {
        BeanUtils.copyProperties(this, t, getNullPropertyNames(this));
    }


    public String[] getNullPropertyNames(Object source) {
        List<String> nullValuePropertyNames = new ArrayList<>();
        nullValuePropertyNames.add("id");
        for (Field f : source.getClass().getDeclaredFields()) {
            try {
                f.setAccessible(true);
                if (f.get(source) == null) {
                    nullValuePropertyNames.add(f.getName());
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return nullValuePropertyNames.toArray(new String[0]);
    }

    public E fromDto() {
        try {
            Class<?> clazz = Class.forName(((Class) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0]).getTypeName());
            Constructor<?> cons = clazz.getConstructor();
            E t = (E) cons.newInstance();
            BeanUtils.copyProperties(this, t);
            return t;
        } catch (ClassNotFoundException | NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    public D toDto(E entity) {
        BeanUtils.copyProperties(entity, this);
        return (D) this;
    }




    private Map<String, Object> javaxValidationConstraints(String key , Object value) {
        Map<String, Object> validationKeys = new HashMap<>();
        validationKeys.put("AssertFalse", "booleanValue=false");
        validationKeys.put("AssertTrue", "booleanValue=true");
        validationKeys.put("DecimalMax", "value");
        validationKeys.put("DecimalMin", "value");
        validationKeys.put("Digits", "integer,fraction");
        validationKeys.put("Email", "email=true");
        validationKeys.put("Future", "date=future");
        validationKeys.put("FutureOrPresent", "date=futureOrPresent");
        validationKeys.put("Max", "value");
        validationKeys.put("Min", "value");
        validationKeys.put("Negative", "negative=yes");
        validationKeys.put("NegativeOrZero", "negative=yesOrZero");
        validationKeys.put("NotBlank", "required=true");
        validationKeys.put("NotEmpty", "required=true");
        validationKeys.put("NotNull", "required=true");
        validationKeys.put("Null", "required=false");
        validationKeys.put("Past", "date=past");
        validationKeys.put("PastOrPresent", "date=pastOrPresent");
        validationKeys.put("Pattern", "regexp");
        validationKeys.put("Positive", "negative=no");
        validationKeys.put("PositiveOrZero", "negative=noOrZero");
        validationKeys.put("Size", "min,max");
//        return validationKeys.get(key) != null ? validationKeys.get(key).toString() : null;
        String[] commaVar = {"min", "max", "integer", "fraction"};
        Map<String, Object> result = new HashMap<>();
        if (validationKeys.containsKey(key)) {
            String val = validationKeys.get(key).toString();

            boolean isComma = val.contains(",");
            boolean isEq = val.contains("=");
            if ((isComma && Arrays.stream(commaVar).anyMatch(key::equals)) || key.equals("regexp")) {
                result.put(key, value);
            } else if (isEq) {
                String[] keyVal = val.split("=");
                result.put(keyVal[0], keyVal[1]);
            } else if (val.equals("value")) {
                System.out.println(key + " " +  value);
                result.put(key, value);
            }
        }
        return result;
    }

}
