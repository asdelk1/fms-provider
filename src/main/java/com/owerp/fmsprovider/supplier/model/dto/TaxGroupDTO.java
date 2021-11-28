package com.owerp.fmsprovider.supplier.model.dto;

import com.owerp.fmsprovider.helper.model.dto.FinTaxTypeDTO;
import com.owerp.fmsprovider.helper.model.enums.TaxOperator;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TaxGroupDTO {

    private Long id;
    private String groupCode;
    private String description;
    private Boolean status = true;
    private TaxOperator taxOperator_01;
    private FinTaxTypeDTO finTaxType_01;
    private FinTaxTypeDTO finTaxType_02;
}
