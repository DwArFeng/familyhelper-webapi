package com.dwarfeng.familyhelper.webapi.impl.service.acckeeper;

import com.dwarfeng.acckeeper.stack.bean.entity.Account;
import com.dwarfeng.acckeeper.stack.bean.entity.LoginHistory;
import com.dwarfeng.acckeeper.stack.service.AccountMaintainService;
import com.dwarfeng.acckeeper.stack.service.LoginHistoryMaintainService;
import com.dwarfeng.familyhelper.webapi.stack.bean.acckeeper.disp.DispLoginHistory;
import com.dwarfeng.familyhelper.webapi.stack.service.acckeeper.LoginHistoryResponseService;
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
public class LoginHistoryResponseServiceImpl implements LoginHistoryResponseService {

    private final LoginHistoryMaintainService loginHistoryMaintainService;
    private final AccountMaintainService accountMaintainService;

    public LoginHistoryResponseServiceImpl(
            @Qualifier("acckeeperLoginHistoryMaintainService") LoginHistoryMaintainService loginHistoryMaintainService,
            @Qualifier("acckeeperAccountMaintainService") AccountMaintainService accountMaintainService
    ) {
        this.loginHistoryMaintainService = loginHistoryMaintainService;
        this.accountMaintainService = accountMaintainService;
    }

    @Override
    public boolean exists(LongIdKey key) throws ServiceException {
        return loginHistoryMaintainService.exists(key);
    }

    @Override
    public LoginHistory get(LongIdKey key) throws ServiceException {
        return loginHistoryMaintainService.get(key);
    }

    @Override
    public PagedData<LoginHistory> all(PagingInfo pagingInfo) throws ServiceException {
        return loginHistoryMaintainService.lookup(
                LoginHistoryMaintainService.HAPPENED_DATE_DESC,
                new Object[0],
                pagingInfo
        );
    }

    @Override
    public PagedData<LoginHistory> accountIdEquals(String accountId, PagingInfo pagingInfo) throws ServiceException {
        return loginHistoryMaintainService.lookup(
                LoginHistoryMaintainService.ACCOUNT_ID_EQUALS_HAPPENED_DATE_DESC,
                new Object[]{accountId},
                pagingInfo
        );
    }

    @Override
    public PagedData<LoginHistory> accountIdLike(String pattern, PagingInfo pagingInfo) throws ServiceException {
        return loginHistoryMaintainService.lookup(
                LoginHistoryMaintainService.ACCOUNT_ID_LIKE_HAPPENED_DATE_DESC,
                new Object[]{pattern},
                pagingInfo
        );
    }

    @Override
    public DispLoginHistory getDisp(LongIdKey key) throws ServiceException {
        LoginHistory loginHistory = loginHistoryMaintainService.get(key);
        return toDisp(loginHistory);
    }

    @Override
    public PagedData<DispLoginHistory> allDisp(PagingInfo pagingInfo) throws ServiceException {
        PagedData<LoginHistory> pagedData = loginHistoryMaintainService.lookup(
                LoginHistoryMaintainService.HAPPENED_DATE_DESC,
                new Object[0],
                pagingInfo
        );
        return toDispPagedData(pagedData);
    }

    @Override
    public PagedData<DispLoginHistory> accountIdEqualsDisp(String accountId, PagingInfo pagingInfo)
            throws ServiceException {
        PagedData<LoginHistory> pagedData = loginHistoryMaintainService.lookup(
                LoginHistoryMaintainService.ACCOUNT_ID_EQUALS_HAPPENED_DATE_DESC,
                new Object[]{accountId},
                pagingInfo
        );
        return toDispPagedData(pagedData);
    }

    @Override
    public PagedData<DispLoginHistory> accountIdLikeDisp(String pattern, PagingInfo pagingInfo)
            throws ServiceException {
        PagedData<LoginHistory> pagedData = loginHistoryMaintainService.lookup(
                LoginHistoryMaintainService.ACCOUNT_ID_LIKE_HAPPENED_DATE_DESC,
                new Object[]{pattern},
                pagingInfo
        );
        return toDispPagedData(pagedData);
    }

    private DispLoginHistory toDisp(LoginHistory loginHistory) throws ServiceException {
        if (Objects.isNull(loginHistory)) {
            return null;
        }
        String accountId = loginHistory.getAccountId();
        Account account = null;
        if (Objects.nonNull(accountId)) {
            account = accountMaintainService.getIfExists(new StringIdKey(accountId));
        }
        return DispLoginHistory.of(loginHistory, account);
    }

    private PagedData<DispLoginHistory> toDispPagedData(PagedData<LoginHistory> pagedData) throws ServiceException {
        List<DispLoginHistory> dispLoginHistories = new ArrayList<>(pagedData.getData().size());
        for (LoginHistory loginHistory : pagedData.getData()) {
            dispLoginHistories.add(toDisp(loginHistory));
        }
        return new PagedData<>(
                pagedData.getCurrentPage(), pagedData.getTotalPages(), pagedData.getRows(), pagedData.getCount(),
                dispLoginHistories
        );
    }
}
