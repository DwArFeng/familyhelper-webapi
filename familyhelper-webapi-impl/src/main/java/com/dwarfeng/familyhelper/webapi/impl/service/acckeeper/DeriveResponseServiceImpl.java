package com.dwarfeng.familyhelper.webapi.impl.service.acckeeper;

import com.dwarfeng.acckeeper.stack.bean.dto.DynamicDeriveInfo;
import com.dwarfeng.acckeeper.stack.bean.dto.DynamicDeriveResult;
import com.dwarfeng.acckeeper.stack.bean.dto.StaticDeriveInfo;
import com.dwarfeng.acckeeper.stack.bean.dto.StaticDeriveResult;
import com.dwarfeng.acckeeper.stack.service.DeriveService;
import com.dwarfeng.familyhelper.webapi.stack.service.acckeeper.DeriveResponseService;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class DeriveResponseServiceImpl implements DeriveResponseService {

    private final DeriveService deriveService;

    public DeriveResponseServiceImpl(@Qualifier("acckeeperDeriveService") DeriveService deriveService) {
        this.deriveService = deriveService;
    }

    @Override
    public DynamicDeriveResult dynamicDerive(DynamicDeriveInfo info) throws ServiceException {
        return deriveService.dynamicDerive(info);
    }

    @Override
    public StaticDeriveResult staticDerive(StaticDeriveInfo info) throws ServiceException {
        return deriveService.staticDerive(info);
    }
}
