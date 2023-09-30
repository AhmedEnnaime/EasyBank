CREATE TABLE simulations (
    id SERIAL PRIMARY KEY,
    monthly_payment DOUBLE PRECISION,
    borrowed_capital DOUBLE PRECISION,
    monthly_payment_num INT,
    state VARCHAR(255) DEFAULT 'PENDING',
    result DOUBLE PRECISION
)