package com.dwarfeng.familyhelper.webapi.impl.service.acckeeper;

import com.dwarfeng.acckeeper.stack.bean.entity.ProtectorSupport;
import com.dwarfeng.acckeeper.stack.service.ProtectorSupportMaintainService;
import com.dwarfeng.familyhelper.webapi.stack.service.acckeeper.ProtectorSupportResponseService;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class ProtectorSupportResponseServiceImpl implements ProtectorSupportResponseService {

    private final ProtectorSupportMaintainService protectorSupportMaintainService;

    public ProtectorSupportResponseServiceImpl(
            @Qualifier("acckeeperProtectorSupportMaintainService")
            ProtectorSupportMaintainService protectorSupportMaintainService
    ) {
        this.protectorSupportMaintainService = protectorSupportMaintainService;
    }

    @Override
    public boolean exists(StringIdKey key) throws ServiceException {
        return protectorSupportMaintainService.exists(key);
    }

    @Override
    public ProtectorSupport get(StringIdKey key) throws ServiceException {
        return protectorSupportMaintainService.get(key);
    }

    @Override
    public PagedData<ProtectorSupport> all(PagingInfo pagingInfo) throws ServiceException {
        return protectorSupportMaintainService.lookup(pagingInfo);
    }

    @Override
    public PagedData<ProtectorSupport> idLike(String pattern, PagingInfo pagingInfo) throws ServiceException {
        return protectorSupportMaintainService.lookup(
                ProtectorSupportMaintainService.ID_LIKE,
                new Object[]{pattern},
                pagingInfo
        );
    }

    @Override
    public PagedData<ProtectorSupport> labelLike(String pattern, PagingInfo pagingInfo) throws ServiceException {
        return protectorSupportMaintainService.lookup(
                ProtectorSupportMaintainService.LABEL_LIKE,
                new Object[]{pattern},
                pagingInfo
        );
    }
}
