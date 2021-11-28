package com.owerp.fmsprovider.helper.repository;

import com.owerp.fmsprovider.helper.model.data.PaymentTerms;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentTermsRepository extends JpaRepository<PaymentTerms, Long> {

    List<PaymentTerms> findAllByStatusIsTrue();
}
