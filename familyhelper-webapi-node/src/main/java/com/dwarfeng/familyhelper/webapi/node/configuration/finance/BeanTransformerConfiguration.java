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
import com.dwarfeng.subgrade.impl.bean.DozerBeanTransformer;
import com.dwarfeng.subgrade.stack.bean.BeanTransformer;
import org.dozer.Mapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration("finance.beanTransformerConfiguration")
public class BeanTransformerConfiguration {

    private final Mapper mapper;

    public BeanTransformerConfiguration(Mapper mapper) {
        this.mapper = mapper;
    }

    @Bean("finance.accountBookBeanTransformer")
    public BeanTransformer<AccountBook, JSFixedFastJsonAccountBook> accountBookBeanTransformer() {
        return new DozerBeanTransformer<>(AccountBook.class, JSFixedFastJsonAccountBook.class, mapper);
    }

    @Bean("finance.dispAccountBookBeanTransformer")
    public BeanTransformer<DispAccountBook, JSFixedFastJsonDispAccountBook> dispAccountBookBeanTransformer() {
        return new DozerBeanTransformer<>(DispAccountBook.class, JSFixedFastJsonDispAccountBook.class, mapper);
    }

    @Bean
    public BeanTransformer<BankCardTypeIndicator, FastJsonBankCardTypeIndicator>
    bankCardTypeIndicatorBeanTransformer() {
        return new DozerBeanTransformer<>(BankCardTypeIndicator.class, FastJsonBankCardTypeIndicator.class, mapper);
    }

    @Bean("finance.bankCardBeanTransformer")
    public BeanTransformer<BankCard, JSFixedFastJsonBankCard> bankCardBeanTransformer() {
        return new DozerBeanTransformer<>(BankCard.class, JSFixedFastJsonBankCard.class, mapper);
    }

    @Bean("finance.dispBankCardBeanTransformer")
    public BeanTransformer<DispBankCard, JSFixedFastJsonDispBankCard> dispBankCardBeanTransformer() {
        return new DozerBeanTransformer<>(DispBankCard.class, JSFixedFastJsonDispBankCard.class, mapper);
    }

    @Bean
    public BeanTransformer<FundChangeTypeIndicator, FastJsonFundChangeTypeIndicator>
    fundChangeTypeIndicatorBeanTransformer() {
        return new DozerBeanTransformer<>(FundChangeTypeIndicator.class, FastJsonFundChangeTypeIndicator.class, mapper);
    }

    @Bean("finance.fundChangeBeanTransformer")
    public BeanTransformer<FundChange, JSFixedFastJsonFundChange> fundChangeBeanTransformer() {
        return new DozerBeanTransformer<>(FundChange.class, JSFixedFastJsonFundChange.class, mapper);
    }

    @Bean("finance.dispFundChangeBeanTransformer")
    public BeanTransformer<DispFundChange, JSFixedFastJsonDispFundChange> dispFundChangeBeanTransformer() {
        return new DozerBeanTransformer<>(DispFundChange.class, JSFixedFastJsonDispFundChange.class, mapper);
    }

    @Bean
    public BeanTransformer<TotalBalanceHistory, JSFixedFastJsonTotalBalanceHistory>
    totalBalanceHistoryBeanTransformer() {
        return new DozerBeanTransformer<>(TotalBalanceHistory.class, JSFixedFastJsonTotalBalanceHistory.class, mapper);
    }

    @Bean
    public BeanTransformer<BankCardBalanceHistory, JSFixedFastJsonBankCardBalanceHistory>
    bankCardBalanceHistoryBeanTransformer() {
        return new DozerBeanTransformer<>(
                BankCardBalanceHistory.class, JSFixedFastJsonBankCardBalanceHistory.class, mapper
        );
    }

    @Bean("finance.poabBeanTransformer")
    public BeanTransformer<Poab, JSFixedFastJsonPoab> poabBeanTransformer() {
        return new DozerBeanTransformer<>(Poab.class, JSFixedFastJsonPoab.class, mapper);
    }

    @Bean("finance.dispPoabBeanTransformer")
    public BeanTransformer<DispPoab, JSFixedFastJsonDispPoab> dispPoabBeanTransformer() {
        return new DozerBeanTransformer<>(DispPoab.class, JSFixedFastJsonDispPoab.class, mapper);
    }

    @Bean("finance.billFileInfoBeanTransformer")
    public BeanTransformer<BillFileInfo, JSFixedFastJsonBillFileInfo> billFileInfoBeanTransformer() {
        return new DozerBeanTransformer<>(BillFileInfo.class, JSFixedFastJsonBillFileInfo.class, mapper);
    }

    @Bean("finance.remindDriverInfoBeanTransformer")
    public BeanTransformer<RemindDriverInfo, JSFixedFastJsonRemindDriverInfo> remindDriverInfoBeanTransformer() {
        return new DozerBeanTransformer<>(RemindDriverInfo.class, JSFixedFastJsonRemindDriverInfo.class, mapper);
    }

    @Bean("finance.remindDriverSupportBeanTransformer")
    public BeanTransformer<RemindDriverSupport, FastJsonRemindDriverSupport> remindDriverSupportBeanTransformer() {
        return new DozerBeanTransformer<>(RemindDriverSupport.class, FastJsonRemindDriverSupport.class, mapper);
    }
}
