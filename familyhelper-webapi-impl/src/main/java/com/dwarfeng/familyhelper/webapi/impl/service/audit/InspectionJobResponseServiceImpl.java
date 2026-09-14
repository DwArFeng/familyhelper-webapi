package com.dwarfeng.familyhelper.webapi.impl.service.audit;

import com.dwarfeng.audit.stack.bean.dto.InspectionJobExecuteInfo;
import com.dwarfeng.audit.stack.service.InspectionJobService;
import com.dwarfeng.familyhelper.webapi.stack.service.audit.InspectionJobResponseService;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class InspectionJobResponseServiceImpl implements InspectionJobResponseService {

    private final InspectionJobService inspectionJobService;

    public InspectionJobResponseServiceImpl(
            @Qualifier("auditInspectionJobService") InspectionJobService inspectionJobService
    ) {
        this.inspectionJobService = inspectionJobService;
    }

    @Override
    public void execute(InspectionJobExecuteInfo info) throws ServiceException {
        inspectionJobService.execute(info);
    }
}
