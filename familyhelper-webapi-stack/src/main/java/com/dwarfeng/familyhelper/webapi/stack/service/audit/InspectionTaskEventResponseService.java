package com.dwarfeng.familyhelper.webapi.stack.service.audit;

import com.dwarfeng.audit.stack.bean.entity.InspectionTaskEvent;
import com.dwarfeng.familyhelper.webapi.stack.bean.audit.disp.DispInspectionTaskEvent;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.service.Service;

/**
 * 自动审计任务事件响应服务。
 *
 * @author DwArFeng
 * @since 2.2.0
 */
public interface InspectionTaskEventResponseService extends Service {

    boolean exists(LongIdKey key) throws ServiceException;

    InspectionTaskEvent get(LongIdKey key) throws ServiceException;

    PagedData<InspectionTaskEvent> all(PagingInfo pagingInfo) throws ServiceException;

    PagedData<InspectionTaskEvent> childForInspectionTask(LongIdKey inspectionTaskKey, PagingInfo pagingInfo)
            throws ServiceException;

    DispInspectionTaskEvent getDisp(LongIdKey key) throws ServiceException;

    PagedData<DispInspectionTaskEvent> allDisp(PagingInfo pagingInfo) throws ServiceException;

    PagedData<DispInspectionTaskEvent> childForInspectionTaskDisp(LongIdKey inspectionTaskKey, PagingInfo pagingInfo)
            throws ServiceException;
}
