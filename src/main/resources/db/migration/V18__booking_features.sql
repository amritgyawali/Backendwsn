ALTER TABLE vendors ADD COLUMN commission DECIMAL(5,2) DEFAULT 15.00;

CREATE TABLE inquiries (
    id UUID PRIMARY KEY,
    customer_id UUID NOT NULL REFERENCES customers(id),
    vendor_id UUID NOT NULL REFERENCES vendors(id),
    service_type VARCHAR(100) NOT NULL,
    event_date DATE NOT NULL,
    budget NUMERIC(12,2),
    notes TEXT,
    status VARCHAR(20) NOT NULL CHECK (status IN('OPEN','RESPONDED','CLOSED','CONVERTED')),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Only add inquiry_id column if it doesn't exist
ALTER TABLE bookings 
    ADD COLUMN IF NOT EXISTS inquiry_id UUID REFERENCES inquiries(id);

-- Add commission column if it doesn't exist
ALTER TABLE vendors 
    ADD COLUMN IF NOT EXISTS commission FLOAT8 DEFAULT 15.00;