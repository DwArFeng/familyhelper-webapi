package com.dwarfeng.familyhelper.webapi.impl.service.system;

import com.dwarfeng.familyhelper.webapi.stack.service.system.PexpResponseService;
import com.dwarfeng.rbacds.stack.bean.entity.Pexp;
import com.dwarfeng.rbacds.stack.service.PexpMaintainService;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
public class PexpResponseServiceImpl implements PexpResponseService {

    private final PexpMaintainService pexpMaintainService;

    public PexpResponseServiceImpl(@Qualifier("rbacPexpMaintainService") PexpMaintainService pexpMaintainService) {
        this.pexpMaintainService = pexpMaintainService;
    }

    @Override
    public boolean exists(LongIdKey key) throws ServiceException {
        return pexpMaintainService.exists(key);
    }

    @Override
    public Pexp get(LongIdKey key) throws ServiceException {
        return pexpMaintainService.get(key);
    }

    @Override
    public LongIdKey insert(Pexp pexp) throws ServiceException {
        return pexpMaintainService.insert(pexp);
    }

    @Override
    public void update(Pexp pexp) throws ServiceException {
        pexpMaintainService.update(pexp);
    }

    @Override
    public void delete(LongIdKey key) throws ServiceException {
        pexpMaintainService.delete(key);
    }

    @Override
    public PagedData<Pexp> childForRole(StringIdKey roleKey, PagingInfo pagingInfo) throws ServiceException {
        return pexpMaintainService.lookup(PexpMaintainService.PEXP_FOR_ROLE, new Object[]{roleKey}, pagingInfo);
    }
}
