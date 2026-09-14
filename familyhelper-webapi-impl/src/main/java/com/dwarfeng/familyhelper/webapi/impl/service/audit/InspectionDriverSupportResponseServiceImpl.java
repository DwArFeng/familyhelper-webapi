package com.dwarfeng.familyhelper.webapi.impl.service.audit;

import com.dwarfeng.audit.stack.bean.entity.InspectionDriverSupport;
import com.dwarfeng.audit.stack.service.InspectionDriverSupportMaintainService;
import com.dwarfeng.familyhelper.webapi.stack.service.audit.InspectionDriverSupportResponseService;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class InspectionDriverSupportResponseServiceImpl implements InspectionDriverSupportResponseService {

    private final InspectionDriverSupportMaintainService inspectionDriverSupportMaintainService;

    public InspectionDriverSupportResponseServiceImpl(
            @Qualifier("auditInspectionDriverSupportMaintainService")
            InspectionDriverSupportMaintainService inspectionDriverSupportMaintainService
    ) {
        this.inspectionDriverSupportMaintainService = inspectionDriverSupportMaintainService;
    }

    @Override
    public boolean exists(StringIdKey key) throws ServiceException {
        return inspectionDriverSupportMaintainService.exists(key);
    }

    @Override
    public InspectionDriverSupport get(StringIdKey key) throws ServiceException {
        return inspectionDriverSupportMaintainService.get(key);
    }

    @Override
    public PagedData<InspectionDriverSupport> all(PagingInfo pagingInfo) throws ServiceException {
        return inspectionDriverSupportMaintainService.lookup(pagingInfo);
    }
}
