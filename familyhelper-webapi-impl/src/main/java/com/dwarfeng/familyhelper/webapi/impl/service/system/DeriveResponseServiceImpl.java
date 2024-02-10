package com.dwarfeng.familyhelper.webapi.impl.service.system;

import com.dwarfeng.acckeeper.stack.bean.dto.DynamicDeriveInfo;
import com.dwarfeng.acckeeper.stack.bean.dto.StaticDeriveInfo;
import com.dwarfeng.acckeeper.stack.bean.entity.LoginState;
import com.dwarfeng.acckeeper.stack.service.DeriveService;
import com.dwarfeng.familyhelper.webapi.stack.bean.dto.system.DeriveResponse;
import com.dwarfeng.familyhelper.webapi.stack.service.system.DeriveResponseService;
import com.dwarfeng.subgrade.sdk.exception.ServiceExceptionHelper;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.exception.ServiceExceptionMapper;
import com.dwarfeng.subgrade.stack.log.LogLevel;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class DeriveResponseServiceImpl implements DeriveResponseService {

    private final DeriveService deriveService;

    private final ServiceExceptionMapper sem;

    public DeriveResponseServiceImpl(
            @Qualifier("acckeeperDeriveService") DeriveService deriveService,
            ServiceExceptionMapper sem
    ) {
        this.deriveService = deriveService;
        this.sem = sem;
    }

    @Override
    public DeriveResponse dynamicDerive(DynamicDeriveInfo dynamicDeriveInfo) throws ServiceException {
        try {
            LoginState loginState = deriveService.dynamicDerive(dynamicDeriveInfo);
            return new DeriveResponse(
                    loginState.getAccountKey().getStringId(),
                    loginState.getKey().getLongId(),
                    loginState.getExpireDate()
            );
        } catch (Exception e) {
            throw ServiceExceptionHelper.logParse("动态登录时发生异常", LogLevel.WARN, e, sem);
        }
    }

    @Override
    public DeriveResponse staticDerive(StaticDeriveInfo staticDeriveInfo) throws ServiceException {
        try {
            LoginState loginState = deriveService.staticDerive(staticDeriveInfo);
            return new DeriveResponse(
                    loginState.getAccountKey().getStringId(),
                    loginState.getKey().getLongId(),
                    loginState.getExpireDate()
            );
        } catch (Exception e) {
            throw ServiceExceptionHelper.logParse("静态登录时发生异常", LogLevel.WARN, e, sem);
        }
    }
}
