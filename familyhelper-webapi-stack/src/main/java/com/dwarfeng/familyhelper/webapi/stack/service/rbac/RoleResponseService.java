package com.dwarfeng.familyhelper.webapi.stack.service.rbac;

import com.dwarfeng.rbacds.stack.bean.entity.Role;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.service.Service;

/**
 * 角色响应服务。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public interface RoleResponseService extends Service {

    boolean exists(StringIdKey key) throws ServiceException;

    Role get(StringIdKey key) throws ServiceException;

    StringIdKey insert(Role entity) throws ServiceException;

    void update(Role entity) throws ServiceException;

    void delete(StringIdKey key) throws ServiceException;

    PagedData<Role> all(PagingInfo pagingInfo) throws ServiceException;

    PagedData<Role> childForUserAvailable(StringIdKey userKey, PagingInfo pagingInfo) throws ServiceException;

    PagedData<Role> enabled(PagingInfo pagingInfo) throws ServiceException;

    PagedData<Role> idLike(String pattern, PagingInfo pagingInfo) throws ServiceException;

    PagedData<Role> notChildForUser(StringIdKey userKey, PagingInfo pagingInfo) throws ServiceException;
}
