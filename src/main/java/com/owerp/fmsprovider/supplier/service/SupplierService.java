package com.owerp.fmsprovider.supplier.service;

import com.owerp.fmsprovider.supplier.model.data.Supplier;
import com.owerp.fmsprovider.supplier.model.data.SupplierType;
import com.owerp.fmsprovider.supplier.model.dto.SupplierDTO;
import com.owerp.fmsprovider.supplier.model.enums.NumTypes;
import com.owerp.fmsprovider.supplier.repository.SupplierRepository;
import com.owerp.fmsprovider.system.advice.EntityNotFoundException;
import com.owerp.fmsprovider.system.util.EntityModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SupplierService {

    private final SupplierRepository repo;
    private final EntityModelMapper mapper;
    private final FinNumbersService finNumbersService;
    private final SupplierTypeService typeService;

    public SupplierService(SupplierRepository repo, EntityModelMapper mapper, FinNumbersService finNumbersService, SupplierTypeService typeService) {
        this.repo = repo;
        this.mapper = mapper;
        this.finNumbersService = finNumbersService;
        this.typeService = typeService;
    }

    public List<Supplier> getAll() {
        return this.repo.findAll();
    }

    public Optional<Supplier> get(Long id) {
        return this.repo.findById(id);
    }

    public Supplier save(final SupplierDTO dto) {
        Supplier supplier = this.mapper.getEntity(dto, Supplier.class);
        if (supplier.getId() == null) { // get Customer code for new customer
            supplier.setCode(getSupplierNumber(null, dto.getType().getId(), false));
        } else {
            supplier.setCode(dto.getCode()); // Get the Customer code from when saving
        }
        return this.repo.save(supplier);
    }

    private String getSupplierNumber(Long supplierId, Long supplierTypeId, Boolean isDisplayOnly) throws EntityNotFoundException {
        if (supplierId == null) {
            Long nextNumber = finNumbersService.getNextNumber(NumTypes.SUPPLIER, isDisplayOnly);
            SupplierType supplierType = this.getSupplierType(supplierTypeId);
            return supplierType.getTypeCode() + "-" + nextNumber;
        } else {
            /* for existing customer, if customer type change only change the Item Code and keep the Number*/
            String SupplierCode = repo.getById(supplierId).getCode();
            String existingNumber = SupplierCode.split("-")[1];
            SupplierType supplierType = this.getSupplierType(supplierTypeId);
            return supplierType.getTypeCode() + "-" + existingNumber;
        }

    }

    private SupplierType getSupplierType(Long id) throws EntityNotFoundException {
        return this.typeService.get(id).orElseThrow(() -> new EntityNotFoundException("Supplier Type", id));
    }
}
