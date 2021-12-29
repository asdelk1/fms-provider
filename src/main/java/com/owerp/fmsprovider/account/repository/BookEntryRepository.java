package com.owerp.fmsprovider.account.repository;

import com.owerp.fmsprovider.customer.data.enums.BookEntryType;
import com.owerp.fmsprovider.customer.data.enums.DocApproveType;
import com.owerp.fmsprovider.account.model.data.BookEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookEntryRepository extends JpaRepository<BookEntry, Long> {

    List<BookEntry> getBookEntriesByBookEntryType(BookEntryType bookEntryType);
    List<BookEntry> getBookEntriesByDocApproveType(DocApproveType docApproveType);
    List<BookEntry> getBookEntriesByDocApproveTypeAndBookEntryType(DocApproveType docApproveType, BookEntryType bookEntryType);
}
