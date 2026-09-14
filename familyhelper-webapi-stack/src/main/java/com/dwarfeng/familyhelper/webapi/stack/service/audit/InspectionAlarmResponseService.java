package com.dwarfeng.familyhelper.webapi.stack.service.audit;

import com.dwarfeng.audit.stack.bean.entity.InspectionAlarm;
import com.dwarfeng.familyhelper.webapi.stack.bean.audit.disp.DispInspectionAlarm;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.service.Service;

/**
 * 自动审计报警响应服务。
 *
 * @author DwArFeng
 * @since 2.2.0
 */
public interface InspectionAlarmResponseService extends Service {

    boolean exists(LongIdKey key) throws ServiceException;

    InspectionAlarm get(LongIdKey key) throws ServiceException;

    PagedData<InspectionAlarm> all(PagingInfo pagingInfo) throws ServiceException;

    PagedData<InspectionAlarm> childForInspection(LongIdKey inspectionKey, PagingInfo pagingInfo)
            throws ServiceException;

    PagedData<InspectionAlarm> childForInspectionTask(LongIdKey inspectionTaskKey, PagingInfo pagingInfo)
            throws ServiceException;

    PagedData<InspectionAlarm> childForInspectorInfo(LongIdKey inspectorInfoKey, PagingInfo pagingInfo)
            throws ServiceException;

    DispInspectionAlarm getDisp(LongIdKey key) throws ServiceException;

    PagedData<DispInspectionAlarm> allDisp(PagingInfo pagingInfo) throws ServiceException;

    PagedData<DispInspectionAlarm> childForInspectionDisp(LongIdKey inspectionKey, PagingInfo pagingInfo)
            throws ServiceException;

    PagedData<DispInspectionAlarm> childForInspectionTaskDisp(LongIdKey inspectionTaskKey, PagingInfo pagingInfo)
            throws ServiceException;

    PagedData<DispInspectionAlarm> childForInspectorInfoDisp(LongIdKey inspectorInfoKey, PagingInfo pagingInfo)
            throws ServiceException;
}
