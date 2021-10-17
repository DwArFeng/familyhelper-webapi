package com.dwarfeng.familyhelper.webapi.stack.service.finance;

import com.dwarfeng.familyhelper.finance.stack.bean.entity.BankCardBalanceHistory;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.service.Service;

/**
 * 银行卡余额响应服务。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public interface BankCardBalanceHistoryResponseService extends Service {

    boolean exists(LongIdKey key) throws ServiceException;

    BankCardBalanceHistory get(LongIdKey key) throws ServiceException;

    PagedData<BankCardBalanceHistory> all(PagingInfo pagingInfo) throws ServiceException;

    PagedData<BankCardBalanceHistory> childForBankCard(LongIdKey bankCardKey, PagingInfo pagingInfo)
            throws ServiceException;

    PagedData<BankCardBalanceHistory> childForBankCardDesc(LongIdKey bankCardKey, PagingInfo pagingInfo)
            throws ServiceException;
}
