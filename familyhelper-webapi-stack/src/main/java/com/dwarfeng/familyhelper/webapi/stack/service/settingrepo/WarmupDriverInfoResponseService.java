package com.dwarfeng.familyhelper.webapi.stack.service.settingrepo;

import com.dwarfeng.familyhelper.webapi.stack.bean.settingrepo.disp.DispWarmupDriverInfo;
import com.dwarfeng.settingrepo.stack.bean.entity.WarmupDriverInfo;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.service.Service;

/**
 * 预热驱动信息响应服务。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public interface WarmupDriverInfoResponseService extends Service {

    boolean exists(LongIdKey key) throws ServiceException;

    WarmupDriverInfo get(LongIdKey key) throws ServiceException;

    LongIdKey insert(WarmupDriverInfo warmupDriverInfo) throws ServiceException;

    void update(WarmupDriverInfo warmupDriverInfo) throws ServiceException;

    void delete(LongIdKey key) throws ServiceException;

    PagedData<WarmupDriverInfo> all(PagingInfo pagingInfo) throws ServiceException;

    PagedData<WarmupDriverInfo> childForWarmupSetting(LongIdKey warmupSettingKey, PagingInfo pagingInfo)
            throws ServiceException;

    PagedData<WarmupDriverInfo> childForWarmupSettingEnabled(LongIdKey warmupSettingKey, PagingInfo pagingInfo)
            throws ServiceException;

    DispWarmupDriverInfo getDisp(LongIdKey key) throws ServiceException;

    PagedData<DispWarmupDriverInfo> allDisp(PagingInfo pagingInfo) throws ServiceException;

    PagedData<DispWarmupDriverInfo> childForWarmupSettingDisp(LongIdKey warmupSettingKey, PagingInfo pagingInfo)
            throws ServiceException;

    PagedData<DispWarmupDriverInfo> childForWarmupSettingEnabledDisp(
            LongIdKey warmupSettingKey, PagingInfo pagingInfo
    ) throws ServiceException;
}
