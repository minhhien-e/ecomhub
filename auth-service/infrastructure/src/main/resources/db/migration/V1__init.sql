CREATE TABLE account
(
    id            BINARY(16)   NOT NULL,
    username      VARCHAR(255) NOT NULL,
    email         VARCHAR(255) NOT NULL,
    phone_number  VARCHAR(255) NULL,
    password_hash VARCHAR(255) NOT NULL,
    provider      VARCHAR(255) NOT NULL,
    active        BIT(1)       NOT NULL,
    created_at    datetime     NOT NULL,
    updated_at    datetime     NOT NULL,
    CONSTRAINT pk_account PRIMARY KEY (id)
);

CREATE TABLE account_permission
(
    account_id    BINARY(16) NOT NULL,
    permission_id BINARY(16) NOT NULL,
    CONSTRAINT pk_account_permission PRIMARY KEY (account_id, permission_id)
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
    `description` LONGTEXT NULL,
    CONSTRAINT pk_permission PRIMARY KEY (id)
);

CREATE TABLE refresh_token
(
    id         BINARY(16)   NOT NULL,
    token      VARCHAR(255) NOT NULL,
    user_agent VARCHAR(255) NOT NULL,
    ip_address VARCHAR(255) NOT NULL,
    issued_at  datetime     NOT NULL,
    expires_at datetime     NOT NULL,
    revoked    BIT(1)       NOT NULL,
    revoked_at datetime NULL,
    revoked_by BINARY(16)   NULL,
    account_id BINARY(16)   NOT NULL,
    CONSTRAINT pk_refresh_token PRIMARY KEY (id)
);

CREATE TABLE `role`
(
    id            BINARY(16)   NOT NULL,
    name          VARCHAR(100) NOT NULL,
    `description` LONGTEXT NULL,
    CONSTRAINT pk_role PRIMARY KEY (id)
);

ALTER TABLE account
    ADD CONSTRAINT uc_account_email UNIQUE (email);

ALTER TABLE account
    ADD CONSTRAINT uc_account_username UNIQUE (username);

ALTER TABLE permission
    ADD CONSTRAINT uc_permission_name UNIQUE (name);

ALTER TABLE refresh_token
    ADD CONSTRAINT uc_refresh_token_token UNIQUE (token);

ALTER TABLE `role`
    ADD CONSTRAINT uc_role_name UNIQUE (name);

ALTER TABLE account_permission
    ADD CONSTRAINT FK_ACCOUNT_PERMISSION_ON_ACCOUNT FOREIGN KEY (account_id) REFERENCES account (id);

ALTER TABLE account_permission
    ADD CONSTRAINT FK_ACCOUNT_PERMISSION_ON_PERMISSION FOREIGN KEY (permission_id) REFERENCES permission (id);

ALTER TABLE account_role
    ADD CONSTRAINT FK_ACCOUNT_ROLE_ON_ACCOUNT FOREIGN KEY (account_id) REFERENCES account (id);

ALTER TABLE account_role
    ADD CONSTRAINT FK_ACCOUNT_ROLE_ON_ROLE FOREIGN KEY (role_id) REFERENCES `role` (id);

ALTER TABLE refresh_token
    ADD CONSTRAINT FK_REFRESH_TOKEN_ON_ACCOUNT FOREIGN KEY (account_id) REFERENCES account (id);