package com.dwarfeng.familyhelper.webapi.stack.service.acckeeper;

import com.dwarfeng.acckeeper.stack.bean.entity.DeriveHistory;
import com.dwarfeng.familyhelper.webapi.stack.bean.acckeeper.disp.DispDeriveHistory;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.service.Service;

/**
 * 派生历史响应服务。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public interface DeriveHistoryResponseService extends Service {

    boolean exists(LongIdKey key) throws ServiceException;

    DeriveHistory get(LongIdKey key) throws ServiceException;

    PagedData<DeriveHistory> all(PagingInfo pagingInfo) throws ServiceException;

    PagedData<DeriveHistory> accountIdEquals(String accountId, PagingInfo pagingInfo) throws ServiceException;

    PagedData<DeriveHistory> accountIdLike(String pattern, PagingInfo pagingInfo) throws ServiceException;

    DispDeriveHistory getDisp(LongIdKey key) throws ServiceException;

    PagedData<DispDeriveHistory> allDisp(PagingInfo pagingInfo) throws ServiceException;

    PagedData<DispDeriveHistory> accountIdEqualsDisp(String accountId, PagingInfo pagingInfo)
            throws ServiceException;

    PagedData<DispDeriveHistory> accountIdLikeDisp(String pattern, PagingInfo pagingInfo)
            throws ServiceException;
}
