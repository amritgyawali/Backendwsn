-- Add version column to user_profiles table for optimistic locking
ALTER TABLE user_profiles ADD COLUMN version BIGINT DEFAULT 0 NOT NULL;

-- Update existing records to have version 0
UPDATE user_profiles SET version = 0 WHERE version IS NULL;
