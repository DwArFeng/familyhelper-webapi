package com.dwarfeng.familyhelper.webapi.sdk.bean.life;

import com.dwarfeng.familyhelper.webapi.sdk.bean.life.disp.*;
import com.dwarfeng.familyhelper.webapi.stack.bean.life.disp.*;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;

/**
 * Bean 映射器。
 *
 * <p>
 * 该映射器中包含了 <code>sdk</code> 模块中所有实体与 <code>stack</code> 模块中对应实体的映射方法。
 *
 * @author DwArFeng
 * @since 1.8.1
 */
@Mapper
public interface BeanMapper {

    // -----------------------------------------------------------Familyhelper-webapi Disp-----------------------------------------------------------
    JSFixedFastJsonDispActivity dispActivityToJSFixedFastJson(DispActivity dispActivity);

    @InheritInverseConfiguration
    DispActivity dispActivityFromJSFixedFastJson(JSFixedFastJsonDispActivity jSFixedFastJsonDispActivity);

    JSFixedFastJsonDispActivityDataItem dispActivityDataItemToJSFixedFastJson(
            DispActivityDataItem dispActivityDataItem
    );

    @InheritInverseConfiguration
    DispActivityDataItem dispActivityDataItemFromJSFixedFastJson(
            JSFixedFastJsonDispActivityDataItem jSFixedFastJsonDispActivityDataItem
    );

    JSFixedFastJsonDispActivityDataNode dispActivityDataNodeToJSFixedFastJson(
            DispActivityDataNode dispActivityDataNode
    );

    @InheritInverseConfiguration
    DispActivityDataNode dispActivityDataNodeFromJSFixedFastJson(
            JSFixedFastJsonDispActivityDataNode jSFixedFastJsonDispActivityDataNode
    );

    JSFixedFastJsonDispActivityDataRecord dispActivityDataRecordToJSFixedFastJson(
            DispActivityDataRecord dispActivityDataRecord
    );

    @InheritInverseConfiguration
    DispActivityDataRecord dispActivityDataRecordFromJSFixedFastJson(
            JSFixedFastJsonDispActivityDataRecord jSFixedFastJsonDispActivityDataRecord
    );

    JSFixedFastJsonDispActivityDataSet dispActivityDataSetToJSFixedFastJson(DispActivityDataSet dispActivityDataSet);

    @InheritInverseConfiguration
    DispActivityDataSet dispActivityDataSetFromJSFixedFastJson(
            JSFixedFastJsonDispActivityDataSet jSFixedFastJsonDispActivityDataSet
    );

    JSFixedFastJsonDispActivityParticipant dispActivityParticipantToJSFixedFastJson(
            DispActivityParticipant dispActivityParticipant
    );

    @InheritInverseConfiguration
    DispActivityParticipant dispActivityParticipantFromJSFixedFastJson(
            JSFixedFastJsonDispActivityParticipant jSFixedFastJsonDispActivityParticipant
    );

    JSFixedFastJsonDispActivityTemplate dispActivityTemplateToJSFixedFastJson(
            DispActivityTemplate dispActivityTemplate
    );

    @InheritInverseConfiguration
    DispActivityTemplate dispActivityTemplateFromJSFixedFastJson(
            JSFixedFastJsonDispActivityTemplate jSFixedFastJsonDispActivityTemplate
    );

    JSFixedFastJsonDispActivityTemplateDataInfo dispActivityTemplateDataInfoToJSFixedFastJson(
            DispActivityTemplateDataInfo dispActivityTemplateDataInfo
    );

    @InheritInverseConfiguration
    DispActivityTemplateDataInfo dispActivityTemplateDataInfoFromJSFixedFastJson(
            JSFixedFastJsonDispActivityTemplateDataInfo jSFixedFastJsonDispActivityTemplateDataInfo
    );

    JSFixedFastJsonDispActivityTemplateParticipant dispActivityTemplateParticipantToJSFixedFastJson(
            DispActivityTemplateParticipant dispActivityTemplateParticipant
    );

    @InheritInverseConfiguration
    DispActivityTemplateParticipant dispActivityTemplateParticipantFromJSFixedFastJson(
            JSFixedFastJsonDispActivityTemplateParticipant jSFixedFastJsonDispActivityTemplateParticipant
    );

    JSFixedFastJsonDispPbItem dispPbItemToJSFixedFastJson(DispPbItem dispPbItem);

    @InheritInverseConfiguration
    DispPbItem dispPbItemFromJSFixedFastJson(JSFixedFastJsonDispPbItem jSFixedFastJsonDispPbItem);

    JSFixedFastJsonDispPbNode dispPbNodeToJSFixedFastJson(DispPbNode dispPbNode);

    @InheritInverseConfiguration
    DispPbNode dispPbNodeFromJSFixedFastJson(JSFixedFastJsonDispPbNode jSFixedFastJsonDispPbNode);

    JSFixedFastJsonDispPbSet dispPbSetToJSFixedFastJson(DispPbSet dispPbSet);

    @InheritInverseConfiguration
    DispPbSet dispPbSetFromJSFixedFastJson(JSFixedFastJsonDispPbSet jSFixedFastJsonDispPbSet);

    JSFixedFastJsonDispPoac dispPoacToJSFixedFastJson(DispPoac dispPoac);

    @InheritInverseConfiguration
    DispPoac dispPoacFromJSFixedFastJson(JSFixedFastJsonDispPoac jSFixedFastJsonDispPoac);

    JSFixedFastJsonDispPoad dispPoadToJSFixedFastJson(DispPoad dispPoad);

    @InheritInverseConfiguration
    DispPoad dispPoadFromJSFixedFastJson(JSFixedFastJsonDispPoad jSFixedFastJsonDispPoad);

    JSFixedFastJsonDispPoat dispPoatToJSFixedFastJson(DispPoat dispPoat);

    @InheritInverseConfiguration
    DispPoat dispPoatFromJSFixedFastJson(JSFixedFastJsonDispPoat jSFixedFastJsonDispPoat);

    JSFixedFastJsonDispPoatac dispPoatacToJSFixedFastJson(DispPoatac dispPoatac);

    @InheritInverseConfiguration
    DispPoatac dispPoatacFromJSFixedFastJson(JSFixedFastJsonDispPoatac jSFixedFastJsonDispPoatac);

    JSFixedFastJsonDispPopb dispPopbToJSFixedFastJson(DispPopb dispPopb);

    @InheritInverseConfiguration
    DispPopb dispPopbFromJSFixedFastJson(JSFixedFastJsonDispPopb jSFixedFastJsonDispPopb);
}
