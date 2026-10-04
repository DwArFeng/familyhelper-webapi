package com.dwarfeng.familyhelper.webapi.sdk.bean.logicengine;

import com.dwarfeng.familyhelper.webapi.sdk.bean.logicengine.disp.*;
import com.dwarfeng.familyhelper.webapi.stack.bean.logicengine.disp.*;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;

/**
 * Bean 映射器。
 *
 * <p>
 * 该映射器中包含了 <code>sdk</code> 模块中所有实体与 <code>stack</code> 模块中对应实体的映射方法。
 *
 * @author DwArFeng
 * @since 2.4.0
 */
@Mapper
public interface BeanMapper {

    JSFixedFastJsonDispDriverInfo dispDriverInfoToJSFixedFastJson(DispDriverInfo dispDriverInfo);

    @InheritInverseConfiguration
    DispDriverInfo dispDriverInfoFromJSFixedFastJson(JSFixedFastJsonDispDriverInfo jsFixedFastJsonDispDriverInfo);

    JSFixedFastJsonDispGuarderInfo dispGuarderInfoToJSFixedFastJson(DispGuarderInfo dispGuarderInfo);

    @InheritInverseConfiguration
    DispGuarderInfo dispGuarderInfoFromJSFixedFastJson(JSFixedFastJsonDispGuarderInfo jsFixedFastJsonDispGuarderInfo);

    JSFixedFastJsonDispPerformerInfo dispPerformerInfoToJSFixedFastJson(DispPerformerInfo dispPerformerInfo);

    @InheritInverseConfiguration
    DispPerformerInfo dispPerformerInfoFromJSFixedFastJson(
            JSFixedFastJsonDispPerformerInfo jsFixedFastJsonDispPerformerInfo
    );

    JSFixedFastJsonDispState dispStateToJSFixedFastJson(DispState dispState);

    @InheritInverseConfiguration
    DispState dispStateFromJSFixedFastJson(JSFixedFastJsonDispState jsFixedFastJsonDispState);

    JSFixedFastJsonDispTask dispTaskToJSFixedFastJson(DispTask dispTask);

    @InheritInverseConfiguration
    DispTask dispTaskFromJSFixedFastJson(JSFixedFastJsonDispTask jsFixedFastJsonDispTask);

    JSFixedFastJsonDispTaskEvent dispTaskEventToJSFixedFastJson(DispTaskEvent dispTaskEvent);

    @InheritInverseConfiguration
    DispTaskEvent dispTaskEventFromJSFixedFastJson(JSFixedFastJsonDispTaskEvent jsFixedFastJsonDispTaskEvent);

    JSFixedFastJsonDispTaskVariable dispTaskVariableToJSFixedFastJson(DispTaskVariable dispTaskVariable);

    @InheritInverseConfiguration
    DispTaskVariable dispTaskVariableFromJSFixedFastJson(
            JSFixedFastJsonDispTaskVariable jsFixedFastJsonDispTaskVariable
    );
}
