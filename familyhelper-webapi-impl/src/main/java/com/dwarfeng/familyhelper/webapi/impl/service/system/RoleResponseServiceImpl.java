package com.dwarfeng.familyhelper.webapi.impl.service.system;

import com.dwarfeng.acckeeper.stack.service.AccountOperateService;
import com.dwarfeng.familyhelper.webapi.stack.service.system.RoleResponseService;
import com.dwarfeng.rbacds.stack.bean.entity.Role;
import com.dwarfeng.rbacds.stack.bean.entity.User;
import com.dwarfeng.rbacds.stack.service.RoleMaintainService;
import com.dwarfeng.rbacds.stack.service.UserMaintainService;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
public class RoleResponseServiceImpl implements RoleResponseService {

    private final RoleMaintainService roleMaintainService;
    private final UserMaintainService userMaintainService;
    private final AccountOperateService accountOperateService;

    public RoleResponseServiceImpl(
            @Qualifier("rbacRoleMaintainService") RoleMaintainService roleMaintainService,
            @Qualifier("rbacUserMaintainService") UserMaintainService userMaintainService,
            @Qualifier("acckeeperAccountOperateService") AccountOperateService accountOperateService
    ) {
        this.roleMaintainService = roleMaintainService;
        this.userMaintainService = userMaintainService;
        this.accountOperateService = accountOperateService;
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
    public StringIdKey insert(Role role) throws ServiceException {
        return roleMaintainService.insert(role);
    }

    @Override
    public void update(Role role) throws ServiceException {
        Role oldRole = roleMaintainService.get(role.getKey());
        if (oldRole.isEnabled() != role.isEnabled()) {
            invalidInfluencedUsers(role.getKey());
        }
        roleMaintainService.update(role);
    }

    @Override
    public void delete(StringIdKey key) throws ServiceException {
        invalidInfluencedUsers(key);
        roleMaintainService.delete(key);
    }

    private void invalidInfluencedUsers(StringIdKey roleKey) throws ServiceException {
        List<StringIdKey> influencedUserKeys = userMaintainService.lookup(
                UserMaintainService.CHILD_FOR_ROLE, new Object[]{roleKey}
        ).getData().stream().map(User::getKey).collect(Collectors.toList());
        for (StringIdKey influencedUserKey : influencedUserKeys) {
            accountOperateService.invalid(influencedUserKey);
        }
    }

    @Override
    public void addAccountRelation(StringIdKey roleIdKey, StringIdKey accountKey) throws ServiceException {
        roleMaintainService.addUserRelation(roleIdKey, accountKey);
    }

    @Override
    public void deleteAccountRelation(StringIdKey roleIdKey, StringIdKey accountKey) throws ServiceException {
        roleMaintainService.deleteUserRelation(roleIdKey, accountKey);
    }

    @Override
    public PagedData<Role> all(PagingInfo pagingInfo) throws ServiceException {
        return roleMaintainService.lookup(pagingInfo);
    }

    @Override
    public PagedData<Role> childForAccount(StringIdKey accountKey, PagingInfo pagingInfo) throws ServiceException {
        return roleMaintainService.lookup(RoleMaintainService.ROLE_FOR_USER, new Object[]{accountKey}, pagingInfo);
    }
}
