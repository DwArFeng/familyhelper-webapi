package com.dwarfeng.familyhelper.webapi.impl.service.audit;

import com.dwarfeng.audit.stack.bean.entity.Inspection;
import com.dwarfeng.audit.stack.bean.entity.InspectionTask;
import com.dwarfeng.audit.stack.service.InspectionMaintainService;
import com.dwarfeng.audit.stack.service.InspectionTaskMaintainService;
import com.dwarfeng.familyhelper.webapi.stack.bean.audit.disp.DispInspectionTask;
import com.dwarfeng.familyhelper.webapi.stack.service.audit.InspectionTaskResponseService;
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
public class InspectionTaskResponseServiceImpl implements InspectionTaskResponseService {

    private final InspectionTaskMaintainService inspectionTaskMaintainService;
    private final InspectionMaintainService inspectionMaintainService;

    public InspectionTaskResponseServiceImpl(
            @Qualifier("auditInspectionTaskMaintainService") InspectionTaskMaintainService inspectionTaskMaintainService,
            @Qualifier("auditInspectionMaintainService") InspectionMaintainService inspectionMaintainService
    ) {
        this.inspectionTaskMaintainService = inspectionTaskMaintainService;
        this.inspectionMaintainService = inspectionMaintainService;
    }

    @Override
    public boolean exists(LongIdKey key) throws ServiceException {
        return inspectionTaskMaintainService.exists(key);
    }

    @Override
    public InspectionTask get(LongIdKey key) throws ServiceException {
        return inspectionTaskMaintainService.get(key);
    }

    @Override
    public PagedData<InspectionTask> all(PagingInfo pagingInfo) throws ServiceException {
        return inspectionTaskMaintainService.lookup(pagingInfo);
    }

    @Override
    public PagedData<InspectionTask> childForInspection(LongIdKey inspectionKey, PagingInfo pagingInfo)
            throws ServiceException {
        return inspectionTaskMaintainService.lookup(
                InspectionTaskMaintainService.CHILD_FOR_INSPECTION,
                new Object[]{inspectionKey},
                pagingInfo
        );
    }

    @Override
    public DispInspectionTask getDisp(LongIdKey key) throws ServiceException {
        InspectionTask inspectionTask = inspectionTaskMaintainService.get(key);
        return toDisp(inspectionTask);
    }

    @Override
    public PagedData<DispInspectionTask> allDisp(PagingInfo pagingInfo) throws ServiceException {
        PagedData<InspectionTask> lookup = inspectionTaskMaintainService.lookup(pagingInfo);
        return toDispPagedData(lookup);
    }

    @Override
    public PagedData<DispInspectionTask> childForInspectionDisp(LongIdKey inspectionKey, PagingInfo pagingInfo)
            throws ServiceException {
        PagedData<InspectionTask> lookup = inspectionTaskMaintainService.lookup(
                InspectionTaskMaintainService.CHILD_FOR_INSPECTION,
                new Object[]{inspectionKey},
                pagingInfo
        );
        return toDispPagedData(lookup);
    }

    private DispInspectionTask toDisp(InspectionTask inspectionTask) throws ServiceException {
        if (Objects.isNull(inspectionTask)) {
            return null;
        }
        LongIdKey inspectionKey = inspectionTask.getInspectionKey();
        Inspection inspection = null;
        if (Objects.nonNull(inspectionKey)) {
            inspection = inspectionMaintainService.getIfExists(inspectionKey);
        }
        return DispInspectionTask.of(inspectionTask, inspection);
    }

    private PagedData<DispInspectionTask> toDispPagedData(PagedData<InspectionTask> pagedData)
            throws ServiceException {
        List<DispInspectionTask> dispInspectionTasks = new ArrayList<>(pagedData.getData().size());
        for (InspectionTask inspectionTask : pagedData.getData()) {
            dispInspectionTasks.add(toDisp(inspectionTask));
        }
        return new PagedData<>(
                pagedData.getCurrentPage(), pagedData.getTotalPages(), pagedData.getRows(), pagedData.getCount(),
                dispInspectionTasks
        );
    }
}
