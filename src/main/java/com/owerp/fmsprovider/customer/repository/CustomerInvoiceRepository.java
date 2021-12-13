package com.owerp.fmsprovider.customer.repository;

import com.owerp.fmsprovider.customer.data.enums.InvoicePaymentStatus;
import com.owerp.fmsprovider.customer.data.model.CustomerInvoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerInvoiceRepository extends JpaRepository<CustomerInvoice, Long> {

    @Query("SELECT i FROM CustomerInvoice i WHERE i.customer.id= :customerId and i.invoicePaymentStatus <> :invoicePaymentStatus")
    List<CustomerInvoice> getCustomerInvoicesToBePay(@Param("customerId") Long customerId, @Param("invoicePaymentStatus") InvoicePaymentStatus invoicePaymentStatus);

    @Query("SELECT i FROM CustomerInvoice i WHERE i.invoiceNumber = :invoiceNumber")
    CustomerInvoice getCustomerInvoicesByNumber(@Param("invoiceNumber") String invoiceNumber);
}
