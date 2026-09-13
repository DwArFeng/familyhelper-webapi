package com.dwarfeng.familyhelper.webapi.sdk.bean.fileio;

import com.dwarfeng.familyhelper.webapi.sdk.bean.fileio.disp.JSFixedFastJsonDispExportTask;
import com.dwarfeng.familyhelper.webapi.sdk.bean.fileio.disp.JSFixedFastJsonDispImportTask;
import com.dwarfeng.familyhelper.webapi.stack.bean.fileio.disp.DispExportTask;
import com.dwarfeng.familyhelper.webapi.stack.bean.fileio.disp.DispImportTask;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;

/**
 * Bean 映射器。
 *
 * <p>
 * 该映射器中包含了 <code>sdk</code> 模块中所有实体与 <code>stack</code> 模块中对应实体的映射方法。
 *
 * @author DwArFeng
 * @since 2.1.0
 */
@Mapper
public interface BeanMapper {

    // region Familyhelper-webapi Disp

    JSFixedFastJsonDispExportTask dispExportTaskToJSFixedFastJson(DispExportTask dispExportTask);

    @InheritInverseConfiguration
    DispExportTask dispExportTaskFromJSFixedFastJson(JSFixedFastJsonDispExportTask jSFixedFastJsonDispExportTask);

    JSFixedFastJsonDispImportTask dispImportTaskToJSFixedFastJson(DispImportTask dispImportTask);

    @InheritInverseConfiguration
    DispImportTask dispImportTaskFromJSFixedFastJson(JSFixedFastJsonDispImportTask jSFixedFastJsonDispImportTask);

    // endregion
}
