package com.dwarfeng.familyhelper.webapi.node.configuration.notify;

import com.dwarfeng.familyhelper.webapi.sdk.bean.disp.notify.JSFixedFastJsonDispMeta;
import com.dwarfeng.familyhelper.webapi.sdk.bean.disp.notify.JSFixedFastJsonDispSendHistory;
import com.dwarfeng.familyhelper.webapi.stack.bean.disp.notify.DispMeta;
import com.dwarfeng.familyhelper.webapi.stack.bean.disp.notify.DispSendHistory;
import com.dwarfeng.notify.sdk.bean.entity.*;
import com.dwarfeng.notify.stack.bean.entity.*;
import com.dwarfeng.subgrade.impl.bean.DozerBeanTransformer;
import com.dwarfeng.subgrade.stack.bean.BeanTransformer;
import org.dozer.Mapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration("notify.beanTransformerConfiguration")
public class BeanTransformerConfiguration {

    private final Mapper mapper;

    public BeanTransformerConfiguration(Mapper mapper) {
        this.mapper = mapper;
    }

    @Bean("notify.notifySettingBeanTransformer")
    public BeanTransformer<NotifySetting, JSFixedFastJsonNotifySetting> notifySettingBeanTransformer() {
        return new DozerBeanTransformer<>(NotifySetting.class, JSFixedFastJsonNotifySetting.class, mapper);
    }

    @Bean("notify.topicBeanTransformer")
    public BeanTransformer<Topic, FastJsonTopic> topicBeanTransformer() {
        return new DozerBeanTransformer<>(Topic.class, FastJsonTopic.class, mapper);
    }

    @Bean("notify.routerInfoBeanTransformer")
    public BeanTransformer<RouterInfo, JSFixedFastJsonRouterInfo> routerInfoBeanTransformer() {
        return new DozerBeanTransformer<>(RouterInfo.class, JSFixedFastJsonRouterInfo.class, mapper);
    }

    @Bean("notify.routerSupportBeanTransformer")
    public BeanTransformer<RouterSupport, FastJsonRouterSupport> routerSupportBeanTransformer() {
        return new DozerBeanTransformer<>(RouterSupport.class, FastJsonRouterSupport.class, mapper);
    }

    @Bean("notify.dispatcherInfoBeanTransformer")
    public BeanTransformer<DispatcherInfo, FastJsonDispatcherInfo> dispatcherInfoBeanTransformer() {
        return new DozerBeanTransformer<>(DispatcherInfo.class, FastJsonDispatcherInfo.class, mapper);
    }

    @Bean("notify.dispatcherSupportBeanTransformer")
    public BeanTransformer<DispatcherSupport, FastJsonDispatcherSupport> dispatcherSupportBeanTransformer() {
        return new DozerBeanTransformer<>(DispatcherSupport.class, FastJsonDispatcherSupport.class, mapper);
    }

    @Bean("notify.senderInfoBeanTransformer")
    public BeanTransformer<SenderInfo, JSFixedFastJsonSenderInfo> senderInfoBeanTransformer() {
        return new DozerBeanTransformer<>(SenderInfo.class, JSFixedFastJsonSenderInfo.class, mapper);
    }

    @Bean("notify.senderSupportBeanTransformer")
    public BeanTransformer<SenderSupport, FastJsonSenderSupport> senderSupportBeanTransformer() {
        return new DozerBeanTransformer<>(SenderSupport.class, FastJsonSenderSupport.class, mapper);
    }

    @Bean("notify.metaBeanTransformer")
    public BeanTransformer<Meta, JSFixedFastJsonMeta> metaBeanTransformer() {
        return new DozerBeanTransformer<>(Meta.class, JSFixedFastJsonMeta.class, mapper);
    }

    @Bean("notify.dispMetaBeanTransformer")
    public BeanTransformer<DispMeta, JSFixedFastJsonDispMeta> dispMetaBeanTransformer() {
        return new DozerBeanTransformer<>(DispMeta.class, JSFixedFastJsonDispMeta.class, mapper);
    }

    @Bean("notify.metaIndicatorBeanTransformer")
    public BeanTransformer<MetaIndicator, FastJsonMetaIndicator> metaIndicatorBeanTransformer() {
        return new DozerBeanTransformer<>(MetaIndicator.class, FastJsonMetaIndicator.class, mapper);
    }

    @Bean("notify.sendHistoryBeanTransformer")
    public BeanTransformer<SendHistory, JSFixedFastJsonSendHistory> sendHistoryBeanTransformer() {
        return new DozerBeanTransformer<>(SendHistory.class, JSFixedFastJsonSendHistory.class, mapper);
    }

    @Bean("notify.dispSendHistoryBeanTransformer")
    public BeanTransformer<DispSendHistory, JSFixedFastJsonDispSendHistory> dispSendHistoryBeanTransformer() {
        return new DozerBeanTransformer<>(DispSendHistory.class, JSFixedFastJsonDispSendHistory.class, mapper);
    }
}
