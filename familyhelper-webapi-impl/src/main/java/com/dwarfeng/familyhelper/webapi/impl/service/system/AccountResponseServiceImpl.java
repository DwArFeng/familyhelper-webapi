package com.dwarfeng.familyhelper.webapi.impl.service.system;

import com.dwarfeng.acckeeper.stack.bean.dto.AccountRegisterInfo;
import com.dwarfeng.acckeeper.stack.bean.dto.AccountUpdateInfo;
import com.dwarfeng.acckeeper.stack.bean.dto.PasswordResetInfo;
import com.dwarfeng.acckeeper.stack.bean.dto.PasswordUpdateInfo;
import com.dwarfeng.acckeeper.stack.service.AccountMaintainService;
import com.dwarfeng.acckeeper.stack.service.AccountOperateService;
import com.dwarfeng.familyhelper.webapi.stack.bean.vo.system.Account;
import com.dwarfeng.familyhelper.webapi.stack.service.system.AccountResponseService;
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

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
public class AccountResponseServiceImpl implements AccountResponseService {

    private final AccountMaintainService accountMaintainService;
    private final AccountOperateService accountOperateService;
    private final com.dwarfeng.rbacds.stack.service.UserMaintainService rbacUserMaintainService;
    private final com.dwarfeng.familyhelper.finance.stack.service.UserMaintainService
            familyhelperFinanceUserMaintainService;
    private final com.dwarfeng.rbacds.stack.service.RoleMaintainService rbacRoleMaintainService;

    public AccountResponseServiceImpl(
            @Qualifier("acckeeperAccountMaintainService") AccountMaintainService accountMaintainService,
            @Qualifier("acckeeperAccountOperateService") AccountOperateService accountOperateService,
            @Qualifier("rbacUserMaintainService")
                    UserMaintainService rbacUserMaintainService,
            @Qualifier("familyhelperFinanceUserMaintainService")
                    com.dwarfeng.familyhelper.finance.stack.service.UserMaintainService
                    familyhelperFinanceUserMaintainService,
            @Qualifier("rbacRoleMaintainService")
                    com.dwarfeng.rbacds.stack.service.RoleMaintainService rbacRoleMaintainService
    ) {
        this.accountMaintainService = accountMaintainService;
        this.accountOperateService = accountOperateService;
        this.rbacUserMaintainService = rbacUserMaintainService;
        this.familyhelperFinanceUserMaintainService = familyhelperFinanceUserMaintainService;
        this.rbacRoleMaintainService = rbacRoleMaintainService;
    }

    @Override
    public boolean exists(StringIdKey key) throws ServiceException {
        return accountMaintainService.exists(key);
    }

    @Override
    public Account get(StringIdKey key) throws ServiceException {
        com.dwarfeng.acckeeper.stack.bean.entity.Account account = accountMaintainService.get(key);
        return new Account(
                key,
                account.getDisplayName(),
                account.isEnabled(),
                account.getRemark()
        );
    }

    @Override
    public void addRoleRelation(StringIdKey accountKey, StringIdKey roleKey) throws ServiceException {
        rbacUserMaintainService.addRoleRelation(accountKey, roleKey);
    }

    @Override
    public void deleteRoleRelation(StringIdKey accountKey, StringIdKey roleKey) throws ServiceException {
        rbacUserMaintainService.deleteRoleRelation(accountKey, roleKey);
    }

    @Override
    public void resetRoleRelation(StringIdKey accountKey, List<StringIdKey> roleKeys) throws ServiceException {
        List<StringIdKey> oldRoleKeys = rbacRoleMaintainService.lookup(
                RoleMaintainService.ROLE_FOR_USER, new Object[]{accountKey}
        ).getData().stream().map(Role::getKey).collect(Collectors.toList());
        rbacUserMaintainService.batchDeleteRoleRelations(accountKey, oldRoleKeys);
        rbacUserMaintainService.batchAddRoleRelations(accountKey, roleKeys);
    }

    @Override
    public PagedData<Account> all(PagingInfo pagingInfo) throws ServiceException {
        PagedData<com.dwarfeng.acckeeper.stack.bean.entity.Account> lookup = accountMaintainService.lookup(pagingInfo);
        return this.transformPagedAcckeeperAccount(lookup);
    }

    @Override
    public PagedData<Account> childForRole(StringIdKey accountKey, PagingInfo pagingInfo) throws ServiceException {
        PagedData<User> lookup = rbacUserMaintainService.lookup(
                UserMaintainService.CHILD_FOR_ROLE, new Object[]{accountKey}, pagingInfo
        );
        return this.transformPagedRbacUser(lookup);
    }

    @Override
    public PagedData<Account> idLike(String pattern, PagingInfo pagingInfo) throws ServiceException {
        PagedData<com.dwarfeng.acckeeper.stack.bean.entity.Account> lookup = accountMaintainService.lookup(
                AccountMaintainService.ID_LIKE, new Object[]{pattern}
        );
        return this.transformPagedAcckeeperAccount(lookup);
    }

    @Override
    public PagedData<Account> displayNameLike(String pattern, PagingInfo pagingInfo) throws ServiceException {
        PagedData<com.dwarfeng.acckeeper.stack.bean.entity.Account> lookup = accountMaintainService.lookup(
                AccountMaintainService.DISPLAY_NAME_LIKE, new Object[]{pattern}
        );
        return this.transformPagedAcckeeperAccount(lookup);
    }

    private PagedData<Account> transformPagedAcckeeperAccount(
            PagedData<com.dwarfeng.acckeeper.stack.bean.entity.Account> lookup
    ) {
        List<Account> accounts = new ArrayList<>();
        for (com.dwarfeng.acckeeper.stack.bean.entity.Account acckeeperAccount : lookup.getData()) {
            StringIdKey key = acckeeperAccount.getKey();
            accounts.add(new Account(
                    key,
                    acckeeperAccount.getDisplayName(),
                    acckeeperAccount.isEnabled(),
                    acckeeperAccount.getRemark()
            ));
        }
        return new PagedData<>(lookup.getCurrentPage(), lookup.getTotalPages(),
                lookup.getRows(), lookup.getCount(), accounts);
    }

    private PagedData<Account> transformPagedRbacUser(
            PagedData<com.dwarfeng.rbacds.stack.bean.entity.User> lookup
    ) throws ServiceException {
        List<Account> accounts = new ArrayList<>();
        for (com.dwarfeng.rbacds.stack.bean.entity.User user : lookup.getData()) {
            StringIdKey key = user.getKey();
            com.dwarfeng.acckeeper.stack.bean.entity.Account acckeeperAccount = accountMaintainService.get(key);
            accounts.add(new Account(
                    key,
                    acckeeperAccount.getDisplayName(),
                    acckeeperAccount.isEnabled(),
                    acckeeperAccount.getRemark()
            ));
        }
        return new PagedData<>(lookup.getCurrentPage(), lookup.getTotalPages(),
                lookup.getRows(), lookup.getCount(), accounts);
    }

    @SuppressWarnings("DuplicatedCode")
    @Override
    public void register(AccountRegisterInfo accountRegisterInfo) throws ServiceException {
        StringIdKey accountKey = accountRegisterInfo.getAccountKey();
        accountOperateService.register(accountRegisterInfo);
        com.dwarfeng.rbacds.stack.bean.entity.User rbacUser =
                new com.dwarfeng.rbacds.stack.bean.entity.User(accountKey, "通过 account 插入/更新自动生成");
        rbacUserMaintainService.insertOrUpdate(rbacUser);
        com.dwarfeng.familyhelper.finance.stack.bean.entity.User familyhelperFinanceUser =
                new com.dwarfeng.familyhelper.finance.stack.bean.entity.User(
                        accountKey, "通过 account 插入/更新自动生成"
                );
        familyhelperFinanceUserMaintainService.insertOrUpdate(familyhelperFinanceUser);
    }

    @SuppressWarnings("DuplicatedCode")
    @Override
    public void update(AccountUpdateInfo accountUpdateInfo) throws ServiceException {
        StringIdKey accountKey = accountUpdateInfo.getAccountKey();
        accountOperateService.update(accountUpdateInfo);
        com.dwarfeng.rbacds.stack.bean.entity.User rbacUser =
                new com.dwarfeng.rbacds.stack.bean.entity.User(accountKey, "通过 account 插入/更新自动生成");
        rbacUserMaintainService.insertOrUpdate(rbacUser);
        com.dwarfeng.familyhelper.finance.stack.bean.entity.User familyhelperFinanceUser =
                new com.dwarfeng.familyhelper.finance.stack.bean.entity.User(
                        accountKey, "通过 account 插入/更新自动生成"
                );
        familyhelperFinanceUserMaintainService.insertOrUpdate(familyhelperFinanceUser);
    }

    @Override
    public void delete(StringIdKey key) throws ServiceException {
        accountOperateService.delete(key);
        rbacUserMaintainService.deleteIfExists(key);
        familyhelperFinanceUserMaintainService.deleteIfExists(key);
    }

    @Override
    public void updatePassword(PasswordUpdateInfo passwordUpdateInfo) throws ServiceException {
        accountOperateService.updatePassword(passwordUpdateInfo);
        accountOperateService.invalid(passwordUpdateInfo.getAccountKey());
    }

    @Override
    public void resetPassword(PasswordResetInfo passwordResetInfo) throws ServiceException {
        accountOperateService.resetPassword(passwordResetInfo);
        accountOperateService.invalid(passwordResetInfo.getAccountKey());
    }

    @Override
    public void invalid(StringIdKey key) throws ServiceException {
        accountOperateService.invalid(key);
    }
}
