package com.dwarfeng.familyhelper.webapi.stack.service.acckeeper;

import com.dwarfeng.acckeeper.stack.bean.entity.ProtectorVariable;
import com.dwarfeng.acckeeper.stack.bean.key.ProtectorVariableKey;
import com.dwarfeng.familyhelper.webapi.stack.bean.acckeeper.disp.DispProtectorVariable;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.service.Service;

/**
 * 保护器变量响应服务。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public interface ProtectorVariableResponseService extends Service {

    boolean exists(ProtectorVariableKey key) throws ServiceException;

    ProtectorVariable get(ProtectorVariableKey key) throws ServiceException;

    ProtectorVariableKey insert(ProtectorVariable protectorVariable) throws ServiceException;

    void update(ProtectorVariable protectorVariable) throws ServiceException;

    void delete(ProtectorVariableKey key) throws ServiceException;

    PagedData<ProtectorVariable> all(PagingInfo pagingInfo) throws ServiceException;

    PagedData<ProtectorVariable> childForProtectorInfo(StringIdKey protectorInfoKey, PagingInfo pagingInfo)
            throws ServiceException;

    DispProtectorVariable getDisp(ProtectorVariableKey key) throws ServiceException;

    PagedData<DispProtectorVariable> allDisp(PagingInfo pagingInfo) throws ServiceException;

    PagedData<DispProtectorVariable> childForProtectorInfoDisp(StringIdKey protectorInfoKey, PagingInfo pagingInfo)
            throws ServiceException;
}
