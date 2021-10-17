package com.dwarfeng.familyhelper.webapi.sdk.bean.dto.finance;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.familyhelper.webapi.stack.bean.dto.finance.BalanceRecordInfo;
import com.dwarfeng.subgrade.stack.bean.dto.Dto;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * WebInput 余额记录信息
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class WebInputBalanceRecordInfo implements Dto {

    private static final long serialVersionUID = -6194214487308026165L;

    public static BalanceRecordInfo toStackBean(WebInputBalanceRecordInfo webInputBalanceRecordInfo) {
        if (Objects.isNull(webInputBalanceRecordInfo)) {
            return null;
        } else {
            return new BalanceRecordInfo(webInputBalanceRecordInfo.getBalance());
        }
    }

    @JSONField(name = "balance")
    @NotNull
    private BigDecimal balance;

    public WebInputBalanceRecordInfo() {
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    @Override
    public String toString() {
        return "WebInputBalanceRecordInfo{" +
                "balance=" + balance +
                '}';
    }
}
