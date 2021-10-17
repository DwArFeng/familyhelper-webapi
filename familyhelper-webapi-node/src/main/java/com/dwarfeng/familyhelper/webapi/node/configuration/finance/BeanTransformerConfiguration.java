package com.dwarfeng.familyhelper.webapi.node.configuration.finance;

import com.dwarfeng.familyhelper.finance.sdk.bean.entity.*;
import com.dwarfeng.familyhelper.finance.stack.bean.entity.*;
import com.dwarfeng.familyhelper.webapi.sdk.bean.disp.finance.JSFixedFastJsonDispAccountBook;
import com.dwarfeng.familyhelper.webapi.sdk.bean.disp.finance.JSFixedFastJsonDispBankCard;
import com.dwarfeng.familyhelper.webapi.sdk.bean.disp.finance.JSFixedFastJsonDispFundChange;
import com.dwarfeng.familyhelper.webapi.stack.bean.disp.finance.DispAccountBook;
import com.dwarfeng.familyhelper.webapi.stack.bean.disp.finance.DispBankCard;
import com.dwarfeng.familyhelper.webapi.stack.bean.disp.finance.DispFundChange;
import com.dwarfeng.subgrade.impl.bean.DozerBeanTransformer;
import com.dwarfeng.subgrade.stack.bean.BeanTransformer;
import org.dozer.Mapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration("financeBeanTransformerConfiguration")
public class BeanTransformerConfiguration {

    private final Mapper mapper;

    public BeanTransformerConfiguration(Mapper mapper) {
        this.mapper = mapper;
    }

    @Bean
    public BeanTransformer<AccountBook, JSFixedFastJsonAccountBook> accountBookBeanTransformer() {
        return new DozerBeanTransformer<>(AccountBook.class, JSFixedFastJsonAccountBook.class, mapper);
    }

    @Bean
    public BeanTransformer<DispAccountBook, JSFixedFastJsonDispAccountBook> dispAccountBookBeanTransformer() {
        return new DozerBeanTransformer<>(DispAccountBook.class, JSFixedFastJsonDispAccountBook.class, mapper);
    }

    @Bean
    public BeanTransformer<BankCardTypeIndicator, FastJsonBankCardTypeIndicator>
    bankCardTypeIndicatorBeanTransformer() {
        return new DozerBeanTransformer<>(BankCardTypeIndicator.class, FastJsonBankCardTypeIndicator.class, mapper);
    }

    @Bean
    public BeanTransformer<BankCard, JSFixedFastJsonBankCard> bankCardBeanTransformer() {
        return new DozerBeanTransformer<>(BankCard.class, JSFixedFastJsonBankCard.class, mapper);
    }

    @Bean
    public BeanTransformer<DispBankCard, JSFixedFastJsonDispBankCard> dispBankCardBeanTransformer() {
        return new DozerBeanTransformer<>(DispBankCard.class, JSFixedFastJsonDispBankCard.class, mapper);
    }

    @Bean
    public BeanTransformer<FundChangeTypeIndicator, FastJsonFundChangeTypeIndicator>
    fundChangeTypeIndicatorBeanTransformer() {
        return new DozerBeanTransformer<>(FundChangeTypeIndicator.class, FastJsonFundChangeTypeIndicator.class, mapper);
    }

    @Bean
    public BeanTransformer<FundChange, JSFixedFastJsonFundChange> fundChangeBeanTransformer() {
        return new DozerBeanTransformer<>(FundChange.class, JSFixedFastJsonFundChange.class, mapper);
    }

    @Bean
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
}
