package com.dwarfeng.familyhelper.webapi.impl.service.notify;

import com.dwarfeng.familyhelper.webapi.stack.service.notify.RouterSupportResponseService;
import com.dwarfeng.notify.stack.bean.entity.RouterSupport;
import com.dwarfeng.notify.stack.service.RouterSupportMaintainService;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service("notifyRouterSupportResponseServiceImpl")
public class RouterSupportResponseServiceImpl implements RouterSupportResponseService {

    private final RouterSupportMaintainService routerSupportMaintainService;

    public RouterSupportResponseServiceImpl(
            @Qualifier("notifyRouterSupportMaintainService")
            RouterSupportMaintainService routerSupportMaintainService
    ) {
        this.routerSupportMaintainService = routerSupportMaintainService;
    }

    @Override
    public boolean exists(StringIdKey key) throws ServiceException {
        return routerSupportMaintainService.exists(key);
    }

    @Override
    public RouterSupport get(StringIdKey key) throws ServiceException {
        return routerSupportMaintainService.get(key);
    }

    @Override
    public PagedData<RouterSupport> all(PagingInfo pagingInfo) throws ServiceException {
        return routerSupportMaintainService.lookup(pagingInfo);
    }

    @Override
    public PagedData<RouterSupport> idLike(String pattern, PagingInfo pagingInfo) throws ServiceException {
        return routerSupportMaintainService.lookup(
                RouterSupportMaintainService.ID_LIKE, new Object[]{pattern}, pagingInfo
        );
    }
}
