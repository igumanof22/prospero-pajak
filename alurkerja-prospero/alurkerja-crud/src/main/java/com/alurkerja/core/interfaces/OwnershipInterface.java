package com.alurkerja.core.interfaces;

import org.springframework.data.jpa.domain.Specification;

public interface OwnershipInterface {

    public Specification getMine();
}
