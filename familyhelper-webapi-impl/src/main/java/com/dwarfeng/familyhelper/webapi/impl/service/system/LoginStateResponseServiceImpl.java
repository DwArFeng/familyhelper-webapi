package com.dwarfeng.familyhelper.webapi.impl.service.system;

import com.dwarfeng.acckeeper.stack.bean.entity.LoginState;
import com.dwarfeng.acckeeper.stack.service.LoginStateMaintainService;
import com.dwarfeng.familyhelper.webapi.stack.service.system.LoginStateResponseService;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class LoginStateResponseServiceImpl implements LoginStateResponseService {

    private final LoginStateMaintainService loginStateMaintainService;

    public LoginStateResponseServiceImpl(
            @Qualifier("acckeeperLoginStateMaintainService") LoginStateMaintainService loginStateMaintainService
    ) {
        this.loginStateMaintainService = loginStateMaintainService;
    }

    @Override
    public boolean exists(LongIdKey key) throws ServiceException {
        return loginStateMaintainService.exists(key);
    }

    @Override
    public LoginState get(LongIdKey key) throws ServiceException {
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
}
