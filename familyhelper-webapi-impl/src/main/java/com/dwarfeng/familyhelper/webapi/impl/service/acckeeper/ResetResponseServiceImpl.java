package com.dwarfeng.familyhelper.webapi.impl.service.acckeeper;

import com.dwarfeng.acckeeper.impl.handler.resetter.DubboResetter;
import com.dwarfeng.familyhelper.webapi.stack.service.acckeeper.ResetResponseService;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service("acckeeperResetResponseServiceImpl")
public class ResetResponseServiceImpl implements ResetResponseService {

    private final DubboResetter.DubboResetService dubboResetService;

    public ResetResponseServiceImpl(
            @Qualifier("acckeeperDubboResetter.dubboResetService")
            DubboResetter.DubboResetService dubboResetService
    ) {
        this.dubboResetService = dubboResetService;
    }

    @Override
    public void resetProtect() throws ServiceException {
        dubboResetService.resetProtect();
    }
}
