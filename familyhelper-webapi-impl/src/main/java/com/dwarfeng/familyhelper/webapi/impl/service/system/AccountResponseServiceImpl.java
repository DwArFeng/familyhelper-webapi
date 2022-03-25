package com.dwarfeng.familyhelper.webapi.impl.service.system;

import com.dwarfeng.acckeeper.stack.bean.dto.AccountRegisterInfo;
import com.dwarfeng.acckeeper.stack.bean.dto.AccountUpdateInfo;
import com.dwarfeng.acckeeper.stack.bean.dto.PasswordResetInfo;
import com.dwarfeng.acckeeper.stack.bean.dto.PasswordUpdateInfo;
import com.dwarfeng.acckeeper.stack.service.AccountMaintainService;
import com.dwarfeng.acckeeper.stack.service.AccountOperateService;
import com.dwarfeng.familyhelper.clannad.stack.bean.entity.Popr;
import com.dwarfeng.familyhelper.clannad.stack.bean.entity.Profile;
import com.dwarfeng.familyhelper.clannad.stack.bean.key.NicknameKey;
import com.dwarfeng.familyhelper.clannad.stack.service.NicknameMaintainService;
import com.dwarfeng.familyhelper.clannad.stack.service.PoprMaintainService;
import com.dwarfeng.familyhelper.clannad.stack.service.ProfileMaintainService;
import com.dwarfeng.familyhelper.webapi.stack.bean.disp.system.DispAccount;
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
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AccountResponseServiceImpl implements AccountResponseService {

    private final AccountMaintainService accountMaintainService;
    private final AccountOperateService accountOperateService;
    private final com.dwarfeng.rbacds.stack.service.UserMaintainService rbacUserMaintainService;
    private final com.dwarfeng.familyhelper.finance.stack.service.UserMaintainService
            familyhelperFinanceUserMaintainService;
    private final com.dwarfeng.familyhelper.clannad.stack.service.UserMaintainService
            familyhelperClannadUserMaintainService;
    private final com.dwarfeng.familyhelper.assets.stack.service.UserMaintainService
            familyhelperAssetsUserMaintainService;
    private final com.dwarfeng.rbacds.stack.service.RoleMaintainService rbacRoleMaintainService;
    private final PoprMaintainService poprMaintainService;
    private final ProfileMaintainService profileMaintainService;
    private final NicknameMaintainService nicknameMaintainService;

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    public AccountResponseServiceImpl(
            @Qualifier("acckeeperAccountMaintainService") AccountMaintainService accountMaintainService,
            @Qualifier("acckeeperAccountOperateService") AccountOperateService accountOperateService,
            @Qualifier("rbacUserMaintainService") UserMaintainService rbacUserMaintainService,
            @Qualifier("familyhelperFinanceUserMaintainService")
                    com.dwarfeng.familyhelper.finance.stack.service.UserMaintainService
                    familyhelperFinanceUserMaintainService,
            @Qualifier("familyhelperClannadUserMaintainService")
                    com.dwarfeng.familyhelper.clannad.stack.service.UserMaintainService
                    familyhelperClannadUserMaintainService,
            @Qualifier("familyhelperAssetsUserMaintainService")
                    com.dwarfeng.familyhelper.assets.stack.service.UserMaintainService
                    familyhelperAssetsUserMaintainService,
            @Qualifier("rbacRoleMaintainService") RoleMaintainService rbacRoleMaintainService,
            @Qualifier("familyhelperClannadPoprMaintainService") PoprMaintainService poprMaintainService,
            @Qualifier("familyhelperClannadProfileMaintainService") ProfileMaintainService profileMaintainService,
            @Qualifier("familyhelperClannadNicknameMaintainService") NicknameMaintainService nicknameMaintainService
    ) {
        this.accountMaintainService = accountMaintainService;
        this.accountOperateService = accountOperateService;
        this.rbacUserMaintainService = rbacUserMaintainService;
        this.familyhelperFinanceUserMaintainService = familyhelperFinanceUserMaintainService;
        this.familyhelperClannadUserMaintainService = familyhelperClannadUserMaintainService;
        this.familyhelperAssetsUserMaintainService = familyhelperAssetsUserMaintainService;
        this.rbacRoleMaintainService = rbacRoleMaintainService;
        this.poprMaintainService = poprMaintainService;
        this.profileMaintainService = profileMaintainService;
        this.nicknameMaintainService = nicknameMaintainService;
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
    public PagedData<Account> childForProfileGuest(StringIdKey profileKey, PagingInfo pagingInfo)
            throws ServiceException {
        PagedData<Popr> lookup = poprMaintainService.lookup(
                PoprMaintainService.CHILD_FOR_PROFILE, new Object[]{profileKey}, pagingInfo
        );
        return this.transformPagedClannadPopr(lookup);
    }

    @Override
    public PagedData<Account> idLike(String pattern, PagingInfo pagingInfo) throws ServiceException {
        PagedData<com.dwarfeng.acckeeper.stack.bean.entity.Account> lookup = accountMaintainService.lookup(
                AccountMaintainService.ID_LIKE, new Object[]{pattern}
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

    private PagedData<Account> transformPagedClannadPopr(PagedData<Popr> lookup) throws ServiceException {
        List<Account> accounts = new ArrayList<>();
        for (Popr popr : lookup.getData()) {
            StringIdKey key = new StringIdKey(popr.getKey().getUserId());
            com.dwarfeng.acckeeper.stack.bean.entity.Account acckeeperAccount = accountMaintainService.get(key);
            accounts.add(new Account(
                    key,
                    acckeeperAccount.getDisplayName(),
                    acckeeperAccount.isEnabled(),
                    acckeeperAccount.getRemark()
            ));
        }
        return new PagedData<>(
                lookup.getCurrentPage(), lookup.getTotalPages(), lookup.getRows(), lookup.getCount(), accounts
        );
    }

    @Override
    public DispAccount getDisp(StringIdKey key, StringIdKey inspectAccountKey) throws ServiceException {
        Account account = get(key);
        return dispAccountFromAccount(account, inspectAccountKey);
    }

    @Override
    public PagedData<DispAccount> idLikeDisp(String pattern, PagingInfo pagingInfo, StringIdKey inspectAccountKey)
            throws ServiceException {
        PagedData<Account> lookup = idLike(pattern, pagingInfo);
        List<DispAccount> dispAccounts = new ArrayList<>();
        for (Account account : lookup.getData()) {
            dispAccounts.add(dispAccountFromAccount(account, inspectAccountKey));
        }
        return new PagedData<>(
                lookup.getCurrentPage(), lookup.getTotalPages(), lookup.getRows(), lookup.getCount(), dispAccounts
        );
    }

    private DispAccount dispAccountFromAccount(Account account, StringIdKey inspectAccountKey)
            throws ServiceException {
        String displayName;

        // 1. 如果主语用户对宾语用户具有昵称，则 displayName 显示为昵称。
        NicknameKey nicknameKey = new NicknameKey(inspectAccountKey.getStringId(), account.getKey().getStringId());
        Profile profile = profileMaintainService.get(account.getKey());
        if (nicknameMaintainService.exists(nicknameKey)) {
            displayName = nicknameMaintainService.get(nicknameKey).getCall();
        }
        // 2. 如果宾语用户的个人简介中名称不为空，则 displayName 显示为个人简介的名称。
        else if (!StringUtils.isEmpty(profile.getName())) {
            displayName = profile.getName();
        }
        // 3. displayName 显示为 account 的 name。
        else {
            displayName = account.getName();
        }

        return DispAccount.of(account, displayName);
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
        com.dwarfeng.familyhelper.clannad.stack.bean.entity.User familyhelperClannadUser =
                new com.dwarfeng.familyhelper.clannad.stack.bean.entity.User(
                        accountKey, "通过 account 插入/更新自动生成"
                );
        familyhelperClannadUserMaintainService.insertOrUpdate(familyhelperClannadUser);
        com.dwarfeng.familyhelper.assets.stack.bean.entity.User familyhelperAssetsUser =
                new com.dwarfeng.familyhelper.assets.stack.bean.entity.User(
                        accountKey, "通过 account 插入/更新自动生成"
                );
        familyhelperAssetsUserMaintainService.insertOrUpdate(familyhelperAssetsUser);
        Profile profile = new Profile(
                accountKey, StringUtils.EMPTY, StringUtils.EMPTY, StringUtils.EMPTY, StringUtils.EMPTY,
                StringUtils.EMPTY, StringUtils.EMPTY, StringUtils.EMPTY, StringUtils.EMPTY, StringUtils.EMPTY,
                StringUtils.EMPTY, StringUtils.EMPTY, StringUtils.EMPTY, StringUtils.EMPTY, StringUtils.EMPTY,
                StringUtils.EMPTY, StringUtils.EMPTY, StringUtils.EMPTY, StringUtils.EMPTY, StringUtils.EMPTY,
                StringUtils.EMPTY
        );
        profileMaintainService.insertOrUpdate(profile);
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
        com.dwarfeng.familyhelper.clannad.stack.bean.entity.User familyhelperClannadUser =
                new com.dwarfeng.familyhelper.clannad.stack.bean.entity.User(
                        accountKey, "通过 account 插入/更新自动生成"
                );
        familyhelperClannadUserMaintainService.insertOrUpdate(familyhelperClannadUser);
        com.dwarfeng.familyhelper.assets.stack.bean.entity.User familyhelperAssetsUser =
                new com.dwarfeng.familyhelper.assets.stack.bean.entity.User(
                        accountKey, "通过 account 插入/更新自动生成"
                );
        familyhelperAssetsUserMaintainService.insertOrUpdate(familyhelperAssetsUser);
    }

    @Override
    public void delete(StringIdKey key) throws ServiceException {
        accountOperateService.delete(key);
        rbacUserMaintainService.deleteIfExists(key);
        familyhelperFinanceUserMaintainService.deleteIfExists(key);
        familyhelperClannadUserMaintainService.deleteIfExists(key);
        familyhelperAssetsUserMaintainService.deleteIfExists(key);
        profileMaintainService.deleteIfExists(key);
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
