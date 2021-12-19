package com.dwarfeng.familyhelper.webapi.stack.service.finance;

import com.dwarfeng.familyhelper.finance.stack.bean.dto.BankCardBalanceRecordInfo;
import com.dwarfeng.familyhelper.finance.stack.bean.dto.BankCardCreateInfo;
import com.dwarfeng.familyhelper.finance.stack.bean.dto.BankCardUpdateInfo;
import com.dwarfeng.familyhelper.finance.stack.bean.entity.BankCard;
import com.dwarfeng.familyhelper.webapi.stack.bean.disp.finance.DispBankCard;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.service.Service;

/**
 * 银行卡响应服务。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public interface BankCardResponseService extends Service {

    boolean exists(LongIdKey key) throws ServiceException;

    BankCard get(LongIdKey key) throws ServiceException;

    PagedData<BankCard> all(PagingInfo pagingInfo) throws ServiceException;

    PagedData<BankCard> childForAccountBook(LongIdKey accountBookKey, PagingInfo pagingInfo) throws ServiceException;

    DispBankCard getDisp(LongIdKey key, StringIdKey inspectAccountKey) throws ServiceException;

    PagedData<DispBankCard> allDisp(StringIdKey accountKey, PagingInfo pagingInfo) throws ServiceException;

    PagedData<DispBankCard> childForAccountBookDisp(
            StringIdKey accountKey, LongIdKey accountBookKey, PagingInfo pagingInfo
    ) throws ServiceException;

    LongIdKey createBankCard(StringIdKey userKey, BankCardCreateInfo bankCardCreateInfo) throws ServiceException;

    void updateBankCard(StringIdKey userKey, BankCardUpdateInfo bankCardUpdateInfo) throws ServiceException;

    void removeBankCard(StringIdKey userKey, LongIdKey bankCardKey) throws ServiceException;

    void recordBalance(StringIdKey userKey, BankCardBalanceRecordInfo bankCardBalanceRecordInfo)
            throws ServiceException;

    void rollbackBalance(StringIdKey userKey, LongIdKey bankCardKey) throws ServiceException;
}
