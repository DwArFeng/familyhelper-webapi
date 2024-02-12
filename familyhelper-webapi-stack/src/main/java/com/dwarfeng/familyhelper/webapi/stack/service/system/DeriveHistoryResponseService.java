package com.dwarfeng.familyhelper.webapi.stack.service.system;

import com.dwarfeng.acckeeper.stack.bean.entity.DeriveHistory;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.service.Service;

/**
 * 派生历史响应服务。
 *
 * @author DwArFeng
 * @since 1.2.1
 */
public interface DeriveHistoryResponseService extends Service {

    boolean exists(LongIdKey key) throws ServiceException;

    DeriveHistory get(LongIdKey key) throws ServiceException;

    PagedData<DeriveHistory> all(PagingInfo pagingInfo) throws ServiceException;

    PagedData<DeriveHistory> accountIdEquals(String accountId, PagingInfo pagingInfo) throws ServiceException;
}
