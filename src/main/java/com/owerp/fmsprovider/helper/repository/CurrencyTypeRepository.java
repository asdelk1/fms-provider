package com.owerp.fmsprovider.helper.repository;

import com.owerp.fmsprovider.helper.model.data.CurrencyType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CurrencyTypeRepository extends JpaRepository<CurrencyType, Long> {

    List<CurrencyType> findAllByStatusIsTrue();
}
