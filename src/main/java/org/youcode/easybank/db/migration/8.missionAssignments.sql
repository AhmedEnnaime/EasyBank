CREATE TABLE missionAssignments (
    debut_date TIMESTAMP,
    end_date TIMESTAMP,
    employee_matricule INT REFERENCES employees(matricule),
    mission_code INT PRIMARY KEY REFERENCES missions(code)
);