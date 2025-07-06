ALTER TABLE account
    ADD CONSTRAINT uc_account_phonenumber UNIQUE (phone_number);
ALTER TABLE account
    MODIFY phone_number VARCHAR(255) NOT NULL;

CREATE INDEX idx_account_email ON account (email);

CREATE INDEX idx_account_phone ON account (phone_number);

CREATE INDEX idx_account_username ON account (username);

CREATE INDEX idx_permission_name ON permission (name);

CREATE INDEX idx_refresh_token_composite ON refresh_token (account_id, revoked, expires_at);

CREATE INDEX idx_refresh_token_expiry ON refresh_token (expires_at);

CREATE INDEX idx_refresh_token_revoked ON refresh_token (revoked);

CREATE INDEX idx_role_name ON `role` (name);

CREATE INDEX idx_refresh_token_account ON refresh_token (account_id);