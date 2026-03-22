package com.dwarfeng.familyhelper.webapi.stack.service.rbac;

import com.dwarfeng.familyhelper.webapi.stack.bean.rbac.disp.DispPermissionGroup;
import com.dwarfeng.rbacds.stack.bean.dto.PermissionGroupCreateInfo;
import com.dwarfeng.rbacds.stack.bean.dto.PermissionGroupCreateResult;
import com.dwarfeng.rbacds.stack.bean.dto.PermissionGroupRemoveInfo;
import com.dwarfeng.rbacds.stack.bean.dto.PermissionGroupUpdateInfo;
import com.dwarfeng.rbacds.stack.bean.entity.PermissionGroup;
import com.dwarfeng.rbacds.stack.bean.key.PermissionGroupKey;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.service.Service;

/**
 * 权限组响应服务。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public interface PermissionGroupResponseService extends Service {

    boolean exists(PermissionGroupKey key) throws ServiceException;

    PermissionGroup get(PermissionGroupKey key) throws ServiceException;

    PagedData<PermissionGroup> all(PagingInfo pagingInfo) throws ServiceException;

    PagedData<PermissionGroup> childForScope(StringIdKey scopeKey, PagingInfo pagingInfo) throws ServiceException;

    PagedData<PermissionGroup> childForParent(PermissionGroupKey parentKey, PagingInfo pagingInfo)
            throws ServiceException;

    PagedData<PermissionGroup> permissionGroupStringIdLike(
            String pattern, PagingInfo pagingInfo
    ) throws ServiceException;

    PagedData<PermissionGroup> childForParentPermissionGroupStringIdLike(
            PermissionGroupKey parentKey, String pattern, PagingInfo pagingInfo
    ) throws ServiceException;

    PagedData<PermissionGroup> nameLike(String pattern, PagingInfo pagingInfo)
            throws ServiceException;

    PagedData<PermissionGroup> childForParentNameLike(
            PermissionGroupKey parentKey, String pattern, PagingInfo pagingInfo
    ) throws ServiceException;

    PagedData<PermissionGroup> childForScopeRoot(StringIdKey scopeKey, PagingInfo pagingInfo)
            throws ServiceException;

    /**
     * 查询某个权限分组到达其根节点的路径。
     *
     * @param key 权限分组的主键。
     * @return 从根节点到该权限分组父节点的路径。
     * @throws ServiceException 服务异常。
     */
    PagedData<PermissionGroup> pathFromRoot(PermissionGroupKey key) throws ServiceException;

    DispPermissionGroup getDisp(PermissionGroupKey key) throws ServiceException;

    PagedData<DispPermissionGroup> allDisp(PagingInfo pagingInfo) throws ServiceException;

    PagedData<DispPermissionGroup> childForScopeDisp(StringIdKey scopeKey, PagingInfo pagingInfo)
            throws ServiceException;

    PagedData<DispPermissionGroup> childForParentDisp(PermissionGroupKey parentKey, PagingInfo pagingInfo)
            throws ServiceException;

    PagedData<DispPermissionGroup> permissionGroupStringIdLikeDisp(
            String pattern, PagingInfo pagingInfo
    ) throws ServiceException;

    PagedData<DispPermissionGroup> childForParentPermissionGroupStringIdLikeDisp(
            PermissionGroupKey parentKey, String pattern, PagingInfo pagingInfo
    ) throws ServiceException;

    PagedData<DispPermissionGroup> nameLikeDisp(String pattern, PagingInfo pagingInfo)
            throws ServiceException;

    PagedData<DispPermissionGroup> childForParentNameLikeDisp(
            PermissionGroupKey parentKey, String pattern, PagingInfo pagingInfo
    ) throws ServiceException;

    PagedData<DispPermissionGroup> childForScopeRootDisp(
            StringIdKey scopeKey, PagingInfo pagingInfo
    ) throws ServiceException;

    /**
     * 查询某个权限分组到达其根节点的路径（包含展示信息）。
     *
     * @param key 权限分组的主键。
     * @return 从根节点到该权限分组父节点的路径（包含展示信息）。
     * @throws ServiceException 服务异常。
     */
    PagedData<DispPermissionGroup> pathFromRootDisp(PermissionGroupKey key) throws ServiceException;

    /**
     * 创建权限组。
     *
     * @param info 创建信息。
     * @return 创建结果。
     * @throws ServiceException 服务异常。
     */
    PermissionGroupCreateResult create(PermissionGroupCreateInfo info) throws ServiceException;

    /**
     * 更新权限组。
     *
     * @param info 更新信息。
     * @throws ServiceException 服务异常。
     */
    void update(PermissionGroupUpdateInfo info) throws ServiceException;

    /**
     * 移除权限组。
     *
     * @param info 移除信息。
     * @throws ServiceException 服务异常。
     */
    void remove(PermissionGroupRemoveInfo info) throws ServiceException;
}
