package com.dwarfeng.familyhelper.webapi.stack.service.notify;

import com.dwarfeng.notify.stack.bean.entity.SenderInfo;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.service.Service;

/**
 * 发送器信息响应服务。
 *
 * @author DwArFeng
 * @since 1.0.6
 */
public interface SenderInfoResponseService extends Service {

    boolean exists(LongIdKey key) throws ServiceException;

    SenderInfo get(LongIdKey key) throws ServiceException;

    LongIdKey insert(SenderInfo senderInfo) throws ServiceException;

    void update(SenderInfo senderInfo) throws ServiceException;

    void delete(LongIdKey key) throws ServiceException;

    PagedData<SenderInfo> all(PagingInfo pagingInfo) throws ServiceException;

    PagedData<SenderInfo> typeEquals(String pattern, PagingInfo pagingInfo) throws ServiceException;
}
