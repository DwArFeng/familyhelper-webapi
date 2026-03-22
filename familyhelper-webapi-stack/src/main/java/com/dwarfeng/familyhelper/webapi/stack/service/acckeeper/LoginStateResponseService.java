package com.dwarfeng.familyhelper.webapi.stack.service.acckeeper;

import com.dwarfeng.acckeeper.stack.bean.entity.LoginState;
import com.dwarfeng.familyhelper.webapi.stack.bean.acckeeper.disp.DispLoginState;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.service.Service;

/**
 * 登录状态响应服务。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public interface LoginStateResponseService extends Service {

    boolean exists(StringIdKey key) throws ServiceException;

    LoginState get(StringIdKey key) throws ServiceException;

    PagedData<LoginState> all(PagingInfo pagingInfo) throws ServiceException;

    PagedData<LoginState> childForAccount(StringIdKey accountKey, PagingInfo pagingInfo) throws ServiceException;

    PagedData<LoginState> childForAccountTypeEqualsDynamic(StringIdKey accountKey, PagingInfo pagingInfo)
            throws ServiceException;

    PagedData<LoginState> childForAccountTypeEqualsStatic(StringIdKey accountKey, PagingInfo pagingInfo)
            throws ServiceException;

    DispLoginState getDisp(StringIdKey key) throws ServiceException;

    PagedData<DispLoginState> allDisp(PagingInfo pagingInfo) throws ServiceException;

    PagedData<DispLoginState> childForAccountDisp(StringIdKey accountKey, PagingInfo pagingInfo)
            throws ServiceException;

    PagedData<DispLoginState> childForAccountTypeEqualsDynamicDisp(StringIdKey accountKey, PagingInfo pagingInfo)
            throws ServiceException;

    PagedData<DispLoginState> childForAccountTypeEqualsStaticDisp(StringIdKey accountKey, PagingInfo pagingInfo)
            throws ServiceException;
}
