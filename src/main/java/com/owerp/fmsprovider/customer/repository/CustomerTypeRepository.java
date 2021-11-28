package com.owerp.fmsprovider.customer.repository;

import com.owerp.fmsprovider.customer.data.model.CustomerType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CustomerTypeRepository extends JpaRepository<CustomerType, Long> {

    List<CustomerType> findAllByStatusIsTrue();
}
