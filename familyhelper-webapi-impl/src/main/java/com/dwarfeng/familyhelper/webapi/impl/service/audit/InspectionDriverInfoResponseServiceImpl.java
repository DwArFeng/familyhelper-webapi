package com.dwarfeng.familyhelper.webapi.impl.service.audit;

import com.dwarfeng.audit.stack.bean.entity.Inspection;
import com.dwarfeng.audit.stack.bean.entity.InspectionDriverInfo;
import com.dwarfeng.audit.stack.service.InspectionDriverInfoMaintainService;
import com.dwarfeng.audit.stack.service.InspectionMaintainService;
import com.dwarfeng.familyhelper.webapi.stack.bean.audit.disp.DispInspectionDriverInfo;
import com.dwarfeng.familyhelper.webapi.stack.service.audit.InspectionDriverInfoResponseService;
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
public class InspectionDriverInfoResponseServiceImpl implements InspectionDriverInfoResponseService {

    private final InspectionDriverInfoMaintainService inspectionDriverInfoMaintainService;
    private final InspectionMaintainService inspectionMaintainService;

    public InspectionDriverInfoResponseServiceImpl(
            @Qualifier("auditInspectionDriverInfoMaintainService")
            InspectionDriverInfoMaintainService inspectionDriverInfoMaintainService,
            @Qualifier("auditInspectionMaintainService") InspectionMaintainService inspectionMaintainService
    ) {
        this.inspectionDriverInfoMaintainService = inspectionDriverInfoMaintainService;
        this.inspectionMaintainService = inspectionMaintainService;
    }

    @Override
    public boolean exists(LongIdKey key) throws ServiceException {
        return inspectionDriverInfoMaintainService.exists(key);
    }

    @Override
    public InspectionDriverInfo get(LongIdKey key) throws ServiceException {
        return inspectionDriverInfoMaintainService.get(key);
    }

    @Override
    public LongIdKey insert(InspectionDriverInfo inspectionDriverInfo) throws ServiceException {
        return inspectionDriverInfoMaintainService.insert(inspectionDriverInfo);
    }

    @Override
    public void update(InspectionDriverInfo inspectionDriverInfo) throws ServiceException {
        inspectionDriverInfoMaintainService.update(inspectionDriverInfo);
    }

    @Override
    public void delete(LongIdKey key) throws ServiceException {
        inspectionDriverInfoMaintainService.delete(key);
    }

    @Override
    public PagedData<InspectionDriverInfo> all(PagingInfo pagingInfo) throws ServiceException {
        return inspectionDriverInfoMaintainService.lookup(pagingInfo);
    }

    @Override
    public PagedData<InspectionDriverInfo> childForInspection(LongIdKey inspectionKey, PagingInfo pagingInfo)
            throws ServiceException {
        return inspectionDriverInfoMaintainService.lookup(
                InspectionDriverInfoMaintainService.CHILD_FOR_INSPECTION,
                new Object[]{inspectionKey},
                pagingInfo
        );
    }

    @Override
    public DispInspectionDriverInfo getDisp(LongIdKey key) throws ServiceException {
        InspectionDriverInfo inspectionDriverInfo = inspectionDriverInfoMaintainService.get(key);
        return toDisp(inspectionDriverInfo);
    }

    @Override
    public PagedData<DispInspectionDriverInfo> allDisp(PagingInfo pagingInfo) throws ServiceException {
        PagedData<InspectionDriverInfo> lookup = inspectionDriverInfoMaintainService.lookup(pagingInfo);
        return toDispPagedData(lookup);
    }

    @Override
    public PagedData<DispInspectionDriverInfo> childForInspectionDisp(LongIdKey inspectionKey, PagingInfo pagingInfo)
            throws ServiceException {
        PagedData<InspectionDriverInfo> lookup = inspectionDriverInfoMaintainService.lookup(
                InspectionDriverInfoMaintainService.CHILD_FOR_INSPECTION,
                new Object[]{inspectionKey},
                pagingInfo
        );
        return toDispPagedData(lookup);
    }

    private DispInspectionDriverInfo toDisp(InspectionDriverInfo inspectionDriverInfo) throws ServiceException {
        if (Objects.isNull(inspectionDriverInfo)) {
            return null;
        }
        LongIdKey inspectionKey = inspectionDriverInfo.getInspectionKey();
        Inspection inspection = null;
        if (Objects.nonNull(inspectionKey)) {
            inspection = inspectionMaintainService.getIfExists(inspectionKey);
        }
        return DispInspectionDriverInfo.of(inspectionDriverInfo, inspection);
    }

    private PagedData<DispInspectionDriverInfo> toDispPagedData(PagedData<InspectionDriverInfo> pagedData)
            throws ServiceException {
        List<DispInspectionDriverInfo> dispInspectionDriverInfos = new ArrayList<>(pagedData.getData().size());
        for (InspectionDriverInfo inspectionDriverInfo : pagedData.getData()) {
            dispInspectionDriverInfos.add(toDisp(inspectionDriverInfo));
        }
        return new PagedData<>(
                pagedData.getCurrentPage(), pagedData.getTotalPages(), pagedData.getRows(), pagedData.getCount(),
                dispInspectionDriverInfos
        );
    }
}
