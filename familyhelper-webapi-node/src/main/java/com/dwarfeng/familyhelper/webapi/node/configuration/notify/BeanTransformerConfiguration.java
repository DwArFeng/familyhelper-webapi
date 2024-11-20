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
import com.dwarfeng.subgrade.impl.bean.MapStructBeanTransformer;
import com.dwarfeng.subgrade.stack.bean.BeanTransformer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration("notify.beanTransformerConfiguration")
public class BeanTransformerConfiguration {

    @Bean("notify.notifySettingBeanTransformer")
    public BeanTransformer<NotifySetting, JSFixedFastJsonNotifySetting> notifySettingBeanTransformer() {
        return new MapStructBeanTransformer<>(
                NotifySetting.class, JSFixedFastJsonNotifySetting.class, FastJsonMapper.class
        );
    }

    @Bean("notify.topicBeanTransformer")
    public BeanTransformer<Topic, FastJsonTopic> topicBeanTransformer() {
        return new MapStructBeanTransformer<>(Topic.class, FastJsonTopic.class, FastJsonMapper.class);
    }

    @Bean("notify.routerInfoBeanTransformer")
    public BeanTransformer<RouterInfo, JSFixedFastJsonRouterInfo> routerInfoBeanTransformer() {
        return new MapStructBeanTransformer<>(RouterInfo.class, JSFixedFastJsonRouterInfo.class, FastJsonMapper.class);
    }

    @Bean("notify.routerSupportBeanTransformer")
    public BeanTransformer<RouterSupport, FastJsonRouterSupport> routerSupportBeanTransformer() {
        return new MapStructBeanTransformer<>(RouterSupport.class, FastJsonRouterSupport.class, FastJsonMapper.class);
    }

    @Bean("notify.dispatcherInfoBeanTransformer")
    public BeanTransformer<DispatcherInfo, FastJsonDispatcherInfo> dispatcherInfoBeanTransformer() {
        return new MapStructBeanTransformer<>(
                DispatcherInfo.class, FastJsonDispatcherInfo.class, FastJsonMapper.class
        );
    }

    @Bean("notify.dispatcherSupportBeanTransformer")
    public BeanTransformer<DispatcherSupport, FastJsonDispatcherSupport> dispatcherSupportBeanTransformer() {
        return new MapStructBeanTransformer<>(
                DispatcherSupport.class, FastJsonDispatcherSupport.class, FastJsonMapper.class
        );
    }

    @Bean("notify.senderInfoBeanTransformer")
    public BeanTransformer<SenderInfo, JSFixedFastJsonSenderInfo> senderInfoBeanTransformer() {
        return new MapStructBeanTransformer<>(SenderInfo.class, JSFixedFastJsonSenderInfo.class, FastJsonMapper.class);
    }

    @Bean("notify.senderSupportBeanTransformer")
    public BeanTransformer<SenderSupport, FastJsonSenderSupport> senderSupportBeanTransformer() {
        return new MapStructBeanTransformer<>(SenderSupport.class, FastJsonSenderSupport.class, FastJsonMapper.class);
    }

    @Bean("notify.metaBeanTransformer")
    public BeanTransformer<Meta, JSFixedFastJsonMeta> metaBeanTransformer() {
        return new MapStructBeanTransformer<>(Meta.class, JSFixedFastJsonMeta.class, FastJsonMapper.class);
    }

    @Bean("notify.dispMetaBeanTransformer")
    public BeanTransformer<DispMeta, JSFixedFastJsonDispMeta> dispMetaBeanTransformer() {
        return new MapStructBeanTransformer<>(DispMeta.class, JSFixedFastJsonDispMeta.class, FastJsonMapper.class);
    }

    @Bean("notify.metaIndicatorBeanTransformer")
    public BeanTransformer<MetaIndicator, FastJsonMetaIndicator> metaIndicatorBeanTransformer() {
        return new MapStructBeanTransformer<>(MetaIndicator.class, FastJsonMetaIndicator.class, FastJsonMapper.class);
    }

    @Bean("notify.notifyHistoryBeanTransformer")
    public BeanTransformer<NotifyHistory, JSFixedFastJsonNotifyHistory> notifyHistoryBeanTransformer() {
        return new MapStructBeanTransformer<>(
                NotifyHistory.class, JSFixedFastJsonNotifyHistory.class, FastJsonMapper.class
        );
    }

    @Bean("notify.dispNotifyHistoryBeanTransformer")
    public BeanTransformer<DispNotifyHistory, JSFixedFastJsonDispNotifyHistory> dispNotifyHistoryBeanTransformer() {
        return new MapStructBeanTransformer<>(
                DispNotifyHistory.class, JSFixedFastJsonDispNotifyHistory.class, FastJsonMapper.class
        );
    }

    @Bean("notify.notifyInfoRecordBeanTransformer")
    public BeanTransformer<NotifyInfoRecord, JSFixedFastJsonNotifyInfoRecord> notifyInfoRecordBeanTransformer() {
        return new MapStructBeanTransformer<>(
                NotifyInfoRecord.class, JSFixedFastJsonNotifyInfoRecord.class, FastJsonMapper.class
        );
    }

    @Bean("notify.dispNotifyInfoRecordBeanTransformer")
    public BeanTransformer<DispNotifyInfoRecord, JSFixedFastJsonDispNotifyInfoRecord>
    dispNotifyInfoRecordBeanTransformer() {
        return new MapStructBeanTransformer<>(
                DispNotifyInfoRecord.class, JSFixedFastJsonDispNotifyInfoRecord.class, FastJsonMapper.class
        );
    }

    @Bean("notify.notifySendRecordBeanTransformer")
    public BeanTransformer<NotifySendRecord, JSFixedFastJsonNotifySendRecord> notifySendRecordBeanTransformer() {
        return new MapStructBeanTransformer<>(
                NotifySendRecord.class, JSFixedFastJsonNotifySendRecord.class, FastJsonMapper.class
        );
    }

    @Bean("notify.dispNotifySendRecordBeanTransformer")
    public BeanTransformer<DispNotifySendRecord, JSFixedFastJsonDispNotifySendRecord>
    dispNotifySendRecordBeanTransformer() {
        return new MapStructBeanTransformer<>(
                DispNotifySendRecord.class, JSFixedFastJsonDispNotifySendRecord.class, FastJsonMapper.class
        );
    }
}
