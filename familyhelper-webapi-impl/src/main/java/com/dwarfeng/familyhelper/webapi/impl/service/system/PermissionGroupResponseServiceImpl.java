package com.dwarfeng.familyhelper.webapi.impl.service.system;

import com.dwarfeng.familyhelper.webapi.stack.bean.disp.system.DispPermissionGroup;
import com.dwarfeng.familyhelper.webapi.stack.service.system.PermissionGroupResponseService;
import com.dwarfeng.rbacds.stack.bean.entity.PermissionGroup;
import com.dwarfeng.rbacds.stack.service.PermissionGroupMaintainService;
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

    @SuppressWarnings("DuplicatedCode")
    @Override
    public PagedData<DispPermissionGroup> idLikeDisp(String pattern, PagingInfo pagingInfo) throws ServiceException {
        PagedData<PermissionGroup> lookup = idLike(pattern, pagingInfo);
        return toDispPagedData(lookup);
    }

    @SuppressWarnings("DuplicatedCode")
    @Override
    public PagedData<DispPermissionGroup> childForParentDisp(StringIdKey parentKey, PagingInfo pagingInfo)
            throws ServiceException {
        PagedData<PermissionGroup> lookup = childForParent(parentKey, pagingInfo);
        return toDispPagedData(lookup);
    }

    @SuppressWarnings("DuplicatedCode")
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

    @Override
    public List<StringIdKey> pathFromRoot(StringIdKey key) throws ServiceException {
        // 获取当前的权限组。
        PermissionGroup permissionGroup = service.get(key);

        // 如果权限组没有父节点，则返回空列表。
        if (Objects.isNull(permissionGroup.getParentKey())) {
            return new ArrayList<>();
        }

        // 定义结果列表。
        List<StringIdKey> result = new ArrayList<>();

        // 获取权限组的父节点主键，作为当前节点主键。
        StringIdKey anchorKey = permissionGroup.getParentKey();

        // 将当前节点主键添加到结果列表中。
        result.add(anchorKey);

        // 循环获取父节点的父节点，直到父节点为空。
        while (Objects.nonNull(anchorKey)) {
            // 获取当前节点。
            permissionGroup = service.get(anchorKey);

            // 获取当前节点的父节点主键。
            StringIdKey parentKey = permissionGroup.getParentKey();

            // 如果当前节点没有父节点，则跳出循环。
            if (Objects.isNull(parentKey)) {
                break;
            }

            // 将父节点主键添加到结果列表中。
            result.add(parentKey);

            // 将父节点主键作为当前节点主键。
            anchorKey = parentKey;
        }

        // 将结果列表反转，并返回。
        Collections.reverse(result);
        return result;
    }
}
