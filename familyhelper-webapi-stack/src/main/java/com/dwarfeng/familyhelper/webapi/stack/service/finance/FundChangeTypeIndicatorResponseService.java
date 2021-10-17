package com.dwarfeng.familyhelper.webapi.stack.service.finance;

import com.dwarfeng.familyhelper.finance.stack.bean.entity.FundChangeTypeIndicator;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.service.Service;

/**
 * 资金变更类型响应服务。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public interface FundChangeTypeIndicatorResponseService extends Service {

    boolean exists(StringIdKey key) throws ServiceException;

    FundChangeTypeIndicator get(StringIdKey key) throws ServiceException;

    StringIdKey insert(FundChangeTypeIndicator fundChangeTypeIndicator) throws ServiceException;

    void update(FundChangeTypeIndicator fundChangeTypeIndicator) throws ServiceException;

    void delete(StringIdKey key) throws ServiceException;

    PagedData<FundChangeTypeIndicator> all(PagingInfo pagingInfo) throws ServiceException;
}
