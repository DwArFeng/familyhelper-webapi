package com.dwarfeng.familyhelper.webapi.stack.service.settingrepo;

import com.dwarfeng.settingrepo.stack.bean.entity.WarmupSetting;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.service.Service;

/**
 * 预热设置响应服务。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public interface WarmupSettingResponseService extends Service {

    boolean exists(LongIdKey key) throws ServiceException;

    WarmupSetting get(LongIdKey key) throws ServiceException;

    LongIdKey insert(WarmupSetting warmupSetting) throws ServiceException;

    void update(WarmupSetting warmupSetting) throws ServiceException;

    void delete(LongIdKey key) throws ServiceException;

    PagedData<WarmupSetting> all(PagingInfo pagingInfo) throws ServiceException;

    PagedData<WarmupSetting> enabled(PagingInfo pagingInfo) throws ServiceException;
}
