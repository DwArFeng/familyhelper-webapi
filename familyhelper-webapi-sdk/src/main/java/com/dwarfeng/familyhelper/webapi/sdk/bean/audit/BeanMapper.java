package com.dwarfeng.familyhelper.webapi.sdk.bean.audit;

import com.dwarfeng.audit.sdk.bean.entity.FastJsonInspectionAlarmTypeIndicator;
import com.dwarfeng.audit.stack.bean.entity.InspectionAlarmTypeIndicator;
import com.dwarfeng.familyhelper.webapi.sdk.bean.audit.disp.*;
import com.dwarfeng.familyhelper.webapi.sdk.bean.audit.dto.WebInputAuditEntryCompositeLookupInfo;
import com.dwarfeng.familyhelper.webapi.sdk.bean.audit.dto.WebInputAuditEntryGroupedLookupInfo;
import com.dwarfeng.familyhelper.webapi.sdk.bean.audit.dto.WebInputAuditRecordInfo;
import com.dwarfeng.familyhelper.webapi.sdk.bean.audit.dto.WebInputInspectorVariableUpsertInfo;
import com.dwarfeng.familyhelper.webapi.stack.bean.audit.disp.*;
import com.dwarfeng.familyhelper.webapi.stack.bean.audit.dto.AuditEntryCompositeLookupInfo;
import com.dwarfeng.familyhelper.webapi.stack.bean.audit.dto.AuditEntryGroupedLookupInfo;
import com.dwarfeng.familyhelper.webapi.stack.bean.audit.dto.AuditRecordInfo;
import com.dwarfeng.familyhelper.webapi.stack.bean.audit.dto.InspectorVariableUpsertInfo;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;

/**
 * Bean 映射器。
 *
 * <p>
 * 该映射器中包含了 <code>sdk</code> 模块中所有实体与 <code>stack</code> 模块中对应实体的映射方法。
 *
 * @author DwArFeng
 * @since 2.2.0
 */
@Mapper
public interface BeanMapper {

    // region Familyhelper-webapi Disp

    JSFixedFastJsonDispAuditEntry dispAuditEntryToJSFixedFastJson(DispAuditEntry dispAuditEntry);

    @InheritInverseConfiguration
    DispAuditEntry dispAuditEntryFromJSFixedFastJson(JSFixedFastJsonDispAuditEntry jsFixedFastJsonDispAuditEntry);

    JSFixedFastJsonDispAuditEntryProperty dispAuditEntryPropertyToJSFixedFastJson(
            DispAuditEntryProperty dispAuditEntryProperty
    );

    @InheritInverseConfiguration
    DispAuditEntryProperty dispAuditEntryPropertyFromJSFixedFastJson(
            JSFixedFastJsonDispAuditEntryProperty jsFixedFastJsonDispAuditEntryProperty
    );

    JSFixedFastJsonDispAuditPropertyIndicator dispAuditPropertyIndicatorToJSFixedFastJson(
            DispAuditPropertyIndicator dispAuditPropertyIndicator
    );

    @InheritInverseConfiguration
    DispAuditPropertyIndicator dispAuditPropertyIndicatorFromJSFixedFastJson(
            JSFixedFastJsonDispAuditPropertyIndicator jsFixedFastJsonDispAuditPropertyIndicator
    );

    JSFixedFastJsonDispInspectionAlarm dispInspectionAlarmToJSFixedFastJson(
            DispInspectionAlarm dispInspectionAlarm
    );

    @InheritInverseConfiguration
    DispInspectionAlarm dispInspectionAlarmFromJSFixedFastJson(
            JSFixedFastJsonDispInspectionAlarm jsFixedFastJsonDispInspectionAlarm
    );

    FastJsonInspectionAlarmTypeIndicator inspectionAlarmTypeIndicatorToFastJson(
            InspectionAlarmTypeIndicator inspectionAlarmTypeIndicator
    );

    @InheritInverseConfiguration
    InspectionAlarmTypeIndicator inspectionAlarmTypeIndicatorFromFastJson(
            FastJsonInspectionAlarmTypeIndicator fastJsonInspectionAlarmTypeIndicator
    );

    JSFixedFastJsonDispInspectionDriverInfo dispInspectionDriverInfoToJSFixedFastJson(
            DispInspectionDriverInfo dispInspectionDriverInfo
    );

    @InheritInverseConfiguration
    DispInspectionDriverInfo dispInspectionDriverInfoFromJSFixedFastJson(
            JSFixedFastJsonDispInspectionDriverInfo jsFixedFastJsonDispInspectionDriverInfo
    );

    JSFixedFastJsonDispInspectionTask dispInspectionTaskToJSFixedFastJson(DispInspectionTask dispInspectionTask);

    @InheritInverseConfiguration
    DispInspectionTask dispInspectionTaskFromJSFixedFastJson(
            JSFixedFastJsonDispInspectionTask jsFixedFastJsonDispInspectionTask
    );

    JSFixedFastJsonDispInspectionTaskEvent dispInspectionTaskEventToJSFixedFastJson(
            DispInspectionTaskEvent dispInspectionTaskEvent
    );

    @InheritInverseConfiguration
    DispInspectionTaskEvent dispInspectionTaskEventFromJSFixedFastJson(
            JSFixedFastJsonDispInspectionTaskEvent jsFixedFastJsonDispInspectionTaskEvent
    );

    JSFixedFastJsonDispInspectorInfo dispInspectorInfoToJSFixedFastJson(DispInspectorInfo dispInspectorInfo);

    @InheritInverseConfiguration
    DispInspectorInfo dispInspectorInfoFromJSFixedFastJson(
            JSFixedFastJsonDispInspectorInfo jsFixedFastJsonDispInspectorInfo
    );

    JSFixedFastJsonDispInspectorVariable dispInspectorVariableToJSFixedFastJson(
            DispInspectorVariable dispInspectorVariable
    );

    @InheritInverseConfiguration
    DispInspectorVariable dispInspectorVariableFromJSFixedFastJson(
            JSFixedFastJsonDispInspectorVariable jsFixedFastJsonDispInspectorVariable
    );

    // endregion

    // region Familyhelper-webapi DTO

    WebInputAuditEntryCompositeLookupInfo auditEntryCompositeLookupInfoToWebInput(
            AuditEntryCompositeLookupInfo auditEntryCompositeLookupInfo
    );

    @InheritInverseConfiguration
    AuditEntryCompositeLookupInfo auditEntryCompositeLookupInfoFromWebInput(
            WebInputAuditEntryCompositeLookupInfo webInputAuditEntryCompositeLookupInfo
    );

    WebInputAuditEntryGroupedLookupInfo auditEntryGroupedLookupInfoToWebInput(
            AuditEntryGroupedLookupInfo auditEntryGroupedLookupInfo
    );

    @InheritInverseConfiguration
    AuditEntryGroupedLookupInfo auditEntryGroupedLookupInfoFromWebInput(
            WebInputAuditEntryGroupedLookupInfo webInputAuditEntryGroupedLookupInfo
    );

    WebInputAuditRecordInfo auditRecordInfoToWebInput(AuditRecordInfo auditRecordInfo);

    @InheritInverseConfiguration
    AuditRecordInfo auditRecordInfoFromWebInput(WebInputAuditRecordInfo webInputAuditRecordInfo);

    WebInputAuditRecordInfo.PropertyItem auditRecordPropertyItemToWebInput(
            AuditRecordInfo.PropertyItem propertyItem
    );

    @InheritInverseConfiguration
    AuditRecordInfo.PropertyItem auditRecordPropertyItemFromWebInput(
            WebInputAuditRecordInfo.PropertyItem propertyItem
    );

    WebInputInspectorVariableUpsertInfo inspectorVariableUpsertInfoToWebInput(
            InspectorVariableUpsertInfo inspectorVariableUpsertInfo
    );

    @InheritInverseConfiguration
    InspectorVariableUpsertInfo inspectorVariableUpsertInfoFromWebInput(
            WebInputInspectorVariableUpsertInfo webInputInspectorVariableUpsertInfo
    );

    // endregion
}
