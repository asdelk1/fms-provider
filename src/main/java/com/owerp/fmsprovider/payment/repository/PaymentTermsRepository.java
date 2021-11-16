package com.owerp.fmsprovider.payment.repository;

import com.owerp.fmsprovider.payment.model.data.PaymentTerms;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentTermsRepository extends JpaRepository<PaymentTerms, Long> {
}
