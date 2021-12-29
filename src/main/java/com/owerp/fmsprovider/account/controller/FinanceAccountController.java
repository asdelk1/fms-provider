package com.owerp.fmsprovider.account.controller;

import com.owerp.fmsprovider.account.model.data.BookEntry;
import com.owerp.fmsprovider.account.model.data.StandingJe;
import com.owerp.fmsprovider.account.model.dto.BookEntryDTO;
import com.owerp.fmsprovider.account.model.dto.StandingJeDTO;
import com.owerp.fmsprovider.account.service.FinanceAccountService;
import com.owerp.fmsprovider.customer.data.dto.DocumentApproveDTO;
import com.owerp.fmsprovider.customer.data.enums.DocApproveType;
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

    @GetMapping("/journal-entries/{id}")
    public ResponseEntity<ApiResponse> getJournalEntry(@PathVariable long id) {
        ApiResponse res = new ApiResponse(HttpStatus.OK, this.mapper.getDTO(this.service.getJournalEntry(id), BookEntryDTO.class));
        return ResponseEntity.ok(res);
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

    @GetMapping("/standing")
    public ResponseEntity<ApiResponse> listStandingJournalEntries() {
        List<StandingJe> list = this.service.getAllStandingJournalEntries();
        return ResponseEntity.ok(new ApiResponse(HttpStatus.OK, list.stream().map(e -> this.mapper.getDTO(e, StandingJeDTO.class)).collect(Collectors.toList())));
    }

    @GetMapping("/standing/{id}")
    public ResponseEntity<ApiResponse> getStandingJournalEntries(@PathVariable long id) {
        BookEntryDTO entry = this.mapper.getDTO(this.service.getBookEntryFromStandingJE(id), BookEntryDTO.class);
        entry.setSaveAsSje(true);
        return ResponseEntity.ok(new ApiResponse(HttpStatus.OK, entry));
    }

    @GetMapping("/to-approve")
    public ResponseEntity<ApiResponse> listAllEntriesToApprove() {
        // TODO: add permission settings
        List<BookEntry> list = this.service.getAllEntriesForOperations(DocApproveType.CHECKED);
        ApiResponse res = new ApiResponse(HttpStatus.OK, this.map(list));
        return ResponseEntity.ok(res);
    }

    @GetMapping("/to-check")
    public ResponseEntity<ApiResponse> listAllEntriesToCheck() {
        // TODO: add permission settings
        List<BookEntry> list = this.service.getAllEntriesForOperations(DocApproveType.NONE);
        list.addAll(this.service.getAllEntriesForOperations(DocApproveType.APPROVE_REJECTED));
        ApiResponse res = new ApiResponse(HttpStatus.OK, this.map(list));
        return ResponseEntity.ok(res);
    }

    @PostMapping("/check")
    public ResponseEntity<ApiResponse> checkEntry(@RequestBody DocumentApproveDTO dto) {
        BookEntry entry = this.service.checkEntry(dto, false);
        return ResponseEntity.ok(new ApiResponse(HttpStatus.OK, this.map(entry)));
    }

    @PostMapping("/check-reject")
    public ResponseEntity<ApiResponse> checkRejectEntry(@RequestBody DocumentApproveDTO dto) {
        BookEntry entry = this.service.checkEntry(dto, true);
        return ResponseEntity.ok(new ApiResponse(HttpStatus.OK, this.map(entry)));
    }

    @PostMapping("/approve")
    public ResponseEntity<ApiResponse> approveEntry(@RequestBody DocumentApproveDTO dto) {
        BookEntry entry = this.service.approveEntry(dto, false);
        return ResponseEntity.ok(new ApiResponse(HttpStatus.OK, this.map(entry)));
    }

    @PostMapping("/approve-reject")
    public ResponseEntity<ApiResponse> approveRejectEntry(@RequestBody DocumentApproveDTO dto) {
        BookEntry entry = this.service.checkEntry(dto, true);
        return ResponseEntity.ok(new ApiResponse(HttpStatus.OK, this.map(entry)));
    }

    private List<BookEntryDTO> map(List<BookEntry> list) {
        return list.stream().map(e -> this.mapper.getDTO(e, BookEntryDTO.class)).collect(Collectors.toList());
    }

    private BookEntryDTO map(BookEntry entry){
        return this.mapper.getDTO(entry, BookEntryDTO.class);
    }
}
