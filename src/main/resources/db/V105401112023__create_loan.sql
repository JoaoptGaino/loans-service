--Create loan sql
CREATE TABLE IF NOT EXISTS loans(
    id uuid DEFAULT uuid_generate_v4() PRIMARY KEY,
    type VARCHAR(255),
    interestRate INTEGER,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);