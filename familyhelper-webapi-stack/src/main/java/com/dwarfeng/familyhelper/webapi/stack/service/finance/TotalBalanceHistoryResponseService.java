package com.dwarfeng.familyhelper.webapi.stack.service.finance;

import com.dwarfeng.familyhelper.finance.stack.bean.entity.TotalBalanceHistory;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.service.Service;

/**
 * 总余额响应服务。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public interface TotalBalanceHistoryResponseService extends Service {

    boolean exists(LongIdKey key) throws ServiceException;

    TotalBalanceHistory get(LongIdKey key) throws ServiceException;

    PagedData<TotalBalanceHistory> all(PagingInfo pagingInfo) throws ServiceException;

    PagedData<TotalBalanceHistory> childForAccountBook(LongIdKey accountBookKey, PagingInfo pagingInfo)
            throws ServiceException;

    PagedData<TotalBalanceHistory> childForAccountBookDesc(LongIdKey accountBookKey, PagingInfo pagingInfo)
            throws ServiceException;
}
