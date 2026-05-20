package com.nat.trp.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "WS_ACCOUNT", schema = "nad")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "acct_ident_id")
    private Long acctIdentId;

    @Column(name = "acct_id", nullable = false)
    private Long acctId;

    @Column(name = "acct_type", length = 50, nullable = false)
    private String acctType;

    @Column(name = "acct_currency_type", length = 10, nullable = false)
    private String acctCurrencyType;

    @Column(name = "acct_holder_fn", length = 100, nullable = false)
    private String acctHolderFn;

    @Column(name = "acct_holder_ln", length = 100, nullable = false)
    private String acctHolderLn;

    @Column(name = "acct_created_time", columnDefinition = "TIMESTAMP")
    private LocalDateTime acctCreatedTime;

    @Column(name = "acct_updatedted_time", columnDefinition = "TIMESTAMP")
    private LocalDateTime acctUpdatedtedTime;

    @Column(name = "acct_desc", length = 255)
    private String acctDesc;

    @Column(name = "acct_balance", precision = 15, scale = 2)
    private BigDecimal acctBalance;

    public Account() {}

    // getters and setters

    public Long getAcctIdentId() { return acctIdentId; }
    public void setAcctIdentId(Long acctIdentId) { this.acctIdentId = acctIdentId; }

    public Long getAcctId() { return acctId; }
    public void setAcctId(Long acctId) { this.acctId = acctId; }

    public String getAcctType() { return acctType; }
    public void setAcctType(String acctType) { this.acctType = acctType; }

    public String getAcctCurrencyType() { return acctCurrencyType; }
    public void setAcctCurrencyType(String acctCurrencyType) { this.acctCurrencyType = acctCurrencyType; }

    public String getAcctHolderFn() { return acctHolderFn; }
    public void setAcctHolderFn(String acctHolderFn) { this.acctHolderFn = acctHolderFn; }

    public String getAcctHolderLn() { return acctHolderLn; }
    public void setAcctHolderLn(String acctHolderLn) { this.acctHolderLn = acctHolderLn; }

    public LocalDateTime getAcctCreatedTime() { return acctCreatedTime; }
    public void setAcctCreatedTime(LocalDateTime acctCreatedTime) { this.acctCreatedTime = acctCreatedTime; }

    public LocalDateTime getAcctUpdatedtedTime() { return acctUpdatedtedTime; }
    public void setAcctUpdatedtedTime(LocalDateTime acctUpdatedtedTime) { this.acctUpdatedtedTime = acctUpdatedtedTime; }

    public String getAcctDesc() { return acctDesc; }
    public void setAcctDesc(String acctDesc) { this.acctDesc = acctDesc; }

    public BigDecimal getAcctBalance() { return acctBalance; }
    public void setAcctBalance(BigDecimal acctBalance) { this.acctBalance = acctBalance; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Account)) return false;
        Account account = (Account) o;
        return Objects.equals(acctIdentId, account.acctIdentId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(acctIdentId);
    }

    @Override
    public String toString() {
        return "Account{" +
                "acctIdentId=" + acctIdentId +
                ", acctId=" + acctId +
                ", acctType='" + acctType + '\'' +
                ", acctCurrencyType='" + acctCurrencyType + '\'' +
                ", acctHolderFn='" + acctHolderFn + '\'' +
                ", acctHolderLn='" + acctHolderLn + '\'' +
                ", acctCreatedTime=" + acctCreatedTime +
                ", acctUpdatedtedTime=" + acctUpdatedtedTime +
                ", acctDesc='" + acctDesc + '\'' +
                ", acctBalance=" + acctBalance +
                '}';
    }
}

