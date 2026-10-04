package com.dwarfeng.familyhelper.webapi.stack.service.logicengine;

import com.dwarfeng.familyhelper.webapi.stack.bean.logicengine.disp.DispDriverInfo;
import com.dwarfeng.logicengine.stack.bean.entity.DriverInfo;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.service.Service;

/**
 * 驱动器信息响应服务。
 *
 * @author DwArFeng
 * @since 2.4.0
 */
public interface DriverInfoResponseService extends Service {

    boolean exists(LongIdKey key) throws ServiceException;

    DriverInfo get(LongIdKey key) throws ServiceException;

    LongIdKey insert(DriverInfo driverInfo) throws ServiceException;

    void update(DriverInfo driverInfo) throws ServiceException;

    void delete(LongIdKey key) throws ServiceException;

    PagedData<DriverInfo> all(PagingInfo pagingInfo) throws ServiceException;

    PagedData<DriverInfo> childForSection(LongIdKey sectionKey, PagingInfo pagingInfo) throws ServiceException;

    PagedData<DriverInfo> childForSectionEnabled(LongIdKey sectionKey, PagingInfo pagingInfo) throws ServiceException;

    DispDriverInfo getDisp(LongIdKey key) throws ServiceException;

    PagedData<DispDriverInfo> allDisp(PagingInfo pagingInfo) throws ServiceException;

    PagedData<DispDriverInfo> childForSectionDisp(LongIdKey sectionKey, PagingInfo pagingInfo) throws ServiceException;

    PagedData<DispDriverInfo> childForSectionEnabledDisp(LongIdKey sectionKey, PagingInfo pagingInfo)
            throws ServiceException;
}
