package com.dwarfeng.familyhelper.webapi.impl.service.logicengine;

import com.dwarfeng.familyhelper.webapi.stack.service.logicengine.PerformerSupportResponseService;
import com.dwarfeng.logicengine.stack.bean.entity.PerformerSupport;
import com.dwarfeng.logicengine.stack.service.PerformerSupportMaintainService;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class PerformerSupportResponseServiceImpl implements PerformerSupportResponseService {

    private final PerformerSupportMaintainService performerSupportMaintainService;

    public PerformerSupportResponseServiceImpl(
            @Qualifier("logicenginePerformerSupportMaintainService")
            PerformerSupportMaintainService performerSupportMaintainService
    ) {
        this.performerSupportMaintainService = performerSupportMaintainService;
    }

    @Override
    public boolean exists(StringIdKey key) throws ServiceException {
        return performerSupportMaintainService.exists(key);
    }

    @Override
    public PerformerSupport get(StringIdKey key) throws ServiceException {
        return performerSupportMaintainService.get(key);
    }

    @Override
    public PagedData<PerformerSupport> all(PagingInfo pagingInfo) throws ServiceException {
        return performerSupportMaintainService.lookup(pagingInfo);
    }

    @Override
    public PagedData<PerformerSupport> idLike(String pattern, PagingInfo pagingInfo) throws ServiceException {
        return performerSupportMaintainService.lookup(
                PerformerSupportMaintainService.ID_LIKE, new Object[]{pattern}, pagingInfo
        );
    }

    @Override
    public PagedData<PerformerSupport> labelLike(String pattern, PagingInfo pagingInfo) throws ServiceException {
        return performerSupportMaintainService.lookup(
                PerformerSupportMaintainService.LABEL_LIKE, new Object[]{pattern}, pagingInfo
        );
    }
}
