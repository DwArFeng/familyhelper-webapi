package com.dwarfeng.familyhelper.webapi.stack.service.system;

import com.dwarfeng.acckeeper.stack.bean.entity.LoginHistory;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.service.Service;

/**
 * 登录历史响应服务。
 *
 * @author DwArFeng
 * @since 1.2.1
 */
public interface LoginHistoryResponseService extends Service {

    boolean exists(LongIdKey key) throws ServiceException;

    LoginHistory get(LongIdKey key) throws ServiceException;

    PagedData<LoginHistory> all(PagingInfo pagingInfo) throws ServiceException;

    PagedData<LoginHistory> accountIdEquals(String accountId, PagingInfo pagingInfo) throws ServiceException;
}
