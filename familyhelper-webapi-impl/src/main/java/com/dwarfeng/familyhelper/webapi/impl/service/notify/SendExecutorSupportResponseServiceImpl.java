package com.dwarfeng.familyhelper.webapi.impl.service.notify;

import com.dwarfeng.familyhelper.plugin.notify.bean.entity.SendExecutorSupport;
import com.dwarfeng.familyhelper.plugin.notify.service.SendExecutorSupportMaintainService;
import com.dwarfeng.familyhelper.webapi.stack.service.notify.SendExecutorSupportResponseService;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class SendExecutorSupportResponseServiceImpl implements SendExecutorSupportResponseService {

    private final SendExecutorSupportMaintainService sendExecutorSupportMaintainService;

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    public SendExecutorSupportResponseServiceImpl(
            @Qualifier("notifySendExecutorSupportMaintainService")
            SendExecutorSupportMaintainService sendExecutorSupportMaintainService
    ) {
        this.sendExecutorSupportMaintainService = sendExecutorSupportMaintainService;
    }

    @Override
    public boolean exists(StringIdKey key) throws ServiceException {
        return sendExecutorSupportMaintainService.exists(key);
    }

    @Override
    public SendExecutorSupport get(StringIdKey key) throws ServiceException {
        return sendExecutorSupportMaintainService.get(key);
    }

    @Override
    public PagedData<SendExecutorSupport> all(PagingInfo pagingInfo) throws ServiceException {
        return sendExecutorSupportMaintainService.lookup(pagingInfo);
    }
}
