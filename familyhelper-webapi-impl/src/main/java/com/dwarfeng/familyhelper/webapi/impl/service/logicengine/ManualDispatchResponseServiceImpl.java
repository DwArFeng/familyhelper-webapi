package com.dwarfeng.familyhelper.webapi.impl.service.logicengine;

import com.dwarfeng.familyhelper.webapi.stack.service.logicengine.ManualDispatchResponseService;
import com.dwarfeng.logicengine.stack.bean.dto.ManualDispatchInfo;
import com.dwarfeng.logicengine.stack.service.ManualDispatchService;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * 手动调度响应服务实现。
 *
 * @author DwArFeng
 * @since 2.4.0
 */
@Service
public class ManualDispatchResponseServiceImpl implements ManualDispatchResponseService {

    private final ManualDispatchService manualDispatchService;

    public ManualDispatchResponseServiceImpl(
            @Qualifier("logicengineManualDispatchService") ManualDispatchService manualDispatchService
    ) {
        this.manualDispatchService = manualDispatchService;
    }

    @Override
    public void dispatch(ManualDispatchInfo info) throws ServiceException {
        manualDispatchService.dispatch(info);
    }
}