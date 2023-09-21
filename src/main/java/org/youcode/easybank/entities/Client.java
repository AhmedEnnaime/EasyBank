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

    public Client(String lastName, String firstName, LocalDate birthDate, String phone, String address, int code, List<Account> accounts) {
        super(lastName, firstName, birthDate, phone, address);
        this._code = code;
        this._accounts = accounts;
    }

}
