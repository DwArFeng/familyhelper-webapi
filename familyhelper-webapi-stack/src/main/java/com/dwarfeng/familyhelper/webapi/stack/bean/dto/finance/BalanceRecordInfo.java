package com.dwarfeng.familyhelper.webapi.stack.bean.dto.finance;

import com.dwarfeng.subgrade.stack.bean.dto.Dto;

import java.math.BigDecimal;

/**
 * 余额记录信息。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class BalanceRecordInfo implements Dto {

    private static final long serialVersionUID = -211935626873334996L;

    private BigDecimal balance;

    public BalanceRecordInfo() {
    }

    public BalanceRecordInfo(BigDecimal balance) {
        this.balance = balance;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    @Override
    public String toString() {
        return "BalanceRecordInfo{" +
                "balance=" + balance +
                '}';
    }
}
