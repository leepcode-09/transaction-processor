CREATE TABLE nad.WS_ACCOUNT_DETAILS_H (
    acct_details_h_ident_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    acct_details_ident_id   BIGINT NOT NULL,
    acct_ident_id           BIGINT NOT NULL,
    acct_id                 BIGINT NOT NULL,
    tran_type               VARCHAR(50) NOT NULL,
    updated_acct_bal        DECIMAL(15,2) DEFAULT 0.00,
    tran_time               TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    tran_reversal_time      TIMESTAMP NULL,
    tran_desc               VARCHAR(255),

    -- Foreign key to WS_ACCOUNT_DETAILS
    CONSTRAINT fk_acct_details FOREIGN KEY (acct_details_ident_id)
        REFERENCES WS_ACCOUNT_DETAILS(acct_details_ident_id)
        ON DELETE CASCADE,

    -- Foreign key to WS_ACCOUNT
    CONSTRAINT fk_acct FOREIGN KEY (acct_ident_id)
        REFERENCES WS_ACCOUNT(acct_ident_id)
        ON DELETE CASCADE
);