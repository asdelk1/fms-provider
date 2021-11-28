package com.owerp.fmsprovider.customer.service;

import com.owerp.fmsprovider.customer.data.dto.CustomerItemDTO;
import com.owerp.fmsprovider.customer.data.dto.CustomerItemFormulaDTO;
import com.owerp.fmsprovider.customer.data.enums.CustomerFormulaType;
import com.owerp.fmsprovider.customer.data.model.CustomerItem;
import com.owerp.fmsprovider.customer.data.model.CustomerItemFormula;
import com.owerp.fmsprovider.customer.repository.CustomerItemRepository;
import com.owerp.fmsprovider.system.util.EntityModelMapper;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerItemService {

    private final CustomerItemRepository repo;
    private final EntityModelMapper mapper;

    public CustomerItemService(CustomerItemRepository repo, EntityModelMapper mapper) {
        this.repo = repo;
        this.mapper = mapper;
    }

    public List<CustomerItem> getAll() {
        return this.repo.findAll();
    }

    public List<CustomerItem> getAllActive(){
        return this.repo.findAllByStatusIsTrue();
    }

    public Optional<CustomerItem> get(long id){
        return this.repo.findById(id);
    }

    public CustomerItem save(CustomerItemDTO dto){
        CustomerItem item = this.mapper.getEntity(dto, CustomerItem.class);
        this.addCustomerItemFormula(dto, item);
        item = this.repo.save(item);
        return item;
    }

    private void addCustomerItemFormula(CustomerItemDTO customerItemDTO, CustomerItem customerItem){

        if(customerItem.getFormulaSlab() == null){
            customerItem.setFormulaSlab(new HashSet<>());
        }

        if(customerItemDTO.getFormulaType() == CustomerFormulaType.NONE){
            customerItem.getFormulaSlab().clear();
            customerItem.setFixedAmount(null);
            customerItem.setFixedPercentage(null);
        }else if (customerItemDTO.getFormulaType() == CustomerFormulaType.FIXED){
            customerItem.getFormulaSlab().clear();
        }else if (customerItemDTO.getFormulaType() == CustomerFormulaType.SLAB){

            customerItem.setFixedAmount(null);
            customerItem.setFixedPercentage(null);

            customerItem.getFormulaSlab().clear();
            for(CustomerItemFormulaDTO formulaDTO : customerItemDTO.getFormulaSlab()){
                CustomerItemFormula customerItemFormula = new CustomerItemFormula();

                customerItemFormula.setAmount(formulaDTO.getAmount());
                customerItemFormula.setFromAmount(formulaDTO.getFromAmount());
                customerItemFormula.setGraterThanAmount(formulaDTO.getGraterThanAmount());
                customerItemFormula.setPercentage(formulaDTO.getPercentage());
                customerItemFormula.setPerUnitAmount(formulaDTO.getPerUnitAmount());
                customerItemFormula.setToAmount(formulaDTO.getToAmount());
                customerItemFormula.setPerUnitValue(formulaDTO.getPerUnitValue());
                customerItemFormula.setGreaterThan(formulaDTO.getGreaterThan());

                customerItem.getFormulaSlab().add(customerItemFormula);
            }
        }
    }
}
