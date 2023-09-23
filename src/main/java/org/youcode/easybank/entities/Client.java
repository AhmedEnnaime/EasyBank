package org.youcode.easybank.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Client extends Person{
    private int _code;

    private List<Account> _accounts;

    private Employee _employee;

    public Client(String lastName, String firstName, LocalDate birthDate, String phone, String address, int code, List<Account> accounts, Employee employee) {
        super(lastName, firstName, birthDate, phone, address);
        this._code = code;
        this._accounts = accounts;
        this._employee = employee;
    }

    public Client(String lastName, String firstName, LocalDate birthDate, String phone, String address) {
        super(lastName, firstName, birthDate, phone, address);
    }

    public Client(String lastName, String firstName, LocalDate birthDate, String phone, String address, Employee employee) {
        super(lastName, firstName, birthDate, phone, address);
        this._employee = employee;
    }

}
