package com.owerp.fmsprovider.payment.model.data;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "fin_payment_method", uniqueConstraints = {@UniqueConstraint(name = "payment_method_constraint", columnNames = {"paymentMethod"})})
@Getter
@Setter
public class PaymentMethod {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String paymentMethod;
    private String description;
    private Boolean status = true;
}
