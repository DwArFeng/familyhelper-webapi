package com.dwarfeng.familyhelper.webapi.impl.service.system;

import com.dwarfeng.acckeeper.stack.service.AccountOperateService;
import com.dwarfeng.familyhelper.webapi.stack.service.system.PexpResponseService;
import com.dwarfeng.rbacds.stack.bean.entity.Pexp;
import com.dwarfeng.rbacds.stack.bean.entity.User;
import com.dwarfeng.rbacds.stack.service.PexpMaintainService;
import com.dwarfeng.rbacds.stack.service.UserMaintainService;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class PexpResponseServiceImpl implements PexpResponseService {

    private final PexpMaintainService pexpMaintainService;
    private final UserMaintainService userMaintainService;
    private final AccountOperateService accountOperateService;

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    public PexpResponseServiceImpl(
            @Qualifier("rbacPexpMaintainService") PexpMaintainService pexpMaintainService,
            @Qualifier("rbacUserMaintainService") UserMaintainService userMaintainService,
            @Qualifier("acckeeperAccountOperateService") AccountOperateService accountOperateService
    ) {
        this.pexpMaintainService = pexpMaintainService;
        this.userMaintainService = userMaintainService;
        this.accountOperateService = accountOperateService;
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
        if (Objects.nonNull(pexp.getRoleKey())) {
            invalidInfluencedUsers(Collections.singleton(pexp.getRoleKey()));
        }
        return pexpMaintainService.insert(pexp);
    }

    @Override
    public void update(Pexp pexp) throws ServiceException {
        Pexp oldPexp = pexpMaintainService.get(pexp.getKey());
        Set<StringIdKey> roleKeySet = new HashSet<>();
        if (Objects.nonNull(oldPexp.getRoleKey())) {
            roleKeySet.add(oldPexp.getRoleKey());
        }
        if (Objects.nonNull(pexp.getRoleKey())) {
            roleKeySet.add(pexp.getRoleKey());
        }
        invalidInfluencedUsers(roleKeySet);
        pexpMaintainService.update(pexp);
    }

    @Override
    public void delete(LongIdKey key) throws ServiceException {
        Pexp pexp = pexpMaintainService.get(key);
        if (Objects.nonNull(pexp.getRoleKey())) {
            invalidInfluencedUsers(Collections.singleton(pexp.getRoleKey()));
        }
        pexpMaintainService.delete(key);
    }

    private void invalidInfluencedUsers(Collection<StringIdKey> roleKeys) throws ServiceException {
        if (roleKeys.isEmpty()) {
            return;
        }
        Set<StringIdKey> influencedUserKeySet = new HashSet<>();
        for (StringIdKey roleKey : roleKeys) {
            influencedUserKeySet.addAll(userMaintainService.lookup(
                    UserMaintainService.CHILD_FOR_ROLE, new Object[]{roleKey}
            ).getData().stream().map(User::getKey).collect(Collectors.toList()));
        }
        for (StringIdKey influencedUserKey : influencedUserKeySet) {
            accountOperateService.invalid(influencedUserKey);
        }
    }

    @Override
    public PagedData<Pexp> childForRole(StringIdKey roleKey, PagingInfo pagingInfo) throws ServiceException {
        return pexpMaintainService.lookup(PexpMaintainService.PEXP_FOR_ROLE, new Object[]{roleKey}, pagingInfo);
    }
}
