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
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;

/**
 * FastJson Bean 映射器。
 *
 * @author DwArFeng
 * @since 1.4.0
 */
@Mapper
public interface FastJsonMapper {

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
