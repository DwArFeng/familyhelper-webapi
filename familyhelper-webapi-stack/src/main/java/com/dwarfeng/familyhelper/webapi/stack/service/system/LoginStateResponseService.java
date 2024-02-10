package com.dwarfeng.familyhelper.webapi.stack.service.system;

import com.dwarfeng.acckeeper.stack.bean.entity.LoginState;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.service.Service;

/**
 * 登录状态响应服务。
 *
 * @author DwArFeng
 * @since 1.2.1
 */
public interface LoginStateResponseService extends Service {

    boolean exists(LongIdKey key) throws ServiceException;

    LoginState get(LongIdKey key) throws ServiceException;

    PagedData<LoginState> all(PagingInfo pagingInfo) throws ServiceException;

    PagedData<LoginState> childForAccount(StringIdKey accountKey, PagingInfo pagingInfo) throws ServiceException;

    PagedData<LoginState> childForAccountTypeEqualsDynamic(StringIdKey accountKey, PagingInfo pagingInfo)
            throws ServiceException;

    PagedData<LoginState> childForAccountTypeEqualsStatic(StringIdKey accountKey, PagingInfo pagingInfo)
            throws ServiceException;
}
