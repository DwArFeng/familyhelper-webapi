package com.dwarfeng.familyhelper.webapi.impl.service.finance;

import com.dwarfeng.familyhelper.finance.stack.bean.dto.FundChangeRecordInfo;
import com.dwarfeng.familyhelper.finance.stack.bean.dto.FundChangeUpdateInfo;
import com.dwarfeng.familyhelper.finance.stack.bean.entity.FundChange;
import com.dwarfeng.familyhelper.finance.stack.bean.entity.FundChangeTypeIndicator;
import com.dwarfeng.familyhelper.finance.stack.service.BalanceOperateService;
import com.dwarfeng.familyhelper.finance.stack.service.FundChangeMaintainService;
import com.dwarfeng.familyhelper.finance.stack.service.FundChangeOperateService;
import com.dwarfeng.familyhelper.finance.stack.service.FundChangeTypeIndicatorMaintainService;
import com.dwarfeng.familyhelper.webapi.stack.bean.disp.finance.DispAccountBook;
import com.dwarfeng.familyhelper.webapi.stack.bean.disp.finance.DispFundChange;
import com.dwarfeng.familyhelper.webapi.stack.service.finance.AccountBookResponseService;
import com.dwarfeng.familyhelper.webapi.stack.service.finance.FundChangeResponseService;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class FundChangeResponseServiceImpl implements FundChangeResponseService {

    private final FundChangeMaintainService fundChangeMaintainService;
    private final FundChangeOperateService fundChangeOperateService;
    private final FundChangeTypeIndicatorMaintainService fundChangeTypeIndicatorMaintainService;

    private final AccountBookResponseService accountBookResponseService;

    public FundChangeResponseServiceImpl(
            @Qualifier("familyhelperFinanceFundChangeMaintainService")
            FundChangeMaintainService fundChangeMaintainService,
            @Qualifier("familyhelperFinanceFundChangeOperateService")
            FundChangeOperateService fundChangeOperateService,
            @Qualifier("familyhelperFinanceFundChangeTypeIndicatorMaintainService")
            FundChangeTypeIndicatorMaintainService fundChangeTypeIndicatorMaintainService,
            @Qualifier("familyhelperFinanceBalanceOperateService")
            BalanceOperateService balanceOperateService,
            AccountBookResponseService accountBookResponseService
    ) {
        this.fundChangeMaintainService = fundChangeMaintainService;
        this.fundChangeOperateService = fundChangeOperateService;
        this.fundChangeTypeIndicatorMaintainService = fundChangeTypeIndicatorMaintainService;
        this.accountBookResponseService = accountBookResponseService;
    }

    @Override
    public boolean exists(LongIdKey key) throws ServiceException {
        return fundChangeMaintainService.exists(key);
    }

    @Override
    public FundChange get(LongIdKey key) throws ServiceException {
        return fundChangeMaintainService.get(key);
    }

    @Override
    public PagedData<FundChange> all(PagingInfo pagingInfo) throws ServiceException {
        return fundChangeMaintainService.lookup(pagingInfo);
    }

    @Override
    public PagedData<FundChange> childForAccountBook(LongIdKey accountBookKey, PagingInfo pagingInfo)
            throws ServiceException {
        return fundChangeMaintainService.lookup(
                FundChangeMaintainService.CHILD_FOR_ACCOUNT_BOOK, new Object[]{accountBookKey}, pagingInfo
        );
    }

    @Override
    public PagedData<FundChange> childForAccountBookDesc(LongIdKey accountBookKey, PagingInfo pagingInfo)
            throws ServiceException {
        return fundChangeMaintainService.lookup(
                FundChangeMaintainService.CHILD_FOR_ACCOUNT_BOOK_DESC, new Object[]{accountBookKey}, pagingInfo
        );
    }

    @Override
    public PagedData<FundChange> childForAccountBookTypeEquals(
            LongIdKey accountBookKey, String changeType, PagingInfo pagingInfo
    ) throws ServiceException {
        return fundChangeMaintainService.lookup(
                FundChangeMaintainService.CHILD_FOR_ACCOUNT_BOOK_TYPE_EQUALS,
                new Object[]{accountBookKey, changeType},
                pagingInfo
        );
    }

    @Override
    public PagedData<FundChange> childForAccountBookTypeEqualsDesc(
            LongIdKey accountBookKey, String changeType, PagingInfo pagingInfo
    ) throws ServiceException {
        return fundChangeMaintainService.lookup(
                FundChangeMaintainService.CHILD_FOR_ACCOUNT_BOOK_TYPE_EQUALS_DESC,
                new Object[]{accountBookKey, changeType},
                pagingInfo
        );
    }

    @Override
    public PagedData<FundChange> childForAccountBookWithConditionDisplay(
            LongIdKey accountBookKey, String changeType, String remarkPattern, PagingInfo pagingInfo
    ) throws ServiceException {
        return fundChangeMaintainService.lookup(
                FundChangeMaintainService.CHILD_FOR_ACCOUNT_BOOK_WITH_CONDITION_DISPLAY,
                new Object[]{accountBookKey, changeType, remarkPattern},
                pagingInfo
        );
    }

    @Override
    public DispFundChange getDisp(LongIdKey key, StringIdKey inspectAccountKey) throws ServiceException {
        FundChange fundChange = get(key);
        return toDisp(fundChange, inspectAccountKey);
    }

    @Override
    public PagedData<DispFundChange> allDisp(StringIdKey accountKey, PagingInfo pagingInfo) throws ServiceException {
        PagedData<FundChange> lookup = all(pagingInfo);
        return toDispPagedData(lookup, accountKey);
    }

    @Override
    public PagedData<DispFundChange> childForAccountBookDisp(
            StringIdKey accountKey, LongIdKey accountBookKey, PagingInfo pagingInfo
    ) throws ServiceException {
        PagedData<FundChange> lookup = childForAccountBook(accountBookKey, pagingInfo);
        return toDispPagedData(lookup, accountKey);
    }

    @Override
    public PagedData<DispFundChange> childForAccountBookDescDisp(
            StringIdKey accountKey, LongIdKey accountBookKey, PagingInfo pagingInfo
    ) throws ServiceException {
        PagedData<FundChange> lookup = childForAccountBookDesc(accountBookKey, pagingInfo);
        return toDispPagedData(lookup, accountKey);
    }

    @Override
    public PagedData<DispFundChange> childForAccountBookTypeEqualsDisp(
            StringIdKey accountKey, LongIdKey accountBookKey, String changeType, PagingInfo pagingInfo
    ) throws ServiceException {
        PagedData<FundChange> lookup = childForAccountBookTypeEquals(accountBookKey, changeType, pagingInfo);
        return toDispPagedData(lookup, accountKey);
    }

    @Override
    public PagedData<DispFundChange> childForAccountBookTypeEqualsDescDisp(
            StringIdKey accountKey, LongIdKey accountBookKey, String changeType, PagingInfo pagingInfo
    ) throws ServiceException {
        PagedData<FundChange> lookup = childForAccountBookTypeEqualsDesc(accountBookKey, changeType, pagingInfo);
        return toDispPagedData(lookup, accountKey);
    }

    @Override
    public PagedData<DispFundChange> childForAccountBookWithConditionDisplayDisp(
            StringIdKey accountKey, LongIdKey accountBookKey, String changeType, String remarkPattern,
            PagingInfo pagingInfo
    ) throws ServiceException {
        PagedData<FundChange> lookup = childForAccountBookWithConditionDisplay(
                accountBookKey, changeType, remarkPattern, pagingInfo
        );
        return toDispPagedData(lookup, accountKey);
    }

    private DispFundChange toDisp(FundChange fundChange, StringIdKey inspectAccountKey) throws ServiceException {
        DispAccountBook accountBook = null;
        if (Objects.nonNull(fundChange.getAccountBookKey())) {
            accountBook = accountBookResponseService.getDisp(fundChange.getAccountBookKey(), inspectAccountKey);
        }
        FundChangeTypeIndicator typeIndicator = null;
        if (Objects.nonNull(fundChange.getChangeType())) {
            typeIndicator = fundChangeTypeIndicatorMaintainService.getIfExists(
                    new StringIdKey(fundChange.getChangeType())
            );
        }
        return DispFundChange.of(fundChange, accountBook, typeIndicator);
    }

    private DispFundChange toDispWithCache(
            FundChange fundChange, StringIdKey inspectAccountKey, Map<LongIdKey, DispAccountBook> cachedAccountBookMap,
            Map<String, FundChangeTypeIndicator> cachedTypeIndicatorMap
    ) throws ServiceException {
        DispAccountBook accountBook = toDispAccountBookWithCache(fundChange, inspectAccountKey, cachedAccountBookMap);
        FundChangeTypeIndicator typeIndicator = toDispFundChangeTypeIndicatorWithCache(
                fundChange, cachedTypeIndicatorMap
        );
        return DispFundChange.of(fundChange, accountBook, typeIndicator);
    }

    private DispAccountBook toDispAccountBookWithCache(
            FundChange fundChange, StringIdKey inspectAccountKey,
            Map<LongIdKey, DispAccountBook> cachedAccountBookMap
    ) throws ServiceException {
        LongIdKey accountBookKey = fundChange.getAccountBookKey();
        if (Objects.isNull(accountBookKey)) {
            return null;
        }
        DispAccountBook accountBook = cachedAccountBookMap.getOrDefault(accountBookKey, null);
        if (Objects.isNull(accountBook)) {
            accountBook = accountBookResponseService.getDisp(accountBookKey, inspectAccountKey);
            cachedAccountBookMap.put(accountBookKey, accountBook);
        }
        return accountBook;
    }

    private FundChangeTypeIndicator toDispFundChangeTypeIndicatorWithCache(
            FundChange fundChange, Map<String, FundChangeTypeIndicator> cachedTypeIndicatorMap
    ) throws ServiceException {
        String changeType = fundChange.getChangeType();
        if (Objects.isNull(changeType)) {
            return null;
        }
        FundChangeTypeIndicator typeIndicator = cachedTypeIndicatorMap.getOrDefault(changeType, null);
        if (Objects.isNull(typeIndicator)) {
            typeIndicator = fundChangeTypeIndicatorMaintainService.getIfExists(new StringIdKey(changeType));
            cachedTypeIndicatorMap.put(changeType, typeIndicator);
        }
        return typeIndicator;
    }

    private PagedData<DispFundChange> toDispPagedData(PagedData<FundChange> lookup, StringIdKey inspectAccountKey)
            throws ServiceException {
        List<DispFundChange> dispFundChanges = new ArrayList<>();
        Map<LongIdKey, DispAccountBook> cachedAccountBookMap = new HashMap<>();
        Map<String, FundChangeTypeIndicator> cachedTypeIndicatorMap = new HashMap<>();
        for (FundChange fundChange : lookup.getData()) {
            dispFundChanges.add(toDispWithCache(
                    fundChange, inspectAccountKey, cachedAccountBookMap, cachedTypeIndicatorMap
            ));
        }
        return new PagedData<>(
                lookup.getCurrentPage(), lookup.getTotalPages(), lookup.getRows(), lookup.getCount(), dispFundChanges
        );
    }

    @Override
    public LongIdKey recordFundChange(StringIdKey userKey, FundChangeRecordInfo fundChangeRecordInfo)
            throws ServiceException {
        return fundChangeOperateService.recordFundChange(userKey, fundChangeRecordInfo);
    }

    @Override
    public void updateFundChange(StringIdKey userKey, FundChangeUpdateInfo fundChangeUpdateInfo)
            throws ServiceException {
        fundChangeOperateService.updateFundChange(userKey, fundChangeUpdateInfo);
    }

    @Override
    public void removeFundChange(StringIdKey userKey, LongIdKey fundChangeKey) throws ServiceException {
        fundChangeOperateService.removeFundChange(userKey, fundChangeKey);
    }
}
