package com.dwarfeng.familyhelper.webapi.stack.service.settingrepo;

import com.dwarfeng.settingrepo.stack.bean.entity.SettingCategory;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.service.Service;

/**
 * 设置类型响应服务。
 *
 * @author DwArFeng
 * @since 1.0.6
 */
public interface SettingCategoryResponseService extends Service {

    boolean exists(StringIdKey key) throws ServiceException;

    SettingCategory get(StringIdKey key) throws ServiceException;

    StringIdKey insert(SettingCategory settingCategory) throws ServiceException;

    void update(SettingCategory settingCategory) throws ServiceException;

    void delete(StringIdKey key) throws ServiceException;

    PagedData<SettingCategory> all(PagingInfo pagingInfo) throws ServiceException;

    PagedData<SettingCategory> idLike(String pattern, PagingInfo pagingInfo) throws ServiceException;
}
