package com.owerp.fmsprovider.helper.service;

import com.owerp.fmsprovider.helper.model.data.PaymentMethod;
import com.owerp.fmsprovider.helper.model.dto.PaymentMethodDTO;
import com.owerp.fmsprovider.helper.repository.PaymentMethodRepository;
import com.owerp.fmsprovider.system.util.EntityModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PaymentMethodService {

    private final PaymentMethodRepository repo;
    private final EntityModelMapper mapper;

    public PaymentMethodService(EntityModelMapper mapper, PaymentMethodRepository repo) {
        this.mapper = mapper;
        this.repo = repo;
    }

    public List<PaymentMethod> getAll(){
        return this.repo.findAll();
    }

    public List<PaymentMethod> getAllActive(){
        return this.repo.findAllByStatusIsTrue();
    }

    public Optional<PaymentMethod> get(long id){
        return this.repo.findById(id);
    }

    public PaymentMethod save(PaymentMethodDTO dto){
        PaymentMethod method = this.mapper.getEntity(dto, PaymentMethod.class);
        return this.repo.save(method);
    }
}
