package org.youcode.easybank.services;

import org.youcode.easybank.dao.daoImpl.OperationDaoImpl;
import org.youcode.easybank.dao.daoImpl.PaymentDaoImpl;
import org.youcode.easybank.entities.Operation;
import org.youcode.easybank.entities.Payment;
import org.youcode.easybank.exceptions.OperationException;

import java.util.Optional;

public class PaymentService {

    private PaymentDaoImpl paymentDao;

    private OperationDaoImpl operationDao;
    public PaymentService(PaymentDaoImpl paymentDao, OperationDaoImpl operationDao) {
        this.paymentDao = paymentDao;
        this.operationDao = operationDao;
    }

    public Payment createPayment(Payment payment, Operation operation) throws Exception {
        if (payment.getFrom_account() == null || payment.getTo_account() == null || payment.get_employee() == null) {
            return null;
        }else {
            Optional<Operation> optionalOperation = operationDao.create(operation);
            if (optionalOperation.isPresent()) {
                if (payment.get_amount() > payment.getFrom_account().get_balance()) {
                    throw new Exception("Amount insufficient in your account");
                }
                Optional<Payment> optionalPayment = paymentDao.create(payment);
                return optionalPayment.get();
            }else {
                return null;
            }
        }
    }
}
