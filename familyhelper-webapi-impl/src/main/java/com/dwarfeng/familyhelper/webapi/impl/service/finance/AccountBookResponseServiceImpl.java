package com.dwarfeng.familyhelper.webapi.impl.service.finance;

import com.dwarfeng.familyhelper.finance.stack.bean.dto.AccountBookCreateInfo;
import com.dwarfeng.familyhelper.finance.stack.bean.dto.AccountBookUpdateInfo;
import com.dwarfeng.familyhelper.finance.stack.bean.dto.PermissionRemoveInfo;
import com.dwarfeng.familyhelper.finance.stack.bean.dto.PermissionUpsertInfo;
import com.dwarfeng.familyhelper.finance.stack.bean.entity.AccountBook;
import com.dwarfeng.familyhelper.finance.stack.bean.entity.Poab;
import com.dwarfeng.familyhelper.finance.stack.service.AccountBookMaintainService;
import com.dwarfeng.familyhelper.finance.stack.service.AccountBookOperateService;
import com.dwarfeng.familyhelper.finance.stack.service.BalanceOperateService;
import com.dwarfeng.familyhelper.finance.stack.service.PoabMaintainService;
import com.dwarfeng.familyhelper.webapi.stack.bean.disp.finance.DispAccountBook;
import com.dwarfeng.familyhelper.webapi.stack.bean.disp.system.DispAccount;
import com.dwarfeng.familyhelper.webapi.stack.service.finance.AccountBookResponseService;
import com.dwarfeng.familyhelper.webapi.stack.service.system.AccountResponseService;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
public class AccountBookResponseServiceImpl implements AccountBookResponseService {

    private final AccountBookMaintainService accountBookMaintainService;
    private final PoabMaintainService poabMaintainService;
    private final AccountBookOperateService accountBookOperateService;
    private final BalanceOperateService balanceOperateService;

    private final AccountResponseService accountResponseService;

    public AccountBookResponseServiceImpl(
            @Qualifier("familyhelperFinanceAccountBookMaintainService")
                    AccountBookMaintainService accountBookMaintainService,
            @Qualifier("familyhelperFinancePoabMaintainService")
                    PoabMaintainService poabMaintainService,
            @Qualifier("familyhelperFinanceAccountBookOperateService")
                    AccountBookOperateService accountBookOperateService,
            @Qualifier("familyhelperFinanceBalanceOperateService")
                    BalanceOperateService balanceOperateService,
            AccountResponseService accountResponseService
    ) {
        this.accountBookMaintainService = accountBookMaintainService;
        this.poabMaintainService = poabMaintainService;
        this.accountBookOperateService = accountBookOperateService;
        this.balanceOperateService = balanceOperateService;
        this.accountResponseService = accountResponseService;
    }

    @Override
    public boolean exists(LongIdKey key) throws ServiceException {
        return accountBookMaintainService.exists(key);
    }

    @Override
    public AccountBook get(LongIdKey key) throws ServiceException {
        return accountBookMaintainService.get(key);
    }

    @Override
    public PagedData<AccountBook> all(PagingInfo pagingInfo) throws ServiceException {
        return accountBookMaintainService.lookup(pagingInfo);
    }

    @Override
    public DispAccountBook getDisp(LongIdKey key, StringIdKey inspectAccountKey) throws ServiceException {
        AccountBook accountBook = accountBookMaintainService.get(key);
        return dispAccountBookFromAccountBook(accountBook, inspectAccountKey);
    }

    private DispAccountBook dispAccountBookFromAccountBook(AccountBook accountBook, StringIdKey inspectAccountKey)
            throws ServiceException {
        List<Poab> relatedPoabs = poabMaintainService.lookup(
                PoabMaintainService.CHILD_FOR_ACCOUNT_BOOK, new Object[]{accountBook.getKey()}
        ).getData();
        Poab ownerPoab = relatedPoabs.stream().filter(
                p -> Objects.equals(p.getPermissionLevel(), Poab.PERMISSION_LEVEL_OWNER)
        ).findFirst().orElse(null);
        Poab myPoab = relatedPoabs.stream().filter(
                p -> Objects.equals(p.getKey().getStringId(), inspectAccountKey.getStringId())
        ).findFirst().orElse(null);
        DispAccount ownerAccount = null;
        if (Objects.nonNull(ownerPoab)) {
            ownerAccount = accountResponseService.getDisp(
                    inspectAccountKey, new StringIdKey(ownerPoab.getKey().getStringId())
            );
        }
        Integer permissionLevel = Optional.ofNullable(myPoab).map(Poab::getPermissionLevel).orElse(null);
        return DispAccountBook.of(accountBook, ownerAccount, permissionLevel);
    }

    @SuppressWarnings("DuplicatedCode")
    @Override
    public PagedData<DispAccountBook> allPermittedDisp(StringIdKey accountKey, PagingInfo pagingInfo)
            throws ServiceException {
        PagedData<Poab> lookup = poabMaintainService.lookup(
                PoabMaintainService.CHILD_FOR_USER, new Object[]{accountKey}, pagingInfo
        );
        List<DispAccountBook> dispAccountBooks = new ArrayList<>();
        for (Poab poab : lookup.getData()) {
            dispAccountBooks.add(dispAccountBookFromPoab(poab, accountKey));
        }
        return new PagedData<>(
                lookup.getCurrentPage(), lookup.getTotalPages(), lookup.getRows(), lookup.getCount(), dispAccountBooks
        );
    }

    @SuppressWarnings("DuplicatedCode")
    @Override
    public PagedData<DispAccountBook> allOwnedDisp(StringIdKey accountKey, PagingInfo pagingInfo)
            throws ServiceException {
        PagedData<Poab> lookup = poabMaintainService.lookup(
                PoabMaintainService.CHILD_FOR_USER_PERMISSION_LEVEL_EQUALS,
                new Object[]{accountKey, Poab.PERMISSION_LEVEL_OWNER},
                pagingInfo
        );
        List<DispAccountBook> dispAccountBooks = new ArrayList<>();
        for (Poab poab : lookup.getData()) {
            dispAccountBooks.add(dispAccountBookFromPoab(poab, accountKey));
        }
        return new PagedData<>(
                lookup.getCurrentPage(), lookup.getTotalPages(), lookup.getRows(), lookup.getCount(), dispAccountBooks
        );
    }

    private DispAccountBook dispAccountBookFromPoab(Poab poab, StringIdKey inspectAccountKey) throws ServiceException {
        AccountBook accountBook = accountBookMaintainService.get(new LongIdKey(poab.getKey().getLongId()));
        List<Poab> relatedPoabs = poabMaintainService.lookup(
                PoabMaintainService.CHILD_FOR_ACCOUNT_BOOK, new Object[]{accountBook.getKey()}
        ).getData();
        Poab ownerPoab = relatedPoabs.stream().filter(
                p -> Objects.equals(p.getPermissionLevel(), Poab.PERMISSION_LEVEL_OWNER)
        ).findFirst().orElse(null);
        DispAccount ownerAccount = null;
        if (Objects.nonNull(ownerPoab)) {
            ownerAccount = accountResponseService.getDisp(
                    new StringIdKey(ownerPoab.getKey().getStringId()), inspectAccountKey
            );
        }
        Integer permissionLevel = poab.getPermissionLevel();
        return DispAccountBook.of(accountBook, ownerAccount, permissionLevel);
    }

    @Override
    public LongIdKey createAccountBook(StringIdKey userKey, AccountBookCreateInfo accountBookCreateInfo)
            throws ServiceException {
        return accountBookOperateService.createAccountBook(userKey, accountBookCreateInfo);
    }

    @Override
    public void updateAccountBook(StringIdKey userKey, AccountBookUpdateInfo accountBookUpdateInfo)
            throws ServiceException {
        accountBookOperateService.updateAccountBook(userKey, accountBookUpdateInfo);
    }

    @Override
    public void removeAccountBook(StringIdKey userKey, LongIdKey accountBookKey) throws ServiceException {
        accountBookOperateService.removeAccountBook(userKey, accountBookKey);
    }

    @Override
    public void upsertPermission(StringIdKey userKey, PermissionUpsertInfo permissionUpsertInfo) throws ServiceException {
        accountBookOperateService.upsertPermission(userKey, permissionUpsertInfo);
    }

    @Override
    public void removePermission(StringIdKey userKey, PermissionRemoveInfo permissionRemoveInfo) throws ServiceException {
        accountBookOperateService.removePermission(userKey, permissionRemoveInfo);
    }

    @Override
    public void recordCommit(StringIdKey userKey, LongIdKey accountBookKey) throws ServiceException {
        balanceOperateService.recordCommit(userKey, accountBookKey);
    }

    @Override
    public void rollbackAll(StringIdKey userKey, LongIdKey accountBookKey) throws ServiceException {
        balanceOperateService.rollbackAll(userKey, accountBookKey);
    }
}
