package com.alurkerja.core.service;

public interface GeneratorSequence {
    void create(String sequenceName);
    long get(String sequenceName);
}
