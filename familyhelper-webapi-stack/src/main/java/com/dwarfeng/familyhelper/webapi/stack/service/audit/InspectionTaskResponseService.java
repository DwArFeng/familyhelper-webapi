package com.dwarfeng.familyhelper.webapi.stack.service.audit;

import com.dwarfeng.audit.stack.bean.entity.InspectionTask;
import com.dwarfeng.familyhelper.webapi.stack.bean.audit.disp.DispInspectionTask;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.service.Service;

/**
 * 自动审计任务响应服务。
 *
 * @author DwArFeng
 * @since 2.2.0
 */
public interface InspectionTaskResponseService extends Service {

    boolean exists(LongIdKey key) throws ServiceException;

    InspectionTask get(LongIdKey key) throws ServiceException;

    PagedData<InspectionTask> all(PagingInfo pagingInfo) throws ServiceException;

    PagedData<InspectionTask> childForInspection(LongIdKey inspectionKey, PagingInfo pagingInfo)
            throws ServiceException;

    DispInspectionTask getDisp(LongIdKey key) throws ServiceException;

    PagedData<DispInspectionTask> allDisp(PagingInfo pagingInfo) throws ServiceException;

    PagedData<DispInspectionTask> childForInspectionDisp(LongIdKey inspectionKey, PagingInfo pagingInfo)
            throws ServiceException;
}
