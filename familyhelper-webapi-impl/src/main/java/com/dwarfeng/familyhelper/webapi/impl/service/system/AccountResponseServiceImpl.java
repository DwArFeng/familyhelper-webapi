package com.dwarfeng.familyhelper.webapi.impl.service.system;

import com.dwarfeng.acckeeper.stack.bean.entity.dto.AccountInfo;
import com.dwarfeng.acckeeper.stack.service.AccountMaintainService;
import com.dwarfeng.acckeeper.stack.service.AccountService;
import com.dwarfeng.acckeeper.stack.service.PasswordService;
import com.dwarfeng.familyhelper.webapi.stack.bean.dto.system.PasswordResetInfo;
import com.dwarfeng.familyhelper.webapi.stack.bean.dto.system.PasswordUpdateInfo;
import com.dwarfeng.familyhelper.webapi.stack.bean.dto.system.RegisterInfo;
import com.dwarfeng.familyhelper.webapi.stack.bean.vo.system.Account;
import com.dwarfeng.familyhelper.webapi.stack.service.system.AccountResponseService;
import com.dwarfeng.rbacds.stack.service.UserMaintainService;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
public class AccountResponseServiceImpl implements AccountResponseService {

    private final AccountMaintainService accountMaintainService;
    private final AccountService accountService;
    private final PasswordService passwordService;
    private final UserMaintainService rbacUserMaintainService;
    private final com.dwarfeng.familyhelper.finance.stack.service.UserMaintainService
            familyhelperFinanceUserMaintainService;

    public AccountResponseServiceImpl(
            @Qualifier("acckeeperAccountMaintainService") AccountMaintainService accountMaintainService,
            @Qualifier("acckeeperAccountService") AccountService accountService,
            @Qualifier("acckeeperPasswordService") PasswordService passwordService,
            @Qualifier("rbacUserMaintainService") UserMaintainService rbacUserMaintainService,
            @Qualifier("familyhelperFinanceUserMaintainService")
                    com.dwarfeng.familyhelper.finance.stack.service.UserMaintainService
                    familyhelperFinanceUserMaintainService
    ) {
        this.accountMaintainService = accountMaintainService;
        this.accountService = accountService;
        this.passwordService = passwordService;
        this.rbacUserMaintainService = rbacUserMaintainService;
        this.familyhelperFinanceUserMaintainService = familyhelperFinanceUserMaintainService;
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
                "敬请期待",
                account.isEnabled(),
                account.getRemark()
        );
    }

    @SuppressWarnings("DuplicatedCode")
    @Override
    public void update(Account account) throws ServiceException {
        StringIdKey accountKey = account.getKey();
        AccountInfo accountInfo = new AccountInfo(accountKey, account.isEnabled(), account.getRemark());
        com.dwarfeng.rbacds.stack.bean.entity.User rbacUser =
                new com.dwarfeng.rbacds.stack.bean.entity.User(accountKey, "通过 uiAccount 插入/更新自动生成");
        accountService.update(accountInfo);
        rbacUserMaintainService.insertOrUpdate(rbacUser);
    }

    @Override
    public void delete(StringIdKey key) throws ServiceException {
        accountMaintainService.delete(key);
        rbacUserMaintainService.deleteIfExists(key);
        familyhelperFinanceUserMaintainService.deleteIfExists(key);
    }

    @Override
    public PagedData<Account> all(PagingInfo pagingInfo) throws ServiceException {
        PagedData<com.dwarfeng.acckeeper.stack.bean.entity.Account> lookup = accountMaintainService.lookup(pagingInfo);
        return this.transformPagedAcckeeperAccount(lookup);
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
                    "敬请期待",
                    acckeeperAccount.isEnabled(),
                    acckeeperAccount.getRemark()
            ));
        }
        return new PagedData<>(lookup.getCurrentPage(), lookup.getTotalPages(),
                lookup.getRows(), lookup.getCount(), accounts);
    }

    @Override
    public void register(RegisterInfo registerInfo) throws ServiceException {
        StringIdKey accountKey = registerInfo.getKey();
        accountService.register(
                new AccountInfo(accountKey, registerInfo.isEnabled(), "调用 familyhelper-web 相关接口注册的账户"),
                registerInfo.getPassword()
        );
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
    public void updatePassword(StringIdKey key, PasswordUpdateInfo passwordUpdateInfo) throws ServiceException {
        passwordService.updatePassword(key, passwordUpdateInfo.getOldPassword(), passwordUpdateInfo.getNewPassword());
    }

    @Override
    public void resetPassword(PasswordResetInfo passwordResetInfo) throws ServiceException {
        passwordService.resetPassword(passwordResetInfo.getAccountKey(), passwordResetInfo.getNewPassword());
    }
}
