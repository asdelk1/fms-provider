package com.owerp.fmsprovider.helper.repository;

import com.owerp.fmsprovider.helper.model.data.AccountingPeriod;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AccountingPeriodRepository extends JpaRepository<AccountingPeriod, Long> {

    List<AccountingPeriod> findAllByStatusIsTrue();
}
