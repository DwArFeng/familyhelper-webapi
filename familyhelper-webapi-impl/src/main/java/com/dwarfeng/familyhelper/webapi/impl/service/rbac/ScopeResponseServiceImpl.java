package com.dwarfeng.familyhelper.webapi.impl.service.rbac;

import com.dwarfeng.familyhelper.webapi.stack.service.rbac.ScopeResponseService;
import com.dwarfeng.rbacds.stack.bean.entity.Scope;
import com.dwarfeng.rbacds.stack.service.ScopeMaintainService;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class ScopeResponseServiceImpl implements ScopeResponseService {

    private final ScopeMaintainService scopeMaintainService;

    public ScopeResponseServiceImpl(
            @Qualifier("rbacScopeMaintainService") ScopeMaintainService scopeMaintainService
    ) {
        this.scopeMaintainService = scopeMaintainService;
    }

    @Override
    public boolean exists(StringIdKey key) throws ServiceException {
        return scopeMaintainService.exists(key);
    }

    @Override
    public Scope get(StringIdKey key) throws ServiceException {
        return scopeMaintainService.get(key);
    }

    @Override
    public StringIdKey insert(Scope entity) throws ServiceException {
        return scopeMaintainService.insert(entity);
    }

    @Override
    public void update(Scope entity) throws ServiceException {
        scopeMaintainService.update(entity);
    }

    @Override
    public void delete(StringIdKey key) throws ServiceException {
        scopeMaintainService.delete(key);
    }

    @Override
    public PagedData<Scope> all(PagingInfo pagingInfo) throws ServiceException {
        return scopeMaintainService.lookup(pagingInfo);
    }

    @Override
    public PagedData<Scope> idLike(String pattern, PagingInfo pagingInfo) throws ServiceException {
        return scopeMaintainService.lookup(
                ScopeMaintainService.ID_LIKE,
                new Object[]{pattern},
                pagingInfo
        );
    }

    @Override
    public PagedData<Scope> nameLike(String pattern, PagingInfo pagingInfo) throws ServiceException {
        return scopeMaintainService.lookup(
                ScopeMaintainService.NAME_LIKE,
                new Object[]{pattern},
                pagingInfo
        );
    }
}
