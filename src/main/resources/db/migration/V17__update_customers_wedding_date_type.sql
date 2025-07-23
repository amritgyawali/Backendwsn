-- Update the wedding_date column in customers table to use timestamp type
ALTER TABLE customers
    ALTER COLUMN wedding_date TYPE TIMESTAMP WITHOUT TIME ZONE
    USING wedding_date::timestamp without time zone;
