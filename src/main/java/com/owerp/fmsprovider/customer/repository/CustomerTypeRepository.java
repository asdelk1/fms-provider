package com.owerp.fmsprovider.customer.repository;

import com.owerp.fmsprovider.customer.data.model.CustomerType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerTypeRepository extends JpaRepository<CustomerType, Long> {
}
