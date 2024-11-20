package com.dwarfeng.familyhelper.webapi.node.configuration.finance;

import com.dwarfeng.familyhelper.finance.sdk.bean.entity.*;
import com.dwarfeng.familyhelper.finance.stack.bean.entity.*;
import com.dwarfeng.familyhelper.webapi.sdk.bean.disp.finance.JSFixedFastJsonDispAccountBook;
import com.dwarfeng.familyhelper.webapi.sdk.bean.disp.finance.JSFixedFastJsonDispBankCard;
import com.dwarfeng.familyhelper.webapi.sdk.bean.disp.finance.JSFixedFastJsonDispFundChange;
import com.dwarfeng.familyhelper.webapi.sdk.bean.disp.finance.JSFixedFastJsonDispPoab;
import com.dwarfeng.familyhelper.webapi.stack.bean.disp.finance.DispAccountBook;
import com.dwarfeng.familyhelper.webapi.stack.bean.disp.finance.DispBankCard;
import com.dwarfeng.familyhelper.webapi.stack.bean.disp.finance.DispFundChange;
import com.dwarfeng.familyhelper.webapi.stack.bean.disp.finance.DispPoab;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;

/**
 * FastJson Bean 映射器。
 *
 * @author DwArFeng
 * @since 1.4.0
 */
@Mapper
public interface FastJsonMapper {

    JSFixedFastJsonAccountBook accountBookToJsFixedFastJson(AccountBook accountBook);

    @InheritInverseConfiguration
    AccountBook accountBookFromJsFixedFastJson(JSFixedFastJsonAccountBook jsFixedFastJsonAccountBook);

    JSFixedFastJsonDispAccountBook dispAccountBookToJsFixedFastJson(DispAccountBook dispAccountBook);

    @InheritInverseConfiguration
    DispAccountBook dispAccountBookFromJsFixedFastJson(JSFixedFastJsonDispAccountBook jsFixedFastJsonDispAccountBook);

    FastJsonBankCardTypeIndicator bankCardTypeIndicatorToFastJson(BankCardTypeIndicator bankCardTypeIndicator);

    @InheritInverseConfiguration
    BankCardTypeIndicator bankCardTypeIndicatorFromFastJson(
            FastJsonBankCardTypeIndicator fastJsonBankCardTypeIndicator
    );

    JSFixedFastJsonBankCard bankCardToJsFixedFastJson(BankCard bankCard);

    @InheritInverseConfiguration
    BankCard bankCardFromJsFixedFastJson(JSFixedFastJsonBankCard jsFixedFastJsonBankCard);

    JSFixedFastJsonDispBankCard dispBankCardToJsFixedFastJson(DispBankCard dispBankCard);

    @InheritInverseConfiguration
    DispBankCard dispBankCardFromJsFixedFastJson(JSFixedFastJsonDispBankCard jsFixedFastJsonDispBankCard);

    FastJsonFundChangeTypeIndicator fundChangeTypeIndicatorToFastJson(FundChangeTypeIndicator fundChangeTypeIndicator);

    @InheritInverseConfiguration
    FundChangeTypeIndicator fundChangeTypeIndicatorFromFastJson(
            FastJsonFundChangeTypeIndicator fastJsonFundChangeTypeIndicator
    );

    JSFixedFastJsonFundChange fundChangeToJsFixedFastJson(FundChange fundChange);

    @InheritInverseConfiguration
    FundChange fundChangeFromJsFixedFastJson(JSFixedFastJsonFundChange jsFixedFastJsonFundChange);

    JSFixedFastJsonDispFundChange dispFundChangeToJsFixedFastJson(DispFundChange dispFundChange);

    @InheritInverseConfiguration
    DispFundChange dispFundChangeFromJsFixedFastJson(JSFixedFastJsonDispFundChange jsFixedFastJsonDispFundChange);

    JSFixedFastJsonTotalBalanceHistory totalBalanceHistoryToJsFixedFastJson(TotalBalanceHistory totalBalanceHistory);

    @InheritInverseConfiguration
    TotalBalanceHistory totalBalanceHistoryFromJsFixedFastJson(
            JSFixedFastJsonTotalBalanceHistory jsFixedFastJsonTotalBalanceHistory
    );

    JSFixedFastJsonBankCardBalanceHistory bankCardBalanceHistoryToJsFixedFastJson(
            BankCardBalanceHistory bankCardBalanceHistory
    );

    @InheritInverseConfiguration
    BankCardBalanceHistory bankCardBalanceHistoryFromJsFixedFastJson(
            JSFixedFastJsonBankCardBalanceHistory jsFixedFastJsonBankCardBalanceHistory
    );

    JSFixedFastJsonPoab poabToJsFixedFastJson(Poab poab);

    @InheritInverseConfiguration
    Poab poabFromJsFixedFastJson(JSFixedFastJsonPoab jsFixedFastJsonPoab);

    JSFixedFastJsonDispPoab dispPoabToJsFixedFastJson(DispPoab dispPoab);

    @InheritInverseConfiguration
    DispPoab dispPoabFromJsFixedFastJson(JSFixedFastJsonDispPoab jsFixedFastJsonDispPoab);

    JSFixedFastJsonBillFileInfo billFileInfoToJsFixedFastJson(BillFileInfo billFileInfo);

    @InheritInverseConfiguration
    BillFileInfo billFileInfoFromJsFixedFastJson(JSFixedFastJsonBillFileInfo jsFixedFastJsonBillFileInfo);

    JSFixedFastJsonRemindDriverInfo remindDriverInfoToJsFixedFastJson(RemindDriverInfo remindDriverInfo);

    @InheritInverseConfiguration
    RemindDriverInfo remindDriverInfoFromJsFixedFastJson(
            JSFixedFastJsonRemindDriverInfo jsFixedFastJsonRemindDriverInfo
    );

    FastJsonRemindDriverSupport remindDriverSupportToFastJson(RemindDriverSupport remindDriverSupport);

    @InheritInverseConfiguration
    RemindDriverSupport remindDriverSupportFromFastJson(FastJsonRemindDriverSupport fastJsonRemindDriverSupport);
}
