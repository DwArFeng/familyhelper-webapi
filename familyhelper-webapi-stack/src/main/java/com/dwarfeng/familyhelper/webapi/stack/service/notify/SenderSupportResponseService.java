package com.dwarfeng.familyhelper.webapi.stack.service.notify;

import com.dwarfeng.notify.stack.bean.entity.SenderSupport;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.service.Service;

/**
 * 发送器支持响应服务。
 *
 * @author DwArFeng
 * @since 1.0.7
 */
public interface SenderSupportResponseService extends Service {

    boolean exists(StringIdKey key) throws ServiceException;

    SenderSupport get(StringIdKey key) throws ServiceException;

    PagedData<SenderSupport> all(PagingInfo pagingInfo) throws ServiceException;

    PagedData<SenderSupport> idLike(String pattern, PagingInfo pagingInfo) throws ServiceException;
}
