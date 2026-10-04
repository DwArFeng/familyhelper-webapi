package com.dwarfeng.familyhelper.webapi.impl.service.logicengine;

import com.dwarfeng.familyhelper.webapi.stack.service.logicengine.ResetResponseService;
import com.dwarfeng.logicengine.impl.handler.resetter.DubboResetter;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service("logicengineResetResponseServiceImpl")
public class ResetResponseServiceImpl implements ResetResponseService {

    private final DubboResetter.DubboResetService dubboResetService;

    public ResetResponseServiceImpl(
            @Qualifier("logicengineDubboResetter.dubboResetService")
            DubboResetter.DubboResetService dubboResetService
    ) {
        this.dubboResetService = dubboResetService;
    }

    @Override
    public void resetSupervise() throws ServiceException {
        dubboResetService.resetSupervise();
    }

    @Override
    public void resetJob() throws ServiceException {
        dubboResetService.resetJob();
    }
}
