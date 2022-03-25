package com.dwarfeng.familyhelper.webapi.impl.service.finance;

import com.dwarfeng.familyhelper.finance.stack.bean.entity.TotalBalanceHistory;
import com.dwarfeng.familyhelper.finance.stack.service.TotalBalanceHistoryMaintainService;
import com.dwarfeng.familyhelper.webapi.stack.service.finance.TotalBalanceHistoryResponseService;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class TotalBalanceHistoryResponseServiceImpl implements TotalBalanceHistoryResponseService {

    private final TotalBalanceHistoryMaintainService totalBalanceHistoryMaintainService;

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    public TotalBalanceHistoryResponseServiceImpl(
            @Qualifier("familyhelperFinanceTotalBalanceHistoryMaintainService")
                    TotalBalanceHistoryMaintainService totalBalanceHistoryMaintainService
    ) {
        this.totalBalanceHistoryMaintainService = totalBalanceHistoryMaintainService;
    }

    @Override
    public boolean exists(LongIdKey key) throws ServiceException {
        return totalBalanceHistoryMaintainService.exists(key);
    }

    @Override
    public TotalBalanceHistory get(LongIdKey key) throws ServiceException {
        return totalBalanceHistoryMaintainService.get(key);
    }

    @Override
    public PagedData<TotalBalanceHistory> all(PagingInfo pagingInfo) throws ServiceException {
        return totalBalanceHistoryMaintainService.lookup(pagingInfo);
    }

    @Override
    public PagedData<TotalBalanceHistory> childForAccountBook(LongIdKey accountBookKey, PagingInfo pagingInfo)
            throws ServiceException {
        return totalBalanceHistoryMaintainService.lookup(
                TotalBalanceHistoryMaintainService.CHILD_FOR_ACCOUNT_BOOK, new Object[]{accountBookKey}, pagingInfo
        );
    }

    @Override
    public PagedData<TotalBalanceHistory> childForAccountBookDesc(LongIdKey accountBookKey, PagingInfo pagingInfo)
            throws ServiceException {
        return totalBalanceHistoryMaintainService.lookup(
                TotalBalanceHistoryMaintainService.CHILD_FOR_ACCOUNT_BOOK_DESC, new Object[]{accountBookKey},
                pagingInfo
        );
    }
}
