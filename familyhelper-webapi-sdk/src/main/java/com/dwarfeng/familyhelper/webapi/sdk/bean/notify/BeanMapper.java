package com.dwarfeng.familyhelper.webapi.sdk.bean.notify;

import com.dwarfeng.familyhelper.webapi.sdk.bean.notify.disp.JSFixedFastJsonDispMeta;
import com.dwarfeng.familyhelper.webapi.sdk.bean.notify.disp.JSFixedFastJsonDispNotifyHistory;
import com.dwarfeng.familyhelper.webapi.sdk.bean.notify.disp.JSFixedFastJsonDispNotifyInfoRecord;
import com.dwarfeng.familyhelper.webapi.sdk.bean.notify.disp.JSFixedFastJsonDispNotifySendRecord;
import com.dwarfeng.familyhelper.webapi.stack.bean.notify.disp.DispMeta;
import com.dwarfeng.familyhelper.webapi.stack.bean.notify.disp.DispNotifyHistory;
import com.dwarfeng.familyhelper.webapi.stack.bean.notify.disp.DispNotifyInfoRecord;
import com.dwarfeng.familyhelper.webapi.stack.bean.notify.disp.DispNotifySendRecord;
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

    // -----------------------------------------------------------Familyhelper-webapi Disp-----------------------------------------------------------
    JSFixedFastJsonDispMeta dispMetaToJSFixedFastJson(DispMeta dispMeta);

    @InheritInverseConfiguration
    DispMeta dispMetaFromJSFixedFastJson(JSFixedFastJsonDispMeta jSFixedFastJsonDispMeta);

    JSFixedFastJsonDispNotifyHistory dispNotifyHistoryToJSFixedFastJson(DispNotifyHistory dispNotifyHistory);

    @InheritInverseConfiguration
    DispNotifyHistory dispNotifyHistoryFromJSFixedFastJson(JSFixedFastJsonDispNotifyHistory jSFixedFastJsonDispNotifyHistory);

    JSFixedFastJsonDispNotifyInfoRecord dispNotifyInfoRecordToJSFixedFastJson(DispNotifyInfoRecord dispNotifyInfoRecord);

    @InheritInverseConfiguration
    DispNotifyInfoRecord dispNotifyInfoRecordFromJSFixedFastJson(JSFixedFastJsonDispNotifyInfoRecord jSFixedFastJsonDispNotifyInfoRecord);

    JSFixedFastJsonDispNotifySendRecord dispNotifySendRecordToJSFixedFastJson(DispNotifySendRecord dispNotifySendRecord);

    @InheritInverseConfiguration
    DispNotifySendRecord dispNotifySendRecordFromJSFixedFastJson(JSFixedFastJsonDispNotifySendRecord jSFixedFastJsonDispNotifySendRecord);
}
