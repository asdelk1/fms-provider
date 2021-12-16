package com.owerp.fmsprovider.customer.repository;

import com.owerp.fmsprovider.customer.data.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    List<Customer> getCustomerByStatusIsTrue();
    Customer getCustomerByCustomerCode(String code);

    @Query("SELECT c FROM Customer c WHERE c.status  = true AND c.controlAccount is not null")
    List<Customer> getActiveCustomers();

    @Query("SELECT c FROM Customer c WHERE c.status  = true AND c.controlAccount is not null and c.customerType.id = :customerTypeId")
    List<Customer> getActiveCustomersByType(@Param("customerTypeId") final Long customerTypeId);
}
