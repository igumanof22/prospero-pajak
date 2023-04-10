package com.alurkerja.core.repository;

import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

import javax.persistence.EntityManager;


public class CrudRepositoryImp<T> extends SimpleJpaRepository<T, Long> implements CrudRepository<T> {
    public CrudRepositoryImp(JpaEntityInformation<T, ?> entityInformation, EntityManager entityManager) {
        super(entityInformation, entityManager);
    }
}
