package com.dwarfeng.familyhelper.webapi.stack.service.finance;

import com.dwarfeng.familyhelper.finance.stack.bean.dto.FundChangeRecordInfo;
import com.dwarfeng.familyhelper.finance.stack.bean.dto.FundChangeUpdateInfo;
import com.dwarfeng.familyhelper.finance.stack.bean.entity.FundChange;
import com.dwarfeng.familyhelper.webapi.stack.bean.disp.finance.DispFundChange;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.service.Service;

/**
 * 资金变更响应服务。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public interface FundChangeResponseService extends Service {

    boolean exists(LongIdKey key) throws ServiceException;

    FundChange get(LongIdKey key) throws ServiceException;

    PagedData<FundChange> all(PagingInfo pagingInfo) throws ServiceException;

    PagedData<FundChange> childForAccountBook(LongIdKey accountBookKey, PagingInfo pagingInfo) throws ServiceException;

    PagedData<FundChange> childForAccountBookDesc(LongIdKey accountBookKey, PagingInfo pagingInfo)
            throws ServiceException;

    PagedData<FundChange> childForAccountBookTypeEquals(
            LongIdKey accountBookKey, String changeType, PagingInfo pagingInfo
    ) throws ServiceException;

    PagedData<FundChange> childForAccountBookTypeEqualsDesc(
            LongIdKey accountBookKey, String changeType, PagingInfo pagingInfo
    ) throws ServiceException;

    DispFundChange getDisp(LongIdKey key, StringIdKey inspectAccountKey) throws ServiceException;

    PagedData<DispFundChange> allDisp(StringIdKey accountKey, PagingInfo pagingInfo) throws ServiceException;

    PagedData<DispFundChange> childForAccountBookDisp(
            StringIdKey accountKey, LongIdKey accountBookKey, PagingInfo pagingInfo
    ) throws ServiceException;

    PagedData<DispFundChange> childForAccountBookDescDisp(
            StringIdKey accountKey, LongIdKey accountBookKey, PagingInfo pagingInfo
    ) throws ServiceException;

    PagedData<DispFundChange> childForAccountBookTypeEqualsDisp(
            StringIdKey accountKey, LongIdKey accountBookKey, String changeType, PagingInfo pagingInfo
    ) throws ServiceException;

    PagedData<DispFundChange> childForAccountBookTypeEqualsDescDisp(
            StringIdKey accountKey, LongIdKey accountBookKey, String changeType, PagingInfo pagingInfo
    ) throws ServiceException;

    LongIdKey recordFundChange(StringIdKey userKey, LongIdKey accountBookKey, FundChangeRecordInfo fundChangeRecordInfo)
            throws ServiceException;

    void updateFundChange(StringIdKey userKey, LongIdKey fundChangeKey, FundChangeUpdateInfo fundChangeUpdateInfo)
            throws ServiceException;

    void removeFundChange(StringIdKey userKey, LongIdKey fundChangeKey) throws ServiceException;
}
