CREATE TABLE account
(
    id              BINARY(16)   NOT NULL,
    username        VARCHAR(255) NOT NULL,
    email           VARCHAR(255) NOT NULL,
    phone_number    VARCHAR(255) NOT NULL,
    hashed_password VARCHAR(255) NOT NULL,
    provider        VARCHAR(255) NOT NULL,
    active          BIT(1)       NOT NULL,
    created_at      datetime     NOT NULL,
    updated_at      datetime     NOT NULL,
    CONSTRAINT pk_account PRIMARY KEY (id)
);

CREATE TABLE account_role
(
    account_id BINARY(16) NOT NULL,
    role_id    BINARY(16) NOT NULL,
    CONSTRAINT pk_account_role PRIMARY KEY (account_id, role_id)
);

CREATE TABLE permission
(
    id            BINARY(16)   NOT NULL,
    name          VARCHAR(100) NOT NULL,
    `key`         VARCHAR(100) NOT NULL,
    `description` LONGTEXT NULL,
    CONSTRAINT pk_permission PRIMARY KEY (id)
);

CREATE TABLE `role`
(
    id            BINARY(16)   NOT NULL,
    name          VARCHAR(100) NOT NULL,
    `key`         VARCHAR(100) NOT NULL,
    status        VARCHAR(50)  NOT NULL,
    type          VARCHAR(50)  NOT NULL,
    `description` LONGTEXT NULL,
    level         INT          NOT NULL,
    CONSTRAINT pk_role PRIMARY KEY (id)
);

CREATE TABLE role_permission
(
    role_id       BINARY(16) NOT NULL,
    permission_id BINARY(16) NOT NULL,
    CONSTRAINT pk_role_permission PRIMARY KEY (role_id, permission_id)
);

ALTER TABLE account
    ADD CONSTRAINT uc_account_email UNIQUE (email);

ALTER TABLE account
    ADD CONSTRAINT uc_account_phonenumber UNIQUE (phone_number);

ALTER TABLE account
    ADD CONSTRAINT uc_account_username UNIQUE (username);

ALTER TABLE permission
    ADD CONSTRAINT uc_permission_key UNIQUE (`key`);

ALTER TABLE permission
    ADD CONSTRAINT uc_permission_name UNIQUE (name);

ALTER TABLE `role`
    ADD CONSTRAINT uc_role_key UNIQUE (`key`);

ALTER TABLE `role`
    ADD CONSTRAINT uc_role_name UNIQUE (name);

ALTER TABLE `role`
    ADD CONSTRAINT uc_role_status UNIQUE (status);

ALTER TABLE `role`
    ADD CONSTRAINT uc_role_type UNIQUE (type);

CREATE INDEX idx_account_email ON account (email);

CREATE INDEX idx_account_phone ON account (phone_number);

CREATE INDEX idx_account_username ON account (username);

CREATE INDEX idx_permission_key ON permission (`key`);

CREATE INDEX idx_role_key ON `role` (`key`);

CREATE INDEX idx_role_status ON `role` (status);

ALTER TABLE account_role
    ADD CONSTRAINT FK_ACCOUNT_ROLE_ON_ACCOUNT FOREIGN KEY (account_id) REFERENCES account (id);

ALTER TABLE account_role
    ADD CONSTRAINT FK_ACCOUNT_ROLE_ON_ROLE FOREIGN KEY (role_id) REFERENCES `role` (id);

ALTER TABLE role_permission
    ADD CONSTRAINT FK_ROLE_PERMISSION_ON_PERMISSION FOREIGN KEY (permission_id) REFERENCES permission (id);

ALTER TABLE role_permission
    ADD CONSTRAINT FK_ROLE_PERMISSION_ON_ROLE FOREIGN KEY (role_id) REFERENCES `role` (id);