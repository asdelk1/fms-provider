package com.owerp.fmsprovider.supplier.repository;

import com.owerp.fmsprovider.supplier.model.data.SupplierItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SupplierItemRepository extends JpaRepository<SupplierItem, Long> {

List<SupplierItem> findAllByState(Boolean state);

}
