package com.owerp.fmsprovider.supplier.repository;

import com.owerp.fmsprovider.supplier.data.model.SupplierType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SupplierTypeRepository extends JpaRepository<SupplierType, Long> {
}
