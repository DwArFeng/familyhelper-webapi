package com.dwarfeng.familyhelper.webapi.stack.service.rbac;

import com.dwarfeng.rbacds.stack.bean.entity.Scope;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.service.Service;

/**
 * 作用域响应服务。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public interface ScopeResponseService extends Service {

    boolean exists(StringIdKey key) throws ServiceException;

    Scope get(StringIdKey key) throws ServiceException;

    StringIdKey insert(Scope entity) throws ServiceException;

    void update(Scope entity) throws ServiceException;

    void delete(StringIdKey key) throws ServiceException;

    PagedData<Scope> all(PagingInfo pagingInfo) throws ServiceException;

    PagedData<Scope> idLike(String pattern, PagingInfo pagingInfo) throws ServiceException;

    PagedData<Scope> nameLike(String pattern, PagingInfo pagingInfo) throws ServiceException;
}
