package com.dwarfeng.familyhelper.webapi.impl.service.system;

import com.dwarfeng.acckeeper.stack.bean.entity.LoginHistory;
import com.dwarfeng.acckeeper.stack.service.LoginHistoryMaintainService;
import com.dwarfeng.familyhelper.webapi.stack.service.system.LoginHistoryResponseService;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class LoginHistoryResponseServiceImpl implements LoginHistoryResponseService {

    private final LoginHistoryMaintainService loginHistoryMaintainService;

    public LoginHistoryResponseServiceImpl(
            @Qualifier("acckeeperLoginHistoryMaintainService") LoginHistoryMaintainService loginHistoryMaintainService
    ) {
        this.loginHistoryMaintainService = loginHistoryMaintainService;
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
        return loginHistoryMaintainService.lookup(pagingInfo);
    }

    @Override
    public PagedData<LoginHistory> accountIdEquals(String accountId, PagingInfo pagingInfo)
            throws ServiceException {
        return loginHistoryMaintainService.lookup(
                LoginHistoryMaintainService.ACCOUNT_ID_EQUALS_HAPPENED_DATE_DESC,
                new Object[]{accountId},
                pagingInfo
        );
    }
}
