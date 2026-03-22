package com.dwarfeng.familyhelper.webapi.stack.service.rbac;

import com.dwarfeng.familyhelper.webapi.stack.bean.rbac.disp.DispPermission;
import com.dwarfeng.rbacds.stack.bean.dto.PermissionCreateInfo;
import com.dwarfeng.rbacds.stack.bean.dto.PermissionCreateResult;
import com.dwarfeng.rbacds.stack.bean.dto.PermissionRemoveInfo;
import com.dwarfeng.rbacds.stack.bean.dto.PermissionUpdateInfo;
import com.dwarfeng.rbacds.stack.bean.entity.Permission;
import com.dwarfeng.rbacds.stack.bean.key.PermissionGroupKey;
import com.dwarfeng.rbacds.stack.bean.key.PermissionKey;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.service.Service;

/**
 * 权限响应服务。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public interface PermissionResponseService extends Service {

    boolean exists(PermissionKey key) throws ServiceException;

    Permission get(PermissionKey key) throws ServiceException;

    PermissionKey insert(Permission entity) throws ServiceException;

    void update(Permission entity) throws ServiceException;

    void delete(PermissionKey key) throws ServiceException;

    PagedData<Permission> all(PagingInfo pagingInfo) throws ServiceException;

    PagedData<Permission> childForScope(StringIdKey scopeKey, PagingInfo pagingInfo) throws ServiceException;

    PagedData<Permission> childForGroup(PermissionGroupKey groupKey, PagingInfo pagingInfo) throws ServiceException;

    PagedData<Permission> permissionStringIdLike(String pattern, PagingInfo pagingInfo)
            throws ServiceException;

    PagedData<Permission> nameLike(String pattern, PagingInfo pagingInfo) throws ServiceException;

    PagedData<Permission> childForScopePermissionStringIdLike(
            StringIdKey scopeKey, String pattern, PagingInfo pagingInfo
    ) throws ServiceException;

    /**
     * 创建权限。
     *
     * @param info 创建信息。
     * @return 创建结果。
     * @throws ServiceException 服务异常。
     */
    PermissionCreateResult create(PermissionCreateInfo info) throws ServiceException;

    /**
     * 更新权限。
     *
     * @param info 更新信息。
     * @throws ServiceException 服务异常。
     */
    void update(PermissionUpdateInfo info) throws ServiceException;

    /**
     * 移除权限。
     *
     * @param info 移除信息。
     * @throws ServiceException 服务异常。
     */
    void remove(PermissionRemoveInfo info) throws ServiceException;

    DispPermission getDisp(PermissionKey key) throws ServiceException;

    PagedData<DispPermission> allDisp(PagingInfo pagingInfo) throws ServiceException;

    PagedData<DispPermission> childForScopeDisp(StringIdKey scopeKey, PagingInfo pagingInfo) throws ServiceException;

    PagedData<DispPermission> childForGroupDisp(PermissionGroupKey groupKey, PagingInfo pagingInfo)
            throws ServiceException;

    PagedData<DispPermission> permissionStringIdLikeDisp(String pattern, PagingInfo pagingInfo)
            throws ServiceException;

    PagedData<DispPermission> nameLikeDisp(String pattern, PagingInfo pagingInfo)
            throws ServiceException;
}
