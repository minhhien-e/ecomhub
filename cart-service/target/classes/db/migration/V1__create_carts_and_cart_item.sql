

CREATE TABLE carts (
                       id  BINARY(16) PRIMARY KEY,
                       note VARCHAR(255),
                       total_price DOUBLE DEFAULT 0,
                       created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE cart_item (
                           id  BINARY(16) PRIMARY KEY,
                           cart_id BINARY(16) NOT NULL,
                           variant_id BINARY(16),
                           quantity INT NOT NULL DEFAULT 1,
                           price DOUBLE NOT NULL,
                           created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                           FOREIGN KEY (cart_id) REFERENCES carts(id) ON DELETE CASCADE
);

CREATE INDEX idx_cart_id ON cart_item(cart_id);
