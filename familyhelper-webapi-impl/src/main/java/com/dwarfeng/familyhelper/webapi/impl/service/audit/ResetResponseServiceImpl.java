package com.dwarfeng.familyhelper.webapi.impl.service.audit;

import com.dwarfeng.audit.impl.handler.resetter.DubboResetter;
import com.dwarfeng.familyhelper.webapi.stack.service.audit.ResetResponseService;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service("auditResetResponseServiceImpl")
public class ResetResponseServiceImpl implements ResetResponseService {

    private final DubboResetter.DubboResetService dubboResetService;

    public ResetResponseServiceImpl(
            @Qualifier("auditDubboResetter.dubboResetService")
            DubboResetter.DubboResetService dubboResetService
    ) {
        this.dubboResetService = dubboResetService;
    }

    @Override
    public void resetAuditRecord() throws ServiceException {
        dubboResetService.resetAuditRecord();
    }

    @Override
    public void resetInspectionSupervise() throws ServiceException {
        dubboResetService.resetInspectionSupervise();
    }

    @Override
    public void resetInspectionJob() throws ServiceException {
        dubboResetService.resetInspectionJob();
    }
}
