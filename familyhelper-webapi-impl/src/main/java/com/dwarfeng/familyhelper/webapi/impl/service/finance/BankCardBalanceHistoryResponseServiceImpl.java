package com.dwarfeng.familyhelper.webapi.impl.service.finance;

import com.dwarfeng.familyhelper.finance.stack.bean.entity.BankCardBalanceHistory;
import com.dwarfeng.familyhelper.finance.stack.service.BankCardBalanceHistoryMaintainService;
import com.dwarfeng.familyhelper.webapi.stack.service.finance.BankCardBalanceHistoryResponseService;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class BankCardBalanceHistoryResponseServiceImpl implements BankCardBalanceHistoryResponseService {

    private final BankCardBalanceHistoryMaintainService bankCardBalanceHistoryMaintainService;

    public BankCardBalanceHistoryResponseServiceImpl(
            @Qualifier("familyhelperFinanceBankCardBalanceHistoryMaintainService")
                    BankCardBalanceHistoryMaintainService bankCardBalanceHistoryMaintainService
    ) {
        this.bankCardBalanceHistoryMaintainService = bankCardBalanceHistoryMaintainService;
    }

    @Override
    public boolean exists(LongIdKey key) throws ServiceException {
        return bankCardBalanceHistoryMaintainService.exists(key);
    }

    @Override
    public BankCardBalanceHistory get(LongIdKey key) throws ServiceException {
        return bankCardBalanceHistoryMaintainService.get(key);
    }

    @Override
    public PagedData<BankCardBalanceHistory> all(PagingInfo pagingInfo) throws ServiceException {
        return bankCardBalanceHistoryMaintainService.lookup(pagingInfo);
    }

    @Override
    public PagedData<BankCardBalanceHistory> childForBankCard(LongIdKey bankCardKey, PagingInfo pagingInfo)
            throws ServiceException {
        return bankCardBalanceHistoryMaintainService.lookup(
                BankCardBalanceHistoryMaintainService.CHILD_FOR_BANK_CARD, new Object[]{bankCardKey}, pagingInfo
        );
    }

    @Override
    public PagedData<BankCardBalanceHistory> childForBankCardDesc(LongIdKey bankCardKey, PagingInfo pagingInfo)
            throws ServiceException {
        return bankCardBalanceHistoryMaintainService.lookup(
                BankCardBalanceHistoryMaintainService.CHILD_FOR_BANK_CARD_DESC, new Object[]{bankCardKey},
                pagingInfo
        );
    }
}
