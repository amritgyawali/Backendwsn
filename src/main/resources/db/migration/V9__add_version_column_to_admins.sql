-- Add version column to admins table for optimistic locking
ALTER TABLE admins ADD COLUMN version BIGINT NOT NULL DEFAULT 0;
