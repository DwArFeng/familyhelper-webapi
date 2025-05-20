package com.dwarfeng.familyhelper.webapi.sdk.bean.settingrepo;

import com.dwarfeng.settingrepo.sdk.bean.entity.*;
import com.dwarfeng.settingrepo.stack.bean.entity.*;
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

    FastJsonSettingNode settingNodeToFastJson(SettingNode settingNode);

    @InheritInverseConfiguration
    SettingNode settingNodeFromFastJson(FastJsonSettingNode fastJsonSettingNode);

    FastJsonSettingCategory settingCategoryToFastJson(SettingCategory settingCategory);

    @InheritInverseConfiguration
    SettingCategory settingCategoryFromFastJson(FastJsonSettingCategory fastJsonSettingCategory);

    FastJsonFormatterSupport formatterSupportToFastJson(FormatterSupport formatterSupport);

    @InheritInverseConfiguration
    FormatterSupport formatterSupportFromFastJson(FastJsonFormatterSupport fastJsonFormatterSupport);

    FastJsonTextNode textNodeToFastJson(TextNode textNode);

    @InheritInverseConfiguration
    TextNode textNodeFromFastJson(FastJsonTextNode fastJsonTextNode);

    FastJsonImageNode imageNodeToFastJson(ImageNode imageNode);

    @InheritInverseConfiguration
    ImageNode imageNodeFromFastJson(FastJsonImageNode fastJsonImageNode);

    FastJsonImageListNode imageListNodeToFastJson(ImageListNode imageListNode);

    @InheritInverseConfiguration
    ImageListNode imageListNodeFromFastJson(FastJsonImageListNode fastJsonImageListNode);
}
