package com.dwarfeng.familyhelper.webapi.impl.service.rbac;

import com.dwarfeng.familyhelper.webapi.stack.bean.rbac.disp.DispPermissionGroup;
import com.dwarfeng.familyhelper.webapi.stack.service.rbac.PermissionGroupResponseService;
import com.dwarfeng.rbacds.stack.bean.dto.PermissionGroupCreateInfo;
import com.dwarfeng.rbacds.stack.bean.dto.PermissionGroupCreateResult;
import com.dwarfeng.rbacds.stack.bean.dto.PermissionGroupRemoveInfo;
import com.dwarfeng.rbacds.stack.bean.dto.PermissionGroupUpdateInfo;
import com.dwarfeng.rbacds.stack.bean.entity.PermissionGroup;
import com.dwarfeng.rbacds.stack.bean.entity.Scope;
import com.dwarfeng.rbacds.stack.bean.key.PermissionGroupKey;
import com.dwarfeng.rbacds.stack.service.PermissionGroupMaintainService;
import com.dwarfeng.rbacds.stack.service.PermissionGroupOperateService;
import com.dwarfeng.rbacds.stack.service.ScopeMaintainService;
import com.dwarfeng.subgrade.sdk.bean.dto.PagingUtil;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Service
public class PermissionGroupResponseServiceImpl implements PermissionGroupResponseService {

    private final PermissionGroupMaintainService permissionGroupMaintainService;
    private final ScopeMaintainService scopeMaintainService;
    private final PermissionGroupOperateService permissionGroupOperateService;

    public PermissionGroupResponseServiceImpl(
            @Qualifier("rbacPermissionGroupMaintainService")
            PermissionGroupMaintainService permissionGroupMaintainService,
            @Qualifier("rbacScopeMaintainService") ScopeMaintainService scopeMaintainService,
            @Qualifier("rbacPermissionGroupOperateService") PermissionGroupOperateService permissionGroupOperateService
    ) {
        this.permissionGroupMaintainService = permissionGroupMaintainService;
        this.permissionGroupOperateService = permissionGroupOperateService;
        this.scopeMaintainService = scopeMaintainService;
    }

    @Override
    public boolean exists(PermissionGroupKey key) throws ServiceException {
        return permissionGroupMaintainService.exists(key);
    }

    @Override
    public PermissionGroup get(PermissionGroupKey key) throws ServiceException {
        return permissionGroupMaintainService.get(key);
    }

    @Override
    public PagedData<PermissionGroup> all(PagingInfo pagingInfo) throws ServiceException {
        return permissionGroupMaintainService.lookup(pagingInfo);
    }

    @Override
    public PagedData<PermissionGroup> childForScope(StringIdKey scopeKey, PagingInfo pagingInfo)
            throws ServiceException {
        return permissionGroupMaintainService.lookup(
                PermissionGroupMaintainService.CHILD_FOR_SCOPE,
                new Object[]{scopeKey},
                pagingInfo
        );
    }

    @Override
    public PagedData<PermissionGroup> childForParent(PermissionGroupKey parentKey, PagingInfo pagingInfo)
            throws ServiceException {
        return permissionGroupMaintainService.lookup(
                PermissionGroupMaintainService.CHILD_FOR_PARENT_PERMISSION_GROUP_STRING_ID_ASC,
                new Object[]{parentKey},
                pagingInfo
        );
    }

    @Override
    public PagedData<PermissionGroup> permissionGroupStringIdLike(
            String pattern, PagingInfo pagingInfo
    ) throws ServiceException {
        return permissionGroupMaintainService.lookup(
                PermissionGroupMaintainService.PERMISSION_GROUP_STRING_ID_LIKE_PERMISSION_GROUP_STRING_ID_ASC,
                new Object[]{pattern},
                pagingInfo
        );
    }

    @Override
    public PagedData<PermissionGroup> childForParentPermissionGroupStringIdLike(
            PermissionGroupKey parentKey, String pattern, PagingInfo pagingInfo
    ) throws ServiceException {
        return permissionGroupMaintainService.lookup(
                PermissionGroupMaintainService.
                        CHILD_FOR_PARENT_PERMISSION_GROUP_STRING_ID_LIKE_PERMISSION_GROUP_STRING_ID_ASC,
                new Object[]{parentKey, pattern},
                pagingInfo
        );
    }

    @Override
    public PagedData<PermissionGroup> nameLike(String pattern, PagingInfo pagingInfo)
            throws ServiceException {
        return permissionGroupMaintainService.lookup(
                PermissionGroupMaintainService.NAME_LIKE_PERMISSION_GROUP_STRING_ID_ASC,
                new Object[]{pattern},
                pagingInfo
        );
    }

    @Override
    public PagedData<PermissionGroup> childForParentNameLike(
            PermissionGroupKey parentKey,
            String pattern,
            PagingInfo pagingInfo
    ) throws ServiceException {
        return permissionGroupMaintainService.lookup(
                PermissionGroupMaintainService.CHILD_FOR_PARENT_NAME_LIKE_PERMISSION_GROUP_STRING_ID_ASC,
                new Object[]{parentKey, pattern},
                pagingInfo
        );
    }

    @Override
    public PagedData<PermissionGroup> childForScopeRoot(
            StringIdKey scopeKey, PagingInfo pagingInfo
    ) throws ServiceException {
        return permissionGroupMaintainService.lookup(
                PermissionGroupMaintainService.CHILD_FOR_SCOPE_ROOT_PERMISSION_GROUP_STRING_ID_ASC,
                new Object[]{scopeKey},
                pagingInfo
        );
    }

    @Override
    public PagedData<PermissionGroup> pathFromRoot(PermissionGroupKey key) throws ServiceException {
        // 获取当前的权限分组作为锚点。
        PermissionGroup anchor = permissionGroupMaintainService.get(key);

        // 定义结果列表。
        List<PermissionGroup> result = new ArrayList<>();

        // 如果锚点的父键不为 null，则循环。
        while (Objects.nonNull(anchor.getParentKey())) {
            // 获取锚点的父键对应的权限组。
            anchor = permissionGroupMaintainService.get(anchor.getParentKey());
            // 将锚点加入结果列表。
            result.add(anchor);
        }

        // 将结果列表反转。
        Collections.reverse(result);

        // 返回结果。
        return PagingUtil.pagedData(result);
    }

    @Override
    public DispPermissionGroup getDisp(PermissionGroupKey key) throws ServiceException {
        PermissionGroup permissionGroup = permissionGroupMaintainService.get(key);
        return toDisp(permissionGroup);
    }

    @Override
    public PagedData<DispPermissionGroup> allDisp(PagingInfo pagingInfo) throws ServiceException {
        PagedData<PermissionGroup> pagedData = permissionGroupMaintainService.lookup(pagingInfo);
        return toDispPagedData(pagedData);
    }

    @Override
    public PagedData<DispPermissionGroup> childForScopeDisp(StringIdKey scopeKey, PagingInfo pagingInfo)
            throws ServiceException {
        PagedData<PermissionGroup> pagedData = permissionGroupMaintainService.lookup(
                PermissionGroupMaintainService.CHILD_FOR_SCOPE,
                new Object[]{scopeKey},
                pagingInfo
        );
        return toDispPagedData(pagedData);
    }

    @Override
    public PagedData<DispPermissionGroup> childForParentDisp(
            PermissionGroupKey parentKey,
            PagingInfo pagingInfo
    ) throws ServiceException {
        PagedData<PermissionGroup> pagedData = permissionGroupMaintainService.lookup(
                PermissionGroupMaintainService.CHILD_FOR_PARENT_PERMISSION_GROUP_STRING_ID_ASC,
                new Object[]{parentKey},
                pagingInfo
        );
        return toDispPagedData(pagedData);
    }

    @Override
    public PagedData<DispPermissionGroup> permissionGroupStringIdLikeDisp(
            String pattern,
            PagingInfo pagingInfo
    ) throws ServiceException {
        PagedData<PermissionGroup> pagedData = permissionGroupMaintainService.lookup(
                PermissionGroupMaintainService.PERMISSION_GROUP_STRING_ID_LIKE_PERMISSION_GROUP_STRING_ID_ASC,
                new Object[]{pattern},
                pagingInfo
        );
        return toDispPagedData(pagedData);
    }

    @Override
    public PagedData<DispPermissionGroup> childForParentPermissionGroupStringIdLikeDisp(
            PermissionGroupKey parentKey,
            String pattern,
            PagingInfo pagingInfo
    ) throws ServiceException {
        PagedData<PermissionGroup> pagedData = permissionGroupMaintainService.lookup(
                PermissionGroupMaintainService.
                        CHILD_FOR_PARENT_PERMISSION_GROUP_STRING_ID_LIKE_PERMISSION_GROUP_STRING_ID_ASC,
                new Object[]{parentKey, pattern},
                pagingInfo
        );
        return toDispPagedData(pagedData);
    }

    @Override
    public PagedData<DispPermissionGroup> nameLikeDisp(
            String pattern, PagingInfo pagingInfo
    ) throws ServiceException {
        PagedData<PermissionGroup> pagedData = permissionGroupMaintainService.lookup(
                PermissionGroupMaintainService.NAME_LIKE_PERMISSION_GROUP_STRING_ID_ASC,
                new Object[]{pattern},
                pagingInfo
        );
        return toDispPagedData(pagedData);
    }

    @Override
    public PagedData<DispPermissionGroup> childForParentNameLikeDisp(
            PermissionGroupKey parentKey, String pattern, PagingInfo pagingInfo
    ) throws ServiceException {
        PagedData<PermissionGroup> pagedData = permissionGroupMaintainService.lookup(
                PermissionGroupMaintainService.CHILD_FOR_PARENT_NAME_LIKE_PERMISSION_GROUP_STRING_ID_ASC,
                new Object[]{parentKey, pattern},
                pagingInfo
        );
        return toDispPagedData(pagedData);
    }

    @Override
    public PagedData<DispPermissionGroup> childForScopeRootDisp(
            StringIdKey scopeKey, PagingInfo pagingInfo
    ) throws ServiceException {
        PagedData<PermissionGroup> pagedData = permissionGroupMaintainService.lookup(
                PermissionGroupMaintainService.CHILD_FOR_SCOPE_ROOT_PERMISSION_GROUP_STRING_ID_ASC,
                new Object[]{scopeKey},
                pagingInfo
        );
        return toDispPagedData(pagedData);
    }

    @Override
    public PagedData<DispPermissionGroup> pathFromRootDisp(PermissionGroupKey key) throws ServiceException {
        PagedData<PermissionGroup> pagedData = pathFromRoot(key);
        return toDispPagedData(pagedData);
    }

    @Override
    public PermissionGroupCreateResult create(PermissionGroupCreateInfo info) throws ServiceException {
        return permissionGroupOperateService.create(info);
    }

    @Override
    public void update(PermissionGroupUpdateInfo info) throws ServiceException {
        permissionGroupOperateService.update(info);
    }

    @Override
    public void remove(PermissionGroupRemoveInfo info) throws ServiceException {
        permissionGroupOperateService.remove(info);
    }

    private DispPermissionGroup toDisp(PermissionGroup permissionGroup) throws ServiceException {
        if (Objects.isNull(permissionGroup)) {
            return null;
        }
        PermissionGroupKey key = permissionGroup.getKey();
        Scope scope = null;
        if (Objects.nonNull(key) && Objects.nonNull(key.getScopeStringId())) {
            scope = scopeMaintainService.getIfExists(new StringIdKey(key.getScopeStringId()));
        }
        PermissionGroupKey parentKey = permissionGroup.getParentKey();
        PermissionGroup parent = null;
        if (Objects.nonNull(parentKey)) {
            parent = permissionGroupMaintainService.getIfExists(parentKey);
        }
        boolean hasNoChild = Objects.isNull(permissionGroupMaintainService.lookupFirst(
                PermissionGroupMaintainService.CHILD_FOR_PARENT, new Object[]{key}
        ));
        return DispPermissionGroup.of(permissionGroup, scope, parent, hasNoChild);
    }

    private PagedData<DispPermissionGroup> toDispPagedData(PagedData<PermissionGroup> pagedData)
            throws ServiceException {
        List<DispPermissionGroup> dispList = new ArrayList<>(pagedData.getData().size());
        for (PermissionGroup permissionGroup : pagedData.getData()) {
            dispList.add(toDisp(permissionGroup));
        }
        return new PagedData<>(
                pagedData.getCurrentPage(), pagedData.getTotalPages(), pagedData.getRows(), pagedData.getCount(),
                dispList
        );
    }
}
