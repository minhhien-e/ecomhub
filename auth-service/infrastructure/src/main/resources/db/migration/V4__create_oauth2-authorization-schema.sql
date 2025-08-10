CREATE TABLE oauth2_authorization
(
    id                            VARCHAR(100) NOT NULL,
    registered_client_id          VARCHAR(100) NOT NULL,
    principal_name                VARCHAR(200) NOT NULL,
    authorization_grant_type      VARCHAR(100) NOT NULL,
    authorized_scopes             VARCHAR(1000) DEFAULT NULL,

    attributes                    LONGTEXT      DEFAULT NULL,
    state                         VARCHAR(500)  DEFAULT NULL,

    authorization_code_value      LONGTEXT      DEFAULT NULL,
    authorization_code_issued_at  DATETIME      DEFAULT NULL,
    authorization_code_expires_at DATETIME      DEFAULT NULL,
    authorization_code_metadata   LONGTEXT      DEFAULT NULL,

    access_token_value            LONGTEXT      DEFAULT NULL,
    access_token_issued_at        DATETIME      DEFAULT NULL,
    access_token_expires_at       DATETIME      DEFAULT NULL,
    access_token_metadata         LONGTEXT      DEFAULT NULL,
    access_token_type             VARCHAR(100)  DEFAULT NULL,
    access_token_scopes           VARCHAR(1000) DEFAULT NULL,

    oidc_id_token_value           LONGTEXT      DEFAULT NULL,
    oidc_id_token_issued_at       DATETIME      DEFAULT NULL,
    oidc_id_token_expires_at      DATETIME      DEFAULT NULL,
    oidc_id_token_metadata        LONGTEXT      DEFAULT NULL,

    refresh_token_value           LONGTEXT      DEFAULT NULL,
    refresh_token_issued_at       DATETIME      DEFAULT NULL,
    refresh_token_expires_at      DATETIME      DEFAULT NULL,
    refresh_token_metadata        LONGTEXT      DEFAULT NULL,

    user_code_value               LONGTEXT      DEFAULT NULL,
    user_code_issued_at           DATETIME      DEFAULT NULL,
    user_code_expires_at          DATETIME      DEFAULT NULL,
    user_code_metadata            LONGTEXT      DEFAULT NULL,

    device_code_value             LONGTEXT      DEFAULT NULL,
    device_code_issued_at         DATETIME      DEFAULT NULL,
    device_code_expires_at        DATETIME      DEFAULT NULL,
    device_code_metadata          LONGTEXT      DEFAULT NULL,

    PRIMARY KEY (id)
);
