package com.dwarfeng.familyhelper.webapi.stack.service.notify;

import com.dwarfeng.familyhelper.webapi.stack.bean.disp.notify.DispRouterInfo;
import com.dwarfeng.notify.stack.bean.entity.RouterInfo;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.service.Service;

/**
 * 路由器信息响应服务。
 *
 * @author DwArFeng
 * @since 1.0.6
 */
public interface RouterInfoResponseService extends Service {

    boolean exists(LongIdKey key) throws ServiceException;

    RouterInfo get(LongIdKey key) throws ServiceException;

    LongIdKey insert(RouterInfo routerInfo) throws ServiceException;

    void update(RouterInfo routerInfo) throws ServiceException;

    void delete(LongIdKey key) throws ServiceException;

    PagedData<RouterInfo> all(PagingInfo pagingInfo) throws ServiceException;

    PagedData<RouterInfo> typeEquals(String pattern, PagingInfo pagingInfo) throws ServiceException;

    DispRouterInfo getDisp(LongIdKey key) throws ServiceException;

    PagedData<DispRouterInfo> allDisp(PagingInfo pagingInfo) throws ServiceException;

    PagedData<DispRouterInfo> typeEqualsDisp(String pattern, PagingInfo pagingInfo) throws ServiceException;
}
