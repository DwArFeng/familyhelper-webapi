package com.dwarfeng.familyhelper.webapi.node.configuration.settingrepo;

import com.dwarfeng.settingrepo.sdk.bean.entity.*;
import com.dwarfeng.settingrepo.stack.bean.entity.*;
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
