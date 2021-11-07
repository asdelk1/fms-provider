package com.owerp.fmsprovider.payment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.owerp.fmsprovider.payment.model.data.PaymentMethod;

public interface PaymentMethodRepository extends JpaRepository<PaymentMethod, Long> {
}
