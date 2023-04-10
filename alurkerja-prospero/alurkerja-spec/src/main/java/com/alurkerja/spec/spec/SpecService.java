package com.alurkerja.spec.spec;

import com.alurkerja.spec.annotation.FormReference;
import com.alurkerja.spec.annotation.ListOption;
import com.alurkerja.spec.annotation.ObjectRenderer;
import lombok.SneakyThrows;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;

public class SpecService {

    @SneakyThrows
    public static  List<FormSpec> specifications(Class clazz) throws ClassNotFoundException {
//        Class<?> clazz = this.getClass();
        List<FormSpec> result = new ArrayList<>();

        for (Field field : clazz.getDeclaredFields()) {
            FormSpec form = new FormSpec();
            form.setName(field.getName());
            form.setLabel(com.alurkerja.core.util.StringUtil.convertFieldToLabel(field.getName()));
//            String[] constraints = new String[field.getDeclaredAnnotations().length];
            List<Map<String , Object>> constraints = new ArrayList<>();
            form.setType(field.getType().getSimpleName().toUpperCase(Locale.ROOT));
            if (field.getDeclaredAnnotations().length > 0) {
                ArrayList<String> xx = new ArrayList<>();

                Map<String, Object> constraintsConv = new HashMap<>();


                boolean  hasFormReference =  false;
                HashMap<String, Object> metadata = new HashMap<>();
                for (Annotation annotation : field.getDeclaredAnnotations()) {
//                    constraints[i++] = annotation.annotationType().getSimpleName();
                    Object[] mValue = new Object[annotation.annotationType().getDeclaredMethods().length];
                    int i = 0;
                    for (Method method : annotation.annotationType().getDeclaredMethods()) {
                        Object value = method.invoke(annotation, (Object[]) null);

                        if(value != null && value != "" && !value.getClass().isArray() ) {
                            mValue[i++] = value;
                        }
                    }
//                    Map<String, Object> constraint = this.javaxValidationConstraints(annotation.annotationType().getSimpleName() , mValue );
//                    if (constraint != null)  {
//                        constraints.add(constraint);
//                    }

                    if (annotation.annotationType().getSimpleName().equals(FormReference.class.getSimpleName())) {
                        hasFormReference = true;
                        FormReference formReference = (FormReference) annotation;
                        metadata.putAll(constraintsConv);
                        if (formReference.format() != null || formReference.format() != "") {
                            metadata.put("format", formReference.format());
                        }
                        if (formReference.format() != null ||  formReference.format() != "") {
                            metadata.put("tooltips", formReference.tooltips());
                        }
                        if (formReference.canSort()) {
                            metadata.put("canSort" ,  true);
                        }else {
                            metadata.put("canSort" ,  false);
                        }
                        if (formReference.canFilter()) {
                            metadata.put("canFilter" ,  true);
                        }else {
                            metadata.put("canFilter" ,  false);
                        }
                        if (formReference.format() != null ||  formReference.format() != "") {
                            metadata.put("tooltips", formReference.tooltips());
                        }
                        if (formReference.placeholder() != null ||   formReference.placeholder() != "") {
                            metadata.put("placeholder", formReference.placeholder());
                        } else {
                            metadata.put("placeholder", com.alurkerja.core.util.StringUtil.convertFieldToLabel(field.getName()));
                        }

//                        Replace Type when reference present
                        if (!formReference.type().isEmpty()) {
                            form.setType(formReference.type().toUpperCase(Locale.ROOT));
                        }
//                        Replace Title when reference present
                        if (!formReference.label().isEmpty()) {
                            form.setLabel(formReference.label());
                        }
                    }
                    if (annotation.annotationType().getSimpleName().equals(ListOption.class.getSimpleName())) {
                        ListOption formReference = (ListOption) annotation;

                        HashMap<String, Object> items = new HashMap<>();
                        items.put("key",  formReference.key());
                        items.put("url",  formReference.url());
                        items.put("value",  formReference.value());

                        if(formReference.values() != EmptyValueSelectItem.class) {
                            EmptyValueSelectItem selectValueInterface = (EmptyValueSelectItem) formReference.values().getDeclaredConstructor().newInstance();

                            items.put("values", selectValueInterface.getValue());
                        }
                        metadata.put("listOption", items);
                    }
                    if (annotation.annotationType().getSimpleName().equals(ObjectRenderer.class.getSimpleName())) {
                        ObjectRenderer formReference = (ObjectRenderer) annotation;
                        HashMap<String, Object> items = new HashMap<>();
                        items.put("value",  formReference.value());
                        metadata.put("objectRenderer", items);
                    }

                }

                if(hasFormReference) {

                    metadata.put("canSort", true);
                    metadata.put("canFilter", true);
                }
                form.setMetadata(metadata);


            } else {
                HashMap<String, Object> metadata = new HashMap<>();

                metadata.put("canSort", true);
                metadata.put("canFilter", true);
                metadata.put("format", null);
                metadata.put("placeholder", com.alurkerja.core.util.StringUtil.convertFieldToLabel(field.getName()));
                form.setMetadata(metadata);

            }


            form.setConstraints(new ArrayList<>());
//            form.setRequired(Arrays.stream(constraints).anyMatch("NotEmpty"::equals));
            result.add(form);
        }
        return result;
    }
}
