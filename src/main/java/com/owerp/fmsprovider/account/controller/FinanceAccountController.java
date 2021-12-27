package com.owerp.fmsprovider.account.controller;

import com.owerp.fmsprovider.account.model.data.BookEntry;
import com.owerp.fmsprovider.account.model.dto.BookEntryDTO;
import com.owerp.fmsprovider.account.service.FinanceAccountService;
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
@RequestMapping("/finance-accounts")
public class FinanceAccountController {

    private final FinanceAccountService service;
    private final EntityModelMapper mapper;

    public FinanceAccountController(FinanceAccountService service, EntityModelMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @GetMapping("/journal-entries")
    public ResponseEntity<ApiResponse> getJournalEntries() {
        List<BookEntry> entries = this.service.getAllJournalEntries();
        return ResponseEntity.ok(new ApiResponse(HttpStatus.OK, this.map(entries)));
    }

    @GetMapping("/new-journal-entry-number")
    public ResponseEntity<ApiResponse> getNewJournalEntryNumber() {
        String entryNumber = this.service.getJournalEntryNumber(null, true);
        Map<String, String> result = new HashMap<>();
        result.put("entryNumber", entryNumber);
        return ResponseEntity.ok(new ApiResponse(HttpStatus.OK, result));
    }


    @PostMapping
    public ResponseEntity<ApiResponse> createJournalEntry(@RequestBody BookEntryDTO dto) {
        BookEntry entry = this.service.addBookEntry(dto);
        return ResponseEntity.ok(new ApiResponse(HttpStatus.OK, this.mapper.getDTO(entry, BookEntryDTO.class)));
    }

//    @GetMapping("/journal-entry-item-detail")
//    public ResponseEntity<ApiResponse> getJournalEntryItemDetails(){
//
//    }

    private List<BookEntryDTO> map(List<BookEntry> list) {
        return list.stream().map(e -> this.mapper.getDTO(e, BookEntryDTO.class)).collect(Collectors.toList());
    }
}
