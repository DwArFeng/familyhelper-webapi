package com.dwarfeng.familyhelper.webapi.impl.service.system;

import com.dwarfeng.acckeeper.stack.bean.entity.DeriveHistory;
import com.dwarfeng.acckeeper.stack.service.DeriveHistoryMaintainService;
import com.dwarfeng.familyhelper.webapi.stack.service.system.DeriveHistoryResponseService;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class DeriveHistoryResponseServiceImpl implements DeriveHistoryResponseService {

    private final DeriveHistoryMaintainService deriveHistoryMaintainService;

    public DeriveHistoryResponseServiceImpl(
            @Qualifier("acckeeperDeriveHistoryMaintainService")
            DeriveHistoryMaintainService deriveHistoryMaintainService
    ) {
        this.deriveHistoryMaintainService = deriveHistoryMaintainService;
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
        return deriveHistoryMaintainService.lookup(pagingInfo);
    }

    @Override
    public PagedData<DeriveHistory> accountIdEquals(String accountId, PagingInfo pagingInfo)
            throws ServiceException {
        return deriveHistoryMaintainService.lookup(
                DeriveHistoryMaintainService.ACCOUNT_ID_EQUALS_HAPPENED_DATE_DESC,
                new Object[]{accountId},
                pagingInfo
        );
    }
}
