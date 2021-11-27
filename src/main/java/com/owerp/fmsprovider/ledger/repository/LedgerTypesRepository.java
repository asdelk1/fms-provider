package com.owerp.fmsprovider.ledger.repository;

import com.owerp.fmsprovider.ledger.model.data.LedgerTypes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LedgerTypesRepository extends JpaRepository<LedgerTypes, Long> {

    @Query("SELECT t FROM LedgerTypes t WHERE t.typeCategory = :typeCategory")
    List<LedgerTypes> getLedgerTypesByCategory(@Param("typeCategory") final Integer typeCategory);
}

