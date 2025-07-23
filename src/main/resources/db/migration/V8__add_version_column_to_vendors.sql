-- Add version column to vendors table for optimistic locking
ALTER TABLE vendors ADD COLUMN version BIGINT NOT NULL DEFAULT 0;
