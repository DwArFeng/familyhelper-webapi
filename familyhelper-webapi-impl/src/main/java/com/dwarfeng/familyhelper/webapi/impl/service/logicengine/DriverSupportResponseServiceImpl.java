package com.dwarfeng.familyhelper.webapi.impl.service.logicengine;

import com.dwarfeng.familyhelper.webapi.stack.service.logicengine.DriverSupportResponseService;
import com.dwarfeng.logicengine.stack.bean.entity.DriverSupport;
import com.dwarfeng.logicengine.stack.service.DriverSupportMaintainService;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class DriverSupportResponseServiceImpl implements DriverSupportResponseService {

    private final DriverSupportMaintainService driverSupportMaintainService;

    public DriverSupportResponseServiceImpl(
            @Qualifier("logicengineDriverSupportMaintainService")
            DriverSupportMaintainService driverSupportMaintainService
    ) {
        this.driverSupportMaintainService = driverSupportMaintainService;
    }

    @Override
    public boolean exists(StringIdKey key) throws ServiceException {
        return driverSupportMaintainService.exists(key);
    }

    @Override
    public DriverSupport get(StringIdKey key) throws ServiceException {
        return driverSupportMaintainService.get(key);
    }

    @Override
    public PagedData<DriverSupport> all(PagingInfo pagingInfo) throws ServiceException {
        return driverSupportMaintainService.lookup(pagingInfo);
    }

    @Override
    public PagedData<DriverSupport> idLike(String pattern, PagingInfo pagingInfo) throws ServiceException {
        return driverSupportMaintainService.lookup(
                DriverSupportMaintainService.ID_LIKE, new Object[]{pattern}, pagingInfo
        );
    }

    @Override
    public PagedData<DriverSupport> labelLike(String pattern, PagingInfo pagingInfo) throws ServiceException {
        return driverSupportMaintainService.lookup(
                DriverSupportMaintainService.LABEL_LIKE, new Object[]{pattern}, pagingInfo
        );
    }
}
