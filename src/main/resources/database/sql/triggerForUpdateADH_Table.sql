-- Trigger for UPDATE
DELIMITER //
CREATE TRIGGER nad.trg_ws_account_details_update
AFTER UPDATE ON nad.WS_ACCOUNT_DETAILS
FOR EACH ROW
BEGIN
    INSERT INTO nad.WS_ACCOUNT_DETAILS_H (
        acct_details_ident_id,
        acct_ident_id,
        acct_id,
        tran_type,
        updated_acct_bal,
        tran_time,
        tran_reversal_time,
        tran_desc
    ) VALUES (
        NEW.acct_details_ident_id,
        NEW.acct_ident_id,
        NEW.acct_id,
        NEW.tran_type,
        NEW.updated_acct_bal,
        NEW.tran_time,
        NEW.tran_reversal_time,
        NEW.tran_desc
    );
END //
DELIMITER ;