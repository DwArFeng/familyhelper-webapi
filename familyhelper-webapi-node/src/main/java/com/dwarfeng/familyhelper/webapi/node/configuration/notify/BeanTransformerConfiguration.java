package com.dwarfeng.familyhelper.webapi.node.configuration.notify;

import com.dwarfeng.familyhelper.webapi.sdk.bean.disp.notify.JSFixedFastJsonDispRouterInfo;
import com.dwarfeng.familyhelper.webapi.sdk.bean.disp.notify.JSFixedFastJsonDispSenderRelation;
import com.dwarfeng.familyhelper.webapi.sdk.bean.vo.notify.FastJsonTopic;
import com.dwarfeng.familyhelper.webapi.sdk.bean.vo.notify.JSFixedFastJsonNotifySetting;
import com.dwarfeng.familyhelper.webapi.stack.bean.disp.notify.DispRouterInfo;
import com.dwarfeng.familyhelper.webapi.stack.bean.disp.notify.DispSenderRelation;
import com.dwarfeng.familyhelper.webapi.stack.bean.vo.notify.NotifySetting;
import com.dwarfeng.familyhelper.webapi.stack.bean.vo.notify.Topic;
import com.dwarfeng.notify.sdk.bean.entity.*;
import com.dwarfeng.notify.stack.bean.entity.*;
import com.dwarfeng.subgrade.impl.bean.DozerBeanTransformer;
import com.dwarfeng.subgrade.stack.bean.BeanTransformer;
import org.dozer.Mapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration("notify.beanTransformerConfiguration")
public class BeanTransformerConfiguration {

    @SuppressWarnings("FieldCanBeLocal")
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

    @Bean("notify.dispRouterInfoBeanTransformer")
    public BeanTransformer<DispRouterInfo, JSFixedFastJsonDispRouterInfo> dispRouterInfoBeanTransformer() {
        return new DozerBeanTransformer<>(DispRouterInfo.class, JSFixedFastJsonDispRouterInfo.class, mapper);
    }

    @Bean("notify.routerSupportBeanTransformer")
    public BeanTransformer<RouterSupport, FastJsonRouterSupport> routerSupportBeanTransformer() {
        return new DozerBeanTransformer<>(RouterSupport.class, FastJsonRouterSupport.class, mapper);
    }

    @Bean("notify.senderInfoBeanTransformer")
    public BeanTransformer<SenderInfo, JSFixedFastJsonSenderInfo> senderInfoBeanTransformer() {
        return new DozerBeanTransformer<>(SenderInfo.class, JSFixedFastJsonSenderInfo.class, mapper);
    }

    @Bean("notify.senderSupportBeanTransformer")
    public BeanTransformer<SenderSupport, FastJsonSenderSupport> senderSupportBeanTransformer() {
        return new DozerBeanTransformer<>(SenderSupport.class, FastJsonSenderSupport.class, mapper);
    }

    @Bean("notify.senderRelationBeanTransformer")
    public BeanTransformer<SenderRelation, JSFixedFastJsonSenderRelation> senderRelationBeanTransformer() {
        return new DozerBeanTransformer<>(SenderRelation.class, JSFixedFastJsonSenderRelation.class, mapper);
    }

    @Bean("notify.dispSenderRelationBeanTransformer")
    public BeanTransformer<DispSenderRelation, JSFixedFastJsonDispSenderRelation> dispSenderRelationBeanTransformer() {
        return new DozerBeanTransformer<>(DispSenderRelation.class, JSFixedFastJsonDispSenderRelation.class, mapper);
    }
}
