package com.alurkerja.pajak;

import com.alurkerja.core.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PajakRepository extends CrudRepository<Pajak> {
}
