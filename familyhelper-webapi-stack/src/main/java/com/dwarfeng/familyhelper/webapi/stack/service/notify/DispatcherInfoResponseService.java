package com.dwarfeng.familyhelper.webapi.stack.service.notify;

import com.dwarfeng.notify.stack.bean.entity.DispatcherInfo;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.service.Service;

/**
 * 调度器信息响应服务。
 *
 * @author DwArFeng
 * @since 1.0.7
 */
public interface DispatcherInfoResponseService extends Service {

    boolean exists(StringIdKey key) throws ServiceException;

    DispatcherInfo get(StringIdKey key) throws ServiceException;

    StringIdKey insert(DispatcherInfo dispatcherInfo) throws ServiceException;

    void update(DispatcherInfo dispatcherInfo) throws ServiceException;

    void delete(StringIdKey key) throws ServiceException;

    PagedData<DispatcherInfo> all(PagingInfo pagingInfo) throws ServiceException;

    PagedData<DispatcherInfo> typeEquals(String pattern, PagingInfo pagingInfo) throws ServiceException;
}
