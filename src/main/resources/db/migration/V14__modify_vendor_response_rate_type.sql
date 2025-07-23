-- Drop the existing column constraint if it exists
ALTER TABLE vendors
    ALTER COLUMN response_rate DROP DEFAULT,
    ALTER COLUMN response_rate DROP NOT NULL;

-- Update the column type to float8
ALTER TABLE vendors
    ALTER COLUMN response_rate TYPE float8 USING response_rate::float8,
    ALTER COLUMN response_rate SET DEFAULT 80.0,
    ALTER COLUMN response_rate SET NOT NULL;
