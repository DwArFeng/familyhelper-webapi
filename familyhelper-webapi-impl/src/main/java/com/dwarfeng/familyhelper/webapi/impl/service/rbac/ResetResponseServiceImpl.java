package com.dwarfeng.familyhelper.webapi.impl.service.rbac;

import com.dwarfeng.familyhelper.webapi.stack.service.rbac.ResetResponseService;
import com.dwarfeng.rbacds.impl.handler.resetter.DubboResetter;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service("rbacResetResponseServiceImpl")
public class ResetResponseServiceImpl implements ResetResponseService {

    private final DubboResetter.DubboResetService dubboResetService;

    public ResetResponseServiceImpl(
            @Qualifier("rbacdsDubboResetter.dubboResetService")
            DubboResetter.DubboResetService dubboResetService
    ) {
        this.dubboResetService = dubboResetService;
    }

    @Override
    public void resetFilter() throws ServiceException {
        dubboResetService.resetFilter();
    }

    @Override
    public void resetAnalysis() throws ServiceException {
        dubboResetService.resetAnalysis();
    }
}
