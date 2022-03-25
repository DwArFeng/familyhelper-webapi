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
import java.util.List;
import java.util.Objects;

@Service
public class PermissionGroupResponseServiceImpl implements PermissionGroupResponseService {

    private final PermissionGroupMaintainService service;

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
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
    public DispPermissionGroup getDisp(StringIdKey key) throws ServiceException {
        return DispPermissionGroup(service.get(key));
    }

    @SuppressWarnings("DuplicatedCode")
    @Override
    public PagedData<DispPermissionGroup> idLikeDisp(String pattern, PagingInfo pagingInfo) throws ServiceException {
        PagedData<PermissionGroup> lookup = service.lookup(PermissionGroupMaintainService.ID_LIKE,
                new Object[]{pattern}, pagingInfo);
        List<DispPermissionGroup> DispPermissionGroups = new ArrayList<>();
        for (PermissionGroup permissionGroup : lookup.getData()) {
            DispPermissionGroups.add(DispPermissionGroup(permissionGroup));
        }
        return new PagedData<>(lookup.getCurrentPage(), lookup.getTotalPages(), lookup.getRows(), lookup.getCount(),
                DispPermissionGroups);
    }

    @SuppressWarnings("DuplicatedCode")
    @Override
    public PagedData<DispPermissionGroup> childForParentDisp(StringIdKey parentKey, PagingInfo pagingInfo) throws ServiceException {
        PagedData<PermissionGroup> lookup = service.lookup(PermissionGroupMaintainService.CHILD_FOR_PARENT,
                new Object[]{parentKey}, pagingInfo);
        List<DispPermissionGroup> DispPermissionGroups = new ArrayList<>();
        for (PermissionGroup permissionGroup : lookup.getData()) {
            DispPermissionGroups.add(DispPermissionGroup(permissionGroup));
        }
        return new PagedData<>(lookup.getCurrentPage(), lookup.getTotalPages(), lookup.getRows(), lookup.getCount(),
                DispPermissionGroups);
    }

    @SuppressWarnings("DuplicatedCode")
    @Override
    public PagedData<DispPermissionGroup> childForRootDisp(PagingInfo pagingInfo) throws ServiceException {
        PagedData<PermissionGroup> lookup = service.lookup(PermissionGroupMaintainService.CHILD_FOR_PARENT,
                new Object[]{null}, pagingInfo);
        List<DispPermissionGroup> DispPermissionGroups = new ArrayList<>();
        for (PermissionGroup permissionGroup : lookup.getData()) {
            DispPermissionGroups.add(DispPermissionGroup(permissionGroup));
        }
        return new PagedData<>(lookup.getCurrentPage(), lookup.getTotalPages(), lookup.getRows(), lookup.getCount(),
                DispPermissionGroups);
    }

    private DispPermissionGroup DispPermissionGroup(PermissionGroup permissionGroup) throws ServiceException {
        boolean hasNoChild = service.lookup(PermissionGroupMaintainService.CHILD_FOR_PARENT,
                new Object[]{permissionGroup.getKey()}, new PagingInfo(0, 1)).getCount() <= 0;
        PermissionGroup parentPermissionGroup = null;
        if (Objects.nonNull(permissionGroup.getParentKey())) {
            parentPermissionGroup = service.getIfExists(permissionGroup.getParentKey());
        }
        return DispPermissionGroup.of(permissionGroup, parentPermissionGroup, hasNoChild);
    }
}
