package com.alurkerja.core.util;


public class GenericClassUtil<E> {
    private final Class<E> type;

    public GenericClassUtil(Class<E> type) {
        this.type = type;
    }

    public Class<E> getMyType() {
        return this.type;
    }
    public String getSimpleName(){
        return this.type.getSimpleName();
    }

    public String getLoweredClassName(){
        return this.type.getSimpleName().toLowerCase();
    }
}
