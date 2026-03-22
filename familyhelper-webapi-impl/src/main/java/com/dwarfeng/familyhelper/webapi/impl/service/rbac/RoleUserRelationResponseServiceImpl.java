package com.dwarfeng.familyhelper.webapi.impl.service.rbac;

import com.dwarfeng.familyhelper.webapi.stack.bean.rbac.disp.DispRoleUserRelation;
import com.dwarfeng.familyhelper.webapi.stack.service.rbac.RoleUserRelationResponseService;
import com.dwarfeng.rbacds.stack.bean.entity.Role;
import com.dwarfeng.rbacds.stack.bean.entity.RoleUserRelation;
import com.dwarfeng.rbacds.stack.bean.entity.User;
import com.dwarfeng.rbacds.stack.bean.key.RoleUserRelationKey;
import com.dwarfeng.rbacds.stack.service.RoleMaintainService;
import com.dwarfeng.rbacds.stack.service.RoleUserRelationMaintainService;
import com.dwarfeng.rbacds.stack.service.UserMaintainService;
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
public class RoleUserRelationResponseServiceImpl implements RoleUserRelationResponseService {

    private final RoleUserRelationMaintainService roleUserRelationMaintainService;
    private final RoleMaintainService roleMaintainService;
    private final UserMaintainService userMaintainService;

    public RoleUserRelationResponseServiceImpl(
            @Qualifier("rbacRoleUserRelationMaintainService") RoleUserRelationMaintainService
                    roleUserRelationMaintainService,
            @Qualifier("rbacRoleMaintainService") RoleMaintainService roleMaintainService,
            @Qualifier("rbacUserMaintainService") UserMaintainService userMaintainService
    ) {
        this.roleUserRelationMaintainService = roleUserRelationMaintainService;
        this.roleMaintainService = roleMaintainService;
        this.userMaintainService = userMaintainService;
    }

    @Override
    public boolean exists(RoleUserRelationKey key) throws ServiceException {
        return roleUserRelationMaintainService.exists(key);
    }

    @Override
    public RoleUserRelation get(RoleUserRelationKey key) throws ServiceException {
        return roleUserRelationMaintainService.get(key);
    }

    @Override
    public RoleUserRelationKey insert(RoleUserRelation entity) throws ServiceException {
        return roleUserRelationMaintainService.insert(entity);
    }

    @Override
    public void update(RoleUserRelation entity) throws ServiceException {
        roleUserRelationMaintainService.update(entity);
    }

    @Override
    public void delete(RoleUserRelationKey key) throws ServiceException {
        roleUserRelationMaintainService.delete(key);
    }

    @Override
    public PagedData<RoleUserRelation> all(PagingInfo pagingInfo) throws ServiceException {
        return roleUserRelationMaintainService.lookup(pagingInfo);
    }

    @Override
    public PagedData<RoleUserRelation> childForRole(StringIdKey roleKey, PagingInfo pagingInfo)
            throws ServiceException {
        return roleUserRelationMaintainService.lookup(
                RoleUserRelationMaintainService.CHILD_FOR_ROLE,
                new Object[]{roleKey},
                pagingInfo
        );
    }

    @Override
    public PagedData<RoleUserRelation> childForUser(StringIdKey userKey, PagingInfo pagingInfo)
            throws ServiceException {
        return roleUserRelationMaintainService.lookup(
                RoleUserRelationMaintainService.CHILD_FOR_USER,
                new Object[]{userKey},
                pagingInfo
        );
    }

    @Override
    public DispRoleUserRelation getDisp(RoleUserRelationKey key) throws ServiceException {
        RoleUserRelation roleUserRelation = roleUserRelationMaintainService.get(key);
        return toDisp(roleUserRelation);
    }

    @Override
    public PagedData<DispRoleUserRelation> allDisp(PagingInfo pagingInfo) throws ServiceException {
        PagedData<RoleUserRelation> lookup = roleUserRelationMaintainService.lookup(pagingInfo);
        return toDispPagedData(lookup);
    }

    @Override
    public PagedData<DispRoleUserRelation> childForRoleDisp(StringIdKey roleKey, PagingInfo pagingInfo)
            throws ServiceException {
        PagedData<RoleUserRelation> lookup = roleUserRelationMaintainService.lookup(
                RoleUserRelationMaintainService.CHILD_FOR_ROLE,
                new Object[]{roleKey},
                pagingInfo
        );
        return toDispPagedData(lookup);
    }

    @Override
    public PagedData<DispRoleUserRelation> childForUserDisp(StringIdKey userKey, PagingInfo pagingInfo)
            throws ServiceException {
        PagedData<RoleUserRelation> lookup = roleUserRelationMaintainService.lookup(
                RoleUserRelationMaintainService.CHILD_FOR_USER,
                new Object[]{userKey},
                pagingInfo
        );
        return toDispPagedData(lookup);
    }

    private DispRoleUserRelation toDisp(RoleUserRelation roleUserRelation) throws ServiceException {
        if (Objects.isNull(roleUserRelation)) {
            return null;
        }
        RoleUserRelationKey key = roleUserRelation.getKey();
        Role role = null;
        User user = null;
        if (Objects.nonNull(key)) {
            if (Objects.nonNull(key.getRoleStringId())) {
                role = roleMaintainService.getIfExists(new StringIdKey(key.getRoleStringId()));
            }
            if (Objects.nonNull(key.getUserStringId())) {
                user = userMaintainService.getIfExists(new StringIdKey(key.getUserStringId()));
            }
        }
        return DispRoleUserRelation.of(roleUserRelation, role, user);
    }

    private PagedData<DispRoleUserRelation> toDispPagedData(PagedData<RoleUserRelation> pagedData)
            throws ServiceException {
        List<DispRoleUserRelation> dispList = new ArrayList<>(pagedData.getData().size());
        for (RoleUserRelation roleUserRelation : pagedData.getData()) {
            dispList.add(toDisp(roleUserRelation));
        }
        return new PagedData<>(
                pagedData.getCurrentPage(), pagedData.getTotalPages(), pagedData.getRows(), pagedData.getCount(),
                dispList
        );
    }
}
