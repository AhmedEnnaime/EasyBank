package org.youcode.easybank.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.youcode.easybank.enums.OPERATION;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SimpleOperation extends Operation {

    private OPERATION _type;

    private Account account;

    public SimpleOperation(Operation operation, OPERATION type, Account account) {
        super(operation);
        this._type = type;
        this.account = account;
    }

}
