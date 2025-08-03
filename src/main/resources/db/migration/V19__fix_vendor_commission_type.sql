-- Fix commission column type in vendors table
ALTER TABLE vendors 
    ALTER COLUMN commission TYPE FLOAT8 USING commission::float8,
    ALTER COLUMN commission SET DEFAULT 15.00;
