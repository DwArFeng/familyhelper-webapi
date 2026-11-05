package com.dwarfeng.familyhelper.webapi.impl.service.settingrepo;

import com.dwarfeng.familyhelper.webapi.stack.service.settingrepo.IahnNodeLocaleResponseService;
import com.dwarfeng.settingrepo.stack.bean.entity.IahnNodeLocale;
import com.dwarfeng.settingrepo.stack.bean.key.IahnNodeLocaleKey;
import com.dwarfeng.settingrepo.stack.service.IahnNodeLocaleMaintainService;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class IahnNodeLocaleResponseServiceImpl implements IahnNodeLocaleResponseService {

    private final IahnNodeLocaleMaintainService iahnNodeLocaleMaintainService;

    public IahnNodeLocaleResponseServiceImpl(
            @Qualifier("settingrepoIahnNodeLocaleMaintainService")
            IahnNodeLocaleMaintainService iahnNodeLocaleMaintainService
    ) {
        this.iahnNodeLocaleMaintainService = iahnNodeLocaleMaintainService;
    }

    @Override
    public boolean exists(IahnNodeLocaleKey key) throws ServiceException {
        return iahnNodeLocaleMaintainService.exists(key);
    }

    @Override
    public IahnNodeLocale get(IahnNodeLocaleKey key) throws ServiceException {
        return iahnNodeLocaleMaintainService.get(key);
    }

    @Override
    public PagedData<IahnNodeLocale> all(PagingInfo pagingInfo) throws ServiceException {
        return iahnNodeLocaleMaintainService.lookup(pagingInfo);
    }

    @Override
    public PagedData<IahnNodeLocale> childForNode(StringIdKey nodeKey, PagingInfo pagingInfo) throws ServiceException {
        return iahnNodeLocaleMaintainService.lookup(
                IahnNodeLocaleMaintainService.CHILD_FOR_NODE, new Object[]{nodeKey}, pagingInfo
        );
    }

    @Override
    public PagedData<IahnNodeLocale> childForNodeOrdered(StringIdKey nodeKey, PagingInfo pagingInfo)
            throws ServiceException {
        return iahnNodeLocaleMaintainService.lookup(
                IahnNodeLocaleMaintainService.CHILD_FOR_NODE_ORDERED, new Object[]{nodeKey}, pagingInfo
        );
    }
}
