package com.alurkerja.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;


@NoRepositoryBean
public interface CrudRepository<T> extends JpaRepository<T, Long>, JpaSpecificationExecutor<T> {

}
