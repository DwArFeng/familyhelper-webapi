package com.dwarfeng.familyhelper.webapi.impl.service.audit;

import com.dwarfeng.audit.stack.bean.entity.InspectionAlarmTypeIndicator;
import com.dwarfeng.audit.stack.service.InspectionAlarmTypeIndicatorMaintainService;
import com.dwarfeng.familyhelper.webapi.stack.service.audit.InspectionAlarmTypeIndicatorResponseService;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class InspectionAlarmTypeIndicatorResponseServiceImpl implements InspectionAlarmTypeIndicatorResponseService {

    private final InspectionAlarmTypeIndicatorMaintainService inspectionAlarmTypeIndicatorMaintainService;

    public InspectionAlarmTypeIndicatorResponseServiceImpl(
            @Qualifier("auditInspectionAlarmTypeIndicatorMaintainService")
            InspectionAlarmTypeIndicatorMaintainService inspectionAlarmTypeIndicatorMaintainService
    ) {
        this.inspectionAlarmTypeIndicatorMaintainService = inspectionAlarmTypeIndicatorMaintainService;
    }

    @Override
    public boolean exists(StringIdKey key) throws ServiceException {
        return inspectionAlarmTypeIndicatorMaintainService.exists(key);
    }

    @Override
    public InspectionAlarmTypeIndicator get(StringIdKey key) throws ServiceException {
        return inspectionAlarmTypeIndicatorMaintainService.get(key);
    }

    @Override
    public StringIdKey insert(InspectionAlarmTypeIndicator inspectionAlarmTypeIndicator) throws ServiceException {
        return inspectionAlarmTypeIndicatorMaintainService.insert(inspectionAlarmTypeIndicator);
    }

    @Override
    public void update(InspectionAlarmTypeIndicator inspectionAlarmTypeIndicator) throws ServiceException {
        inspectionAlarmTypeIndicatorMaintainService.update(inspectionAlarmTypeIndicator);
    }

    @Override
    public void delete(StringIdKey key) throws ServiceException {
        inspectionAlarmTypeIndicatorMaintainService.delete(key);
    }

    @Override
    public PagedData<InspectionAlarmTypeIndicator> all(PagingInfo pagingInfo) throws ServiceException {
        return inspectionAlarmTypeIndicatorMaintainService.lookup(pagingInfo);
    }
}
