package org.youcode.easybank.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Payment {
    private Integer id;

    private LocalDateTime transaction_time;

    private Account from_account;

    private Account to_account;

    private Operation operation;
}
