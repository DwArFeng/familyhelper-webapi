package com.dwarfeng.familyhelper.webapi.impl.service.acckeeper;

import com.dwarfeng.acckeeper.stack.bean.entity.Account;
import com.dwarfeng.acckeeper.stack.bean.entity.LoginState;
import com.dwarfeng.acckeeper.stack.service.AccountMaintainService;
import com.dwarfeng.acckeeper.stack.service.LoginStateMaintainService;
import com.dwarfeng.familyhelper.webapi.stack.bean.acckeeper.disp.DispLoginState;
import com.dwarfeng.familyhelper.webapi.stack.service.acckeeper.LoginStateResponseService;
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
public class LoginStateResponseServiceImpl implements LoginStateResponseService {

    private final LoginStateMaintainService loginStateMaintainService;
    private final AccountMaintainService accountMaintainService;

    public LoginStateResponseServiceImpl(
            @Qualifier("acckeeperLoginStateMaintainService") LoginStateMaintainService loginStateMaintainService,
            @Qualifier("acckeeperAccountMaintainService") AccountMaintainService accountMaintainService
    ) {
        this.loginStateMaintainService = loginStateMaintainService;
        this.accountMaintainService = accountMaintainService;
    }

    @Override
    public boolean exists(StringIdKey key) throws ServiceException {
        return loginStateMaintainService.exists(key);
    }

    @Override
    public LoginState get(StringIdKey key) throws ServiceException {
        return loginStateMaintainService.get(key);
    }

    @Override
    public PagedData<LoginState> all(PagingInfo pagingInfo) throws ServiceException {
        return loginStateMaintainService.lookup(pagingInfo);
    }

    @Override
    public PagedData<LoginState> childForAccount(StringIdKey accountKey, PagingInfo pagingInfo)
            throws ServiceException {
        return loginStateMaintainService.lookup(
                LoginStateMaintainService.CHILD_FOR_ACCOUNT_GENERATED_DATE_DESC,
                new Object[]{accountKey},
                pagingInfo
        );
    }

    @Override
    public PagedData<LoginState> childForAccountTypeEqualsDynamic(StringIdKey accountKey, PagingInfo pagingInfo)
            throws ServiceException {
        return loginStateMaintainService.lookup(
                LoginStateMaintainService.CHILD_FOR_ACCOUNT_TYPE_EQUALS_DYNAMIC_GENERATED_DATE_DESC,
                new Object[]{accountKey},
                pagingInfo
        );
    }

    @Override
    public PagedData<LoginState> childForAccountTypeEqualsStatic(StringIdKey accountKey, PagingInfo pagingInfo)
            throws ServiceException {
        return loginStateMaintainService.lookup(
                LoginStateMaintainService.CHILD_FOR_ACCOUNT_TYPE_EQUALS_STATIC_GENERATED_DATE_DESC,
                new Object[]{accountKey},
                pagingInfo
        );
    }

    @Override
    public DispLoginState getDisp(StringIdKey key) throws ServiceException {
        LoginState loginState = loginStateMaintainService.get(key);
        return toDisp(loginState);
    }

    @Override
    public PagedData<DispLoginState> allDisp(PagingInfo pagingInfo) throws ServiceException {
        PagedData<LoginState> pagedData = loginStateMaintainService.lookup(pagingInfo);
        return toDispPagedData(pagedData);
    }

    @Override
    public PagedData<DispLoginState> childForAccountDisp(StringIdKey accountKey, PagingInfo pagingInfo)
            throws ServiceException {
        PagedData<LoginState> pagedData = loginStateMaintainService.lookup(
                LoginStateMaintainService.CHILD_FOR_ACCOUNT_GENERATED_DATE_DESC,
                new Object[]{accountKey},
                pagingInfo
        );
        return toDispPagedData(pagedData);
    }

    @Override
    public PagedData<DispLoginState> childForAccountTypeEqualsDynamicDisp(StringIdKey accountKey, PagingInfo pagingInfo)
            throws ServiceException {
        PagedData<LoginState> pagedData = loginStateMaintainService.lookup(
                LoginStateMaintainService.CHILD_FOR_ACCOUNT_TYPE_EQUALS_DYNAMIC_GENERATED_DATE_DESC,
                new Object[]{accountKey},
                pagingInfo
        );
        return toDispPagedData(pagedData);
    }

    @Override
    public PagedData<DispLoginState> childForAccountTypeEqualsStaticDisp(StringIdKey accountKey, PagingInfo pagingInfo)
            throws ServiceException {
        PagedData<LoginState> pagedData = loginStateMaintainService.lookup(
                LoginStateMaintainService.CHILD_FOR_ACCOUNT_TYPE_EQUALS_STATIC_GENERATED_DATE_DESC,
                new Object[]{accountKey},
                pagingInfo
        );
        return toDispPagedData(pagedData);
    }

    private DispLoginState toDisp(LoginState loginState) throws ServiceException {
        if (Objects.isNull(loginState)) {
            return null;
        }
        StringIdKey accountKey = loginState.getAccountKey();
        Account account = null;
        if (Objects.nonNull(accountKey)) {
            account = accountMaintainService.getIfExists(accountKey);
        }
        return DispLoginState.of(loginState, account);
    }

    private PagedData<DispLoginState> toDispPagedData(PagedData<LoginState> pagedData) throws ServiceException {
        List<DispLoginState> dispLoginStates = new ArrayList<>(pagedData.getData().size());
        for (LoginState loginState : pagedData.getData()) {
            dispLoginStates.add(toDisp(loginState));
        }
        return new PagedData<>(
                pagedData.getCurrentPage(), pagedData.getTotalPages(), pagedData.getRows(), pagedData.getCount(),
                dispLoginStates
        );
    }
}
