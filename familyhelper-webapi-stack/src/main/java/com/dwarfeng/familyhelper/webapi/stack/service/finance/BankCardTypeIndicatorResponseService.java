package com.dwarfeng.familyhelper.webapi.stack.service.finance;

import com.dwarfeng.familyhelper.finance.stack.bean.entity.BankCardTypeIndicator;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.service.Service;

/**
 * 银行卡类型响应服务。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public interface BankCardTypeIndicatorResponseService extends Service {

    boolean exists(StringIdKey key) throws ServiceException;

    BankCardTypeIndicator get(StringIdKey key) throws ServiceException;

    StringIdKey insert(BankCardTypeIndicator bankCardTypeIndicator) throws ServiceException;

    void update(BankCardTypeIndicator bankCardTypeIndicator) throws ServiceException;

    void delete(StringIdKey key) throws ServiceException;

    PagedData<BankCardTypeIndicator> all(PagingInfo pagingInfo) throws ServiceException;
}
