package com.owerp.fmsprovider.customer.repository;

import com.owerp.fmsprovider.customer.data.enums.DocApproveType;
import com.owerp.fmsprovider.customer.data.model.SalesInvoice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SalesInvoiceRepository extends JpaRepository<SalesInvoice, Long> {

    List<SalesInvoice> getSalesInvoicesByDocApproveType(DocApproveType docApproveType);
    List<SalesInvoice> getSalesInvoicesByCustomerIdOrderByIdDesc(Long customerId);
}
