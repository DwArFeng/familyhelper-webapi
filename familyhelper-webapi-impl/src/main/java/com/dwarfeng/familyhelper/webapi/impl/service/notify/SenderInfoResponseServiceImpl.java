package com.dwarfeng.familyhelper.webapi.impl.service.notify;

import com.dwarfeng.familyhelper.webapi.stack.service.notify.SenderInfoResponseService;
import com.dwarfeng.notify.stack.bean.entity.SenderInfo;
import com.dwarfeng.notify.stack.bean.key.SenderInfoKey;
import com.dwarfeng.notify.stack.service.SenderInfoMaintainService;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class SenderInfoResponseServiceImpl implements SenderInfoResponseService {

    private final SenderInfoMaintainService senderInfoMaintainService;

    public SenderInfoResponseServiceImpl(
            @Qualifier("notifySenderInfoMaintainService")
            SenderInfoMaintainService senderInfoMaintainService
    ) {
        this.senderInfoMaintainService = senderInfoMaintainService;
    }

    @Override
    public boolean exists(SenderInfoKey key) throws ServiceException {
        return senderInfoMaintainService.exists(key);
    }

    @Override
    public SenderInfo get(SenderInfoKey key) throws ServiceException {
        return senderInfoMaintainService.get(key);
    }

    @Override
    public SenderInfoKey insert(SenderInfo senderInfo) throws ServiceException {
        return senderInfoMaintainService.insert(senderInfo);
    }

    @Override
    public void update(SenderInfo senderInfo) throws ServiceException {
        senderInfoMaintainService.update(senderInfo);
    }

    @Override
    public void delete(SenderInfoKey key) throws ServiceException {
        senderInfoMaintainService.delete(key);
    }

    @Override
    public PagedData<SenderInfo> all(PagingInfo pagingInfo) throws ServiceException {
        return senderInfoMaintainService.lookup(pagingInfo);
    }

    @Override
    public PagedData<SenderInfo> typeEquals(String pattern, PagingInfo pagingInfo) throws ServiceException {
        return senderInfoMaintainService.lookup(
                SenderInfoMaintainService.TYPE_EQUALS, new Object[]{pattern}, pagingInfo
        );
    }
}
