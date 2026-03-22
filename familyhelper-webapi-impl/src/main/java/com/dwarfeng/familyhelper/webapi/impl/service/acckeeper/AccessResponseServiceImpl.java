package com.dwarfeng.familyhelper.webapi.impl.service.acckeeper;

import com.dwarfeng.acckeeper.stack.bean.dto.*;
import com.dwarfeng.acckeeper.stack.service.AccessService;
import com.dwarfeng.familyhelper.webapi.stack.service.acckeeper.AccessResponseService;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class AccessResponseServiceImpl implements AccessResponseService {

    private final AccessService accessService;

    public AccessResponseServiceImpl(@Qualifier("acckeeperAccessService") AccessService accessService) {
        this.accessService = accessService;
    }

    @Override
    public DynamicLoginResult dynamicLogin(DynamicLoginInfo info) throws ServiceException {
        return accessService.dynamicLogin(info);
    }

    @Override
    public StaticLoginResult staticLogin(StaticLoginInfo info) throws ServiceException {
        return accessService.staticLogin(info);
    }

    @Override
    public void logout(LogoutInfo info) throws ServiceException {
        accessService.logout(info);
    }

    @Override
    public PostponeResult postpone(PostponeInfo info) throws ServiceException {
        return accessService.postpone(info);
    }
}
