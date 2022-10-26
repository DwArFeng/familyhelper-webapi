package com.dwarfeng.familyhelper.webapi.impl.service.notify;

import com.dwarfeng.familyhelper.webapi.stack.service.notify.RouterInfoResponseService;
import com.dwarfeng.notify.stack.bean.entity.RouterInfo;
import com.dwarfeng.notify.stack.service.RouterInfoMaintainService;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service("notifyRouterInfoResponseServiceImpl")
public class RouterInfoResponseServiceImpl implements RouterInfoResponseService {

    private final RouterInfoMaintainService routerInfoMaintainService;

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    public RouterInfoResponseServiceImpl(
            @Qualifier("notifyRouterInfoMaintainService")
            RouterInfoMaintainService routerInfoMaintainService
    ) {
        this.routerInfoMaintainService = routerInfoMaintainService;
    }

    @Override
    public boolean exists(LongIdKey key) throws ServiceException {
        return routerInfoMaintainService.exists(key);
    }

    @Override
    public RouterInfo get(LongIdKey key) throws ServiceException {
        return routerInfoMaintainService.get(key);
    }

    @Override
    public LongIdKey insert(RouterInfo routerInfo) throws ServiceException {
        return routerInfoMaintainService.insert(routerInfo);
    }

    @Override
    public void update(RouterInfo routerInfo) throws ServiceException {
        routerInfoMaintainService.update(routerInfo);
    }

    @Override
    public void delete(LongIdKey key) throws ServiceException {
        routerInfoMaintainService.delete(key);
    }

    @Override
    public PagedData<RouterInfo> all(PagingInfo pagingInfo) throws ServiceException {
        return routerInfoMaintainService.lookup(pagingInfo);
    }

    @Override
    public PagedData<RouterInfo> typeEquals(String pattern, PagingInfo pagingInfo) throws ServiceException {
        return routerInfoMaintainService.lookup(
                RouterInfoMaintainService.TYPE_EQUALS, new Object[]{pattern}, pagingInfo
        );
    }
}
