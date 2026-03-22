package com.dwarfeng.familyhelper.webapi.stack.service.rbac;

import com.dwarfeng.rbacds.stack.bean.entity.FilterSupport;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.service.Service;

/**
 * 过滤器支持响应服务。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public interface FilterSupportResponseService extends Service {

    boolean exists(StringIdKey key) throws ServiceException;

    FilterSupport get(StringIdKey key) throws ServiceException;

    PagedData<FilterSupport> all(PagingInfo pagingInfo) throws ServiceException;

    PagedData<FilterSupport> idLike(String pattern, PagingInfo pagingInfo) throws ServiceException;

    PagedData<FilterSupport> labelLike(String pattern, PagingInfo pagingInfo) throws ServiceException;
}
