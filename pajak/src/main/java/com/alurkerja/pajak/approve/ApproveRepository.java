package com.alurkerja.pajak.approve;

import com.alurkerja.core.repository.CrudRepository;
import com.alurkerja.pajak.Pajak;
import org.springframework.stereotype.Repository;

@Repository
public interface ApproveRepository extends CrudRepository<Pajak> {
}
