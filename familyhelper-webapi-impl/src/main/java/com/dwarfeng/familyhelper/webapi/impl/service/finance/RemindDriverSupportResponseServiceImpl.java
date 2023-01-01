package com.dwarfeng.familyhelper.webapi.impl.service.finance;

import com.dwarfeng.familyhelper.finance.stack.bean.entity.RemindDriverSupport;
import com.dwarfeng.familyhelper.finance.stack.service.RemindDriverSupportMaintainService;
import com.dwarfeng.familyhelper.webapi.stack.service.finance.RemindDriverSupportResponseService;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class RemindDriverSupportResponseServiceImpl implements RemindDriverSupportResponseService {

    private final RemindDriverSupportMaintainService remindDriverSupportMaintainService;

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    public RemindDriverSupportResponseServiceImpl(
            @Qualifier("familyhelperFinanceRemindDriverSupportMaintainService")
            RemindDriverSupportMaintainService remindDriverSupportMaintainService
    ) {
        this.remindDriverSupportMaintainService = remindDriverSupportMaintainService;
    }

    @Override
    public boolean exists(StringIdKey key) throws ServiceException {
        return remindDriverSupportMaintainService.exists(key);
    }

    @Override
    public RemindDriverSupport get(StringIdKey key) throws ServiceException {
        return remindDriverSupportMaintainService.get(key);
    }

    @Override
    public PagedData<RemindDriverSupport> all(PagingInfo pagingInfo) throws ServiceException {
        return remindDriverSupportMaintainService.lookup(pagingInfo);
    }

    @Override
    public PagedData<RemindDriverSupport> idLike(String pattern, PagingInfo pagingInfo) throws ServiceException {
        return remindDriverSupportMaintainService.lookup(
                RemindDriverSupportMaintainService.ID_LIKE, new Object[]{pattern}, pagingInfo
        );
    }
}
