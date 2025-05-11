package com.dwarfeng.familyhelper.webapi.node.configuration.finance;

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
import com.dwarfeng.subgrade.impl.bean.MapStructBeanTransformer;
import com.dwarfeng.subgrade.stack.bean.BeanTransformer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration("finance.beanTransformerConfiguration")
public class BeanTransformerConfiguration {

    @Bean("finance.accountBookBeanTransformer")
    public BeanTransformer<AccountBook, JSFixedFastJsonAccountBook> accountBookBeanTransformer() {
        return new MapStructBeanTransformer<>(
                AccountBook.class, JSFixedFastJsonAccountBook.class, FastJsonMapper.class
        );
    }

    @Bean("finance.dispAccountBookBeanTransformer")
    public BeanTransformer<DispAccountBook, JSFixedFastJsonDispAccountBook> dispAccountBookBeanTransformer() {
        return new MapStructBeanTransformer<>(
                DispAccountBook.class, JSFixedFastJsonDispAccountBook.class, FastJsonMapper.class
        );
    }

    @Bean("finance.bankCardTypeIndicatorBeanTransformer")
    public BeanTransformer<BankCardTypeIndicator, FastJsonBankCardTypeIndicator>
    bankCardTypeIndicatorBeanTransformer() {
        return new MapStructBeanTransformer<>(
                BankCardTypeIndicator.class, FastJsonBankCardTypeIndicator.class, FastJsonMapper.class
        );
    }

    @Bean("finance.bankCardBeanTransformer")
    public BeanTransformer<BankCard, JSFixedFastJsonBankCard> bankCardBeanTransformer() {
        return new MapStructBeanTransformer<>(BankCard.class, JSFixedFastJsonBankCard.class, FastJsonMapper.class);
    }

    @Bean("finance.dispBankCardBeanTransformer")
    public BeanTransformer<DispBankCard, JSFixedFastJsonDispBankCard> dispBankCardBeanTransformer() {
        return new MapStructBeanTransformer<>(
                DispBankCard.class, JSFixedFastJsonDispBankCard.class, FastJsonMapper.class
        );
    }

    @Bean("finance.fundTypeIndicatorBeanTransformer")
    public BeanTransformer<FundChangeTypeIndicator, FastJsonFundChangeTypeIndicator>
    fundChangeTypeIndicatorBeanTransformer() {
        return new MapStructBeanTransformer<>(
                FundChangeTypeIndicator.class, FastJsonFundChangeTypeIndicator.class, FastJsonMapper.class
        );
    }

    @Bean("finance.fundChangeBeanTransformer")
    public BeanTransformer<FundChange, JSFixedFastJsonFundChange> fundChangeBeanTransformer() {
        return new MapStructBeanTransformer<>(FundChange.class, JSFixedFastJsonFundChange.class, FastJsonMapper.class);
    }

    @Bean("finance.dispFundChangeBeanTransformer")
    public BeanTransformer<DispFundChange, JSFixedFastJsonDispFundChange> dispFundChangeBeanTransformer() {
        return new MapStructBeanTransformer<>(
                DispFundChange.class, JSFixedFastJsonDispFundChange.class, FastJsonMapper.class
        );
    }

    @Bean("finance.totalBalanceHistoryBeanTransformer")
    public BeanTransformer<TotalBalanceHistory, JSFixedFastJsonTotalBalanceHistory>
    totalBalanceHistoryBeanTransformer() {
        return new MapStructBeanTransformer<>(
                TotalBalanceHistory.class, JSFixedFastJsonTotalBalanceHistory.class, FastJsonMapper.class
        );
    }

    @Bean("finance.bankCardBalanceHistoryBeanTransformer")
    public BeanTransformer<BankCardBalanceHistory, JSFixedFastJsonBankCardBalanceHistory>
    bankCardBalanceHistoryBeanTransformer() {
        return new MapStructBeanTransformer<>(
                BankCardBalanceHistory.class, JSFixedFastJsonBankCardBalanceHistory.class, FastJsonMapper.class
        );
    }

    @Bean("finance.poabBeanTransformer")
    public BeanTransformer<Poab, JSFixedFastJsonPoab> poabBeanTransformer() {
        return new MapStructBeanTransformer<>(Poab.class, JSFixedFastJsonPoab.class, FastJsonMapper.class);
    }

    @Bean("finance.dispPoabBeanTransformer")
    public BeanTransformer<DispPoab, JSFixedFastJsonDispPoab> dispPoabBeanTransformer() {
        return new MapStructBeanTransformer<>(DispPoab.class, JSFixedFastJsonDispPoab.class, FastJsonMapper.class);
    }

    @Bean("finance.billFileInfoBeanTransformer")
    public BeanTransformer<BillFileInfo, JSFixedFastJsonBillFileInfo> billFileInfoBeanTransformer() {
        return new MapStructBeanTransformer<>(
                BillFileInfo.class, JSFixedFastJsonBillFileInfo.class, FastJsonMapper.class
        );
    }

    @Bean("finance.remindDriverInfoBeanTransformer")
    public BeanTransformer<RemindDriverInfo, JSFixedFastJsonRemindDriverInfo> remindDriverInfoBeanTransformer() {
        return new MapStructBeanTransformer<>(
                RemindDriverInfo.class, JSFixedFastJsonRemindDriverInfo.class, FastJsonMapper.class
        );
    }

    @Bean("finance.remindDriverSupportBeanTransformer")
    public BeanTransformer<RemindDriverSupport, FastJsonRemindDriverSupport> remindDriverSupportBeanTransformer() {
        return new MapStructBeanTransformer<>(
                RemindDriverSupport.class, FastJsonRemindDriverSupport.class, FastJsonMapper.class
        );
    }
}
