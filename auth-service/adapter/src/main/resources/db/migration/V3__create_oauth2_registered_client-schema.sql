CREATE TABLE oauth2_registered_client
(
    id                            VARCHAR(100) NOT NULL,
    client_id                     VARCHAR(100) NOT NULL,
    client_id_issued_at           DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    client_secret                 VARCHAR(200)          DEFAULT NULL,
    client_secret_expires_at      DATETIME              DEFAULT NULL,
    client_name                   VARCHAR(200) NOT NULL,

    client_authentication_methods TEXT         NOT NULL,
    authorization_grant_types     TEXT         NOT NULL,
    redirect_uris                 TEXT                  DEFAULT NULL,
    post_logout_redirect_uris     TEXT                  DEFAULT NULL,
    scopes                        TEXT         NOT NULL,
    client_settings               TEXT         NOT NULL,
    token_settings                TEXT         NOT NULL,

    PRIMARY KEY (id),
    UNIQUE KEY uk_oauth2_registered_client_client_id (client_id)
);
