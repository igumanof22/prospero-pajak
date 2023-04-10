package com.alurkerja.pajak.check;

import com.alurkerja.core.repository.CrudRepository;
import com.alurkerja.pajak.Pajak;
import org.springframework.stereotype.Repository;

@Repository
public interface CheckRepository extends CrudRepository<Pajak> {
}
