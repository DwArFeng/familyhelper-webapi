package com.dwarfeng.familyhelper.webapi.node.configuration.life;

import com.dwarfeng.familyhelper.life.sdk.bean.entity.*;
import com.dwarfeng.familyhelper.life.stack.bean.entity.*;
import com.dwarfeng.familyhelper.webapi.sdk.bean.disp.life.*;
import com.dwarfeng.familyhelper.webapi.stack.bean.disp.life.*;
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

    JSFixedFastJsonPbSet pbSetToJsFixedFastJson(PbSet pbSet);

    @InheritInverseConfiguration
    PbSet pbSetFromJsFixedFastJson(JSFixedFastJsonPbSet jsFixedFastJsonPbSet);

    JSFixedFastJsonDispPbSet dispPbSetToJsFixedFastJson(DispPbSet dispPbSet);

    @InheritInverseConfiguration
    DispPbSet dispPbSetFromJsFixedFastJson(JSFixedFastJsonDispPbSet jsFixedFastJsonDispPbSet);

    JSFixedFastJsonPopb popbToJsFixedFastJson(Popb popb);

    @InheritInverseConfiguration
    Popb popbFromJsFixedFastJson(JSFixedFastJsonPopb jsFixedFastJsonPopb);

    JSFixedFastJsonDispPopb dispPopbToJsFixedFastJson(DispPopb dispPopb);

    @InheritInverseConfiguration
    DispPopb dispPopbFromJsFixedFastJson(JSFixedFastJsonDispPopb jsFixedFastJsonDispPopb);

    JSFixedFastJsonPbNode pbNodeToJsFixedFastJson(PbNode pbNode);

    @InheritInverseConfiguration
    PbNode pbNodeFromJsFixedFastJson(JSFixedFastJsonPbNode jsFixedFastJsonPbNode);

    JSFixedFastJsonDispPbNode dispPbNodeToJsFixedFastJson(DispPbNode dispPbNode);

    @InheritInverseConfiguration
    DispPbNode dispPbNodeFromJsFixedFastJson(JSFixedFastJsonDispPbNode jsFixedFastJsonDispPbNode);

    JSFixedFastJsonPbItem pbItemToJsFixedFastJson(PbItem pbItem);

    @InheritInverseConfiguration
    PbItem pbItemFromJsFixedFastJson(JSFixedFastJsonPbItem jsFixedFastJsonPbItem);

    JSFixedFastJsonDispPbItem dispPbItemToJsFixedFastJson(DispPbItem dispPbItem);

    @InheritInverseConfiguration
    DispPbItem dispPbItemFromJsFixedFastJson(JSFixedFastJsonDispPbItem jsFixedFastJsonDispPbItem);

    JSFixedFastJsonPbRecord pbRecordToJsFixedFastJson(PbRecord pbRecord);

    @InheritInverseConfiguration
    PbRecord pbRecordFromJsFixedFastJson(JSFixedFastJsonPbRecord jsFixedFastJsonPbRecord);

    JSFixedFastJsonPbFileInfo pbFileInfoToJsFixedFastJson(PbFileInfo pbFileInfo);

    @InheritInverseConfiguration
    PbFileInfo pbFileInfoFromJsFixedFastJson(JSFixedFastJsonPbFileInfo jsFixedFastJsonPbFileInfo);

    FastJsonActivityTypeIndicator activityTypeIndicatorToFastJson(ActivityTypeIndicator activityTypeIndicator);

    @InheritInverseConfiguration
    ActivityTypeIndicator activityTypeIndicatorFromFastJson(
            FastJsonActivityTypeIndicator fastJsonActivityTypeIndicator
    );

    JSFixedFastJsonActivityTemplateDriverInfo activityTemplateDriverInfoToJsFixedFastJson(
            ActivityTemplateDriverInfo activityTemplateDriverInfo
    );

    @InheritInverseConfiguration
    ActivityTemplateDriverInfo activityTemplateDriverInfoFromJsFixedFastJson(
            JSFixedFastJsonActivityTemplateDriverInfo jsFixedFastJsonActivityTemplateDriverInfo
    );

    FastJsonActivityTemplateDriverSupport activityTemplateDriverSupportToFastJson(
            ActivityTemplateDriverSupport activityTemplateDriverSupport
    );

    @InheritInverseConfiguration
    ActivityTemplateDriverSupport activityTemplateDriverSupportFromFastJson(
            FastJsonActivityTemplateDriverSupport fastJsonActivityTemplateDriverSupport
    );

    JSFixedFastJsonActivityDataSet activityDataSetToJsFixedFastJson(ActivityDataSet activityDataSet);

    @InheritInverseConfiguration
    ActivityDataSet activityDataSetFromJsFixedFastJson(JSFixedFastJsonActivityDataSet jsFixedFastJsonActivityDataSet);

    JSFixedFastJsonDispActivityDataSet dispActivityDataSetToJsFixedFastJson(DispActivityDataSet dispActivityDataSet);

    @InheritInverseConfiguration
    DispActivityDataSet dispActivityDataSetFromJsFixedFastJson(
            JSFixedFastJsonDispActivityDataSet jsFixedFastJsonDispActivityDataSet
    );

    JSFixedFastJsonPoad poadToJsFixedFastJson(Poad poad);

    @InheritInverseConfiguration
    Poad poadFromJsFixedFastJson(JSFixedFastJsonPoad jsFixedFastJsonPoad);

    JSFixedFastJsonDispPoad dispPoadToJsFixedFastJson(DispPoad dispPoad);

    @InheritInverseConfiguration
    DispPoad dispPoadFromJsFixedFastJson(JSFixedFastJsonDispPoad jsFixedFastJsonDispPoad);

    JSFixedFastJsonActivityDataNode activityDataNodeToJsFixedFastJson(ActivityDataNode activityDataNode);

    @InheritInverseConfiguration
    ActivityDataNode activityDataNodeFromJsFixedFastJson(
            JSFixedFastJsonActivityDataNode jsFixedFastJsonActivityDataNode
    );

    JSFixedFastJsonDispActivityDataNode dispActivityDataNodeToJsFixedFastJson(
            DispActivityDataNode dispActivityDataNode
    );

    @InheritInverseConfiguration
    DispActivityDataNode dispActivityDataNodeFromJsFixedFastJson(
            JSFixedFastJsonDispActivityDataNode jsFixedFastJsonDispActivityDataNode
    );

    JSFixedFastJsonActivityDataItem activityDataItemToJsFixedFastJson(ActivityDataItem activityDataItem);

    @InheritInverseConfiguration
    ActivityDataItem activityDataItemFromJsFixedFastJson(
            JSFixedFastJsonActivityDataItem jsFixedFastJsonActivityDataItem
    );

    JSFixedFastJsonDispActivityDataItem dispActivityDataItemToJsFixedFastJson(
            DispActivityDataItem dispActivityDataItem
    );

    @InheritInverseConfiguration
    DispActivityDataItem dispActivityDataItemFromJsFixedFastJson(
            JSFixedFastJsonDispActivityDataItem jsFixedFastJsonDispActivityDataItem
    );

    JSFixedFastJsonActivityDataRecord activityDataRecordToJsFixedFastJson(ActivityDataRecord activityDataRecord);

    @InheritInverseConfiguration
    ActivityDataRecord activityDataRecordFromJsFixedFastJson(
            JSFixedFastJsonActivityDataRecord jsFixedFastJsonActivityDataRecord
    );

    JSFixedFastJsonDispActivityDataRecord dispActivityDataRecordToJsFixedFastJson(
            DispActivityDataRecord dispActivityDataRecord
    );

    @InheritInverseConfiguration
    DispActivityDataRecord dispActivityDataRecordFromJsFixedFastJson(
            JSFixedFastJsonDispActivityDataRecord jsFixedFastJsonDispActivityDataRecord
    );

    JSFixedFastJsonActivityTemplate activityTemplateToJsFixedFastJson(ActivityTemplate activityTemplate);

    @InheritInverseConfiguration
    ActivityTemplate activityTemplateFromJsFixedFastJson(
            JSFixedFastJsonActivityTemplate jsFixedFastJsonActivityTemplate
    );

    JSFixedFastJsonDispActivityTemplate dispActivityTemplateToJsFixedFastJson(
            DispActivityTemplate dispActivityTemplate
    );

    @InheritInverseConfiguration
    DispActivityTemplate dispActivityTemplateFromJsFixedFastJson(
            JSFixedFastJsonDispActivityTemplate jsFixedFastJsonDispActivityTemplate
    );

    JSFixedFastJsonPoat poatToJsFixedFastJson(Poat poat);

    @InheritInverseConfiguration
    Poat poatFromJsFixedFastJson(JSFixedFastJsonPoat jsFixedFastJsonPoat);

    JSFixedFastJsonDispPoat dispPoatToJsFixedFastJson(DispPoat dispPoat);

    @InheritInverseConfiguration
    DispPoat dispPoatFromJsFixedFastJson(JSFixedFastJsonDispPoat jsFixedFastJsonDispPoat);

    JSFixedFastJsonPoatac poatacToJsFixedFastJson(Poatac poatac);

    @InheritInverseConfiguration
    Poatac poatacFromJsFixedFastJson(JSFixedFastJsonPoatac jsFixedFastJsonPoatac);

    JSFixedFastJsonDispPoatac dispPoatacToJsFixedFastJson(DispPoatac dispPoatac);

    @InheritInverseConfiguration
    DispPoatac dispPoatacFromJsFixedFastJson(JSFixedFastJsonDispPoatac jsFixedFastJsonDispPoatac);

    JSFixedFastJsonActivityTemplateCoverInfo activityTemplateCoverInfoToJsFixedFastJson(
            ActivityTemplateCoverInfo activityTemplateCoverInfo
    );

    @InheritInverseConfiguration
    ActivityTemplateCoverInfo activityTemplateCoverInfoFromJsFixedFastJson(
            JSFixedFastJsonActivityTemplateCoverInfo jsFixedFastJsonActivityTemplateCoverInfo
    );

    JSFixedFastJsonActivityTemplateDataInfo activityTemplateDataInfoToJsFixedFastJson(
            ActivityTemplateDataInfo activityTemplateDataInfo
    );

    @InheritInverseConfiguration
    ActivityTemplateDataInfo activityTemplateDataInfoFromJsFixedFastJson(
            JSFixedFastJsonActivityTemplateDataInfo jsFixedFastJsonActivityTemplateDataInfo
    );

    JSFixedFastJsonDispActivityTemplateDataInfo dispActivityTemplateDataInfoToJsFixedFastJson(
            DispActivityTemplateDataInfo dispActivityTemplateDataInfo
    );

    @InheritInverseConfiguration
    DispActivityTemplateDataInfo dispActivityTemplateDataInfoFromJsFixedFastJson(
            JSFixedFastJsonDispActivityTemplateDataInfo jsFixedFastJsonDispActivityTemplateDataInfo
    );

    JSFixedFastJsonActivityTemplateParticipant activityTemplateParticipantToJsFixedFastJson(
            ActivityTemplateParticipant activityTemplateParticipant
    );

    @InheritInverseConfiguration
    ActivityTemplateParticipant activityTemplateParticipantFromJsFixedFastJson(
            JSFixedFastJsonActivityTemplateParticipant jsFixedFastJsonActivityTemplateParticipant
    );

    JSFixedFastJsonDispActivityTemplateParticipant dispActivityTemplateParticipantToJsFixedFastJson(
            DispActivityTemplateParticipant dispActivityTemplateParticipant
    );

    @InheritInverseConfiguration
    DispActivityTemplateParticipant dispActivityTemplateParticipantFromJsFixedFastJson(
            JSFixedFastJsonDispActivityTemplateParticipant jsFixedFastJsonDispActivityTemplateParticipant
    );

    JSFixedFastJsonActivityTemplateFileInfo activityTemplateFileInfoToJsFixedFastJson(
            ActivityTemplateFileInfo activityTemplateFileInfo
    );

    @InheritInverseConfiguration
    ActivityTemplateFileInfo activityTemplateFileInfoFromJsFixedFastJson(
            JSFixedFastJsonActivityTemplateFileInfo jsFixedFastJsonActivityTemplateFileInfo
    );

    JSFixedFastJsonActivity activityToJsFixedFastJson(Activity activity);

    @InheritInverseConfiguration
    Activity activityFromJsFixedFastJson(JSFixedFastJsonActivity jsFixedFastJsonActivity);

    JSFixedFastJsonDispActivity dispActivityToJsFixedFastJson(DispActivity dispActivity);

    @InheritInverseConfiguration
    DispActivity dispActivityFromJsFixedFastJson(JSFixedFastJsonDispActivity jsFixedFastJsonDispActivity);

    JSFixedFastJsonPoac poacToJsFixedFastJson(Poac poac);

    @InheritInverseConfiguration
    Poac poacFromJsFixedFastJson(JSFixedFastJsonPoac jsFixedFastJsonPoac);

    JSFixedFastJsonDispPoac dispPoacToJsFixedFastJson(DispPoac dispPoac);

    @InheritInverseConfiguration
    DispPoac dispPoacFromJsFixedFastJson(JSFixedFastJsonDispPoac jsFixedFastJsonDispPoac);

    JSFixedFastJsonActivityCoverInfo activityCoverInfoToJsFixedFastJson(ActivityCoverInfo activityCoverInfo);

    @InheritInverseConfiguration
    ActivityCoverInfo activityCoverInfoFromJsFixedFastJson(
            JSFixedFastJsonActivityCoverInfo jsFixedFastJsonActivityCoverInfo
    );

    JSFixedFastJsonActivityParticipant activityParticipantToJsFixedFastJson(ActivityParticipant activityParticipant);

    @InheritInverseConfiguration
    ActivityParticipant activityParticipantFromJsFixedFastJson(
            JSFixedFastJsonActivityParticipant jsFixedFastJsonActivityParticipant
    );

    JSFixedFastJsonDispActivityParticipant dispActivityParticipantToJsFixedFastJson(
            DispActivityParticipant dispActivityParticipant
    );

    @InheritInverseConfiguration
    DispActivityParticipant dispActivityParticipantFromJsFixedFastJson(
            JSFixedFastJsonDispActivityParticipant jsFixedFastJsonDispActivityParticipant
    );

    JSFixedFastJsonActivityFileInfo activityFileInfoToJsFixedFastJson(ActivityFileInfo activityFileInfo);

    @InheritInverseConfiguration
    ActivityFileInfo activityFileInfoFromJsFixedFastJson(
            JSFixedFastJsonActivityFileInfo jsFixedFastJsonActivityFileInfo
    );
}
