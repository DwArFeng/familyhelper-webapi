package com.dwarfeng.familyhelper.webapi.stack.service.system;

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
 * @since 1.0.0
 */
public interface RoleResponseService extends Service {

    boolean exists(StringIdKey key) throws ServiceException;

    Role get(StringIdKey key) throws ServiceException;

    StringIdKey insert(Role role) throws ServiceException;

    void update(Role role) throws ServiceException;

    void delete(StringIdKey key) throws ServiceException;

    void addAccountRelation(StringIdKey roleIdKey, StringIdKey accountKey) throws ServiceException;

    void deleteAccountRelation(StringIdKey roleIdKey, StringIdKey accountKey) throws ServiceException;

    PagedData<Role> all(PagingInfo pagingInfo) throws ServiceException;

    PagedData<Role> childForAccount(StringIdKey accountKey, PagingInfo pagingInfo) throws ServiceException;
}
