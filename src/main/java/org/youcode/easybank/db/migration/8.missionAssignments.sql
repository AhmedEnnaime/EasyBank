CREATE TABLE missionAssignments (
    id SERIAL PRIMARY KEY,
    debut_date TIMESTAMP,
    end_date TIMESTAMP,
    employee_matricule INT REFERENCES employees(matricule),
    mission_code INT REFERENCES missions(code)
);