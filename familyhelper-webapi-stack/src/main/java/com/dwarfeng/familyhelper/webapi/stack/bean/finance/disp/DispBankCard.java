package com.dwarfeng.familyhelper.webapi.stack.bean.finance.disp;

import com.dwarfeng.familyhelper.finance.stack.bean.entity.BankCard;
import com.dwarfeng.familyhelper.finance.stack.bean.entity.BankCardTypeIndicator;
import com.dwarfeng.subgrade.stack.bean.dto.Dto;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

/**
 * 可展示银行卡。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class DispBankCard implements Dto {

    private static final long serialVersionUID = 4188401684135531521L;

    public static DispBankCard of(
            BankCard bankCard, DispAccountBook accountBook, BankCardTypeIndicator typeIndicator) {
        if (Objects.isNull(bankCard)) {
            return null;
        } else {
            return new DispBankCard(
                    bankCard.getKey(), bankCard.getAccountBookKey(), bankCard.getName(), bankCard.getCardType(),
                    bankCard.getLastRecordedDate(), bankCard.getBalanceValue(), bankCard.isTempFlag(),
                    bankCard.getTempLastRecordedDate(), bankCard.getTempBalanceValue(), bankCard.getRemark(),
                    accountBook, typeIndicator
            );
        }
    }

    private LongIdKey key;
    private LongIdKey accountBookKey;
    private String name;
    private String cardType;
    private Date lastRecordedDate;
    private BigDecimal balanceValue;
    private boolean tempFlag;
    private Date tempLastRecordedDate;
    private BigDecimal tempBalanceValue;
    private String remark;
    private DispAccountBook accountBook;
    private BankCardTypeIndicator typeIndicator;

    public DispBankCard() {
    }

    public DispBankCard(
            LongIdKey key, LongIdKey accountBookKey, String name, String cardType, Date lastRecordedDate,
            BigDecimal balanceValue, boolean tempFlag, Date tempLastRecordedDate, BigDecimal tempBalanceValue,
            String remark, DispAccountBook accountBook, BankCardTypeIndicator typeIndicator
    ) {
        this.key = key;
        this.accountBookKey = accountBookKey;
        this.name = name;
        this.cardType = cardType;
        this.lastRecordedDate = lastRecordedDate;
        this.balanceValue = balanceValue;
        this.tempFlag = tempFlag;
        this.tempLastRecordedDate = tempLastRecordedDate;
        this.tempBalanceValue = tempBalanceValue;
        this.remark = remark;
        this.accountBook = accountBook;
        this.typeIndicator = typeIndicator;
    }

    public LongIdKey getKey() {
        return key;
    }

    public void setKey(LongIdKey key) {
        this.key = key;
    }

    public LongIdKey getAccountBookKey() {
        return accountBookKey;
    }

    public void setAccountBookKey(LongIdKey accountBookKey) {
        this.accountBookKey = accountBookKey;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public Date getLastRecordedDate() {
        return lastRecordedDate;
    }

    public void setLastRecordedDate(Date lastRecordedDate) {
        this.lastRecordedDate = lastRecordedDate;
    }

    public BigDecimal getBalanceValue() {
        return balanceValue;
    }

    public void setBalanceValue(BigDecimal balanceValue) {
        this.balanceValue = balanceValue;
    }

    public boolean isTempFlag() {
        return tempFlag;
    }

    public void setTempFlag(boolean tempFlag) {
        this.tempFlag = tempFlag;
    }

    public Date getTempLastRecordedDate() {
        return tempLastRecordedDate;
    }

    public void setTempLastRecordedDate(Date tempLastRecordedDate) {
        this.tempLastRecordedDate = tempLastRecordedDate;
    }

    public BigDecimal getTempBalanceValue() {
        return tempBalanceValue;
    }

    public void setTempBalanceValue(BigDecimal tempBalanceValue) {
        this.tempBalanceValue = tempBalanceValue;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public DispAccountBook getAccountBook() {
        return accountBook;
    }

    public void setAccountBook(DispAccountBook accountBook) {
        this.accountBook = accountBook;
    }

    public BankCardTypeIndicator getTypeIndicator() {
        return typeIndicator;
    }

    public void setTypeIndicator(BankCardTypeIndicator typeIndicator) {
        this.typeIndicator = typeIndicator;
    }

    @Override
    public String toString() {
        return "DispBankCard{" +
                "key=" + key +
                ", accountBookKey=" + accountBookKey +
                ", name='" + name + '\'' +
                ", cardType='" + cardType + '\'' +
                ", lastRecordedDate=" + lastRecordedDate +
                ", balanceValue=" + balanceValue +
                ", tempFlag=" + tempFlag +
                ", tempLastRecordedDate=" + tempLastRecordedDate +
                ", tempBalanceValue=" + tempBalanceValue +
                ", remark='" + remark + '\'' +
                ", accountBook=" + accountBook +
                ", typeIndicator=" + typeIndicator +
                '}';
    }
}
