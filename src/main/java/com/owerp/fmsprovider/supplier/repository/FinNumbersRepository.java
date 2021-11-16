package com.owerp.fmsprovider.supplier.repository;

import com.owerp.fmsprovider.supplier.model.data.FinNumbers;
import com.owerp.fmsprovider.supplier.model.enums.NumTypes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FinNumbersRepository extends JpaRepository<FinNumbers, Long> {

    FinNumbers getFinNumbersByNumType(NumTypes numType);
}
