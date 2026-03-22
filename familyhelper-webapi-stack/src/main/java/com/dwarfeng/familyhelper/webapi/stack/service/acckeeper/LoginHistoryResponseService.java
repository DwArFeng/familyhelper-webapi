package com.dwarfeng.familyhelper.webapi.stack.service.acckeeper;

import com.dwarfeng.acckeeper.stack.bean.entity.LoginHistory;
import com.dwarfeng.familyhelper.webapi.stack.bean.acckeeper.disp.DispLoginHistory;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.service.Service;

/**
 * 登录历史响应服务。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public interface LoginHistoryResponseService extends Service {

    boolean exists(LongIdKey key) throws ServiceException;

    LoginHistory get(LongIdKey key) throws ServiceException;

    PagedData<LoginHistory> all(PagingInfo pagingInfo) throws ServiceException;

    PagedData<LoginHistory> accountIdEquals(String accountId, PagingInfo pagingInfo) throws ServiceException;

    PagedData<LoginHistory> accountIdLike(String pattern, PagingInfo pagingInfo) throws ServiceException;

    DispLoginHistory getDisp(LongIdKey key) throws ServiceException;

    PagedData<DispLoginHistory> allDisp(PagingInfo pagingInfo) throws ServiceException;

    PagedData<DispLoginHistory> accountIdEqualsDisp(String accountId, PagingInfo pagingInfo) throws ServiceException;

    PagedData<DispLoginHistory> accountIdLikeDisp(String pattern, PagingInfo pagingInfo) throws ServiceException;
}
