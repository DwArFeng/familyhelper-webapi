package com.dwarfeng.familyhelper.webapi.impl.service.rbac;

import com.dwarfeng.familyhelper.webapi.stack.bean.rbac.disp.DispPermission;
import com.dwarfeng.familyhelper.webapi.stack.service.rbac.PermissionResponseService;
import com.dwarfeng.rbacds.stack.bean.dto.PermissionCreateInfo;
import com.dwarfeng.rbacds.stack.bean.dto.PermissionCreateResult;
import com.dwarfeng.rbacds.stack.bean.dto.PermissionRemoveInfo;
import com.dwarfeng.rbacds.stack.bean.dto.PermissionUpdateInfo;
import com.dwarfeng.rbacds.stack.bean.entity.Permission;
import com.dwarfeng.rbacds.stack.bean.entity.PermissionGroup;
import com.dwarfeng.rbacds.stack.bean.key.PermissionGroupKey;
import com.dwarfeng.rbacds.stack.bean.key.PermissionKey;
import com.dwarfeng.rbacds.stack.service.PermissionGroupMaintainService;
import com.dwarfeng.rbacds.stack.service.PermissionMaintainService;
import com.dwarfeng.rbacds.stack.service.PermissionOperateService;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service("rbacPermissionResponseService")
public class PermissionResponseServiceImpl implements PermissionResponseService {

    private final PermissionMaintainService permissionMaintainService;
    private final PermissionOperateService permissionOperateService;
    private final PermissionGroupMaintainService permissionGroupMaintainService;

    public PermissionResponseServiceImpl(
            @Qualifier("rbacPermissionMaintainService") PermissionMaintainService permissionMaintainService,
            @Qualifier("rbacPermissionOperateService") PermissionOperateService permissionOperateService,
            @Qualifier("rbacPermissionGroupMaintainService")
            PermissionGroupMaintainService permissionGroupMaintainService
    ) {
        this.permissionMaintainService = permissionMaintainService;
        this.permissionOperateService = permissionOperateService;
        this.permissionGroupMaintainService = permissionGroupMaintainService;
    }

    @Override
    public boolean exists(PermissionKey key) throws ServiceException {
        return permissionMaintainService.exists(key);
    }

    @Override
    public Permission get(PermissionKey key) throws ServiceException {
        return permissionMaintainService.get(key);
    }

    @Override
    public PermissionKey insert(Permission entity) throws ServiceException {
        return permissionMaintainService.insert(entity);
    }

    @Override
    public void update(Permission entity) throws ServiceException {
        permissionMaintainService.update(entity);
    }

    @Override
    public void delete(PermissionKey key) throws ServiceException {
        permissionMaintainService.delete(key);
    }

    @Override
    public PagedData<Permission> all(PagingInfo pagingInfo) throws ServiceException {
        return permissionMaintainService.lookup(pagingInfo);
    }

    @Override
    public PagedData<Permission> childForScope(StringIdKey scopeKey, PagingInfo pagingInfo) throws ServiceException {
        return permissionMaintainService.lookup(
                PermissionMaintainService.CHILD_FOR_SCOPE_PERMISSION_STRING_ID_ASC,
                new Object[]{scopeKey},
                pagingInfo
        );
    }

    @Override
    public PagedData<Permission> childForGroup(PermissionGroupKey groupKey, PagingInfo pagingInfo)
            throws ServiceException {
        return permissionMaintainService.lookup(
                PermissionMaintainService.CHILD_FOR_GROUP_PERMISSION_STRING_ID_ASC,
                new Object[]{groupKey},
                pagingInfo
        );
    }

    @Override
    public PagedData<Permission> permissionStringIdLike(String pattern, PagingInfo pagingInfo)
            throws ServiceException {
        return permissionMaintainService.lookup(
                PermissionMaintainService.PERMISSION_STRING_ID_LIKE_PERMISSION_STRING_ID_ASC,
                new Object[]{pattern},
                pagingInfo
        );
    }

    @Override
    public PagedData<Permission> nameLike(String pattern, PagingInfo pagingInfo)
            throws ServiceException {
        return permissionMaintainService.lookup(
                PermissionMaintainService.NAME_LIKE_PERMISSION_STRING_ID_ASC,
                new Object[]{pattern},
                pagingInfo
        );
    }

    @Override
    public PagedData<Permission> childForScopePermissionStringIdLike(
            StringIdKey scopeKey, String pattern, PagingInfo pagingInfo
    ) throws ServiceException {
        return permissionMaintainService.lookup(
                PermissionMaintainService.CHILD_FOR_SCOPE_PERMISSION_STRING_ID_LIKE_PERMISSION_STRING_ID_ASC,
                new Object[]{scopeKey, pattern},
                pagingInfo
        );
    }

    @Override
    public PagedData<Permission> childForScopeGroupIsNull(StringIdKey scopeKey, PagingInfo pagingInfo)
            throws ServiceException {
        return permissionMaintainService.lookup(
                PermissionMaintainService.CHILD_FOR_SCOPE_GROUP_IS_NULL_PERMISSION_STRING_ID_ASC,
                new Object[]{scopeKey},
                pagingInfo
        );
    }

    @Override
    public PagedData<Permission> childForScopeGroupIsNullPermissionStringIdLike(
            StringIdKey scopeKey, String pattern, PagingInfo pagingInfo
    ) throws ServiceException {
        return permissionMaintainService.lookup(
                PermissionMaintainService.CHILD_FOR_SCOPE_GROUP_IS_NULL_PERMISSION_STRING_ID_LIKE_PERMISSION_STRING_ID_ASC,
                new Object[]{scopeKey, pattern},
                pagingInfo
        );
    }

    @Override
    public DispPermission getDisp(PermissionKey key) throws ServiceException {
        Permission permission = permissionMaintainService.get(key);
        return toDisp(permission);
    }

    @Override
    public PagedData<DispPermission> allDisp(PagingInfo pagingInfo) throws ServiceException {
        PagedData<Permission> pagedData = permissionMaintainService.lookup(pagingInfo);
        return toDispPagedData(pagedData);
    }

    @Override
    public PagedData<DispPermission> childForScopeDisp(StringIdKey scopeKey, PagingInfo pagingInfo)
            throws ServiceException {
        PagedData<Permission> pagedData = permissionMaintainService.lookup(
                PermissionMaintainService.CHILD_FOR_SCOPE_PERMISSION_STRING_ID_ASC,
                new Object[]{scopeKey},
                pagingInfo
        );
        return toDispPagedData(pagedData);
    }

    @Override
    public PagedData<DispPermission> childForGroupDisp(PermissionGroupKey groupKey, PagingInfo pagingInfo)
            throws ServiceException {
        PagedData<Permission> pagedData = permissionMaintainService.lookup(
                PermissionMaintainService.CHILD_FOR_GROUP,
                new Object[]{groupKey},
                pagingInfo
        );
        return toDispPagedData(pagedData);
    }

    @Override
    public PagedData<DispPermission> permissionStringIdLikeDisp(
            String pattern, PagingInfo pagingInfo
    ) throws ServiceException {
        PagedData<Permission> pagedData = permissionMaintainService.lookup(
                PermissionMaintainService.PERMISSION_STRING_ID_LIKE_PERMISSION_STRING_ID_ASC,
                new Object[]{pattern},
                pagingInfo
        );
        return toDispPagedData(pagedData);
    }

    @Override
    public PagedData<DispPermission> nameLikeDisp(String pattern, PagingInfo pagingInfo)
            throws ServiceException {
        PagedData<Permission> pagedData = permissionMaintainService.lookup(
                PermissionMaintainService.NAME_LIKE_PERMISSION_STRING_ID_ASC,
                new Object[]{pattern},
                pagingInfo
        );
        return toDispPagedData(pagedData);
    }

    @Override
    public PagedData<DispPermission> childForScopeGroupIsNullDisp(
            StringIdKey scopeKey, PagingInfo pagingInfo
    ) throws ServiceException {
        PagedData<Permission> pagedData = permissionMaintainService.lookup(
                PermissionMaintainService.CHILD_FOR_SCOPE_GROUP_IS_NULL_PERMISSION_STRING_ID_ASC,
                new Object[]{scopeKey},
                pagingInfo
        );
        return toDispPagedData(pagedData);
    }

    @Override
    public PagedData<DispPermission> childForScopeGroupIsNullPermissionStringIdLikeDisp(
            StringIdKey scopeKey, String pattern, PagingInfo pagingInfo
    ) throws ServiceException {
        PagedData<Permission> pagedData = permissionMaintainService.lookup(
                PermissionMaintainService
                        .CHILD_FOR_SCOPE_GROUP_IS_NULL_PERMISSION_STRING_ID_LIKE_PERMISSION_STRING_ID_ASC,
                new Object[]{scopeKey, pattern},
                pagingInfo
        );
        return toDispPagedData(pagedData);
    }

    @Override
    public PermissionCreateResult create(PermissionCreateInfo info) throws ServiceException {
        return permissionOperateService.create(info);
    }

    @Override
    public void update(PermissionUpdateInfo info) throws ServiceException {
        permissionOperateService.update(info);
    }

    @Override
    public void remove(PermissionRemoveInfo info) throws ServiceException {
        permissionOperateService.remove(info);
    }

    private DispPermission toDisp(Permission permission) throws ServiceException {
        if (Objects.isNull(permission)) {
            return null;
        }
        PermissionGroupKey groupKey = permission.getGroupKey();
        PermissionGroup group = null;
        if (Objects.nonNull(groupKey)) {
            group = permissionGroupMaintainService.getIfExists(groupKey);
        }
        return DispPermission.of(permission, group);
    }

    private PagedData<DispPermission> toDispPagedData(PagedData<Permission> pagedData) throws ServiceException {
        List<DispPermission> dispList = new ArrayList<>(pagedData.getData().size());
        for (Permission permission : pagedData.getData()) {
            dispList.add(toDisp(permission));
        }
        return new PagedData<>(
                pagedData.getCurrentPage(), pagedData.getTotalPages(), pagedData.getRows(), pagedData.getCount(),
                dispList
        );
    }
}
