package com.dwarfeng.familyhelper.webapi.node.configuration;


import com.dwarfeng.familyhelper.webapi.sdk.bean.vo.basic.FastJsonAccount;
import com.dwarfeng.familyhelper.webapi.stack.bean.vo.basic.Account;
import com.dwarfeng.subgrade.impl.bean.DozerBeanTransformer;
import com.dwarfeng.subgrade.stack.bean.BeanTransformer;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BasicBeanTransformerConfiguration {

    @Autowired
    private Mapper mapper;

    @Bean
    public BeanTransformer<Account, FastJsonAccount> AccountFastJsonAccountBeanTransformer() {
        return new DozerBeanTransformer<>(Account.class, FastJsonAccount.class, mapper);
    }
}
