package com.owerp.fmsprovider.account.service;

import com.owerp.fmsprovider.account.model.data.BookEntry;
import com.owerp.fmsprovider.account.model.data.BookEntryDetails;
import com.owerp.fmsprovider.account.model.data.StandingJe;
import com.owerp.fmsprovider.account.model.data.StandingJeDetails;
import com.owerp.fmsprovider.account.model.dto.BookEntryDTO;
import com.owerp.fmsprovider.account.model.dto.BookEntryDetailsDTO;
import com.owerp.fmsprovider.account.model.dto.StandingJeDTO;
import com.owerp.fmsprovider.account.model.dto.StandingJeDetailsDTO;
import com.owerp.fmsprovider.account.repository.BookEntryRepository;
import com.owerp.fmsprovider.account.repository.StandingJeRepository;
import com.owerp.fmsprovider.customer.data.dto.DocumentApproveDTO;
import com.owerp.fmsprovider.customer.data.enums.BookEntryType;
import com.owerp.fmsprovider.customer.data.enums.DocApproveType;
import com.owerp.fmsprovider.customer.data.model.PersonType;
import com.owerp.fmsprovider.customer.service.CustomerService;
import com.owerp.fmsprovider.helper.model.data.AccountingPeriod;
import com.owerp.fmsprovider.helper.service.AccountingPeriodService;
import com.owerp.fmsprovider.helper.service.CostCenterService;
import com.owerp.fmsprovider.ledger.service.LedgerAccountService;
import com.owerp.fmsprovider.supplier.model.enums.NumTypes;
import com.owerp.fmsprovider.supplier.service.FinNumbersService;
import com.owerp.fmsprovider.supplier.service.SupplierService;
import com.owerp.fmsprovider.system.advice.EntityNotFoundException;
import com.owerp.fmsprovider.system.model.dto.UserDTO;
import com.owerp.fmsprovider.system.service.UserService;
import com.owerp.fmsprovider.system.util.EntityModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class FinanceAccountService {

    private final BookEntryRepository repo;

    private final FinNumbersService finNumbersService;
    private final AccountingPeriodService accountingPeriodService;
    private final UserService userService;
    private final CostCenterService costCenterService;
    private final LedgerAccountService ledgerAccountService;
    private final CustomerService customerService;
    private final SupplierService supplierService;
    private final StandingJeRepository jeRepository;

    private final EntityModelMapper mapper;

    public FinanceAccountService(BookEntryRepository repo, FinNumbersService finNumbersService, AccountingPeriodService accountingPeriodService, UserService userService, CostCenterService costCenterService, LedgerAccountService ledgerAccountService, CustomerService customerService, SupplierService supplierService, StandingJeRepository jeRepository, EntityModelMapper mapper) {
        this.repo = repo;
        this.finNumbersService = finNumbersService;
        this.accountingPeriodService = accountingPeriodService;
        this.userService = userService;
        this.costCenterService = costCenterService;
        this.ledgerAccountService = ledgerAccountService;
        this.customerService = customerService;
        this.supplierService = supplierService;
        this.jeRepository = jeRepository;
        this.mapper = mapper;
    }

    public List<BookEntry> getAllJournalEntries() {
        return this.repo.getBookEntriesByBookEntryType(BookEntryType.JOURNAL_ENTRY);
    }

    public BookEntry getJournalEntry(long id) {
        return this.repo.findById(id).orElseThrow(() -> new EntityNotFoundException("Journal Entry", id));
    }

    public List<StandingJe> getAllStandingJournalEntries() {
        return this.jeRepository.findAll();
    }

    public BookEntry getBookEntryFromStandingJE(Long sjeId) {

        StandingJe standingJe = getStandingJournalEntry(sjeId);
        BookEntry bookEntry = new BookEntry();
        bookEntry.setId(null);
        bookEntry.setNote(standingJe.getNote());
        bookEntry.setEntryNumber(this.getJournalEntryNumber(null, true));
        bookEntry.setEntryDate(standingJe.getEntryDate());

        List<BookEntryDetails> detailsList = new ArrayList<>();
        for (StandingJeDetails entryDetails : standingJe.getBookEntryDetails()) {

            BookEntryDetails bookEntryDetails = new BookEntryDetails();
            bookEntryDetails.setLedgerAccount(entryDetails.getLedgerAccount());
            bookEntryDetails.setAmount(entryDetails.getAmount());
            bookEntryDetails.setEntryType(entryDetails.getEntryType());
            bookEntryDetails.setDetails(entryDetails.getDetails());
            bookEntryDetails.setCostCenter(entryDetails.getCostCenter());
            bookEntryDetails.setPersonType(entryDetails.getPersonType());
            bookEntryDetails.setCustomer(entryDetails.getCustomer());
            bookEntryDetails.setSupplier(entryDetails.getSupplier());
            bookEntryDetails.setBookEntry(bookEntry);
            detailsList.add(bookEntryDetails);
        }
        bookEntry.setBookEntryDetails(detailsList);
        return bookEntry;
    }

    public BookEntry get(Long id) {
        return this.repo.findById(id).orElseThrow(() -> new EntityNotFoundException("Journal Entry", id));
    }

    public String getJournalEntryNumber(Long bookEntryId, Boolean isDisplayOnly) {
        /*JE - current year - sequence number*/
        AccountingPeriod accountingPeriod = this.accountingPeriodService.getLatestPeriod();
        int currentYear = accountingPeriod.getStartDate().getYear();

        if (bookEntryId == null) {
            Long nextNumber = finNumbersService.getNextNumber(NumTypes.JOURNAL_ENTRY, isDisplayOnly);
            return "JE" + "-" + currentYear + "-" + nextNumber;
        } else {
            /* for existing Invoice, if customer type change only change the type Code and keep the Number*/
            String JeNumber = this.get(bookEntryId).getEntryNumber();
            String existingNumber = JeNumber.split("-")[2];
            return "JE" + "-" + currentYear + "-" + existingNumber;
        }

    }

    public StandingJe getStandingJournalEntry(long id) {
        return this.jeRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Standing Journal Entry", id));
    }

    public List<BookEntry> getAllEntriesForOperations(DocApproveType operation) {
        return this.repo.getBookEntriesByDocApproveTypeAndBookEntryType(operation, BookEntryType.JOURNAL_ENTRY);
    }

    public BookEntry addBookEntry(BookEntryDTO dto) {
//        BookEntry entry = this.mapper.getEntity(dto, BookEntry.class);
        BookEntry entry;

        if (dto.getId() == null) { // get Number for new invoice
            entry = new BookEntry();
            dto.setEntryNumber(getJournalEntryNumber(null, false));
        } else {
            entry = this.get(dto.getId());
            entry.setEntryNumber(dto.getEntryNumber());
        }
        entry.setEntryNumber(dto.getEntryNumber());
        entry.setEntryDate(dto.getEntryDate());
        entry.setNote(dto.getNote());
        entry.setBookEntryType(BookEntryType.JOURNAL_ENTRY);
        entry.setDocApproveType(DocApproveType.NONE);

        entry.setEnteredBy(this.userService.getLoggedInUser());
        entry.setEnteredOn(LocalDateTime.now());

        List<BookEntryDetails> detailsList = new ArrayList<>();
        for (BookEntryDetailsDTO bedDTO : dto.getBookEntryDetails()) {

            BookEntryDetails bookEntryDetails = new BookEntryDetails();
            this.ledgerAccountService.get(bedDTO.getLedgerAccount().getId()).ifPresent(bookEntryDetails::setLedgerAccount);
            bookEntryDetails.setAmount(bedDTO.getAmount());
            bookEntryDetails.setEntryType(bedDTO.getEntryType());
            bookEntryDetails.setDetails(bedDTO.getDetails());
            if (bedDTO.getCostCenter() != null) {
                this.costCenterService.get(bedDTO.getCostCenter().getId()).ifPresent(bookEntryDetails::setCostCenter);
            }
            bookEntryDetails.setPersonType(bedDTO.getPersonType());
            if (bedDTO.getPersonType() == PersonType.CUSTOMER) {
                this.customerService.get(bedDTO.getCustomer().getId()).ifPresent(bookEntryDetails::setCustomer);
            } else if (bedDTO.getPersonType() == PersonType.SUPPLIER) {
                this.supplierService.get(bedDTO.getSupplier().getId()).ifPresent(bookEntryDetails::setSupplier);
            }

            bookEntryDetails.setBookEntry(entry);

            detailsList.add(bookEntryDetails);
        }

        entry.getBookEntryDetails().clear();
        entry.getBookEntryDetails().addAll(detailsList);
        entry = this.repo.save(entry);

        if (dto.isSaveAsSje()) {
            saveStandingJE(entry);
        }
        return entry;
    }

    private void saveStandingJE(BookEntry bookEntry) {
        StandingJe standingJe = new StandingJe();
        standingJe.setEntryNumber(bookEntry.getEntryNumber());
        standingJe.setEntryDate(bookEntry.getEntryDate());
        standingJe.setNote(bookEntry.getNote());
        standingJe.setBookEntryType(BookEntryType.JOURNAL_ENTRY);
        standingJe.setDocApproveType(DocApproveType.NONE);
        //Creator
        standingJe.setEnteredBy(bookEntry.getEnteredBy());
        standingJe.setEnteredOn(LocalDateTime.now());

        List<StandingJeDetails> detailsList = new ArrayList<StandingJeDetails>();
        for (BookEntryDetails bedDTO : bookEntry.getBookEntryDetails()) {

            StandingJeDetails standingDetails = new StandingJeDetails();
            standingDetails.setLedgerAccount(bedDTO.getLedgerAccount());
            standingDetails.setAmount(bedDTO.getAmount());
            standingDetails.setEntryType(bedDTO.getEntryType());
            standingDetails.setDetails(bedDTO.getDetails());
            standingDetails.setCostCenter(bedDTO.getCostCenter());
            standingDetails.setPersonType(bedDTO.getPersonType());
            standingDetails.setCustomer(bedDTO.getCustomer());
            standingDetails.setSupplier(bedDTO.getSupplier());
            standingDetails.setBookEntry(standingJe);
            detailsList.add(standingDetails);
        }

        standingJe.getBookEntryDetails().clear();
        standingJe.getBookEntryDetails().addAll(detailsList);
        this.jeRepository.save(standingJe);
    }

    public BookEntry checkEntry(DocumentApproveDTO dto, boolean isReject) {
        BookEntry entry = this.get(dto.getEntryId());
        DocApproveType approveType = !isReject ? DocApproveType.CHECKED : DocApproveType.CHECK_REJECTED;
        entry.setDocApproveType(approveType);
        entry.setCheckerNote(dto.getNote());

        entry.setCheckedBy(this.userService.getLoggedInUser());
        entry.setCheckedOn(LocalDateTime.now());

        entry = this.repo.save(entry);

        // TODO: send an email
        // sendInternalNotificationForCustomerActions();

        return entry;
    }

    public BookEntry approveEntry(DocumentApproveDTO dto, boolean isReject) {
        BookEntry entry = this.get(dto.getEntryId());
        DocApproveType approveType = !isReject ? DocApproveType.APPROVED : DocApproveType.APPROVE_REJECTED;
        entry.setDocApproveType(approveType);
        entry.setApproverNote(dto.getNote());

        entry.setAuthorizedBy(this.userService.getLoggedInUser());
        entry.setAuthorizedOn(LocalDateTime.now());

        entry = this.repo.save(entry);

        // TODO: send an email
        // sendInternalNotificationForCustomerActions();

        return entry;
    }


//    public void sendInternalNotificationForCustomerActions(String menuItem, UserNotificationTypes userNotificationTypes, String subject, String message,
//                                                           boolean isSentEmail, Long commonId, String actionUrl){
//        //Get the User list who have the permission for selected action (Menu item)
//        List<UserDTO> userList = menuService.getUsersHasPermission(menuItem);
//
//        if(userList.size() > 0) {
//            UserNotificationDTO userNotificationDTO = new UserNotificationDTO();
//            userNotificationDTO.setNotificationType(userNotificationTypes);
//            userNotificationDTO.setSubject(subject);
//            userNotificationDTO.setMessage(message);
//            userNotificationDTO.setSentEmail(isSentEmail);
//            userNotificationDTO.setCommonId(commonId);
//            notificationService.addBulkUserNotification(userList, userNotificationDTO, actionUrl);
//        }
//    }
}
