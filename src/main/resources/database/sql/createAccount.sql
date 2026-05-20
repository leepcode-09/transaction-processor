CREATE TABLE nad.WS_ACCOUNT (
    acct_ident_id      BIGINT AUTO_INCREMENT PRIMARY KEY,
    acct_id            BIGINT NOT NULL,
    acct_type          VARCHAR(50) NOT NULL,
    acct_currency_type VARCHAR(10) NOT NULL,
    acct_holder_fn     VARCHAR(100) NOT NULL,
    acct_holder_ln     VARCHAR(100) NOT NULL,
    acct_created_time  TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    acct_updatedted_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP 
                         ON UPDATE CURRENT_TIMESTAMP,
    acct_desc          VARCHAR(255),
    acct_balance       DECIMAL(15,2) DEFAULT 0.00
);