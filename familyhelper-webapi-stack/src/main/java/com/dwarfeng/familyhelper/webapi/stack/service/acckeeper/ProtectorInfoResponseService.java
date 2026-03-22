package com.dwarfeng.familyhelper.webapi.stack.service.acckeeper;

import com.dwarfeng.acckeeper.stack.bean.entity.ProtectorInfo;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.service.Service;

/**
 * 保护器信息响应服务。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public interface ProtectorInfoResponseService extends Service {

    boolean exists(StringIdKey key) throws ServiceException;

    ProtectorInfo get(StringIdKey key) throws ServiceException;

    StringIdKey insert(ProtectorInfo protectorInfo) throws ServiceException;

    void update(ProtectorInfo protectorInfo) throws ServiceException;

    void delete(StringIdKey key) throws ServiceException;

    PagedData<ProtectorInfo> all(PagingInfo pagingInfo) throws ServiceException;

    PagedData<ProtectorInfo> typeEquals(String type, PagingInfo pagingInfo) throws ServiceException;

    PagedData<ProtectorInfo> typeLike(String pattern, PagingInfo pagingInfo) throws ServiceException;
}
