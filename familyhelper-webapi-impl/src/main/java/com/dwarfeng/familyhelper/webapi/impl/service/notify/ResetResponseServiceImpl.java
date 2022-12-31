package com.dwarfeng.familyhelper.webapi.impl.service.notify;

import com.dwarfeng.familyhelper.webapi.stack.service.notify.ResetResponseService;
import com.dwarfeng.notify.impl.handler.resetter.DubboResetter;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class ResetResponseServiceImpl implements ResetResponseService {

    private final DubboResetter.DubboResetService dubboResetService;

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    public ResetResponseServiceImpl(
            @Qualifier("notifyDubboResetter.dubboResetService")
            DubboResetter.DubboResetService dubboResetService
    ) {
        this.dubboResetService = dubboResetService;
    }

    @Override
    public void resetRoute() throws ServiceException {
        dubboResetService.resetRoute();
    }

    @Override
    public void resetDispatch() throws ServiceException {
        dubboResetService.resetDispatch();
    }

    @Override
    public void resetSend() throws ServiceException {
        dubboResetService.resetSend();
    }
}
