-- Fix bookings table structure
-- The bookings table already exists with correct structure from V10 migration
-- We just need to ensure foreign key constraints are properly set up

-- Step 1: Drop the dependent foreign key constraint from payments table if it exists
ALTER TABLE payments DROP CONSTRAINT IF EXISTS payments_booking_id_fkey;

-- Step 2: Ensure the foreign key constraint from payments to bookings exists
-- (The bookings table already has the correct UUID id from V10 migration)
ALTER TABLE payments ADD CONSTRAINT payments_booking_id_fkey
    FOREIGN KEY (booking_id) REFERENCES bookings(id);

-- Step 3: Ensure the foreign key constraint from bookings to customers exists
ALTER TABLE bookings DROP CONSTRAINT IF EXISTS fk_bookings_customer;
ALTER TABLE bookings ADD CONSTRAINT fk_bookings_customer
    FOREIGN KEY (customer_id) REFERENCES customers(id);