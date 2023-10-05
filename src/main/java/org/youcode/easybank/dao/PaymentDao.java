package org.youcode.easybank.dao;

import org.youcode.easybank.entities.Payment;

import java.util.Optional;

public interface PaymentDao {

    public Optional<Payment> create(Payment payment);

    public boolean deleteAll();
}
