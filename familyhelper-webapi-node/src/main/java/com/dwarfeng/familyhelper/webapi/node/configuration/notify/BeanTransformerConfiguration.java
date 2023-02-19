package com.dwarfeng.familyhelper.webapi.node.configuration.notify;

import com.dwarfeng.familyhelper.webapi.sdk.bean.disp.notify.JSFixedFastJsonDispMeta;
import com.dwarfeng.familyhelper.webapi.sdk.bean.disp.notify.JSFixedFastJsonDispNotifyHistory;
import com.dwarfeng.familyhelper.webapi.sdk.bean.disp.notify.JSFixedFastJsonDispNotifyInfoRecord;
import com.dwarfeng.familyhelper.webapi.sdk.bean.disp.notify.JSFixedFastJsonDispNotifySendRecord;
import com.dwarfeng.familyhelper.webapi.stack.bean.disp.notify.DispMeta;
import com.dwarfeng.familyhelper.webapi.stack.bean.disp.notify.DispNotifyHistory;
import com.dwarfeng.familyhelper.webapi.stack.bean.disp.notify.DispNotifyInfoRecord;
import com.dwarfeng.familyhelper.webapi.stack.bean.disp.notify.DispNotifySendRecord;
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

    @Bean("notify.notifyHistoryBeanTransformer")
    public BeanTransformer<NotifyHistory, JSFixedFastJsonNotifyHistory> notifyHistoryBeanTransformer() {
        return new DozerBeanTransformer<>(NotifyHistory.class, JSFixedFastJsonNotifyHistory.class, mapper);
    }

    @Bean("notify.dispNotifyHistoryBeanTransformer")
    public BeanTransformer<DispNotifyHistory, JSFixedFastJsonDispNotifyHistory> dispNotifyHistoryBeanTransformer() {
        return new DozerBeanTransformer<>(DispNotifyHistory.class, JSFixedFastJsonDispNotifyHistory.class, mapper);
    }

    @Bean("notify.notifyInfoRecordBeanTransformer")
    public BeanTransformer<NotifyInfoRecord, JSFixedFastJsonNotifyInfoRecord> notifyInfoRecordBeanTransformer() {
        return new DozerBeanTransformer<>(NotifyInfoRecord.class, JSFixedFastJsonNotifyInfoRecord.class, mapper);
    }

    @Bean("notify.dispNotifyInfoRecordBeanTransformer")
    public BeanTransformer<DispNotifyInfoRecord, JSFixedFastJsonDispNotifyInfoRecord>
    dispNotifyInfoRecordBeanTransformer() {
        return new DozerBeanTransformer<>(
                DispNotifyInfoRecord.class, JSFixedFastJsonDispNotifyInfoRecord.class, mapper
        );
    }

    @Bean("notify.notifySendRecordBeanTransformer")
    public BeanTransformer<NotifySendRecord, JSFixedFastJsonNotifySendRecord> notifySendRecordBeanTransformer() {
        return new DozerBeanTransformer<>(NotifySendRecord.class, JSFixedFastJsonNotifySendRecord.class, mapper);
    }

    @Bean("notify.dispNotifySendRecordBeanTransformer")
    public BeanTransformer<DispNotifySendRecord, JSFixedFastJsonDispNotifySendRecord>
    dispNotifySendRecordBeanTransformer() {
        return new DozerBeanTransformer<>(
                DispNotifySendRecord.class, JSFixedFastJsonDispNotifySendRecord.class, mapper
        );
    }
}
