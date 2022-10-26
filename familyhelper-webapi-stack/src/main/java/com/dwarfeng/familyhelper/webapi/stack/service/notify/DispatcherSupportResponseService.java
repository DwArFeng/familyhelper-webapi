package com.dwarfeng.familyhelper.webapi.stack.service.notify;

import com.dwarfeng.notify.stack.bean.entity.DispatcherSupport;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.service.Service;

/**
 * 调度器支持响应服务。
 *
 * @author DwArFeng
 * @since 1.0.7
 */
public interface DispatcherSupportResponseService extends Service {

    boolean exists(StringIdKey key) throws ServiceException;

    DispatcherSupport get(StringIdKey key) throws ServiceException;

    PagedData<DispatcherSupport> all(PagingInfo pagingInfo) throws ServiceException;

    PagedData<DispatcherSupport> idLike(String pattern, PagingInfo pagingInfo) throws ServiceException;
}
