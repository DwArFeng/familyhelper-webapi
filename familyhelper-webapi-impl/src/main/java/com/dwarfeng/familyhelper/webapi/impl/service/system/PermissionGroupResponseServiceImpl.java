package com.dwarfeng.familyhelper.webapi.impl.service.system;

import com.dwarfeng.familyhelper.webapi.stack.bean.system.disp.DispPermissionGroup;
import com.dwarfeng.familyhelper.webapi.stack.service.system.PermissionGroupResponseService;
import com.dwarfeng.rbacds.stack.bean.entity.PermissionGroup;
import com.dwarfeng.rbacds.stack.service.PermissionGroupMaintainService;
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

    private final PermissionGroupMaintainService service;

    public PermissionGroupResponseServiceImpl(
            @Qualifier("rbacPermissionGroupMaintainService") PermissionGroupMaintainService service
    ) {
        this.service = service;
    }

    @Override
    public boolean exists(StringIdKey key) throws ServiceException {
        return service.exists(key);
    }

    @Override
    public PermissionGroup get(StringIdKey key) throws ServiceException {
        return service.get(key);
    }

    @Override
    public StringIdKey insert(PermissionGroup permissionGroup) throws ServiceException {
        return service.insert(permissionGroup);
    }

    @Override
    public void update(PermissionGroup permissionGroup) throws ServiceException {
        service.update(permissionGroup);
    }

    @Override
    public void delete(StringIdKey key) throws ServiceException {
        service.delete(key);
    }

    @Override
    public void nestedDelete(StringIdKey key) throws ServiceException {
        findAndDelete(key);
    }

    private void findAndDelete(StringIdKey key) throws ServiceException {
        PagedData<PermissionGroup> lookup = service.lookup(
                PermissionGroupMaintainService.CHILD_FOR_PARENT, new Object[]{key}
        );
        for (PermissionGroup permissionGroup : lookup.getData()) {
            findAndDelete(permissionGroup.getKey());
        }
        service.delete(key);
    }

    @Override
    public PagedData<PermissionGroup> all(PagingInfo pagingInfo) throws ServiceException {
        return service.lookup(pagingInfo);
    }

    @Override
    public PagedData<PermissionGroup> idLike(String pattern, PagingInfo pagingInfo) throws ServiceException {
        return service.lookup(PermissionGroupMaintainService.ID_LIKE, new Object[]{pattern}, pagingInfo);
    }

    @Override
    public PagedData<PermissionGroup> childForParent(StringIdKey parentKey, PagingInfo pagingInfo)
            throws ServiceException {
        return service.lookup(PermissionGroupMaintainService.CHILD_FOR_PARENT, new Object[]{parentKey}, pagingInfo);
    }

    @Override
    public PagedData<PermissionGroup> childForRoot(PagingInfo pagingInfo) throws ServiceException {
        return service.lookup(PermissionGroupMaintainService.CHILD_FOR_PARENT, new Object[]{null}, pagingInfo);
    }

    @Override
    public PagedData<PermissionGroup> nameLike(String pattern, PagingInfo pagingInfo) throws ServiceException {
        return service.lookup(PermissionGroupMaintainService.NAME_LIKE, new Object[]{pattern}, pagingInfo);
    }

    @Override
    public DispPermissionGroup getDisp(StringIdKey key) throws ServiceException {
        PermissionGroup permissionGroup = get(key);
        return toDisp(permissionGroup);
    }

    @Override
    public PagedData<DispPermissionGroup> idLikeDisp(String pattern, PagingInfo pagingInfo) throws ServiceException {
        PagedData<PermissionGroup> lookup = idLike(pattern, pagingInfo);
        return toDispPagedData(lookup);
    }

    @Override
    public PagedData<DispPermissionGroup> childForParentDisp(StringIdKey parentKey, PagingInfo pagingInfo)
            throws ServiceException {
        PagedData<PermissionGroup> lookup = childForParent(parentKey, pagingInfo);
        return toDispPagedData(lookup);
    }

    @Override
    public PagedData<DispPermissionGroup> childForRootDisp(PagingInfo pagingInfo) throws ServiceException {
        PagedData<PermissionGroup> lookup = childForRoot(pagingInfo);
        return toDispPagedData(lookup);
    }

    @Override
    public PagedData<DispPermissionGroup> nameLikeDisp(String pattern, PagingInfo pagingInfo) throws ServiceException {
        PagedData<PermissionGroup> lookup = nameLike(pattern, pagingInfo);
        return toDispPagedData(lookup);
    }

    @Override
    public PagedData<PermissionGroup> pathFromRoot(StringIdKey key) throws ServiceException {
        // 获取当前的权限组作为锚点。
        PermissionGroup anchor = service.get(key);

        // 定义结果列表。
        List<PermissionGroup> result = new ArrayList<>();

        // 如果锚点的父键不为 null，则循环。
        while (Objects.nonNull(anchor.getParentKey())) {
            // 获取锚点的父键对应的权限组。
            anchor = service.get(anchor.getParentKey());
            // 将锚点加入结果列表。
            result.add(anchor);
        }

        // 将结果列表反转。
        Collections.reverse(result);

        // 返回结果。
        return PagingUtil.pagedData(result);
    }

    @Override
    public PagedData<DispPermissionGroup> pathFromRootDisp(StringIdKey key) throws ServiceException {
        PagedData<PermissionGroup> pathFromRoot = pathFromRoot(key);
        return toDispPagedData(pathFromRoot);
    }

    private DispPermissionGroup toDisp(PermissionGroup permissionGroup) throws ServiceException {
        PermissionGroup parentPermissionGroup = null;
        if (Objects.nonNull(permissionGroup.getParentKey())) {
            parentPermissionGroup = service.getIfExists(permissionGroup.getParentKey());
        }
        boolean hasNoChild = service.lookup(PermissionGroupMaintainService.CHILD_FOR_PARENT,
                new Object[]{permissionGroup.getKey()}, new PagingInfo(0, 1)).getCount() <= 0;
        return DispPermissionGroup.of(permissionGroup, parentPermissionGroup, hasNoChild);
    }

    private PagedData<DispPermissionGroup> toDispPagedData(PagedData<PermissionGroup> lookup) throws ServiceException {
        List<DispPermissionGroup> DispPermissionGroups = new ArrayList<>();
        for (PermissionGroup permissionGroup : lookup.getData()) {
            DispPermissionGroups.add(toDisp(permissionGroup));
        }
        return new PagedData<>(
                lookup.getCurrentPage(), lookup.getTotalPages(), lookup.getRows(), lookup.getCount(), DispPermissionGroups
        );
    }
}
