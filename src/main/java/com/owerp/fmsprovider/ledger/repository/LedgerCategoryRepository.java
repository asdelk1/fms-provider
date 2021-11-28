package com.owerp.fmsprovider.ledger.repository;

import com.owerp.fmsprovider.ledger.model.data.LedgerCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LedgerCategoryRepository extends JpaRepository<LedgerCategory, Long> {

    List<LedgerCategory> findAllByStatusIsTrue();
}
