package com.dwarfeng.familyhelper.webapi.sdk.bean.acckeeper;

import com.dwarfeng.familyhelper.webapi.sdk.bean.acckeeper.disp.*;
import com.dwarfeng.familyhelper.webapi.stack.bean.acckeeper.disp.*;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;

/**
 * Bean 映射器。
 *
 * <p>
 * 该映射器中包含了 <code>sdk</code> 模块中所有实体与 <code>stack</code> 模块中对应实体的映射方法。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
@Mapper
public interface BeanMapper {

    // -----------------------------------------------------------Familyhelper-webapi Disp-----------------------------------------------------------
    FastJsonDispDeriveHistory dispDeriveHistoryToFastJson(DispDeriveHistory dispDeriveHistory);

    @InheritInverseConfiguration
    DispDeriveHistory dispDeriveHistoryFromFastJson(FastJsonDispDeriveHistory fastJsonDispDeriveHistory);

    FastJsonDispLoginHistory dispLoginHistoryToFastJson(DispLoginHistory dispLoginHistory);

    @InheritInverseConfiguration
    DispLoginHistory dispLoginHistoryFromFastJson(FastJsonDispLoginHistory fastJsonDispLoginHistory);

    FastJsonDispLoginParamRecord dispLoginParamRecordToFastJson(DispLoginParamRecord dispLoginParamRecord);

    @InheritInverseConfiguration
    DispLoginParamRecord dispLoginParamRecordFromFastJson(FastJsonDispLoginParamRecord fastJsonDispLoginParamRecord);

    FastJsonDispLoginState dispLoginStateToFastJson(DispLoginState dispLoginState);

    @InheritInverseConfiguration
    DispLoginState dispLoginStateFromFastJson(FastJsonDispLoginState fastJsonDispLoginState);

    FastJsonDispProtectDetailRecord dispProtectDetailRecordToFastJson(DispProtectDetailRecord dispProtectDetailRecord);

    @InheritInverseConfiguration
    DispProtectDetailRecord dispProtectDetailRecordFromFastJson(
            FastJsonDispProtectDetailRecord fastJsonDispProtectDetailRecord
    );

    FastJsonDispProtectorVariable dispProtectorVariableToFastJson(DispProtectorVariable dispProtectorVariable);

    @InheritInverseConfiguration
    DispProtectorVariable dispProtectorVariableFromFastJson(
            FastJsonDispProtectorVariable fastJsonDispProtectorVariable
    );

    JSFixedFastJsonDispDeriveHistory dispDeriveHistoryToJSFixedFastJson(DispDeriveHistory dispDeriveHistory);

    @InheritInverseConfiguration
    DispDeriveHistory dispDeriveHistoryFromJSFixedFastJson(
            JSFixedFastJsonDispDeriveHistory jSFixedFastJsonDispDeriveHistory
    );

    JSFixedFastJsonDispLoginHistory dispLoginHistoryToJSFixedFastJson(DispLoginHistory dispLoginHistory);

    @InheritInverseConfiguration
    DispLoginHistory dispLoginHistoryFromJSFixedFastJson(
            JSFixedFastJsonDispLoginHistory jSFixedFastJsonDispLoginHistory
    );

    JSFixedFastJsonDispLoginParamRecord dispLoginParamRecordToJSFixedFastJson(
            DispLoginParamRecord dispLoginParamRecord
    );

    @InheritInverseConfiguration
    DispLoginParamRecord dispLoginParamRecordFromJSFixedFastJson(
            JSFixedFastJsonDispLoginParamRecord jSFixedFastJsonDispLoginParamRecord
    );

    JSFixedFastJsonDispLoginState dispLoginStateToJSFixedFastJson(DispLoginState dispLoginState);

    @InheritInverseConfiguration
    DispLoginState dispLoginStateFromJSFixedFastJson(JSFixedFastJsonDispLoginState jSFixedFastJsonDispLoginState);

    JSFixedFastJsonDispProtectDetailRecord dispProtectDetailRecordToJSFixedFastJson(
            DispProtectDetailRecord dispProtectDetailRecord
    );

    @InheritInverseConfiguration
    DispProtectDetailRecord dispProtectDetailRecordFromJSFixedFastJson(
            JSFixedFastJsonDispProtectDetailRecord jSFixedFastJsonDispProtectDetailRecord
    );
}
