package com.dwarfeng.familyhelper.webapi.impl.service.finance;

import com.dwarfeng.familyhelper.finance.stack.bean.entity.BankCardTypeIndicator;
import com.dwarfeng.familyhelper.finance.stack.service.BankCardTypeIndicatorMaintainService;
import com.dwarfeng.familyhelper.webapi.stack.service.finance.BankCardTypeIndicatorResponseService;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class BankCardTypeIndicatorResponseServiceImpl implements BankCardTypeIndicatorResponseService {

    private final BankCardTypeIndicatorMaintainService bankCardTypeIndicatorMaintainService;

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    public BankCardTypeIndicatorResponseServiceImpl(
            @Qualifier("familyhelperFinanceBankCardTypeIndicatorMaintainService")
                    BankCardTypeIndicatorMaintainService bankCardTypeIndicatorMaintainService
    ) {
        this.bankCardTypeIndicatorMaintainService = bankCardTypeIndicatorMaintainService;
    }

    @Override
    public boolean exists(StringIdKey key) throws ServiceException {
        return bankCardTypeIndicatorMaintainService.exists(key);
    }

    @Override
    public BankCardTypeIndicator get(StringIdKey key) throws ServiceException {
        return bankCardTypeIndicatorMaintainService.get(key);
    }

    @Override
    public StringIdKey insert(BankCardTypeIndicator bankCardTypeIndicator) throws ServiceException {
        return bankCardTypeIndicatorMaintainService.insert(bankCardTypeIndicator);
    }

    @Override
    public void update(BankCardTypeIndicator bankCardTypeIndicator) throws ServiceException {
        bankCardTypeIndicatorMaintainService.update(bankCardTypeIndicator);
    }

    @Override
    public void delete(StringIdKey key) throws ServiceException {
        bankCardTypeIndicatorMaintainService.delete(key);
    }

    @Override
    public PagedData<BankCardTypeIndicator> all(PagingInfo pagingInfo) throws ServiceException {
        return bankCardTypeIndicatorMaintainService.lookup(pagingInfo);
    }
}
