package com.dwarfeng.familyhelper.webapi.stack.service.audit;

import com.dwarfeng.audit.stack.bean.entity.InspectionAlarmTypeIndicator;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.service.Service;

/**
 * 自动审计报警类型指示器响应服务。
 *
 * @author DwArFeng
 * @since 2.2.0
 */
public interface InspectionAlarmTypeIndicatorResponseService extends Service {

    boolean exists(StringIdKey key) throws ServiceException;

    InspectionAlarmTypeIndicator get(StringIdKey key) throws ServiceException;

    StringIdKey insert(InspectionAlarmTypeIndicator inspectionAlarmTypeIndicator) throws ServiceException;

    void update(InspectionAlarmTypeIndicator inspectionAlarmTypeIndicator) throws ServiceException;

    void delete(StringIdKey key) throws ServiceException;

    PagedData<InspectionAlarmTypeIndicator> all(PagingInfo pagingInfo) throws ServiceException;
}
