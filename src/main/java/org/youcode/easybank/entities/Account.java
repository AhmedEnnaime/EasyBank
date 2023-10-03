package org.youcode.easybank.entities;

import lombok.*;
import org.youcode.easybank.enums.STATUS;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Account {
    protected int _accountNumber;

    protected double _balance;

    protected LocalDate _creationDate;

    protected STATUS _status;

    protected Employee _employee;

    protected Client _client;

    protected List<Operation> _operations;

    protected Agency _agency;

    public Account(double balance, Employee employee, Client client, Agency agency) {
        this._balance = balance;
        this._employee = employee;
        this._client = client;
        this._agency = agency;
    }

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
        this._agency = account.get_agency();
    }

    public Account(double balance) {
        this._balance = balance;
    }
}
