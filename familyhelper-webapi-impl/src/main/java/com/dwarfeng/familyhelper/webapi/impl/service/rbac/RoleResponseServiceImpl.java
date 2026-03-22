package com.dwarfeng.familyhelper.webapi.impl.service.rbac;

import com.dwarfeng.familyhelper.webapi.stack.service.rbac.RoleResponseService;
import com.dwarfeng.rbacds.stack.bean.entity.Role;
import com.dwarfeng.rbacds.stack.service.RoleMaintainService;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class RoleResponseServiceImpl implements RoleResponseService {

    private final RoleMaintainService roleMaintainService;

    public RoleResponseServiceImpl(
            @Qualifier("rbacRoleMaintainService") RoleMaintainService roleMaintainService
    ) {
        this.roleMaintainService = roleMaintainService;
    }

    @Override
    public boolean exists(StringIdKey key) throws ServiceException {
        return roleMaintainService.exists(key);
    }

    @Override
    public Role get(StringIdKey key) throws ServiceException {
        return roleMaintainService.get(key);
    }

    @Override
    public StringIdKey insert(Role entity) throws ServiceException {
        return roleMaintainService.insert(entity);
    }

    @Override
    public void update(Role entity) throws ServiceException {
        roleMaintainService.update(entity);
    }

    @Override
    public void delete(StringIdKey key) throws ServiceException {
        roleMaintainService.delete(key);
    }

    @Override
    public PagedData<Role> all(PagingInfo pagingInfo) throws ServiceException {
        return roleMaintainService.lookup(pagingInfo);
    }

    @Override
    public PagedData<Role> childForUserAvailable(StringIdKey userKey, PagingInfo pagingInfo)
            throws ServiceException {
        return roleMaintainService.lookup(
                RoleMaintainService.CHILD_FOR_USER_AVAILABLE_KEY_ASC,
                new Object[]{userKey},
                pagingInfo
        );
    }

    @Override
    public PagedData<Role> enabled(PagingInfo pagingInfo) throws ServiceException {
        return roleMaintainService.lookup(
                RoleMaintainService.ENABLED,
                new Object[0],
                pagingInfo
        );
    }

    @Override
    public PagedData<Role> idLike(String pattern, PagingInfo pagingInfo) throws ServiceException {
        return roleMaintainService.lookup(
                RoleMaintainService.ID_LIKE,
                new Object[]{pattern},
                pagingInfo
        );
    }

    @Override
    public PagedData<Role> notChildForUser(StringIdKey userKey, PagingInfo pagingInfo) throws ServiceException {
        return roleMaintainService.lookup(
                RoleMaintainService.NOT_CHILD_FOR_USER_KEY_ASC,
                new Object[]{userKey},
                pagingInfo
        );
    }
}
