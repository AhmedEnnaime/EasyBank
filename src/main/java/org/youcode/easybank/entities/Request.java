package org.youcode.easybank.entities;

import org.youcode.easybank.enums.STATE;

import java.time.LocalDate;

public class Request {

    private Integer _number;

    private LocalDate _credit_date;

    private Double _amount;

    private STATE _state;

    private String _remarks;

    private String _duration;

    private Simulation _simulation;
}
