package org.youcode.easybank.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Payment extends Operation{

    private Integer id;

    private Account from_account;

    private Account to_account;

    public Payment(Operation operation, Account from_account, Account to_account) {
        super(operation);
        this.from_account = from_account;
        this.to_account = to_account;
    }
}
