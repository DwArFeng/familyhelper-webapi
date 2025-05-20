package com.dwarfeng.familyhelper.webapi.sdk.bean.finance;

import com.dwarfeng.familyhelper.finance.sdk.bean.entity.*;
import com.dwarfeng.familyhelper.finance.stack.bean.entity.*;
import com.dwarfeng.familyhelper.webapi.sdk.bean.finance.disp.JSFixedFastJsonDispAccountBook;
import com.dwarfeng.familyhelper.webapi.sdk.bean.finance.disp.JSFixedFastJsonDispBankCard;
import com.dwarfeng.familyhelper.webapi.sdk.bean.finance.disp.JSFixedFastJsonDispFundChange;
import com.dwarfeng.familyhelper.webapi.sdk.bean.finance.disp.JSFixedFastJsonDispPoab;
import com.dwarfeng.familyhelper.webapi.stack.bean.finance.disp.DispAccountBook;
import com.dwarfeng.familyhelper.webapi.stack.bean.finance.disp.DispBankCard;
import com.dwarfeng.familyhelper.webapi.stack.bean.finance.disp.DispFundChange;
import com.dwarfeng.familyhelper.webapi.stack.bean.finance.disp.DispPoab;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;

/**
 * Bean 映射器。
 *
 * <p>
 * 该映射器中包含了 <code>sdk</code> 模块中所有实体与 <code>stack</code> 模块中对应实体的映射方法。
 *
 * @author DwArFeng
 * @since 1.6.0
 */
@Mapper
public interface BeanMapper {

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
