package com.dwarfeng.familyhelper.webapi.impl.service.settingrepo;

import com.dwarfeng.familyhelper.webapi.stack.service.settingrepo.IahnNodeMessageResponseService;
import com.dwarfeng.settingrepo.stack.bean.entity.IahnNodeMessage;
import com.dwarfeng.settingrepo.stack.bean.key.IahnNodeLocaleKey;
import com.dwarfeng.settingrepo.stack.bean.key.IahnNodeMekKey;
import com.dwarfeng.settingrepo.stack.bean.key.IahnNodeMessageKey;
import com.dwarfeng.settingrepo.stack.service.IahnNodeMessageMaintainService;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class IahnNodeMessageResponseServiceImpl implements IahnNodeMessageResponseService {

    private final IahnNodeMessageMaintainService iahnNodeMessageMaintainService;

    public IahnNodeMessageResponseServiceImpl(
            @Qualifier("settingrepoIahnNodeMessageMaintainService")
            IahnNodeMessageMaintainService iahnNodeMessageMaintainService
    ) {
        this.iahnNodeMessageMaintainService = iahnNodeMessageMaintainService;
    }

    @Override
    public boolean exists(IahnNodeMessageKey key) throws ServiceException {
        return iahnNodeMessageMaintainService.exists(key);
    }

    @Override
    public IahnNodeMessage get(IahnNodeMessageKey key) throws ServiceException {
        return iahnNodeMessageMaintainService.get(key);
    }

    @Override
    public PagedData<IahnNodeMessage> all(PagingInfo pagingInfo) throws ServiceException {
        return iahnNodeMessageMaintainService.lookup(pagingInfo);
    }

    @Override
    public PagedData<IahnNodeMessage> childForNode(StringIdKey nodeKey, PagingInfo pagingInfo) throws ServiceException {
        return iahnNodeMessageMaintainService.lookup(
                IahnNodeMessageMaintainService.CHILD_FOR_NODE, new Object[]{nodeKey}, pagingInfo
        );
    }

    @Override
    public PagedData<IahnNodeMessage> childForLocale(IahnNodeLocaleKey localeKey, PagingInfo pagingInfo)
            throws ServiceException {
        return iahnNodeMessageMaintainService.lookup(
                IahnNodeMessageMaintainService.CHILD_FOR_LOCALE, new Object[]{localeKey}, pagingInfo
        );
    }

    @Override
    public PagedData<IahnNodeMessage> childForMek(IahnNodeMekKey mekKey, PagingInfo pagingInfo) throws ServiceException {
        return iahnNodeMessageMaintainService.lookup(
                IahnNodeMessageMaintainService.CHILD_FOR_MEK, new Object[]{mekKey}, pagingInfo
        );
    }
}
