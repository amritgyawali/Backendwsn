-- Add version column to customers table for optimistic locking
ALTER TABLE customers ADD COLUMN version BIGINT NOT NULL DEFAULT 0;
