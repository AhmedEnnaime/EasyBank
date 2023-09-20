package org.youcode.easybank.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MissionAssignment {
    private LocalDate _debutDate;

    private LocalDate _endDate;

    private Employee _employee;

    private Mission _mission;
}
