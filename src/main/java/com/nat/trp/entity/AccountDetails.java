package com.nat.trp.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "WS_ACCOUNT_DETAILS", schema = "nad")
public class AccountDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "acct_details_ident_id")
    private Long acctDetailsIdentId;

    @Column(name = "acct_ident_id", nullable = false)
    private Long acctIdentId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "acct_ident_id", insertable = false, updatable = false)
    private Account account;

    @Column(name = "acct_id", nullable = false)
    private Long acctId;

    @Column(name = "tran_type", length = 50, nullable = false)
    private String tranType;

    @Column(name = "updated_acct_bal", precision = 15, scale = 2)
    private BigDecimal updatedAcctBal;

    @Column(name = "tran_time", columnDefinition = "TIMESTAMP")
    private LocalDateTime tranTime;

    @Column(name = "tran_reversal_time", columnDefinition = "TIMESTAMP")
    private LocalDateTime tranReversalTime;

    @Column(name = "tran_desc", length = 255)
    private String tranDesc;

    public AccountDetails() {}

    public Long getAcctDetailsIdentId() { return acctDetailsIdentId; }
    public void setAcctDetailsIdentId(Long acctDetailsIdentId) { this.acctDetailsIdentId = acctDetailsIdentId; }

    public Long getAcctIdentId() { return acctIdentId; }
    public void setAcctIdentId(Long acctIdentId) { this.acctIdentId = acctIdentId; }

    public Account getAccount() { return account; }
    public void setAccount(Account account) { this.account = account; }

    public Long getAcctId() { return acctId; }
    public void setAcctId(Long acctId) { this.acctId = acctId; }

    public String getTranType() { return tranType; }
    public void setTranType(String tranType) { this.tranType = tranType; }

    public BigDecimal getUpdatedAcctBal() { return updatedAcctBal; }
    public void setUpdatedAcctBal(BigDecimal updatedAcctBal) { this.updatedAcctBal = updatedAcctBal; }

    public LocalDateTime getTranTime() { return tranTime; }
    public void setTranTime(LocalDateTime tranTime) { this.tranTime = tranTime; }

    public LocalDateTime getTranReversalTime() { return tranReversalTime; }
    public void setTranReversalTime(LocalDateTime tranReversalTime) { this.tranReversalTime = tranReversalTime; }

    public String getTranDesc() { return tranDesc; }
    public void setTranDesc(String tranDesc) { this.tranDesc = tranDesc; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AccountDetails)) return false;
        AccountDetails that = (AccountDetails) o;
        return Objects.equals(acctDetailsIdentId, that.acctDetailsIdentId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(acctDetailsIdentId);
    }

    @Override
    public String toString() {
        return "AccountDetails{" +
                "acctDetailsIdentId=" + acctDetailsIdentId +
                ", acctIdentId=" + acctIdentId +
                ", acctId=" + acctId +
                ", tranType='" + tranType + '\'' +
                ", updatedAcctBal=" + updatedAcctBal +
                ", tranTime=" + tranTime +
                ", tranReversalTime=" + tranReversalTime +
                ", tranDesc='" + tranDesc + '\'' +
                '}';
    }
}

