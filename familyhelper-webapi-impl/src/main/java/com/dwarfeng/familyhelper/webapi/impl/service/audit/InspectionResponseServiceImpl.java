package com.dwarfeng.familyhelper.webapi.impl.service.audit;

import com.dwarfeng.audit.stack.bean.entity.Inspection;
import com.dwarfeng.audit.stack.service.InspectionMaintainService;
import com.dwarfeng.familyhelper.webapi.stack.service.audit.InspectionResponseService;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class InspectionResponseServiceImpl implements InspectionResponseService {

    private final InspectionMaintainService inspectionMaintainService;

    public InspectionResponseServiceImpl(
            @Qualifier("auditInspectionMaintainService") InspectionMaintainService inspectionMaintainService
    ) {
        this.inspectionMaintainService = inspectionMaintainService;
    }

    @Override
    public boolean exists(LongIdKey key) throws ServiceException {
        return inspectionMaintainService.exists(key);
    }

    @Override
    public Inspection get(LongIdKey key) throws ServiceException {
        return inspectionMaintainService.get(key);
    }

    @Override
    public LongIdKey insert(Inspection inspection) throws ServiceException {
        return inspectionMaintainService.insert(inspection);
    }

    @Override
    public void update(Inspection inspection) throws ServiceException {
        inspectionMaintainService.update(inspection);
    }

    @Override
    public void delete(LongIdKey key) throws ServiceException {
        inspectionMaintainService.delete(key);
    }

    @Override
    public PagedData<Inspection> all(PagingInfo pagingInfo) throws ServiceException {
        return inspectionMaintainService.lookup(pagingInfo);
    }
}
