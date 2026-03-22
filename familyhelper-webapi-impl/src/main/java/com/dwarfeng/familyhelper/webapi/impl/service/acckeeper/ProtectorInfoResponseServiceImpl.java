package com.dwarfeng.familyhelper.webapi.impl.service.acckeeper;

import com.dwarfeng.acckeeper.stack.bean.entity.ProtectorInfo;
import com.dwarfeng.acckeeper.stack.service.ProtectorInfoMaintainService;
import com.dwarfeng.familyhelper.webapi.stack.service.acckeeper.ProtectorInfoResponseService;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class ProtectorInfoResponseServiceImpl implements ProtectorInfoResponseService {

    private final ProtectorInfoMaintainService protectorInfoMaintainService;

    public ProtectorInfoResponseServiceImpl(
            @Qualifier("acckeeperProtectorInfoMaintainService")
            ProtectorInfoMaintainService protectorInfoMaintainService
    ) {
        this.protectorInfoMaintainService = protectorInfoMaintainService;
    }

    @Override
    public boolean exists(StringIdKey key) throws ServiceException {
        return protectorInfoMaintainService.exists(key);
    }

    @Override
    public ProtectorInfo get(StringIdKey key) throws ServiceException {
        return protectorInfoMaintainService.get(key);
    }

    @Override
    public StringIdKey insert(ProtectorInfo protectorInfo) throws ServiceException {
        return protectorInfoMaintainService.insert(protectorInfo);
    }

    @Override
    public void update(ProtectorInfo protectorInfo) throws ServiceException {
        protectorInfoMaintainService.update(protectorInfo);
    }

    @Override
    public void delete(StringIdKey key) throws ServiceException {
        protectorInfoMaintainService.delete(key);
    }

    @Override
    public PagedData<ProtectorInfo> all(PagingInfo pagingInfo) throws ServiceException {
        return protectorInfoMaintainService.lookup(pagingInfo);
    }

    @Override
    public PagedData<ProtectorInfo> typeEquals(String type, PagingInfo pagingInfo) throws ServiceException {
        return protectorInfoMaintainService.lookup(
                ProtectorInfoMaintainService.TYPE_EQUALS,
                new Object[]{type},
                pagingInfo
        );
    }

    @Override
    public PagedData<ProtectorInfo> typeLike(String pattern, PagingInfo pagingInfo) throws ServiceException {
        return protectorInfoMaintainService.lookup(
                ProtectorInfoMaintainService.TYPE_LIKE,
                new Object[]{pattern},
                pagingInfo
        );
    }
}
