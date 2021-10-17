package com.dwarfeng.familyhelper.webapi.stack.service.finance;

import com.dwarfeng.familyhelper.finance.stack.bean.dto.AccountBookCreateInfo;
import com.dwarfeng.familyhelper.finance.stack.bean.dto.AccountBookUpdateInfo;
import com.dwarfeng.familyhelper.finance.stack.bean.entity.AccountBook;
import com.dwarfeng.familyhelper.webapi.stack.bean.disp.finance.DispAccountBook;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.service.Service;

/**
 * 账本响应服务。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public interface AccountBookResponseService extends Service {

    boolean exists(LongIdKey key) throws ServiceException;

    AccountBook get(LongIdKey key) throws ServiceException;

    PagedData<AccountBook> all(PagingInfo pagingInfo) throws ServiceException;

    DispAccountBook getDisp(LongIdKey key, StringIdKey inspectAccountKey) throws ServiceException;

    PagedData<DispAccountBook> allPermittedDisp(StringIdKey accountKey, PagingInfo pagingInfo) throws ServiceException;

    PagedData<DispAccountBook> allOwnedDisp(StringIdKey accountKey, PagingInfo pagingInfo) throws ServiceException;

    LongIdKey createAccountBook(StringIdKey userKey, AccountBookCreateInfo accountBookCreateInfo)
            throws ServiceException;

    void updateAccountBook(StringIdKey userKey, LongIdKey accountBookKey, AccountBookUpdateInfo accountBookUpdateInfo)
            throws ServiceException;

    void removeAccountBook(StringIdKey userKey, LongIdKey accountBookKey) throws ServiceException;

    void recordCommit(StringIdKey userKey, LongIdKey accountBookKey) throws ServiceException;

    void rollbackAll(StringIdKey userKey, LongIdKey accountBookKey) throws ServiceException;
}
