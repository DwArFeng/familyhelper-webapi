package com.dwarfeng.familyhelper.webapi.stack.service.system;

import com.dwarfeng.rbacds.stack.bean.entity.Permission;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.service.Service;

import java.util.List;

/**
 * 权限响应服务。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public interface PermissionResponseService extends Service {

    boolean exists(StringIdKey key) throws ServiceException;

    Permission get(StringIdKey key) throws ServiceException;

    StringIdKey insert(Permission permission) throws ServiceException;

    void update(Permission permission) throws ServiceException;

    void delete(StringIdKey key) throws ServiceException;

    PagedData<Permission> all(PagingInfo pagingInfo) throws ServiceException;

    PagedData<Permission> idLike(String pattern, PagingInfo pagingInfo) throws ServiceException;

    PagedData<Permission> childForGroup(StringIdKey groupKey, PagingInfo pagingInfo) throws ServiceException;

    List<Permission> lookupForUser(StringIdKey userKey) throws ServiceException;
}
