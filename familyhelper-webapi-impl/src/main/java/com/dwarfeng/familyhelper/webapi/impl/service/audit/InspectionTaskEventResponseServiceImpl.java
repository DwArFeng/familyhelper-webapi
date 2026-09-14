package com.dwarfeng.familyhelper.webapi.impl.service.audit;

import com.dwarfeng.audit.stack.bean.entity.InspectionTask;
import com.dwarfeng.audit.stack.bean.entity.InspectionTaskEvent;
import com.dwarfeng.audit.stack.service.InspectionTaskEventMaintainService;
import com.dwarfeng.audit.stack.service.InspectionTaskMaintainService;
import com.dwarfeng.familyhelper.webapi.stack.bean.audit.disp.DispInspectionTaskEvent;
import com.dwarfeng.familyhelper.webapi.stack.service.audit.InspectionTaskEventResponseService;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class InspectionTaskEventResponseServiceImpl implements InspectionTaskEventResponseService {

    private final InspectionTaskEventMaintainService inspectionTaskEventMaintainService;
    private final InspectionTaskMaintainService inspectionTaskMaintainService;

    public InspectionTaskEventResponseServiceImpl(
            @Qualifier("auditInspectionTaskEventMaintainService")
            InspectionTaskEventMaintainService inspectionTaskEventMaintainService,
            @Qualifier("auditInspectionTaskMaintainService")
            InspectionTaskMaintainService inspectionTaskMaintainService
    ) {
        this.inspectionTaskEventMaintainService = inspectionTaskEventMaintainService;
        this.inspectionTaskMaintainService = inspectionTaskMaintainService;
    }

    @Override
    public boolean exists(LongIdKey key) throws ServiceException {
        return inspectionTaskEventMaintainService.exists(key);
    }

    @Override
    public InspectionTaskEvent get(LongIdKey key) throws ServiceException {
        return inspectionTaskEventMaintainService.get(key);
    }

    @Override
    public PagedData<InspectionTaskEvent> all(PagingInfo pagingInfo) throws ServiceException {
        return inspectionTaskEventMaintainService.lookup(pagingInfo);
    }

    @Override
    public PagedData<InspectionTaskEvent> childForInspectionTask(LongIdKey inspectionTaskKey, PagingInfo pagingInfo)
            throws ServiceException {
        return inspectionTaskEventMaintainService.lookup(
                InspectionTaskEventMaintainService.CHILD_FOR_INSPECTION_TASK,
                new Object[]{inspectionTaskKey},
                pagingInfo
        );
    }

    @Override
    public DispInspectionTaskEvent getDisp(LongIdKey key) throws ServiceException {
        InspectionTaskEvent inspectionTaskEvent = inspectionTaskEventMaintainService.get(key);
        return toDisp(inspectionTaskEvent);
    }

    @Override
    public PagedData<DispInspectionTaskEvent> allDisp(PagingInfo pagingInfo) throws ServiceException {
        PagedData<InspectionTaskEvent> lookup = inspectionTaskEventMaintainService.lookup(pagingInfo);
        return toDispPagedData(lookup);
    }

    @Override
    public PagedData<DispInspectionTaskEvent> childForInspectionTaskDisp(
            LongIdKey inspectionTaskKey, PagingInfo pagingInfo
    ) throws ServiceException {
        PagedData<InspectionTaskEvent> lookup = inspectionTaskEventMaintainService.lookup(
                InspectionTaskEventMaintainService.CHILD_FOR_INSPECTION_TASK,
                new Object[]{inspectionTaskKey},
                pagingInfo
        );
        return toDispPagedData(lookup);
    }

    private DispInspectionTaskEvent toDisp(InspectionTaskEvent inspectionTaskEvent) throws ServiceException {
        if (Objects.isNull(inspectionTaskEvent)) {
            return null;
        }
        LongIdKey inspectionTaskKey = inspectionTaskEvent.getInspectionTaskKey();
        InspectionTask inspectionTask = null;
        if (Objects.nonNull(inspectionTaskKey)) {
            inspectionTask = inspectionTaskMaintainService.getIfExists(inspectionTaskKey);
        }
        return DispInspectionTaskEvent.of(inspectionTaskEvent, inspectionTask);
    }

    private PagedData<DispInspectionTaskEvent> toDispPagedData(PagedData<InspectionTaskEvent> pagedData)
            throws ServiceException {
        List<DispInspectionTaskEvent> dispInspectionTaskEvents = new ArrayList<>(pagedData.getData().size());
        for (InspectionTaskEvent inspectionTaskEvent : pagedData.getData()) {
            dispInspectionTaskEvents.add(toDisp(inspectionTaskEvent));
        }
        return new PagedData<>(
                pagedData.getCurrentPage(), pagedData.getTotalPages(), pagedData.getRows(), pagedData.getCount(),
                dispInspectionTaskEvents
        );
    }
}
