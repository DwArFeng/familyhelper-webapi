package com.dwarfeng.familyhelper.webapi.stack.service.clannad;

import com.dwarfeng.familyhelper.clannad.stack.bean.entity.GenderTypeIndicator;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.service.Service;

/**
 * 性别类型响应服务。
 *
 * @author DwArFeng
 * @since 1.0.1
 */
public interface GenderTypeIndicatorResponseService extends Service {

    boolean exists(StringIdKey key) throws ServiceException;

    GenderTypeIndicator get(StringIdKey key) throws ServiceException;

    StringIdKey insert(GenderTypeIndicator genderTypeIndicator) throws ServiceException;

    void update(GenderTypeIndicator genderTypeIndicator) throws ServiceException;

    void delete(StringIdKey key) throws ServiceException;

    PagedData<GenderTypeIndicator> all(PagingInfo pagingInfo) throws ServiceException;
}
