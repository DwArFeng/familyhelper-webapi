package com.dwarfeng.familyhelper.webapi.impl.service.settingrepo;

import com.dwarfeng.familyhelper.webapi.stack.service.settingrepo.ResetResponseService;
import com.dwarfeng.settingrepo.impl.handler.resetter.DubboResetter;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service("settingRepoResetResponseServiceImpl")
public class ResetResponseServiceImpl implements ResetResponseService {

    private final DubboResetter.DubboResetService dubboResetService;

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    public ResetResponseServiceImpl(
            @Qualifier("settingrepoDubboResetter.dubboResetService")
            DubboResetter.DubboResetService dubboResetService
    ) {
        this.dubboResetService = dubboResetService;
    }

    @Override
    public void resetFormat() throws ServiceException {
        dubboResetService.resetFormat();
    }
}
