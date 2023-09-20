package org.youcode.easybank.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.text.SimpleDateFormat;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Client {
    private int _code;

    private String _lastName;

    private String _firstName;

    private SimpleDateFormat _birthDate;

    private String _phone;

    private String _address;

    private Account _account;
}
