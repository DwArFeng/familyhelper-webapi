package com.dwarfeng.familyhelper.webapi.stack.service.notify;

import com.dwarfeng.notify.stack.bean.entity.SenderInfo;
import com.dwarfeng.notify.stack.bean.entity.key.SenderInfoKey;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.service.Service;

/**
 * 发送器信息响应服务。
 *
 * @author DwArFeng
 * @since 1.0.7
 */
public interface SenderInfoResponseService extends Service {

    boolean exists(SenderInfoKey key) throws ServiceException;

    SenderInfo get(SenderInfoKey key) throws ServiceException;

    SenderInfoKey insert(SenderInfo senderInfo) throws ServiceException;

    void update(SenderInfo senderInfo) throws ServiceException;

    void delete(SenderInfoKey key) throws ServiceException;

    PagedData<SenderInfo> all(PagingInfo pagingInfo) throws ServiceException;

    PagedData<SenderInfo> typeEquals(String pattern, PagingInfo pagingInfo) throws ServiceException;
}
