package com.dwarfeng.familyhelper.webapi.sdk.bean.notify;

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
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;

/**
 * Bean 映射器。
 *
 * <p>
 * 该映射器中包含了 <code>sdk</code> 模块中所有实体与 <code>stack</code> 模块中对应实体的映射方法。
 *
 * @author DwArFeng
 * @since 1.6.0
 */
@Mapper
public interface BeanMapper {

    JSFixedFastJsonNotifySetting notifySettingToJsFixedFastJson(NotifySetting notifySetting);

    @InheritInverseConfiguration
    NotifySetting notifySettingFromJsFixedFastJson(JSFixedFastJsonNotifySetting jsFixedFastJsonNotifySetting);

    FastJsonTopic topicToFastJson(Topic topic);

    @InheritInverseConfiguration
    Topic topicFromFastJson(FastJsonTopic fastJsonTopic);

    JSFixedFastJsonRouterInfo routerInfoToJsFixedFastJson(RouterInfo routerInfo);

    @InheritInverseConfiguration
    RouterInfo routerInfoFromJsFixedFastJson(JSFixedFastJsonRouterInfo jsFixedFastJsonRouterInfo);

    FastJsonRouterSupport routerSupportToFastJson(RouterSupport routerSupport);

    @InheritInverseConfiguration
    RouterSupport routerSupportFromFastJson(FastJsonRouterSupport fastJsonRouterSupport);

    FastJsonDispatcherInfo dispatcherInfoToFastJson(DispatcherInfo dispatcherInfo);

    @InheritInverseConfiguration
    DispatcherInfo dispatcherInfoFromFastJson(FastJsonDispatcherInfo fastJsonDispatcherInfo);

    FastJsonDispatcherSupport dispatcherSupportToFastJson(DispatcherSupport dispatcherSupport);

    @InheritInverseConfiguration
    DispatcherSupport dispatcherSupportFromFastJson(FastJsonDispatcherSupport fastJsonDispatcherSupport);

    JSFixedFastJsonSenderInfo senderInfoToJsFixedFastJson(SenderInfo senderInfo);

    @InheritInverseConfiguration
    SenderInfo senderInfoFromJsFixedFastJson(JSFixedFastJsonSenderInfo jsFixedFastJsonSenderInfo);

    FastJsonSenderSupport senderSupportToFastJson(SenderSupport senderSupport);

    @InheritInverseConfiguration
    SenderSupport senderSupportFromFastJson(FastJsonSenderSupport fastJsonSenderSupport);

    JSFixedFastJsonMeta metaToJsFixedFastJson(Meta meta);

    @InheritInverseConfiguration
    Meta metaFromJsFixedFastJson(JSFixedFastJsonMeta jsFixedFastJsonMeta);

    JSFixedFastJsonDispMeta dispMetaToJsFixedFastJson(DispMeta dispMeta);

    @InheritInverseConfiguration
    DispMeta dispMetaFromJsFixedFastJson(JSFixedFastJsonDispMeta jsFixedFastJsonDispMeta);

    FastJsonMetaIndicator metaIndicatorToFastJson(MetaIndicator metaIndicator);

    @InheritInverseConfiguration
    MetaIndicator metaIndicatorFromFastJson(FastJsonMetaIndicator fastJsonMetaIndicator);

    JSFixedFastJsonNotifyHistory notifyHistoryToJsFixedFastJson(NotifyHistory notifyHistory);

    @InheritInverseConfiguration
    NotifyHistory notifyHistoryFromJsFixedFastJson(JSFixedFastJsonNotifyHistory jsFixedFastJsonNotifyHistory);

    JSFixedFastJsonDispNotifyHistory dispNotifyHistoryToJsFixedFastJson(DispNotifyHistory dispNotifyHistory);

    @InheritInverseConfiguration
    DispNotifyHistory dispNotifyHistoryFromJsFixedFastJson(
            JSFixedFastJsonDispNotifyHistory jsFixedFastJsonDispNotifyHistory
    );

    JSFixedFastJsonNotifyInfoRecord notifyInfoRecordToJsFixedFastJson(NotifyInfoRecord notifyInfoRecord);

    @InheritInverseConfiguration
    NotifyInfoRecord notifyInfoRecordFromJsFixedFastJson(
            JSFixedFastJsonNotifyInfoRecord jsFixedFastJsonNotifyInfoRecord
    );

    JSFixedFastJsonDispNotifyInfoRecord dispNotifyInfoRecordToJsFixedFastJson(
            DispNotifyInfoRecord dispNotifyInfoRecord
    );

    @InheritInverseConfiguration
    DispNotifyInfoRecord dispNotifyInfoRecordFromJsFixedFastJson(
            JSFixedFastJsonDispNotifyInfoRecord jsFixedFastJsonDispNotifyInfoRecord
    );

    JSFixedFastJsonNotifySendRecord notifySendRecordToJsFixedFastJson(NotifySendRecord notifySendRecord);

    @InheritInverseConfiguration
    NotifySendRecord notifySendRecordFromJsFixedFastJson(
            JSFixedFastJsonNotifySendRecord jsFixedFastJsonNotifySendRecord
    );

    JSFixedFastJsonDispNotifySendRecord dispNotifySendRecordToJsFixedFastJson(
            DispNotifySendRecord dispNotifySendRecord
    );

    @InheritInverseConfiguration
    DispNotifySendRecord dispNotifySendRecordFromJsFixedFastJson(
            JSFixedFastJsonDispNotifySendRecord jsFixedFastJsonDispNotifySendRecord
    );
}
