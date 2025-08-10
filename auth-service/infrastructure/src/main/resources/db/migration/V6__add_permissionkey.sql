ALTER TABLE permission
    ADD `key` VARCHAR(100) NULL;

ALTER TABLE permission
    ADD CONSTRAINT uc_permission_key UNIQUE (`key`);
