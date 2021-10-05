package com.dwarfeng.familyhelper.webapi.impl.service.system;

import com.dwarfeng.familyhelper.webapi.stack.service.system.PermissionResponseService;
import com.dwarfeng.rbacds.stack.bean.entity.Permission;
import com.dwarfeng.rbacds.stack.service.PermissionMaintainService;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
public class PermissionResponseServiceImpl implements PermissionResponseService {

    private final PermissionMaintainService permissionMaintainService;

    public PermissionResponseServiceImpl(
            @Qualifier("rbacPermissionMaintainService") PermissionMaintainService permissionMaintainService
    ) {
        this.permissionMaintainService = permissionMaintainService;
    }

    @Override
    public boolean exists(StringIdKey key) throws ServiceException {
        return permissionMaintainService.exists(key);
    }

    @Override
    public Permission get(StringIdKey key) throws ServiceException {
        return permissionMaintainService.get(key);
    }

    @Override
    public StringIdKey insert(Permission permission) throws ServiceException {
        return permissionMaintainService.insert(permission);
    }

    @Override
    public void update(Permission permission) throws ServiceException {
        permissionMaintainService.update(permission);
    }

    @Override
    public void delete(StringIdKey key) throws ServiceException {
        permissionMaintainService.delete(key);
    }

    @Override
    public PagedData<Permission> all(PagingInfo pagingInfo) throws ServiceException {
        return permissionMaintainService.lookup(pagingInfo);
    }

    @Override
    public PagedData<com.dwarfeng.rbacds.stack.bean.entity.Permission> childForGroup(
            StringIdKey groupKey, PagingInfo pagingInfo
    ) throws ServiceException {
        return permissionMaintainService.lookup(
                PermissionMaintainService.CHILD_FOR_GROUP, new Object[]{groupKey}, pagingInfo
        );
    }
}
