package com.dwarfeng.familyhelper.webapi.impl.service.rbac;

import com.dwarfeng.familyhelper.webapi.stack.bean.rbac.disp.DispPexp;
import com.dwarfeng.familyhelper.webapi.stack.service.rbac.PexpResponseService;
import com.dwarfeng.rbacds.stack.bean.dto.PexpCreateInfo;
import com.dwarfeng.rbacds.stack.bean.dto.PexpCreateResult;
import com.dwarfeng.rbacds.stack.bean.dto.PexpRemoveInfo;
import com.dwarfeng.rbacds.stack.bean.dto.PexpUpdateInfo;
import com.dwarfeng.rbacds.stack.bean.entity.Pexp;
import com.dwarfeng.rbacds.stack.bean.entity.Role;
import com.dwarfeng.rbacds.stack.bean.entity.Scope;
import com.dwarfeng.rbacds.stack.bean.key.PexpKey;
import com.dwarfeng.rbacds.stack.service.PexpMaintainService;
import com.dwarfeng.rbacds.stack.service.PexpOperateService;
import com.dwarfeng.rbacds.stack.service.RoleMaintainService;
import com.dwarfeng.rbacds.stack.service.ScopeMaintainService;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class PexpResponseServiceImpl implements PexpResponseService {

    private final PexpMaintainService pexpMaintainService;
    private final ScopeMaintainService scopeMaintainService;
    private final RoleMaintainService roleMaintainService;
    private final PexpOperateService pexpOperateService;

    public PexpResponseServiceImpl(
            @Qualifier("rbacPexpMaintainService") PexpMaintainService pexpMaintainService,
            @Qualifier("rbacScopeMaintainService") ScopeMaintainService scopeMaintainService,
            @Qualifier("rbacRoleMaintainService") RoleMaintainService roleMaintainService,
            @Qualifier("rbacPexpOperateService") PexpOperateService pexpOperateService
    ) {
        this.pexpMaintainService = pexpMaintainService;
        this.scopeMaintainService = scopeMaintainService;
        this.roleMaintainService = roleMaintainService;
        this.pexpOperateService = pexpOperateService;
    }

    @Override
    public boolean exists(PexpKey key) throws ServiceException {
        return pexpMaintainService.exists(key);
    }

    @Override
    public Pexp get(PexpKey key) throws ServiceException {
        return pexpMaintainService.get(key);
    }

    @Override
    public PagedData<Pexp> childForScope(StringIdKey scopeKey, PagingInfo pagingInfo) throws ServiceException {
        return pexpMaintainService.lookup(
                PexpMaintainService.CHILD_FOR_SCOPE,
                new Object[]{scopeKey},
                pagingInfo
        );
    }

    @Override
    public PagedData<Pexp> childForRole(StringIdKey roleKey, PagingInfo pagingInfo) throws ServiceException {
        return pexpMaintainService.lookup(
                PexpMaintainService.CHILD_FOR_ROLE,
                new Object[]{roleKey},
                pagingInfo
        );
    }

    @Override
    public PagedData<Pexp> childForScopeChildForRole(StringIdKey scopeKey, StringIdKey roleKey, PagingInfo pagingInfo)
            throws ServiceException {
        return pexpMaintainService.lookup(
                PexpMaintainService.CHILD_FOR_SCOPE_CHILD_FOR_ROLE_PEXP_STRING_ID_ASC,
                new Object[]{scopeKey, roleKey},
                pagingInfo
        );
    }

    @Override
    public DispPexp getDisp(PexpKey key) throws ServiceException {
        Pexp pexp = pexpMaintainService.get(key);
        return toDisp(pexp);
    }

    @Override
    public PagedData<DispPexp> childForScopeDisp(StringIdKey scopeKey, PagingInfo pagingInfo) throws ServiceException {
        PagedData<Pexp> pagedData = pexpMaintainService.lookup(
                PexpMaintainService.CHILD_FOR_SCOPE,
                new Object[]{scopeKey},
                pagingInfo
        );
        return toDispPagedData(pagedData);
    }

    @Override
    public PagedData<DispPexp> childForRoleDisp(StringIdKey roleKey, PagingInfo pagingInfo) throws ServiceException {
        PagedData<Pexp> pagedData = pexpMaintainService.lookup(
                PexpMaintainService.CHILD_FOR_ROLE,
                new Object[]{roleKey},
                pagingInfo
        );
        return toDispPagedData(pagedData);
    }

    @Override
    public PagedData<DispPexp> childForScopeChildForRoleDisp(
            StringIdKey scopeKey, StringIdKey roleKey, PagingInfo pagingInfo
    ) throws ServiceException {
        PagedData<Pexp> pagedData = pexpMaintainService.lookup(
                PexpMaintainService.CHILD_FOR_SCOPE_CHILD_FOR_ROLE_PEXP_STRING_ID_ASC,
                new Object[]{scopeKey, roleKey},
                pagingInfo
        );
        return toDispPagedData(pagedData);
    }

    @Override
    public PexpCreateResult create(PexpCreateInfo info) throws ServiceException {
        return pexpOperateService.create(info);
    }

    @Override
    public void update(PexpUpdateInfo info) throws ServiceException {
        pexpOperateService.update(info);
    }

    @Override
    public void remove(PexpRemoveInfo info) throws ServiceException {
        pexpOperateService.remove(info);
    }

    private DispPexp toDisp(Pexp pexp) throws ServiceException {
        if (Objects.isNull(pexp)) {
            return null;
        }
        PexpKey key = pexp.getKey();
        Scope scope = null;
        Role role = null;
        if (Objects.nonNull(key)) {
            if (Objects.nonNull(key.getScopeStringId())) {
                scope = scopeMaintainService.getIfExists(new StringIdKey(key.getScopeStringId()));
            }
            if (Objects.nonNull(key.getRoleStringId())) {
                role = roleMaintainService.getIfExists(new StringIdKey(key.getRoleStringId()));
            }
        }
        return DispPexp.of(pexp, scope, role);
    }

    private PagedData<DispPexp> toDispPagedData(PagedData<Pexp> pagedData) throws ServiceException {
        List<DispPexp> dispList = new ArrayList<>(pagedData.getData().size());
        for (Pexp pexp : pagedData.getData()) {
            dispList.add(toDisp(pexp));
        }
        return new PagedData<>(
                pagedData.getCurrentPage(), pagedData.getTotalPages(), pagedData.getRows(), pagedData.getCount(),
                dispList
        );
    }
}
