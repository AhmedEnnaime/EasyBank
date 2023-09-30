CREATE TABLE credits (
    number SERIAL PRIMARY KEY,
    credit_date DATE DEFAULT CURRENT_DATE,
    amount DOUBLE PRECISION,
    remarks VARCHAR(255),
    duration VARCHAR(255),
    simulation_id INT,
    FOREIGN KEY (simulation_id) REFERENCES simulations(id),
)