-- ========================
-- Role permissions
-- ========================
INSERT INTO `permission` (`id`, `name`, `key`, `description`)
VALUES (UUID(), 'Create role', 'role.create', 'Allows creating a role.'),
       (UUID(), 'View role', 'role.read', 'Allows viewing the list and details of roles.'),
       (UUID(), 'Update role', 'role.update', 'Allows updating a role.'),
       (UUID(), 'Grant permission to role', 'role.permission.grant', 'Allows granting a permission to a role.'),
       (UUID(), 'Revoke permission from role', 'role.permission.revoke', 'Allows revoking a permission from a role.'),
       (UUID(), 'Delete role', 'role.delete', 'Allows deleting a role.');

-- ========================
-- Account permissions
-- ========================
INSERT INTO `permission` (`id`, `name`, `key`, `description`)
VALUES (UUID(), 'Assign role to account', 'account.role.assign', 'Allows assigning a role to an account.'),
       (UUID(), 'Revoke role from account', 'account.role.revoke', 'Allows revoking a role from an account.');

-- ========================
-- Permission management
-- ========================
INSERT INTO `permission` (`id`, `name`, `key`, `description`)
VALUES (UUID(), 'View permission', 'permission.read', 'Allows viewing permissions.'),
       (UUID(), 'Update permission', 'permission.update', 'Allows updating a permission.'),
       (UUID(), 'Delete permission', 'permission.delete', 'Allows deleting a permission.');

