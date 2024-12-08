package com.dwarfeng.familyhelper.webapi.impl.service.finance;

import com.dwarfeng.familyhelper.finance.stack.bean.entity.FundChangeTypeIndicator;
import com.dwarfeng.familyhelper.finance.stack.service.FundChangeTypeIndicatorMaintainService;
import com.dwarfeng.familyhelper.webapi.stack.service.finance.FundChangeTypeIndicatorResponseService;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class FundChangeTypeIndicatorResponseServiceImpl implements FundChangeTypeIndicatorResponseService {

    private final FundChangeTypeIndicatorMaintainService fundChangeTypeIndicatorMaintainService;

    public FundChangeTypeIndicatorResponseServiceImpl(
            @Qualifier("familyhelperFinanceFundChangeTypeIndicatorMaintainService")
            FundChangeTypeIndicatorMaintainService fundChangeTypeIndicatorMaintainService
    ) {
        this.fundChangeTypeIndicatorMaintainService = fundChangeTypeIndicatorMaintainService;
    }

    @Override
    public boolean exists(StringIdKey key) throws ServiceException {
        return fundChangeTypeIndicatorMaintainService.exists(key);
    }

    @Override
    public FundChangeTypeIndicator get(StringIdKey key) throws ServiceException {
        return fundChangeTypeIndicatorMaintainService.get(key);
    }

    @Override
    public StringIdKey insert(FundChangeTypeIndicator fundChangeTypeIndicator) throws ServiceException {
        return fundChangeTypeIndicatorMaintainService.insert(fundChangeTypeIndicator);
    }

    @Override
    public void update(FundChangeTypeIndicator fundChangeTypeIndicator) throws ServiceException {
        fundChangeTypeIndicatorMaintainService.update(fundChangeTypeIndicator);
    }

    @Override
    public void delete(StringIdKey key) throws ServiceException {
        fundChangeTypeIndicatorMaintainService.delete(key);
    }

    @Override
    public PagedData<FundChangeTypeIndicator> all(PagingInfo pagingInfo) throws ServiceException {
        return fundChangeTypeIndicatorMaintainService.lookup(pagingInfo);
    }
}
