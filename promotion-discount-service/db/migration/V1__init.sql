-- V1__init.sql: Tạo table promotion_discount
CREATE TABLE IF NOT EXISTS promotion_discount (
    id BINARY(16) NOT NULL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    code VARCHAR(20) UNIQUE NOT NULL,
    description TEXT,
    discount_type ENUM('PERCENTAGE', 'FIXED') NOT NULL,
    discount_value DOUBLE NOT NULL,
    start_date TIMESTAMP NOT NULL,
    end_date TIMESTAMP NOT NULL,
    status ENUM('ACTIVE', 'INACTIVE') NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
    );