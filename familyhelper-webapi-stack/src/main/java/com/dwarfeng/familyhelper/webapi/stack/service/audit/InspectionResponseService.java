package com.dwarfeng.familyhelper.webapi.stack.service.audit;

import com.dwarfeng.audit.stack.bean.entity.Inspection;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.service.Service;

/**
 * 自动审计响应服务。
 *
 * @author DwArFeng
 * @since 2.2.0
 */
public interface InspectionResponseService extends Service {

    boolean exists(LongIdKey key) throws ServiceException;

    Inspection get(LongIdKey key) throws ServiceException;

    LongIdKey insert(Inspection inspection) throws ServiceException;

    void update(Inspection inspection) throws ServiceException;

    void delete(LongIdKey key) throws ServiceException;

    PagedData<Inspection> all(PagingInfo pagingInfo) throws ServiceException;
}
