package com.owerp.fmsprovider.customer.repository;

import com.owerp.fmsprovider.customer.data.model.CustomerItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerItemRepository extends JpaRepository<CustomerItem, Long> {

    List<CustomerItem> findAllByStatusIsTrue();

    @Query("SELECT i FROM CustomerItem i WHERE i.status  = true AND i.ledgerAccount is not null")
    List<CustomerItem> getActiveCustomerItems();

    @Query("SELECT i FROM CustomerItem i WHERE i.status  = true AND i.ledgerAccount is not null and i.customerType.id = :customerTypeId")
    List<CustomerItem> getActiveCustomerItemsByCustomerType(@Param("customerTypeId") Long customerTypeId);
}
