CREATE TABLE transfers (
    id SERIAL PRIMARY KEY,
    transfer_date DATE,
    employee_matricule INT,
    agency_code INT,
    FOREIGN KEY (agency_code) REFERENCES agencies(code),
    FOREIGN KEY (employee_matricule) REFERENCES employees(matricule)
)