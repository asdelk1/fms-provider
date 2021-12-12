package com.owerp.fmsprovider.customer.contoller;

import com.owerp.fmsprovider.customer.data.dto.SalesInvoiceDTO;
import com.owerp.fmsprovider.customer.data.dto.SalesInvoiceDetailDTO;
import com.owerp.fmsprovider.customer.data.dto.SalesInvoiceItemDTO;
import com.owerp.fmsprovider.customer.data.model.SalesInvoice;
import com.owerp.fmsprovider.customer.service.SalesInvoiceService;
import com.owerp.fmsprovider.system.model.dto.ApiResponse;
import com.owerp.fmsprovider.system.util.EntityModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
    public ResponseEntity<ApiResponse> getAll() {
        List<SalesInvoiceDTO> list = this.service.getAll().stream()
                .map((s) -> this.mapper.getDTO(s, SalesInvoiceDTO.class))
                .collect(Collectors.toList());
        return ResponseEntity.ok(new ApiResponse(HttpStatus.OK, list));
    }

    @PostMapping()
    public ResponseEntity<ApiResponse> save(@RequestBody SalesInvoiceDTO dto){
        SalesInvoice invoice =  this.service.save(dto);
        return ResponseEntity.ok(new ApiResponse(HttpStatus.OK, this.mapper.getDTO(invoice, SalesInvoiceDTO.class)));
    }

    @GetMapping("/{customerTypeId}/{salesInvoiceId}/invoice-no")
    public ResponseEntity<ApiResponse> getNewInvoiceNo(@PathVariable long customerTypeId, @PathVariable long salesInvoiceId) {
        Long invoiceId = salesInvoiceId != 0 ? salesInvoiceId : null;
        final String salesInvoiceNo = this.service.getSalesInvoiceNumber(invoiceId, customerTypeId, true);
        Map<String, String> responseMap = new HashMap();
        responseMap.put("salesInvoiceNo", salesInvoiceNo);
        ApiResponse res = new ApiResponse(HttpStatus.OK, responseMap);
        return ResponseEntity.ok(res);
    }

    @PostMapping("/calculate-item-amount")
    public ResponseEntity<ApiResponse> getItemAmount(@RequestBody SalesInvoiceItemDTO dto) {
        Map<String, Double> result = this.service.calculateCustomerItemAmount(dto);
        return ResponseEntity.ok(new ApiResponse(HttpStatus.OK, result));
    }

    @PostMapping("/getSalesItemDetailsAndTax")
    public ResponseEntity<ApiResponse> getItemDetailsAndTax(@RequestBody SalesInvoiceDetailDTO salesInvoiceItemDTO) {
        SalesInvoiceDetailDTO detailDTO = this.service.getSalesItemDetailsAndTax(salesInvoiceItemDTO);
        return ResponseEntity.ok(new ApiResponse(HttpStatus.OK, detailDTO));
    }

    @PostMapping("/removeSalesItemDetailsAndTax")
    public ResponseEntity<ApiResponse> removeSalesItemDetailsAndTax(@RequestBody SalesInvoiceDetailDTO dto) {
        SalesInvoiceDetailDTO invoiceDetail = this.service.removeSalesItemDetailsAndTax(dto);
        return ResponseEntity.ok(new ApiResponse(HttpStatus.OK, invoiceDetail));
    }
}
