-- Add missing columns to vendors table
ALTER TABLE vendors
    ADD COLUMN IF NOT EXISTS active BOOLEAN DEFAULT TRUE NOT NULL,
    ADD COLUMN IF NOT EXISTS response_rate DECIMAL(5,2) DEFAULT 80.0 NOT NULL;
