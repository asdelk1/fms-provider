package com.owerp.fmsprovider.account.repository;

import com.owerp.fmsprovider.account.model.data.BookEntryDetails;
import com.owerp.fmsprovider.customer.data.enums.EntryType;
import com.owerp.fmsprovider.customer.data.enums.RecType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface BookEntryDetailsRepository extends JpaRepository<BookEntryDetails, Long> {
    @Query("SELECT SUM(b.amount) FROM BookEntryDetails b WHERE b.entryType =:entryType AND b.ledgerAccount.id = :ledgerAccId AND b.bookEntry.entryDate <= :tillDate " +
            "AND b.bookEntry.authorized = 0 AND b.bookEntry.status = 1")
    Double getDebitOrCreditSum(@Param("ledgerAccId") final Long ledgerAccId, @Param("tillDate") final LocalDateTime tillDate, @Param("entryType") final EntryType entryType);

    @Query("SELECT b FROM BookEntryDetails b WHERE b.ledgerAccount.id = :ledgerAccId AND b.bookEntry.entryDate >= :startDate " +
            "AND b.bookEntry.entryDate <= :endDate AND b.bookEntry.authorized = 0 AND b.bookEntry.status = 1")
    List<BookEntryDetails> getEntryListForGl(@Param("ledgerAccId") final Long ledgerAccId, @Param("startDate") final LocalDateTime startDate, @Param("endDate") final LocalDateTime endDate);


    @Query("SELECT b FROM BookEntryDetails b WHERE b.entryType =:entryType AND b.bookEntry.id =:entryId ")
    List<BookEntryDetails> getOppAccounts(@Param("entryId") final Long entryId, @Param("entryType") final EntryType entryType);

    @Query("SELECT b FROM BookEntryDetails b WHERE b.ledgerAccount.id = :ledgerAccId AND b.bookEntry.entryDate <= :toDate " +
            "AND b.recType = :chk AND b.entryType =:entryType AND b.bookEntry.authorized = 0 AND b.bookEntry.status = 1")
    List<BookEntryDetails> getDetailsForBankRec(@Param("ledgerAccId") final Long ledgerAccId, @Param("toDate") final LocalDateTime toDate, @Param("chk") RecType recType, @Param("entryType") final EntryType entryType);

}
