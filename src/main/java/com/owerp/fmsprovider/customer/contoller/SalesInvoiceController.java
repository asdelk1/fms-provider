package com.owerp.fmsprovider.customer.contoller;

import com.owerp.fmsprovider.customer.data.dto.SalesInvoiceDTO;
import com.owerp.fmsprovider.customer.service.SalesInvoiceService;
import com.owerp.fmsprovider.system.model.dto.ApiResponse;
import com.owerp.fmsprovider.system.util.EntityModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/sales-invoices")
public class SalesInvoiceController {

    private final SalesInvoiceService service;
    private final EntityModelMapper mapper;

    public SalesInvoiceController(SalesInvoiceService service, EntityModelMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @GetMapping
    public ResponseEntity<ApiResponse> getAll(){
        List<SalesInvoiceDTO> list = this.service.getAll().stream()
                .map((s) -> this.mapper.getDTO(s, SalesInvoiceDTO.class))
                .collect(Collectors.toList());
        return ResponseEntity.ok(new ApiResponse(HttpStatus.OK, list));
    }

//    @GetMapping("/{customerTypeId}/{salesInvoiceId}/new-invoice-no")
//    public ResponseEntity<ApiResponse> getNewInvoiceNo(@PathVariable long customerTypeId, @PathVariable long salesInvoiceId){
//
//    }
}
