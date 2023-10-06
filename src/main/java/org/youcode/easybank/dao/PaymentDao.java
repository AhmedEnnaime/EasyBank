package org.youcode.easybank.dao;

import org.youcode.easybank.entities.Account;
import org.youcode.easybank.entities.Payment;

import java.util.Optional;

public interface PaymentDao {

    public Optional<Payment> create(Payment payment);

    public boolean updateDestinationBalance(Payment payment);

    public boolean updateFromAccountBalance(Payment payment);

    public boolean delete(Integer id);

    public boolean deleteAll();
}
