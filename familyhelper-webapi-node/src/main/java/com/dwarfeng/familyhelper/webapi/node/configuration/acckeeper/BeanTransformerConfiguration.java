package com.dwarfeng.familyhelper.webapi.node.configuration.acckeeper;

import com.dwarfeng.acckeeper.sdk.bean.entity.*;
import com.dwarfeng.acckeeper.stack.bean.entity.*;
import com.dwarfeng.familyhelper.webapi.sdk.bean.acckeeper.disp.*;
import com.dwarfeng.familyhelper.webapi.stack.bean.acckeeper.disp.*;
import com.dwarfeng.subgrade.impl.bean.MapStructBeanTransformer;
import com.dwarfeng.subgrade.stack.bean.BeanTransformer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration("acckeeper.beanTransformerConfiguration")
public class BeanTransformerConfiguration {

    @Bean("acckeeper.loginStateBeanTransformer")
    public BeanTransformer<LoginState, FastJsonLoginState> loginStateBeanTransformer() {
        return new MapStructBeanTransformer<>(
                LoginState.class, FastJsonLoginState.class,
                com.dwarfeng.acckeeper.sdk.bean.BeanMapper.class
        );
    }

    @Bean("acckeeper.loginHistoryBeanTransformer")
    public BeanTransformer<LoginHistory, JSFixedFastJsonLoginHistory> loginHistoryBeanTransformer() {
        return new MapStructBeanTransformer<>(
                LoginHistory.class, JSFixedFastJsonLoginHistory.class,
                com.dwarfeng.acckeeper.sdk.bean.BeanMapper.class
        );
    }

    @Bean("acckeeper.deriveHistoryBeanTransformer")
    public BeanTransformer<DeriveHistory, JSFixedFastJsonDeriveHistory> deriveHistoryBeanTransformer() {
        return new MapStructBeanTransformer<>(
                DeriveHistory.class, JSFixedFastJsonDeriveHistory.class,
                com.dwarfeng.acckeeper.sdk.bean.BeanMapper.class
        );
    }

    @Bean("acckeeper.loginParamRecordBeanTransformer")
    public BeanTransformer<LoginParamRecord, JSFixedFastJsonLoginParamRecord> loginParamRecordBeanTransformer() {
        return new MapStructBeanTransformer<>(
                LoginParamRecord.class, JSFixedFastJsonLoginParamRecord.class,
                com.dwarfeng.acckeeper.sdk.bean.BeanMapper.class
        );
    }

    @Bean("acckeeper.protectDetailRecordBeanTransformer")
    public BeanTransformer<ProtectDetailRecord, JSFixedFastJsonProtectDetailRecord>
    protectDetailRecordBeanTransformer() {
        return new MapStructBeanTransformer<>(
                ProtectDetailRecord.class, JSFixedFastJsonProtectDetailRecord.class,
                com.dwarfeng.acckeeper.sdk.bean.BeanMapper.class
        );
    }

    @Bean("acckeeper.protectorInfoBeanTransformer")
    public BeanTransformer<ProtectorInfo, FastJsonProtectorInfo> protectorInfoBeanTransformer() {
        return new MapStructBeanTransformer<>(
                ProtectorInfo.class, FastJsonProtectorInfo.class,
                com.dwarfeng.acckeeper.sdk.bean.BeanMapper.class
        );
    }

    @Bean("acckeeper.protectorSupportBeanTransformer")
    public BeanTransformer<ProtectorSupport, FastJsonProtectorSupport> protectorSupportBeanTransformer() {
        return new MapStructBeanTransformer<>(
                ProtectorSupport.class, FastJsonProtectorSupport.class,
                com.dwarfeng.acckeeper.sdk.bean.BeanMapper.class
        );
    }

    @Bean("acckeeper.protectorVariableBeanTransformer")
    public BeanTransformer<ProtectorVariable, FastJsonProtectorVariable> protectorVariableBeanTransformer() {
        return new MapStructBeanTransformer<>(
                ProtectorVariable.class, FastJsonProtectorVariable.class,
                com.dwarfeng.acckeeper.sdk.bean.BeanMapper.class
        );
    }

    @Bean("acckeeper.dispLoginStateBeanTransformer")
    public BeanTransformer<DispLoginState, JSFixedFastJsonDispLoginState> dispLoginStateBeanTransformer() {
        return new MapStructBeanTransformer<>(
                DispLoginState.class, JSFixedFastJsonDispLoginState.class,
                com.dwarfeng.familyhelper.webapi.sdk.bean.acckeeper.BeanMapper.class
        );
    }

    @Bean("acckeeper.dispLoginHistoryBeanTransformer")
    public BeanTransformer<DispLoginHistory, JSFixedFastJsonDispLoginHistory> dispLoginHistoryBeanTransformer() {
        return new MapStructBeanTransformer<>(
                DispLoginHistory.class, JSFixedFastJsonDispLoginHistory.class,
                com.dwarfeng.familyhelper.webapi.sdk.bean.acckeeper.BeanMapper.class
        );
    }

    @Bean("acckeeper.dispDeriveHistoryBeanTransformer")
    public BeanTransformer<DispDeriveHistory, JSFixedFastJsonDispDeriveHistory> dispDeriveHistoryBeanTransformer() {
        return new MapStructBeanTransformer<>(
                DispDeriveHistory.class, JSFixedFastJsonDispDeriveHistory.class,
                com.dwarfeng.familyhelper.webapi.sdk.bean.acckeeper.BeanMapper.class
        );
    }

    @Bean("acckeeper.dispLoginParamRecordBeanTransformer")
    public BeanTransformer<DispLoginParamRecord, JSFixedFastJsonDispLoginParamRecord>
    dispLoginParamRecordBeanTransformer() {
        return new MapStructBeanTransformer<>(
                DispLoginParamRecord.class, JSFixedFastJsonDispLoginParamRecord.class,
                com.dwarfeng.familyhelper.webapi.sdk.bean.acckeeper.BeanMapper.class
        );
    }

    @Bean("acckeeper.dispProtectDetailRecordBeanTransformer")
    public BeanTransformer<DispProtectDetailRecord, JSFixedFastJsonDispProtectDetailRecord>
    dispProtectDetailRecordBeanTransformer() {
        return new MapStructBeanTransformer<>(
                DispProtectDetailRecord.class, JSFixedFastJsonDispProtectDetailRecord.class,
                com.dwarfeng.familyhelper.webapi.sdk.bean.acckeeper.BeanMapper.class
        );
    }

    @Bean("acckeeper.dispProtectorVariableBeanTransformer")
    public BeanTransformer<DispProtectorVariable, FastJsonDispProtectorVariable>
    dispProtectorVariableBeanTransformer() {
        return new MapStructBeanTransformer<>(
                DispProtectorVariable.class, FastJsonDispProtectorVariable.class,
                com.dwarfeng.familyhelper.webapi.sdk.bean.acckeeper.BeanMapper.class
        );
    }
}
