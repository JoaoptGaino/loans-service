-- Alter loanType column to unique
ALTER TABLE loan ALTER COLUMN type SET NOT NULL;
ALTER TABLE loan ADD CONSTRAINT type_unique UNIQUE (type);