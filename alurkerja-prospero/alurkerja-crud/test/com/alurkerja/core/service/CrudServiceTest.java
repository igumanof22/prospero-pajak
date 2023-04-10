package com.alurkerja.core.service;

import com.alurkerja.Application;
import com.alurkerja.core.repository.search.GenericSearchSpecification;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.TypedQuery;


import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest(classes = Application.class)
class CrudServiceTest {
    private class User {
        Long id ;

    }
    private class UserDto{
        User user;
        double fullname;
    }

    @Test
    void instantiateSpecification() {
        Specification spec  = new GenericSearchSpecification<>( UserDto.class);



    }

    @Test
    void testInstantiateSpecification() {
    }
}