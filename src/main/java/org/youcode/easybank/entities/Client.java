package org.youcode.easybank.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Client extends Person{
    private int _code;

    private Account _account;

    public Client(String lastName, String firstName, LocalDate birthDate, String phone, String address, int code, Account account) {
        super(lastName, firstName, birthDate, phone, address);
        this._code = code;
        this._account = account;
    }

}
