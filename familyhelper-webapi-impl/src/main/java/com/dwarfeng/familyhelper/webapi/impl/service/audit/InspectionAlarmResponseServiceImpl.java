package com.dwarfeng.familyhelper.webapi.impl.service.audit;

import com.dwarfeng.audit.stack.bean.entity.*;
import com.dwarfeng.audit.stack.service.*;
import com.dwarfeng.familyhelper.webapi.stack.bean.audit.disp.DispInspectionAlarm;
import com.dwarfeng.familyhelper.webapi.stack.service.audit.InspectionAlarmResponseService;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class InspectionAlarmResponseServiceImpl implements InspectionAlarmResponseService {

    private final InspectionAlarmMaintainService inspectionAlarmMaintainService;
    private final InspectionMaintainService inspectionMaintainService;
    private final InspectionTaskMaintainService inspectionTaskMaintainService;
    private final InspectorInfoMaintainService inspectorInfoMaintainService;
    private final InspectionAlarmTypeIndicatorMaintainService inspectionAlarmTypeIndicatorMaintainService;

    public InspectionAlarmResponseServiceImpl(
            @Qualifier("auditInspectionAlarmMaintainService")
            InspectionAlarmMaintainService inspectionAlarmMaintainService,
            @Qualifier("auditInspectionMaintainService") InspectionMaintainService inspectionMaintainService,
            @Qualifier("auditInspectionTaskMaintainService")
            InspectionTaskMaintainService inspectionTaskMaintainService,
            @Qualifier("auditInspectorInfoMaintainService") InspectorInfoMaintainService inspectorInfoMaintainService,
            @Qualifier("auditInspectionAlarmTypeIndicatorMaintainService")
            InspectionAlarmTypeIndicatorMaintainService inspectionAlarmTypeIndicatorMaintainService
    ) {
        this.inspectionAlarmMaintainService = inspectionAlarmMaintainService;
        this.inspectionMaintainService = inspectionMaintainService;
        this.inspectionTaskMaintainService = inspectionTaskMaintainService;
        this.inspectorInfoMaintainService = inspectorInfoMaintainService;
        this.inspectionAlarmTypeIndicatorMaintainService = inspectionAlarmTypeIndicatorMaintainService;
    }

    @Override
    public boolean exists(LongIdKey key) throws ServiceException {
        return inspectionAlarmMaintainService.exists(key);
    }

    @Override
    public InspectionAlarm get(LongIdKey key) throws ServiceException {
        return inspectionAlarmMaintainService.get(key);
    }

    @Override
    public PagedData<InspectionAlarm> all(PagingInfo pagingInfo) throws ServiceException {
        return inspectionAlarmMaintainService.lookup(pagingInfo);
    }

    @Override
    public PagedData<InspectionAlarm> childForInspection(LongIdKey inspectionKey, PagingInfo pagingInfo)
            throws ServiceException {
        return inspectionAlarmMaintainService.lookup(
                InspectionAlarmMaintainService.CHILD_FOR_INSPECTION,
                new Object[]{inspectionKey},
                pagingInfo
        );
    }

    @Override
    public PagedData<InspectionAlarm> childForInspectionTask(LongIdKey inspectionTaskKey, PagingInfo pagingInfo)
            throws ServiceException {
        return inspectionAlarmMaintainService.lookup(
                InspectionAlarmMaintainService.CHILD_FOR_INSPECTION_TASK,
                new Object[]{inspectionTaskKey},
                pagingInfo
        );
    }

    @Override
    public PagedData<InspectionAlarm> childForInspectorInfo(LongIdKey inspectorInfoKey, PagingInfo pagingInfo)
            throws ServiceException {
        return inspectionAlarmMaintainService.lookup(
                InspectionAlarmMaintainService.CHILD_FOR_INSPECTOR_INFO,
                new Object[]{inspectorInfoKey},
                pagingInfo
        );
    }

    @Override
    public DispInspectionAlarm getDisp(LongIdKey key) throws ServiceException {
        InspectionAlarm inspectionAlarm = inspectionAlarmMaintainService.get(key);
        return toDisp(inspectionAlarm);
    }

    @Override
    public PagedData<DispInspectionAlarm> allDisp(PagingInfo pagingInfo) throws ServiceException {
        PagedData<InspectionAlarm> lookup = inspectionAlarmMaintainService.lookup(pagingInfo);
        return toDispPagedData(lookup);
    }

    @Override
    public PagedData<DispInspectionAlarm> childForInspectionDisp(LongIdKey inspectionKey, PagingInfo pagingInfo)
            throws ServiceException {
        PagedData<InspectionAlarm> lookup = inspectionAlarmMaintainService.lookup(
                InspectionAlarmMaintainService.CHILD_FOR_INSPECTION,
                new Object[]{inspectionKey},
                pagingInfo
        );
        return toDispPagedData(lookup);
    }

    @Override
    public PagedData<DispInspectionAlarm> childForInspectionTaskDisp(
            LongIdKey inspectionTaskKey, PagingInfo pagingInfo
    ) throws ServiceException {
        PagedData<InspectionAlarm> lookup = inspectionAlarmMaintainService.lookup(
                InspectionAlarmMaintainService.CHILD_FOR_INSPECTION_TASK,
                new Object[]{inspectionTaskKey},
                pagingInfo
        );
        return toDispPagedData(lookup);
    }

    @Override
    public PagedData<DispInspectionAlarm> childForInspectorInfoDisp(
            LongIdKey inspectorInfoKey, PagingInfo pagingInfo
    ) throws ServiceException {
        PagedData<InspectionAlarm> lookup = inspectionAlarmMaintainService.lookup(
                InspectionAlarmMaintainService.CHILD_FOR_INSPECTOR_INFO,
                new Object[]{inspectorInfoKey},
                pagingInfo
        );
        return toDispPagedData(lookup);
    }

    private DispInspectionAlarm toDisp(InspectionAlarm inspectionAlarm) throws ServiceException {
        if (Objects.isNull(inspectionAlarm)) {
            return null;
        }
        LongIdKey inspectionKey = inspectionAlarm.getInspectionKey();
        Inspection inspection = null;
        if (Objects.nonNull(inspectionKey)) {
            inspection = inspectionMaintainService.getIfExists(inspectionKey);
        }
        LongIdKey inspectionTaskKey = inspectionAlarm.getInspectionTaskKey();
        InspectionTask inspectionTask = null;
        if (Objects.nonNull(inspectionTaskKey)) {
            inspectionTask = inspectionTaskMaintainService.getIfExists(inspectionTaskKey);
        }
        LongIdKey inspectorInfoKey = inspectionAlarm.getInspectorInfoKey();
        InspectorInfo inspectorInfo = null;
        if (Objects.nonNull(inspectorInfoKey)) {
            inspectorInfo = inspectorInfoMaintainService.getIfExists(inspectorInfoKey);
        }
        InspectionAlarmTypeIndicator typeIndicator = null;
        if (Objects.nonNull(inspectionAlarm.getType())) {
            typeIndicator = inspectionAlarmTypeIndicatorMaintainService.getIfExists(
                    new StringIdKey(inspectionAlarm.getType())
            );
        }
        return DispInspectionAlarm.of(inspectionAlarm, inspection, inspectionTask, inspectorInfo, typeIndicator);
    }

    private PagedData<DispInspectionAlarm> toDispPagedData(PagedData<InspectionAlarm> pagedData)
            throws ServiceException {
        List<DispInspectionAlarm> dispInspectionAlarms = new ArrayList<>(pagedData.getData().size());
        for (InspectionAlarm inspectionAlarm : pagedData.getData()) {
            dispInspectionAlarms.add(toDisp(inspectionAlarm));
        }
        return new PagedData<>(
                pagedData.getCurrentPage(), pagedData.getTotalPages(), pagedData.getRows(), pagedData.getCount(),
                dispInspectionAlarms
        );
    }
}
