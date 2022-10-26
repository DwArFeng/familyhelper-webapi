package com.dwarfeng.familyhelper.webapi.impl.service.notify;

import com.dwarfeng.familyhelper.webapi.stack.service.notify.DispatcherInfoResponseService;
import com.dwarfeng.notify.stack.bean.entity.DispatcherInfo;
import com.dwarfeng.notify.stack.service.DispatcherInfoMaintainService;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service("notifyDispatcherInfoResponseServiceImpl")
public class DispatcherInfoResponseServiceImpl implements DispatcherInfoResponseService {

    private final DispatcherInfoMaintainService dispatcherInfoMaintainService;

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    public DispatcherInfoResponseServiceImpl(
            @Qualifier("notifyDispatcherInfoMaintainService")
            DispatcherInfoMaintainService dispatcherInfoMaintainService
    ) {
        this.dispatcherInfoMaintainService = dispatcherInfoMaintainService;
    }

    @Override
    public boolean exists(StringIdKey key) throws ServiceException {
        return dispatcherInfoMaintainService.exists(key);
    }

    @Override
    public DispatcherInfo get(StringIdKey key) throws ServiceException {
        return dispatcherInfoMaintainService.get(key);
    }

    @Override
    public StringIdKey insert(DispatcherInfo dispatcherInfo) throws ServiceException {
        return dispatcherInfoMaintainService.insert(dispatcherInfo);
    }

    @Override
    public void update(DispatcherInfo dispatcherInfo) throws ServiceException {
        dispatcherInfoMaintainService.update(dispatcherInfo);
    }

    @Override
    public void delete(StringIdKey key) throws ServiceException {
        dispatcherInfoMaintainService.delete(key);
    }

    @Override
    public PagedData<DispatcherInfo> all(PagingInfo pagingInfo) throws ServiceException {
        return dispatcherInfoMaintainService.lookup(pagingInfo);
    }

    @Override
    public PagedData<DispatcherInfo> typeEquals(String pattern, PagingInfo pagingInfo) throws ServiceException {
        return dispatcherInfoMaintainService.lookup(
                DispatcherInfoMaintainService.TYPE_EQUALS, new Object[]{pattern}, pagingInfo
        );
    }
}
