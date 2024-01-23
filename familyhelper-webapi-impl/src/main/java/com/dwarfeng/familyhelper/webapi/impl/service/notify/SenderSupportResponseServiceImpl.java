package com.dwarfeng.familyhelper.webapi.impl.service.notify;

import com.dwarfeng.familyhelper.webapi.stack.service.notify.SenderSupportResponseService;
import com.dwarfeng.notify.stack.bean.entity.SenderSupport;
import com.dwarfeng.notify.stack.service.SenderSupportMaintainService;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class SenderSupportResponseServiceImpl implements SenderSupportResponseService {

    private final SenderSupportMaintainService senderSupportMaintainService;

    public SenderSupportResponseServiceImpl(
            @Qualifier("notifySenderSupportMaintainService")
            SenderSupportMaintainService senderSupportMaintainService
    ) {
        this.senderSupportMaintainService = senderSupportMaintainService;
    }

    @Override
    public boolean exists(StringIdKey key) throws ServiceException {
        return senderSupportMaintainService.exists(key);
    }

    @Override
    public SenderSupport get(StringIdKey key) throws ServiceException {
        return senderSupportMaintainService.get(key);
    }

    @Override
    public PagedData<SenderSupport> all(PagingInfo pagingInfo) throws ServiceException {
        return senderSupportMaintainService.lookup(pagingInfo);
    }

    @Override
    public PagedData<SenderSupport> idLike(String pattern, PagingInfo pagingInfo) throws ServiceException {
        return senderSupportMaintainService.lookup(
                SenderSupportMaintainService.ID_LIKE, new Object[]{pattern}, pagingInfo
        );
    }
}
