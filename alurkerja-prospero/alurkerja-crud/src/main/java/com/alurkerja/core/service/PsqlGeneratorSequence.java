package com.alurkerja.core.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class PsqlGeneratorSequence implements GeneratorSequence{
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public PsqlGeneratorSequence(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void create(String sequenceName) {
        jdbcTemplate.execute("CREATE SEQUENCE IF NOT EXISTS \"" + sequenceName + "\" INCREMENT 1 START 1");
    }

    @Override
    public long get(String sequenceName) {
        return jdbcTemplate.queryForObject("select nextval('" + sequenceName + "')", Long.class);
    }
}
