ALTER TABLE permission
    ADD `key` VARCHAR(100) NULL;

ALTER TABLE permission
    MODIFY `key` VARCHAR (100) NOT NULL;

ALTER TABLE permission
    ADD CONSTRAINT uc_permission_key UNIQUE (`key`);

CREATE INDEX idx_permission_key ON permission (`key`);