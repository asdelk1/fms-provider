package com.owerp.fmsprovider.ledger.repository;

import com.owerp.fmsprovider.ledger.model.data.LedgerAccount;
import com.owerp.fmsprovider.ledger.model.data.LedgerCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LedgerAccountRepository extends JpaRepository<LedgerAccount, Long> {

    List<LedgerAccount> findAllByStatusIsTrue();

    List<LedgerAccount> getAllByLedgerCategory(LedgerCategory ledgerCategory);

    List<LedgerAccount> getLedgerAccountsByStatusOrderByLedgerAccCode(Boolean status);
}
