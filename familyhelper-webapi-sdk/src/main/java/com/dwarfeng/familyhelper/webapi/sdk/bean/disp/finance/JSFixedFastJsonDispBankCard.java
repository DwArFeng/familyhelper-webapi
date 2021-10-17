package com.dwarfeng.familyhelper.webapi.sdk.bean.disp.finance;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.familyhelper.finance.sdk.bean.entity.FastJsonBankCardTypeIndicator;
import com.dwarfeng.familyhelper.webapi.stack.bean.disp.finance.DispBankCard;
import com.dwarfeng.subgrade.sdk.bean.key.JSFixedFastJsonLongIdKey;
import com.dwarfeng.subgrade.stack.bean.dto.Dto;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

/**
 * JSFixed FastJson 可展示银行卡。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public class JSFixedFastJsonDispBankCard implements Dto {

    private static final long serialVersionUID = 143754332397369952L;

    public static JSFixedFastJsonDispBankCard of(DispBankCard dispBankCard) {
        if (Objects.isNull(dispBankCard)) {
            return null;
        } else {
            return new JSFixedFastJsonDispBankCard(
                    JSFixedFastJsonLongIdKey.of(dispBankCard.getKey()),
                    JSFixedFastJsonLongIdKey.of(dispBankCard.getAccountBookKey()),
                    dispBankCard.getName(), dispBankCard.getCardType(), dispBankCard.getLastRecordedDate(),
                    dispBankCard.getTempBalanceValue(), dispBankCard.isTempFlag(),
                    dispBankCard.getTempLastRecordedDate(), dispBankCard.getBalanceValue(), dispBankCard.getRemark(),
                    JSFixedFastJsonDispAccountBook.of(dispBankCard.getAccountBook()),
                    FastJsonBankCardTypeIndicator.of(dispBankCard.getTypeIndicator())
            );
        }
    }

    @JSONField(name = "key", ordinal = 1)
    private JSFixedFastJsonLongIdKey key;

    @JSONField(name = "account_book_key", ordinal = 2)
    private JSFixedFastJsonLongIdKey accountBookKey;

    @JSONField(name = "name", ordinal = 3)
    private String name;

    @JSONField(name = "card_type", ordinal = 4)
    private String cardType;

    @JSONField(name = "last_recorded_date", ordinal = 5)
    private Date lastRecordedDate;

    @JSONField(name = "balance_value", ordinal = 6)
    private BigDecimal balanceValue;

    @JSONField(name = "temp_flag", ordinal = 7)
    private boolean tempFlag;

    @JSONField(name = "temp_last_recorded_date", ordinal = 8)
    private Date tempLastRecordedDate;

    @JSONField(name = "temp_balance_value", ordinal = 9)
    private BigDecimal tempBalanceValue;

    @JSONField(name = "remark", ordinal = 10)
    private String remark;

    @JSONField(name = "account_book", ordinal = 11)
    private JSFixedFastJsonDispAccountBook accountBook;

    @JSONField(name = "type_indicator", ordinal = 12)
    private FastJsonBankCardTypeIndicator typeIndicator;

    public JSFixedFastJsonDispBankCard() {
    }

    public JSFixedFastJsonDispBankCard(
            JSFixedFastJsonLongIdKey key, JSFixedFastJsonLongIdKey accountBookKey, String name, String cardType,
            Date lastRecordedDate, BigDecimal balanceValue, boolean tempFlag, Date tempLastRecordedDate,
            BigDecimal tempBalanceValue, String remark, JSFixedFastJsonDispAccountBook accountBook,
            FastJsonBankCardTypeIndicator typeIndicator
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

    public JSFixedFastJsonLongIdKey getKey() {
        return key;
    }

    public void setKey(JSFixedFastJsonLongIdKey key) {
        this.key = key;
    }

    public JSFixedFastJsonLongIdKey getAccountBookKey() {
        return accountBookKey;
    }

    public void setAccountBookKey(JSFixedFastJsonLongIdKey accountBookKey) {
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

    public JSFixedFastJsonDispAccountBook getAccountBook() {
        return accountBook;
    }

    public void setAccountBook(JSFixedFastJsonDispAccountBook accountBook) {
        this.accountBook = accountBook;
    }

    public FastJsonBankCardTypeIndicator getTypeIndicator() {
        return typeIndicator;
    }

    public void setTypeIndicator(FastJsonBankCardTypeIndicator typeIndicator) {
        this.typeIndicator = typeIndicator;
    }

    @Override
    public String toString() {
        return "JSFixedFastJsonDispBankCard{" +
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
