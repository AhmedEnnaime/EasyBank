package org.youcode.easybank.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MissionAssignment {
    private LocalDate _debutDate;

    private LocalDate _endDate;

    private List<Employee> _employees;

    private Mission _mission;
}
