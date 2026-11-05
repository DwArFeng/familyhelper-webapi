package com.dwarfeng.familyhelper.webapi.impl.service.settingrepo;

import com.dwarfeng.familyhelper.webapi.stack.service.settingrepo.IahnNodeMekResponseService;
import com.dwarfeng.settingrepo.stack.bean.entity.IahnNodeMek;
import com.dwarfeng.settingrepo.stack.bean.key.IahnNodeMekKey;
import com.dwarfeng.settingrepo.stack.service.IahnNodeMekMaintainService;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class IahnNodeMekResponseServiceImpl implements IahnNodeMekResponseService {

    private final IahnNodeMekMaintainService iahnNodeMekMaintainService;

    public IahnNodeMekResponseServiceImpl(
            @Qualifier("settingrepoIahnNodeMekMaintainService")
            IahnNodeMekMaintainService iahnNodeMekMaintainService
    ) {
        this.iahnNodeMekMaintainService = iahnNodeMekMaintainService;
    }

    @Override
    public boolean exists(IahnNodeMekKey key) throws ServiceException {
        return iahnNodeMekMaintainService.exists(key);
    }

    @Override
    public IahnNodeMek get(IahnNodeMekKey key) throws ServiceException {
        return iahnNodeMekMaintainService.get(key);
    }

    @Override
    public PagedData<IahnNodeMek> all(PagingInfo pagingInfo) throws ServiceException {
        return iahnNodeMekMaintainService.lookup(pagingInfo);
    }

    @Override
    public PagedData<IahnNodeMek> childForNode(StringIdKey nodeKey, PagingInfo pagingInfo) throws ServiceException {
        return iahnNodeMekMaintainService.lookup(
                IahnNodeMekMaintainService.CHILD_FOR_NODE, new Object[]{nodeKey}, pagingInfo
        );
    }

    @Override
    public PagedData<IahnNodeMek> childForNodeOrdered(StringIdKey nodeKey, PagingInfo pagingInfo)
            throws ServiceException {
        return iahnNodeMekMaintainService.lookup(
                IahnNodeMekMaintainService.CHILD_FOR_NODE_ORDERED, new Object[]{nodeKey}, pagingInfo
        );
    }
}
