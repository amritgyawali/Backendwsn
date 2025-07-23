-- Update the created_at column in analytics table to use timestamp type
ALTER TABLE analytics
    ALTER COLUMN created_at TYPE TIMESTAMP WITHOUT TIME ZONE
    USING created_at::timestamp without time zone;
