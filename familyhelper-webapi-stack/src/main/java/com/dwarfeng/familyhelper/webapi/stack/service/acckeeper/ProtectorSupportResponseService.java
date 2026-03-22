package com.dwarfeng.familyhelper.webapi.stack.service.acckeeper;

import com.dwarfeng.acckeeper.stack.bean.entity.ProtectorSupport;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.service.Service;

/**
 * 保护器支持响应服务。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public interface ProtectorSupportResponseService extends Service {

    boolean exists(StringIdKey key) throws ServiceException;

    ProtectorSupport get(StringIdKey key) throws ServiceException;

    PagedData<ProtectorSupport> all(PagingInfo pagingInfo) throws ServiceException;

    PagedData<ProtectorSupport> idLike(String pattern, PagingInfo pagingInfo) throws ServiceException;

    PagedData<ProtectorSupport> labelLike(String pattern, PagingInfo pagingInfo) throws ServiceException;
}
