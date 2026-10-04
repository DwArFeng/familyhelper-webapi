package com.dwarfeng.familyhelper.webapi.impl.service.logicengine;

import com.dwarfeng.familyhelper.webapi.stack.service.logicengine.GuarderSupportResponseService;
import com.dwarfeng.logicengine.stack.bean.entity.GuarderSupport;
import com.dwarfeng.logicengine.stack.service.GuarderSupportMaintainService;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class GuarderSupportResponseServiceImpl implements GuarderSupportResponseService {

    private final GuarderSupportMaintainService guarderSupportMaintainService;

    public GuarderSupportResponseServiceImpl(
            @Qualifier("logicengineGuarderSupportMaintainService")
            GuarderSupportMaintainService guarderSupportMaintainService
    ) {
        this.guarderSupportMaintainService = guarderSupportMaintainService;
    }

    @Override
    public boolean exists(StringIdKey key) throws ServiceException {
        return guarderSupportMaintainService.exists(key);
    }

    @Override
    public GuarderSupport get(StringIdKey key) throws ServiceException {
        return guarderSupportMaintainService.get(key);
    }

    @Override
    public PagedData<GuarderSupport> all(PagingInfo pagingInfo) throws ServiceException {
        return guarderSupportMaintainService.lookup(pagingInfo);
    }

    @Override
    public PagedData<GuarderSupport> idLike(String pattern, PagingInfo pagingInfo) throws ServiceException {
        return guarderSupportMaintainService.lookup(
                GuarderSupportMaintainService.ID_LIKE, new Object[]{pattern}, pagingInfo
        );
    }

    @Override
    public PagedData<GuarderSupport> labelLike(String pattern, PagingInfo pagingInfo) throws ServiceException {
        return guarderSupportMaintainService.lookup(
                GuarderSupportMaintainService.LABEL_LIKE, new Object[]{pattern}, pagingInfo
        );
    }
}
