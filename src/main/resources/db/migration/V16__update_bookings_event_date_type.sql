-- Update the event_date column in bookings table to use timestamp type
ALTER TABLE bookings
    ALTER COLUMN event_date TYPE TIMESTAMP WITHOUT TIME ZONE
    USING event_date::timestamp without time zone;
