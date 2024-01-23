package com.dwarfeng.familyhelper.webapi.impl.service.notify;

import com.dwarfeng.familyhelper.webapi.stack.service.notify.DispatcherSupportResponseService;
import com.dwarfeng.notify.stack.bean.entity.DispatcherSupport;
import com.dwarfeng.notify.stack.service.DispatcherSupportMaintainService;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class DispatcherSupportResponseServiceImpl implements DispatcherSupportResponseService {

    private final DispatcherSupportMaintainService dispatcherSupportMaintainService;

    public DispatcherSupportResponseServiceImpl(
            @Qualifier("notifyDispatcherSupportMaintainService")
            DispatcherSupportMaintainService dispatcherSupportMaintainService
    ) {
        this.dispatcherSupportMaintainService = dispatcherSupportMaintainService;
    }

    @Override
    public boolean exists(StringIdKey key) throws ServiceException {
        return dispatcherSupportMaintainService.exists(key);
    }

    @Override
    public DispatcherSupport get(StringIdKey key) throws ServiceException {
        return dispatcherSupportMaintainService.get(key);
    }

    @Override
    public PagedData<DispatcherSupport> all(PagingInfo pagingInfo) throws ServiceException {
        return dispatcherSupportMaintainService.lookup(pagingInfo);
    }

    @Override
    public PagedData<DispatcherSupport> idLike(String pattern, PagingInfo pagingInfo) throws ServiceException {
        return dispatcherSupportMaintainService.lookup(
                DispatcherSupportMaintainService.ID_LIKE, new Object[]{pattern}, pagingInfo
        );
    }
}
