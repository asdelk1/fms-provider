package com.owerp.fmsprovider.customer.service;

import com.owerp.fmsprovider.customer.data.dto.*;
import com.owerp.fmsprovider.customer.data.enums.CustomerFormulaType;
import com.owerp.fmsprovider.customer.data.enums.DocApproveType;
import com.owerp.fmsprovider.customer.data.model.*;
import com.owerp.fmsprovider.customer.repository.SalesInvoiceRepository;
import com.owerp.fmsprovider.helper.model.data.AccountingPeriod;
import com.owerp.fmsprovider.helper.model.data.CostCenter;
import com.owerp.fmsprovider.helper.model.data.TaxGroup;
import com.owerp.fmsprovider.helper.model.dto.CostCenterDTO;
import com.owerp.fmsprovider.helper.model.dto.FinTaxTypeDTO;
import com.owerp.fmsprovider.helper.model.enums.TaxOperator;
import com.owerp.fmsprovider.helper.service.AccountingPeriodService;
import com.owerp.fmsprovider.helper.service.CostCenterService;
import com.owerp.fmsprovider.helper.service.TaxGroupService;
import com.owerp.fmsprovider.supplier.model.dto.TaxGroupDTO;
import com.owerp.fmsprovider.supplier.model.enums.NumTypes;
import com.owerp.fmsprovider.supplier.service.FinNumbersService;
import com.owerp.fmsprovider.system.advice.EntityNotFoundException;
import com.owerp.fmsprovider.system.model.data.User;
import com.owerp.fmsprovider.system.service.UserService;
import com.owerp.fmsprovider.system.util.EntityModelMapper;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class SalesInvoiceService {

    private final SalesInvoiceRepository repo;
    private final FinNumbersService finNumbersService;
    private final AccountingPeriodService accountingPeriodService;
    private final CustomerTypeService customerTypeService;
    private final CustomerItemService customerItemService;
    private final TaxGroupService taxGroupService;
    private final CostCenterService costCenterService;
    private final UserService userService;

    private final EntityModelMapper mapper;

    public SalesInvoiceService(SalesInvoiceRepository repo, FinNumbersService finNumbersService, AccountingPeriodService accountingPeriodService, CustomerTypeService customerTypeService, CustomerItemService customerItemService, TaxGroupService taxGroupService, CostCenterService costCenterService, UserService userService, EntityModelMapper mapper) {
        this.repo = repo;
        this.finNumbersService = finNumbersService;
        this.accountingPeriodService = accountingPeriodService;
        this.customerTypeService = customerTypeService;
        this.customerItemService = customerItemService;
        this.taxGroupService = taxGroupService;
        this.costCenterService = costCenterService;
        this.userService = userService;
        this.mapper = mapper;
    }

    public List<SalesInvoice> getAll() {
        return this.repo.findAll(Sort.by(Sort.Direction.DESC, "id"));
    }

    public Optional<SalesInvoice> get(long id){
        return this.repo.findById(id);
    }

    public SalesInvoice save(SalesInvoiceDTO dto) {
        SalesInvoice invoice = this.mapper.getEntity(dto, SalesInvoice.class);

        boolean isNew = true;
        DocApproveType previousDocApproveType = null;

        if (dto.getId() != null && dto.getId() != 0) {
            isNew = false;
            previousDocApproveType = this.repo.getById(dto.getId()).getDocApproveType();
        }
        if (isNew) {
            invoice.setInvoiceNumber(this.getSalesInvoiceNumber(null, invoice.getCustomer().getCustomerType().getId(), false));
        }
        invoice.setDocApproveType(DocApproveType.NONE);

        List<SalesInvoiceItem> items = dto.getSalesInvoiceItems().stream().map((i) -> this.mapper.getEntity(i, SalesInvoiceItem.class)).collect(Collectors.toList());
        invoice.setSalesInvoiceItems(items);

        List<InvoiceTaxDetails> taxDetailsList = dto.getInvoiceTaxDetails().stream().map((t) -> this.mapper.getEntity(t, InvoiceTaxDetails.class)).collect(Collectors.toList());
        invoice.setInvoiceTaxDetails(taxDetailsList);


        User user = this.userService.getLoggedInUser();
        invoice.setEnteredBy(user);
        invoice.setEnteredOn(LocalDateTime.now());

        invoice = this.repo.save(invoice);

//        TODO: Send notification
//        if (isNew || previousDocApproveType == DocApproveType.CHECK_REJECTED) {
//        }
        return invoice;
    }

    public String getSalesInvoiceNumber(Long salesInvoiceId, Long customerTypeId, Boolean isDisplayOnly) {
        /*Customer type code - current year - sequence number*/
        AccountingPeriod accountingPeriod = this.accountingPeriodService.getLatestPeriod();
        int currentYear = accountingPeriod.getStartDate().getYear();

        if (salesInvoiceId == null) {
            Long nextNumber = this.finNumbersService.getNextNumber(NumTypes.SALES_INVOICE, isDisplayOnly);
            CustomerType customerType = this.customerTypeService.get(customerTypeId);
            return customerType.getTypeCode() + "-" + currentYear + "-" + nextNumber;
        } else {
            /* for existing Invoice, if customer type change only change the type Code and keep the Number*/
            String invoiceNumber = this.repo.getById(salesInvoiceId).getInvoiceNumber();
            String existingNumber = invoiceNumber.split("-")[2];
            CustomerType customerType = this.customerTypeService.get(customerTypeId);
            return customerType.getTypeCode() + "-" + currentYear + "-" + existingNumber;
        }
    }

    public Map<String, Double> calculateCustomerItemAmount(SalesInvoiceItemDTO salesInvoiceItemDTO) {
        Map<String, Double> result = new HashMap<>();
        try {
            Double amount = 0d;
            Double taxAmount = 0d;
            //calculate Amount
            CustomerItem customerItem = this.customerItemService.get(salesInvoiceItemDTO.getCustomerItem().getId()).orElseThrow(() -> new EntityNotFoundException("Customer Item", salesInvoiceItemDTO.getId()));
            if (customerItem.getFormulaType() == CustomerFormulaType.NONE) { // No formula
                amount = salesInvoiceItemDTO.getUnitValue();

            } else if (customerItem.getFormulaType() == CustomerFormulaType.FIXED) { // Fixed value For rate
                if (customerItem.getFixedAmount() != null && customerItem.getFixedAmount() > 0) {
                    amount = customerItem.getFixedAmount();
                } else if (customerItem.getFixedPercentage() != null && customerItem.getFixedPercentage() > 0) {

                    //Calculate percentage
                    amount = salesInvoiceItemDTO.getUnitValue() * (customerItem.getFixedPercentage() / 100);
                }

            } else if (customerItem.getFormulaType() == CustomerFormulaType.SLAB) {

                List<CustomerItemFormula> formulas = customerItem.getFormulaSlab().stream().collect(Collectors.toList());
                Collections.sort(formulas, (o1, o2) -> (!o1.getGreaterThan() && !o2.getGreaterThan()) ? o1.getFromAmount().compareTo(o2.getFromAmount()) : 0);
                for (CustomerItemFormula itemFormula : formulas) {

                    if (itemFormula.getGreaterThan()) { // For grater than value
                        if (salesInvoiceItemDTO.getUnitValue() > itemFormula.getGraterThanAmount()) {
                            amount = itemFormula.getAmount();
                            if (itemFormula.getPerUnitValue() != null) {
                                int parts = (int) ((salesInvoiceItemDTO.getUnitValue() - itemFormula.getGraterThanAmount()) / itemFormula.getPerUnitValue());
                                amount = amount + (parts * itemFormula.getPerUnitAmount());
                            }
                            break;
                        }

                    } else {
                        if (salesInvoiceItemDTO.getUnitValue() < itemFormula.getToAmount()) {
                            // amount
                            if (itemFormula.getAmount() != null) {
                                amount = itemFormula.getAmount();
                            } else if (itemFormula.getPercentage() != null) { // Percentage
                                amount = salesInvoiceItemDTO.getUnitValue() * (itemFormula.getPercentage() / 100);
                            }
                            break;
                        }
                    }
                }
            }

            // Calculate Tax
            if (salesInvoiceItemDTO.getTaxGroup() != null && salesInvoiceItemDTO.getTaxGroup().getId() != null && salesInvoiceItemDTO.getTaxGroup().getId() != 0) {
                taxAmount = calculateTaxAsSingleValue(salesInvoiceItemDTO.getTaxGroup(), amount);
            }

            amount = new BigDecimal(amount).setScale(2, RoundingMode.HALF_EVEN).doubleValue();
            taxAmount = new BigDecimal(taxAmount).setScale(2, RoundingMode.HALF_EVEN).doubleValue();
            result.put("amount", amount);//Amount
            result.put("taxAmount", taxAmount);//Tax

        } catch (Exception e) {
            LoggerFactory.getLogger(SalesInvoiceService.class).error("[calculateCustomerItemAmount] " + e.getMessage());
            throw e;
        }
        return result;
    }

    private double calculateTaxAsSingleValue(TaxGroupDTO taxGroupDTO, Double amount) throws EntityNotFoundException {
        double totalTaxValue = 0;
        TaxGroup taxGroup = this.taxGroupService.get(taxGroupDTO.getId()).orElseThrow(() -> new EntityNotFoundException("Tax Group", taxGroupDTO.getId()));

        totalTaxValue = (amount * taxGroup.getFinTaxType_01().getTaxRate()) / 100;

        if (taxGroup.getTaxOperator_01() != null) {
            if (taxGroup.getTaxOperator_01() == TaxOperator.INCLUSIVE) {
                totalTaxValue = totalTaxValue + ((totalTaxValue + amount) * taxGroup.getFinTaxType_02().getTaxRate()) / 100;

            } else if (taxGroup.getTaxOperator_01() == TaxOperator.EXCLUSIVE) {
                totalTaxValue = totalTaxValue + (amount * taxGroup.getFinTaxType_02().getTaxRate()) / 100;
            }
        }
        return totalTaxValue;
    }

    public SalesInvoiceDetailDTO getSalesItemDetailsAndTax(SalesInvoiceDetailDTO salesInvoiceDetail) {
        SalesInvoiceDetailDTO result = new SalesInvoiceDetailDTO();
        //get sales invoice item details
        SalesInvoiceItemDTO salesInvoiceItemDTO = new SalesInvoiceItemDTO();

        long customerItemId = salesInvoiceDetail.getSalesInvoiceItem().getCustomerItem().getId();
        CustomerItem customerItem = this.customerItemService.get(customerItemId).orElseThrow(() -> new EntityNotFoundException("Customer Item", customerItemId));
        salesInvoiceItemDTO.setCustomerItem(this.mapper.getDTO(customerItem, CustomerItemDTO.class));

        salesInvoiceItemDTO.setItemDescription(salesInvoiceDetail.getSalesInvoiceItem().getItemDescription());
        salesInvoiceItemDTO.setUnitValue(salesInvoiceDetail.getSalesInvoiceItem().getUnitValue());
        salesInvoiceItemDTO.setAmount(salesInvoiceDetail.getSalesInvoiceItem().getAmount());

        if (salesInvoiceDetail.getSalesInvoiceItem().getTaxGroup() != null &&
                salesInvoiceDetail.getSalesInvoiceItem().getTaxGroup().getId() != null &&
                salesInvoiceDetail.getSalesInvoiceItem().getTaxGroup().getId() != 0) {

            long taxGroupId = salesInvoiceDetail.getSalesInvoiceItem().getTaxGroup().getId();
            TaxGroup group = this.taxGroupService.get(taxGroupId).orElseThrow(() -> new EntityNotFoundException("Tax Group", taxGroupId));
            salesInvoiceItemDTO.setTaxGroup(this.mapper.getDTO(group, TaxGroupDTO.class));
        }
        if (salesInvoiceDetail.getSalesInvoiceItem().getCostCenter() != null &&
                salesInvoiceDetail.getSalesInvoiceItem().getCostCenter().getId() != null &&
                salesInvoiceDetail.getSalesInvoiceItem().getCostCenter().getId() != 0) {
            long costCenterId = salesInvoiceDetail.getSalesInvoiceItem().getCostCenter().getId();
            CostCenter costCenter = this.costCenterService.get(costCenterId).orElseThrow(() -> new EntityNotFoundException("Cost Center", costCenterId));
            salesInvoiceItemDTO.setCostCenter(this.mapper.getDTO(costCenter, CostCenterDTO.class));
        }
        salesInvoiceItemDTO.setTaxAmount(salesInvoiceDetail.getSalesInvoiceItem().getTaxAmount());

        result.setSalesInvoiceItem(salesInvoiceItemDTO);
        // tax Calculation
        if (salesInvoiceDetail.getSalesInvoiceItem().getTaxGroup() != null &&
                salesInvoiceDetail.getSalesInvoiceItem().getTaxGroup().getId() != null &&
                salesInvoiceDetail.getSalesInvoiceItem().getTaxGroup().getId() != 0) {
            result.setTaxList(calculateTaxSeparate(salesInvoiceDetail.getSalesInvoiceItem().getTaxGroup(), salesInvoiceDetail.getSalesInvoiceItem().getAmount(), salesInvoiceDetail.getTaxList(), true));
        } else {
            result.setTaxList(salesInvoiceDetail.getTaxList());
        }
        return result;
    }

    public SalesInvoiceDetailDTO removeSalesItemDetailsAndTax(SalesInvoiceDetailDTO salesInvoiceDetail) {
        SalesInvoiceDetailDTO result = new SalesInvoiceDetailDTO();

        // tax Calculation
        if (salesInvoiceDetail.getSalesInvoiceItem().getTaxGroup() != null &&
                salesInvoiceDetail.getSalesInvoiceItem().getTaxGroup().getId() != null &&
                salesInvoiceDetail.getSalesInvoiceItem().getTaxGroup().getId() != 0 &&
                salesInvoiceDetail.getTaxList() != null) {
            result.setTaxList(calculateTaxSeparate(salesInvoiceDetail.getSalesInvoiceItem().getTaxGroup(), salesInvoiceDetail.getSalesInvoiceItem().getAmount(), salesInvoiceDetail.getTaxList(), false));
        }


        return result;
    }

    private List<InvoiceTaxDetailsDTO> calculateTaxSeparate(TaxGroupDTO taxGroupDTO, Double amount, List<InvoiceTaxDetailsDTO> taxDetailsList, boolean isAdd) {

        TaxGroup taxGroup = this.taxGroupService.get(taxGroupDTO.getId()).orElseThrow(() -> new EntityNotFoundException("Tax Group", taxGroupDTO.getId()));

        double taxValue1 = (amount * taxGroup.getFinTaxType_01().getTaxRate()) / 100;
        updateTaxList(
                taxDetailsList,
                this.mapper.getDTO(taxGroup.getFinTaxType_01(), FinTaxTypeDTO.class),
                taxValue1,
                isAdd);

        if (taxGroup.getTaxOperator_01() != null) {
            if (taxGroup.getTaxOperator_01() == TaxOperator.INCLUSIVE) {
                double taxValue12 = ((taxValue1 + amount) * taxGroup.getFinTaxType_02().getTaxRate()) / 100;
                updateTaxList(taxDetailsList,
                        this.mapper.getDTO(taxGroup.getFinTaxType_02(), FinTaxTypeDTO.class),
                        taxValue12,
                        isAdd);

            } else if (taxGroup.getTaxOperator_01() == TaxOperator.EXCLUSIVE) {
                double taxValue12 = (amount * taxGroup.getFinTaxType_02().getTaxRate()) / 100;
                updateTaxList(
                        taxDetailsList,
                        this.mapper.getDTO(taxGroup.getFinTaxType_02(), FinTaxTypeDTO.class),
                        taxValue12,
                        isAdd);
            }
        }

        return taxDetailsList;
    }

    private void updateTaxList(List<InvoiceTaxDetailsDTO> taxDetailsList, FinTaxTypeDTO finTaxTypeDTO, double taxAmount, boolean isAdd) {
        taxAmount = new BigDecimal(taxAmount).setScale(2, RoundingMode.HALF_EVEN).doubleValue();
        boolean isExisting = false;
        for (InvoiceTaxDetailsDTO dto : taxDetailsList) {
            if (dto.getFinTaxType().getId().equals(finTaxTypeDTO.getId())) {
                if (isAdd) {
                    dto.setTaxAmount(dto.getTaxAmount() + taxAmount);
                } else {
                    dto.setTaxAmount(dto.getTaxAmount() - taxAmount);
                }
                isExisting = true;
                break;
            }
        }
        if (!isExisting && isAdd) {
            //For new Tax type
            InvoiceTaxDetailsDTO taxDetailsDTO = new InvoiceTaxDetailsDTO();
            taxDetailsDTO.setFinTaxType(finTaxTypeDTO);
            taxDetailsDTO.setTaxAmount(taxAmount);
            taxDetailsList.add(taxDetailsDTO);
        }
    }
}
