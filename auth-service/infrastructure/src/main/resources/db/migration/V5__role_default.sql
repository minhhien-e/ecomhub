-- 1. Tạo role ADMIN và CUSTOMER
INSERT INTO role (id, name, `key`, status, type, description, level)
VALUES (UUID_TO_BIN(UUID()), 'Administrator', 'ADMIN', 'ACTIVE', 'SYSTEM', 'System administrator role', 999),
       (UUID_TO_BIN(UUID()), 'Customer', 'CUSTOMER', 'ACTIVE', 'USER', 'Customer role', 1);

-- 2. Gán tất cả quyền cho ADMIN
INSERT INTO role_permission (role_id, permission_id)
SELECT r.id, p.id
FROM role r
         JOIN permission p ON 1=1
WHERE r.`key` = 'ADMIN';
