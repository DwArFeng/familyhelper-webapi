package com.dwarfeng.familyhelper.webapi.impl.service.settingrepo;

import com.dwarfeng.familyhelper.webapi.stack.service.settingrepo.WarmupDriverSupportResponseService;
import com.dwarfeng.settingrepo.stack.bean.entity.WarmupDriverSupport;
import com.dwarfeng.settingrepo.stack.service.WarmupDriverSupportMaintainService;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class WarmupDriverSupportResponseServiceImpl implements WarmupDriverSupportResponseService {

    private final WarmupDriverSupportMaintainService warmupDriverSupportMaintainService;

    public WarmupDriverSupportResponseServiceImpl(
            @Qualifier("settingrepoWarmupDriverSupportMaintainService")
            WarmupDriverSupportMaintainService warmupDriverSupportMaintainService
    ) {
        this.warmupDriverSupportMaintainService = warmupDriverSupportMaintainService;
    }

    @Override
    public boolean exists(StringIdKey key) throws ServiceException {
        return warmupDriverSupportMaintainService.exists(key);
    }

    @Override
    public WarmupDriverSupport get(StringIdKey key) throws ServiceException {
        return warmupDriverSupportMaintainService.get(key);
    }

    @Override
    public PagedData<WarmupDriverSupport> all(PagingInfo pagingInfo) throws ServiceException {
        return warmupDriverSupportMaintainService.lookup(pagingInfo);
    }

    @Override
    public PagedData<WarmupDriverSupport> idLike(String pattern, PagingInfo pagingInfo) throws ServiceException {
        return warmupDriverSupportMaintainService.lookup(
                WarmupDriverSupportMaintainService.ID_LIKE, new Object[]{pattern}, pagingInfo
        );
    }

    @Override
    public PagedData<WarmupDriverSupport> labelLike(String pattern, PagingInfo pagingInfo) throws ServiceException {
        return warmupDriverSupportMaintainService.lookup(
                WarmupDriverSupportMaintainService.LABEL_LIKE, new Object[]{pattern}, pagingInfo
        );
    }
}
