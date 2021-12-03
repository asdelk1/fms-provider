package com.owerp.fmsprovider.customer.service;

import com.owerp.fmsprovider.customer.data.dto.SalesInvoiceDTO;
import com.owerp.fmsprovider.customer.data.model.SalesInvoice;
import com.owerp.fmsprovider.customer.repository.SalesInvoiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SalesInvoiceService {

    private SalesInvoiceRepository repo;

    public SalesInvoiceService(SalesInvoiceRepository repo) {
        this.repo = repo;
    }

    public List<SalesInvoice> getAll(){
        return this.repo.findAll(Sort.by(Sort.Direction.DESC, "id"));
    }
}
