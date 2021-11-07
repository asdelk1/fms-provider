package com.owerp.fmsprovider.supplier.repository;

import com.owerp.fmsprovider.supplier.model.data.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SupplierRepository extends JpaRepository<Supplier, Long> {
}
