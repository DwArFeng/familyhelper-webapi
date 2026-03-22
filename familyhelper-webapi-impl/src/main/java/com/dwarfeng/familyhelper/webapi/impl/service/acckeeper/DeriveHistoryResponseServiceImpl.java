package com.dwarfeng.familyhelper.webapi.impl.service.acckeeper;

import com.dwarfeng.acckeeper.stack.bean.entity.Account;
import com.dwarfeng.acckeeper.stack.bean.entity.DeriveHistory;
import com.dwarfeng.acckeeper.stack.service.AccountMaintainService;
import com.dwarfeng.acckeeper.stack.service.DeriveHistoryMaintainService;
import com.dwarfeng.familyhelper.webapi.stack.bean.acckeeper.disp.DispDeriveHistory;
import com.dwarfeng.familyhelper.webapi.stack.service.acckeeper.DeriveHistoryResponseService;
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

@Service
public class DeriveHistoryResponseServiceImpl implements DeriveHistoryResponseService {

    private final DeriveHistoryMaintainService deriveHistoryMaintainService;
    private final AccountMaintainService accountMaintainService;

    public DeriveHistoryResponseServiceImpl(
            @Qualifier("acckeeperDeriveHistoryMaintainService")
            DeriveHistoryMaintainService deriveHistoryMaintainService,
            @Qualifier("acckeeperAccountMaintainService")
            AccountMaintainService accountMaintainService
    ) {
        this.deriveHistoryMaintainService = deriveHistoryMaintainService;
        this.accountMaintainService = accountMaintainService;
    }

    @Override
    public boolean exists(LongIdKey key) throws ServiceException {
        return deriveHistoryMaintainService.exists(key);
    }

    @Override
    public DeriveHistory get(LongIdKey key) throws ServiceException {
        return deriveHistoryMaintainService.get(key);
    }

    @Override
    public PagedData<DeriveHistory> all(PagingInfo pagingInfo) throws ServiceException {
        return deriveHistoryMaintainService.lookup(
                DeriveHistoryMaintainService.HAPPENED_DATE_DESC,
                new Object[0],
                pagingInfo
        );
    }

    @Override
    public PagedData<DeriveHistory> accountIdEquals(String accountId, PagingInfo pagingInfo) throws ServiceException {
        return deriveHistoryMaintainService.lookup(
                DeriveHistoryMaintainService.ACCOUNT_ID_EQUALS_HAPPENED_DATE_DESC,
                new Object[]{accountId},
                pagingInfo
        );
    }

    @Override
    public PagedData<DeriveHistory> accountIdLike(String pattern, PagingInfo pagingInfo) throws ServiceException {
        return deriveHistoryMaintainService.lookup(
                DeriveHistoryMaintainService.ACCOUNT_ID_LIKE_HAPPENED_DATE_DESC,
                new Object[]{pattern},
                pagingInfo
        );
    }

    @Override
    public DispDeriveHistory getDisp(LongIdKey key) throws ServiceException {
        DeriveHistory deriveHistory = deriveHistoryMaintainService.get(key);
        return toDisp(deriveHistory);
    }

    @Override
    public PagedData<DispDeriveHistory> allDisp(PagingInfo pagingInfo) throws ServiceException {
        PagedData<DeriveHistory> pagedData = deriveHistoryMaintainService.lookup(
                DeriveHistoryMaintainService.HAPPENED_DATE_DESC,
                new Object[0],
                pagingInfo
        );
        return toDispPagedData(pagedData);
    }

    @Override
    public PagedData<DispDeriveHistory> accountIdEqualsDisp(String accountId, PagingInfo pagingInfo)
            throws ServiceException {
        PagedData<DeriveHistory> pagedData = deriveHistoryMaintainService.lookup(
                DeriveHistoryMaintainService.ACCOUNT_ID_EQUALS_HAPPENED_DATE_DESC,
                new Object[]{accountId},
                pagingInfo
        );
        return toDispPagedData(pagedData);
    }

    @Override
    public PagedData<DispDeriveHistory> accountIdLikeDisp(String pattern, PagingInfo pagingInfo)
            throws ServiceException {
        PagedData<DeriveHistory> pagedData = deriveHistoryMaintainService.lookup(
                DeriveHistoryMaintainService.ACCOUNT_ID_LIKE_HAPPENED_DATE_DESC,
                new Object[]{pattern},
                pagingInfo
        );
        return toDispPagedData(pagedData);
    }

    private DispDeriveHistory toDisp(DeriveHistory deriveHistory) throws ServiceException {
        if (Objects.isNull(deriveHistory)) {
            return null;
        }
        String accountId = deriveHistory.getAccountId();
        Account account = null;
        if (Objects.nonNull(accountId)) {
            account = accountMaintainService.getIfExists(new StringIdKey(accountId));
        }
        return DispDeriveHistory.of(deriveHistory, account);
    }

    private PagedData<DispDeriveHistory> toDispPagedData(PagedData<DeriveHistory> pagedData) throws ServiceException {
        List<DispDeriveHistory> dispDeriveHistories = new ArrayList<>(pagedData.getData().size());
        for (DeriveHistory deriveHistory : pagedData.getData()) {
            dispDeriveHistories.add(toDisp(deriveHistory));
        }
        return new PagedData<>(
                pagedData.getCurrentPage(), pagedData.getTotalPages(), pagedData.getRows(), pagedData.getCount(),
                dispDeriveHistories
        );
    }
}
