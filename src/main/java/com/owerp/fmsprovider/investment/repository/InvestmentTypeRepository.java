package com.owerp.fmsprovider.investment.repository;

import com.owerp.fmsprovider.investment.model.data.InvestmentType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InvestmentTypeRepository extends JpaRepository<InvestmentType, Long> {

    List<InvestmentType> findAllByActiveIsTrue();
}
