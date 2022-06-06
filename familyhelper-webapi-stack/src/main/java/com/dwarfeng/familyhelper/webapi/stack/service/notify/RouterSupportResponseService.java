package com.dwarfeng.familyhelper.webapi.stack.service.notify;

import com.dwarfeng.notify.stack.bean.entity.RouterSupport;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.service.Service;

/**
 * 路由器支持响应服务。
 *
 * @author DwArFeng
 * @since 1.0.6
 */
public interface RouterSupportResponseService extends Service {

    boolean exists(StringIdKey key) throws ServiceException;

    RouterSupport get(StringIdKey key) throws ServiceException;

    PagedData<RouterSupport> all(PagingInfo pagingInfo) throws ServiceException;

    PagedData<RouterSupport> idLike(String pattern, PagingInfo pagingInfo) throws ServiceException;
}
