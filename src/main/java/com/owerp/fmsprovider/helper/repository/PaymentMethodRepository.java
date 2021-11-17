package com.owerp.fmsprovider.helper.repository;

import com.owerp.fmsprovider.helper.model.data.PaymentMethod;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentMethodRepository extends JpaRepository<PaymentMethod, Long> {

    List<PaymentMethod> findAllByStatusIsTrue();
}
