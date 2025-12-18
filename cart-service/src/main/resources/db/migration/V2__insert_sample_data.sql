-- Insert sample carts
INSERT INTO carts (id, note, total_price, created_at) VALUES
                                                          (UUID_TO_BIN(UUID()), 'Giỏ hàng của Hùng', 150000, NOW()),
                                                          (UUID_TO_BIN(UUID()), 'Giỏ hàng của Lan', 230000, NOW()),
                                                          (UUID_TO_BIN(UUID()), 'Giỏ hàng test', 0, NOW());

-- Insert sample cart_items
INSERT INTO cart_item (id, cart_id, variant_id, quantity, price, created_at)
VALUES
    (UUID_TO_BIN(UUID()),
     (SELECT id FROM carts LIMIT 1 OFFSET 0),
     UUID_TO_BIN(UUID()), 2, 75000, NOW()),

    (UUID_TO_BIN(UUID()),
     (SELECT id FROM carts LIMIT 1 OFFSET 1),
     UUID_TO_BIN(UUID()), 1, 230000, NOW()),

    (UUID_TO_BIN(UUID()),
     (SELECT id FROM carts LIMIT 1 OFFSET 2),
     UUID_TO_BIN(UUID()), 5, 0, NOW());
