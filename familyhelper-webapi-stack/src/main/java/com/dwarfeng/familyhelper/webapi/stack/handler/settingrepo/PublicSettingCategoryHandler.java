package com.dwarfeng.familyhelper.webapi.stack.handler.settingrepo;

import com.dwarfeng.subgrade.stack.exception.HandlerException;
import com.dwarfeng.subgrade.stack.handler.Handler;

/**
 * 公共设置类别处理器。
 *
 * @author DwArFeng
 * @since 1.8.0
 */
public interface PublicSettingCategoryHandler extends Handler {

    /**
     * 解析公共设置类别。
     *
     * @param settingCategory 设置类别。
     * @return 根据设置类别解析后的公共设置类别。
     * @throws HandlerException 处理器异常。
     */
    String parsePublicSettingCategory(String settingCategory) throws HandlerException;
}
