package com.dwarfeng.familyhelper.webapi.impl.service.audit;

import com.dwarfeng.audit.stack.bean.entity.InspectorSupport;
import com.dwarfeng.audit.stack.service.InspectorSupportMaintainService;
import com.dwarfeng.familyhelper.webapi.stack.service.audit.InspectorSupportResponseService;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class InspectorSupportResponseServiceImpl implements InspectorSupportResponseService {

    private final InspectorSupportMaintainService inspectorSupportMaintainService;

    public InspectorSupportResponseServiceImpl(
            @Qualifier("auditInspectorSupportMaintainService")
            InspectorSupportMaintainService inspectorSupportMaintainService
    ) {
        this.inspectorSupportMaintainService = inspectorSupportMaintainService;
    }

    @Override
    public boolean exists(StringIdKey key) throws ServiceException {
        return inspectorSupportMaintainService.exists(key);
    }

    @Override
    public InspectorSupport get(StringIdKey key) throws ServiceException {
        return inspectorSupportMaintainService.get(key);
    }

    @Override
    public PagedData<InspectorSupport> all(PagingInfo pagingInfo) throws ServiceException {
        return inspectorSupportMaintainService.lookup(pagingInfo);
    }
}
