package org.youcode.easybank.entities;

import lombok.*;
import org.youcode.easybank.enums.STATUS;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Account {
    private int _accountNumber;

    private double _balance;

    private LocalDate _creationDate;

    private STATUS _status;

    private Employee _employee;

    private Client _client;

    private List<Operation> _operations;

    public Account(double balance, Employee employee, Client client) {
        this._balance = balance;
        this._employee = employee;
        this._client = client;
    }

    public Account(Account account) {
        this._accountNumber = account.get_accountNumber();
        this._balance = account.get_balance();
        this._creationDate = account.get_creationDate();
        this._status = account.get_status();
        this._employee = account.get_employee();
        this._client = account.get_client();
        this._operations = account.get_operations();
    }

    public Account(double balance) {
        this._balance = balance;
    }
}
