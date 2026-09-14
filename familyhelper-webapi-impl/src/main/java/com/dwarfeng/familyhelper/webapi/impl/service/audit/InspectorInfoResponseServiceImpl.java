package com.dwarfeng.familyhelper.webapi.impl.service.audit;

import com.dwarfeng.audit.stack.bean.entity.Inspection;
import com.dwarfeng.audit.stack.bean.entity.InspectorInfo;
import com.dwarfeng.audit.stack.service.InspectionMaintainService;
import com.dwarfeng.audit.stack.service.InspectorInfoMaintainService;
import com.dwarfeng.familyhelper.webapi.stack.bean.audit.disp.DispInspectorInfo;
import com.dwarfeng.familyhelper.webapi.stack.service.audit.InspectorInfoResponseService;
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
public class InspectorInfoResponseServiceImpl implements InspectorInfoResponseService {

    private final InspectorInfoMaintainService inspectorInfoMaintainService;
    private final InspectionMaintainService inspectionMaintainService;

    public InspectorInfoResponseServiceImpl(
            @Qualifier("auditInspectorInfoMaintainService") InspectorInfoMaintainService inspectorInfoMaintainService,
            @Qualifier("auditInspectionMaintainService") InspectionMaintainService inspectionMaintainService
    ) {
        this.inspectorInfoMaintainService = inspectorInfoMaintainService;
        this.inspectionMaintainService = inspectionMaintainService;
    }

    @Override
    public boolean exists(LongIdKey key) throws ServiceException {
        return inspectorInfoMaintainService.exists(key);
    }

    @Override
    public InspectorInfo get(LongIdKey key) throws ServiceException {
        return inspectorInfoMaintainService.get(key);
    }

    @Override
    public LongIdKey insert(InspectorInfo inspectorInfo) throws ServiceException {
        return inspectorInfoMaintainService.insert(inspectorInfo);
    }

    @Override
    public void update(InspectorInfo inspectorInfo) throws ServiceException {
        inspectorInfoMaintainService.update(inspectorInfo);
    }

    @Override
    public void delete(LongIdKey key) throws ServiceException {
        inspectorInfoMaintainService.delete(key);
    }

    @Override
    public PagedData<InspectorInfo> all(PagingInfo pagingInfo) throws ServiceException {
        return inspectorInfoMaintainService.lookup(pagingInfo);
    }

    @Override
    public PagedData<InspectorInfo> childForInspection(LongIdKey inspectionKey, PagingInfo pagingInfo)
            throws ServiceException {
        return inspectorInfoMaintainService.lookup(
                InspectorInfoMaintainService.CHILD_FOR_INSPECTION,
                new Object[]{inspectionKey},
                pagingInfo
        );
    }

    @Override
    public DispInspectorInfo getDisp(LongIdKey key) throws ServiceException {
        InspectorInfo inspectorInfo = inspectorInfoMaintainService.get(key);
        return toDisp(inspectorInfo);
    }

    @Override
    public PagedData<DispInspectorInfo> allDisp(PagingInfo pagingInfo) throws ServiceException {
        PagedData<InspectorInfo> lookup = inspectorInfoMaintainService.lookup(pagingInfo);
        return toDispPagedData(lookup);
    }

    @Override
    public PagedData<DispInspectorInfo> childForInspectionDisp(LongIdKey inspectionKey, PagingInfo pagingInfo)
            throws ServiceException {
        PagedData<InspectorInfo> lookup = inspectorInfoMaintainService.lookup(
                InspectorInfoMaintainService.CHILD_FOR_INSPECTION,
                new Object[]{inspectionKey},
                pagingInfo
        );
        return toDispPagedData(lookup);
    }

    private DispInspectorInfo toDisp(InspectorInfo inspectorInfo) throws ServiceException {
        if (Objects.isNull(inspectorInfo)) {
            return null;
        }
        LongIdKey inspectionKey = inspectorInfo.getInspectionKey();
        Inspection inspection = null;
        if (Objects.nonNull(inspectionKey)) {
            inspection = inspectionMaintainService.getIfExists(inspectionKey);
        }
        return DispInspectorInfo.of(inspectorInfo, inspection);
    }

    private PagedData<DispInspectorInfo> toDispPagedData(PagedData<InspectorInfo> pagedData) throws ServiceException {
        List<DispInspectorInfo> dispInspectorInfos = new ArrayList<>(pagedData.getData().size());
        for (InspectorInfo inspectorInfo : pagedData.getData()) {
            dispInspectorInfos.add(toDisp(inspectorInfo));
        }
        return new PagedData<>(
                pagedData.getCurrentPage(), pagedData.getTotalPages(), pagedData.getRows(), pagedData.getCount(),
                dispInspectorInfos
        );
    }
}
