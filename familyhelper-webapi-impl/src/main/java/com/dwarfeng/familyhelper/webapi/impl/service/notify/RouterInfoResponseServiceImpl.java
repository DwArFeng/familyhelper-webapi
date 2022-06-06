package com.dwarfeng.familyhelper.webapi.impl.service.notify;

import com.dwarfeng.familyhelper.webapi.stack.bean.disp.notify.DispRouterInfo;
import com.dwarfeng.familyhelper.webapi.stack.service.notify.RouterInfoResponseService;
import com.dwarfeng.notify.stack.bean.entity.NotifySetting;
import com.dwarfeng.notify.stack.bean.entity.RouterInfo;
import com.dwarfeng.notify.stack.service.NotifySettingMaintainService;
import com.dwarfeng.notify.stack.service.RouterInfoMaintainService;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service("notifyRouterInfoResponseServiceImpl")
public class RouterInfoResponseServiceImpl implements RouterInfoResponseService {

    private final RouterInfoMaintainService routerInfoMaintainService;
    private final NotifySettingMaintainService notifySettingMaintainService;

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    public RouterInfoResponseServiceImpl(
            @Qualifier("notifyRouterInfoMaintainService")
            RouterInfoMaintainService routerInfoMaintainService,
            @Qualifier("notifyNotifySettingMaintainService")
            NotifySettingMaintainService notifySettingMaintainService
    ) {
        this.routerInfoMaintainService = routerInfoMaintainService;
        this.notifySettingMaintainService = notifySettingMaintainService;
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

    @Override
    public DispRouterInfo getDisp(LongIdKey key) throws ServiceException {
        RouterInfo routerInfo = get(key);
        return toDisp(routerInfo);
    }

    @Override
    public PagedData<DispRouterInfo> allDisp(PagingInfo pagingInfo) throws ServiceException {
        PagedData<RouterInfo> lookup = all(pagingInfo);
        return toDispPagedData(lookup);
    }

    @Override
    public PagedData<DispRouterInfo> typeEqualsDisp(String pattern, PagingInfo pagingInfo) throws ServiceException {
        PagedData<RouterInfo> lookup = typeEquals(pattern, pagingInfo);
        return toDispPagedData(lookup);
    }

    private DispRouterInfo toDisp(RouterInfo routerInfo) throws ServiceException {
        NotifySetting notifySetting = null;
        if (Objects.nonNull(routerInfo.getNotifySettingKey())) {
            notifySetting = notifySettingMaintainService.getIfExists(routerInfo.getNotifySettingKey());
        }
        return DispRouterInfo.of(routerInfo, notifySetting);
    }

    private PagedData<DispRouterInfo> toDispPagedData(PagedData<RouterInfo> lookup) throws ServiceException {
        List<DispRouterInfo> dispRouterInfos = new ArrayList<>();
        for (RouterInfo routerInfo : lookup.getData()) {
            dispRouterInfos.add(toDisp(routerInfo));
        }
        return new PagedData<>(
                lookup.getCurrentPage(), lookup.getTotalPages(), lookup.getRows(), lookup.getCount(),
                dispRouterInfos
        );
    }
}
