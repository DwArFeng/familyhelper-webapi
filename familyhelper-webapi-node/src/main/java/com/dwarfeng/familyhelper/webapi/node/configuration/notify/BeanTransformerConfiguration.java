package com.dwarfeng.familyhelper.webapi.node.configuration.notify;

import com.dwarfeng.familyhelper.webapi.sdk.bean.notify.BeanMapper;
import com.dwarfeng.familyhelper.webapi.sdk.bean.notify.disp.JSFixedFastJsonDispMeta;
import com.dwarfeng.familyhelper.webapi.sdk.bean.notify.disp.JSFixedFastJsonDispNotifyHistory;
import com.dwarfeng.familyhelper.webapi.sdk.bean.notify.disp.JSFixedFastJsonDispNotifyInfoRecord;
import com.dwarfeng.familyhelper.webapi.sdk.bean.notify.disp.JSFixedFastJsonDispNotifySendRecord;
import com.dwarfeng.familyhelper.webapi.stack.bean.notify.disp.DispMeta;
import com.dwarfeng.familyhelper.webapi.stack.bean.notify.disp.DispNotifyHistory;
import com.dwarfeng.familyhelper.webapi.stack.bean.notify.disp.DispNotifyInfoRecord;
import com.dwarfeng.familyhelper.webapi.stack.bean.notify.disp.DispNotifySendRecord;
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
                NotifySetting.class, JSFixedFastJsonNotifySetting.class, BeanMapper.class
        );
    }

    @Bean("notify.topicBeanTransformer")
    public BeanTransformer<Topic, FastJsonTopic> topicBeanTransformer() {
        return new MapStructBeanTransformer<>(Topic.class, FastJsonTopic.class, BeanMapper.class);
    }

    @Bean("notify.routerInfoBeanTransformer")
    public BeanTransformer<RouterInfo, JSFixedFastJsonRouterInfo> routerInfoBeanTransformer() {
        return new MapStructBeanTransformer<>(RouterInfo.class, JSFixedFastJsonRouterInfo.class, BeanMapper.class);
    }

    @Bean("notify.routerSupportBeanTransformer")
    public BeanTransformer<RouterSupport, FastJsonRouterSupport> routerSupportBeanTransformer() {
        return new MapStructBeanTransformer<>(RouterSupport.class, FastJsonRouterSupport.class, BeanMapper.class);
    }

    @Bean("notify.dispatcherInfoBeanTransformer")
    public BeanTransformer<DispatcherInfo, FastJsonDispatcherInfo> dispatcherInfoBeanTransformer() {
        return new MapStructBeanTransformer<>(
                DispatcherInfo.class, FastJsonDispatcherInfo.class, BeanMapper.class
        );
    }

    @Bean("notify.dispatcherSupportBeanTransformer")
    public BeanTransformer<DispatcherSupport, FastJsonDispatcherSupport> dispatcherSupportBeanTransformer() {
        return new MapStructBeanTransformer<>(
                DispatcherSupport.class, FastJsonDispatcherSupport.class, BeanMapper.class
        );
    }

    @Bean("notify.senderInfoBeanTransformer")
    public BeanTransformer<SenderInfo, JSFixedFastJsonSenderInfo> senderInfoBeanTransformer() {
        return new MapStructBeanTransformer<>(SenderInfo.class, JSFixedFastJsonSenderInfo.class, BeanMapper.class);
    }

    @Bean("notify.senderSupportBeanTransformer")
    public BeanTransformer<SenderSupport, FastJsonSenderSupport> senderSupportBeanTransformer() {
        return new MapStructBeanTransformer<>(SenderSupport.class, FastJsonSenderSupport.class, BeanMapper.class);
    }

    @Bean("notify.metaBeanTransformer")
    public BeanTransformer<Meta, JSFixedFastJsonMeta> metaBeanTransformer() {
        return new MapStructBeanTransformer<>(Meta.class, JSFixedFastJsonMeta.class, BeanMapper.class);
    }

    @Bean("notify.dispMetaBeanTransformer")
    public BeanTransformer<DispMeta, JSFixedFastJsonDispMeta> dispMetaBeanTransformer() {
        return new MapStructBeanTransformer<>(DispMeta.class, JSFixedFastJsonDispMeta.class, BeanMapper.class);
    }

    @Bean("notify.metaIndicatorBeanTransformer")
    public BeanTransformer<MetaIndicator, FastJsonMetaIndicator> metaIndicatorBeanTransformer() {
        return new MapStructBeanTransformer<>(MetaIndicator.class, FastJsonMetaIndicator.class, BeanMapper.class);
    }

    @Bean("notify.notifyHistoryBeanTransformer")
    public BeanTransformer<NotifyHistory, JSFixedFastJsonNotifyHistory> notifyHistoryBeanTransformer() {
        return new MapStructBeanTransformer<>(
                NotifyHistory.class, JSFixedFastJsonNotifyHistory.class, BeanMapper.class
        );
    }

    @Bean("notify.dispNotifyHistoryBeanTransformer")
    public BeanTransformer<DispNotifyHistory, JSFixedFastJsonDispNotifyHistory> dispNotifyHistoryBeanTransformer() {
        return new MapStructBeanTransformer<>(
                DispNotifyHistory.class, JSFixedFastJsonDispNotifyHistory.class, BeanMapper.class
        );
    }

    @Bean("notify.notifyInfoRecordBeanTransformer")
    public BeanTransformer<NotifyInfoRecord, JSFixedFastJsonNotifyInfoRecord> notifyInfoRecordBeanTransformer() {
        return new MapStructBeanTransformer<>(
                NotifyInfoRecord.class, JSFixedFastJsonNotifyInfoRecord.class, BeanMapper.class
        );
    }

    @Bean("notify.dispNotifyInfoRecordBeanTransformer")
    public BeanTransformer<DispNotifyInfoRecord, JSFixedFastJsonDispNotifyInfoRecord>
    dispNotifyInfoRecordBeanTransformer() {
        return new MapStructBeanTransformer<>(
                DispNotifyInfoRecord.class, JSFixedFastJsonDispNotifyInfoRecord.class, BeanMapper.class
        );
    }

    @Bean("notify.notifySendRecordBeanTransformer")
    public BeanTransformer<NotifySendRecord, JSFixedFastJsonNotifySendRecord> notifySendRecordBeanTransformer() {
        return new MapStructBeanTransformer<>(
                NotifySendRecord.class, JSFixedFastJsonNotifySendRecord.class, BeanMapper.class
        );
    }

    @Bean("notify.dispNotifySendRecordBeanTransformer")
    public BeanTransformer<DispNotifySendRecord, JSFixedFastJsonDispNotifySendRecord>
    dispNotifySendRecordBeanTransformer() {
        return new MapStructBeanTransformer<>(
                DispNotifySendRecord.class, JSFixedFastJsonDispNotifySendRecord.class, BeanMapper.class
        );
    }
}
