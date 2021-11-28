package com.owerp.fmsprovider.helper.service;

import com.owerp.fmsprovider.helper.model.data.PaymentTerms;
import com.owerp.fmsprovider.helper.model.dto.PaymentTermsDTO;
import com.owerp.fmsprovider.helper.repository.PaymentTermsRepository;
import com.owerp.fmsprovider.system.util.EntityModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PaymentTermsService {

    private final PaymentTermsRepository repo;
    private final EntityModelMapper mapper;

    public PaymentTermsService(PaymentTermsRepository repo, EntityModelMapper mapper) {
        this.repo = repo;
        this.mapper = mapper;
    }

    public List<PaymentTerms> getAll(){
        return this.repo.findAll();
    }

    public List<PaymentTerms> getAllActive(){
        return this.repo.findAllByStatusIsTrue();
    }

    public Optional<PaymentTerms> get(long id){
        return this.repo.findById(id);
    }

    public PaymentTerms save(PaymentTermsDTO dto){
        PaymentTerms terms = this.mapper.getEntity(dto, PaymentTerms.class);
        return this.repo.save(terms);
    }
}
