package com.dwarfeng.familyhelper.webapi.impl.service.life;

import com.dwarfeng.familyhelper.life.sdk.util.Constants;
import com.dwarfeng.familyhelper.life.stack.bean.dto.PbSetCreateInfo;
import com.dwarfeng.familyhelper.life.stack.bean.dto.PbSetPermissionRemoveInfo;
import com.dwarfeng.familyhelper.life.stack.bean.dto.PbSetPermissionUpsertInfo;
import com.dwarfeng.familyhelper.life.stack.bean.dto.PbSetUpdateInfo;
import com.dwarfeng.familyhelper.life.stack.bean.entity.PbSet;
import com.dwarfeng.familyhelper.life.stack.bean.entity.Popb;
import com.dwarfeng.familyhelper.life.stack.service.PbSetMaintainService;
import com.dwarfeng.familyhelper.life.stack.service.PbSetOperateService;
import com.dwarfeng.familyhelper.life.stack.service.PopbMaintainService;
import com.dwarfeng.familyhelper.webapi.stack.bean.life.disp.DispPbSet;
import com.dwarfeng.familyhelper.webapi.stack.bean.system.disp.DispAccount;
import com.dwarfeng.familyhelper.webapi.stack.service.life.PbSetResponseService;
import com.dwarfeng.familyhelper.webapi.stack.service.system.AccountResponseService;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class PbSetResponseServiceImpl implements PbSetResponseService {

    private final PbSetMaintainService pbSetMaintainService;
    private final PopbMaintainService popbMaintainService;
    private final PbSetOperateService pbSetOperateService;

    private final AccountResponseService accountResponseService;

    public PbSetResponseServiceImpl(
            @Qualifier("familyhelperLifePbSetMaintainService") PbSetMaintainService pbSetMaintainService,
            @Qualifier("familyhelperLifePopbMaintainService") PopbMaintainService popbMaintainService,
            @Qualifier("familyhelperLifePbSetOperateService") PbSetOperateService pbSetOperateService,
            AccountResponseService accountResponseService
    ) {
        this.pbSetMaintainService = pbSetMaintainService;
        this.popbMaintainService = popbMaintainService;
        this.pbSetOperateService = pbSetOperateService;
        this.accountResponseService = accountResponseService;
    }

    @Override
    public boolean exists(LongIdKey key) throws ServiceException {
        return pbSetMaintainService.exists(key);
    }

    @Override
    public PbSet get(LongIdKey key) throws ServiceException {
        return pbSetMaintainService.get(key);
    }

    @Override
    public PagedData<PbSet> all(PagingInfo pagingInfo) throws ServiceException {
        return pbSetMaintainService.lookup(pagingInfo);
    }

    @Override
    public DispPbSet getDisp(LongIdKey key, StringIdKey inspectAccountKey) throws ServiceException {
        PbSet pbSet = pbSetMaintainService.get(key);
        return dispPbSetFromPbSet(pbSet, inspectAccountKey);
    }

    private DispPbSet dispPbSetFromPbSet(PbSet pbSet, StringIdKey inspectAccountKey)
            throws ServiceException {
        List<Popb> relatedPopbs = popbMaintainService.lookup(
                PopbMaintainService.CHILD_FOR_PB_SET, new Object[]{pbSet.getKey()}
        ).getData();
        Popb ownerPopb = relatedPopbs.stream().filter(
                p -> Objects.equals(p.getPermissionLevel(), Constants.PERMISSION_LEVEL_OWNER)
        ).findFirst().orElse(null);
        Popb myPopb = relatedPopbs.stream().filter(
                p -> Objects.equals(p.getKey().getUserStringId(), inspectAccountKey.getStringId())
        ).findFirst().orElse(null);
        DispAccount ownerAccount = null;
        if (Objects.nonNull(ownerPopb)) {
            ownerAccount = accountResponseService.getDisp(
                    inspectAccountKey, new StringIdKey(ownerPopb.getKey().getUserStringId())
            );
        }
        Integer permissionLevel = Optional.ofNullable(myPopb).map(Popb::getPermissionLevel).orElse(null);
        return DispPbSet.of(pbSet, ownerAccount, permissionLevel);
    }

    @SuppressWarnings("DuplicatedCode")
    @Override
    public PagedData<DispPbSet> allPermittedDisp(StringIdKey accountKey, PagingInfo pagingInfo)
            throws ServiceException {
        PagedData<Popb> lookup = popbMaintainService.lookup(
                PopbMaintainService.CHILD_FOR_USER, new Object[]{accountKey}, pagingInfo
        );
        List<DispPbSet> dispPbSets = new ArrayList<>();
        for (Popb popb : lookup.getData()) {
            dispPbSets.add(dispPbSetFromPopb(popb, accountKey));
        }
        return new PagedData<>(
                lookup.getCurrentPage(), lookup.getTotalPages(), lookup.getRows(), lookup.getCount(), dispPbSets
        );
    }

    @SuppressWarnings("DuplicatedCode")
    @Override
    public PagedData<DispPbSet> allOwnedDisp(StringIdKey accountKey, PagingInfo pagingInfo)
            throws ServiceException {
        PagedData<Popb> lookup = popbMaintainService.lookup(
                PopbMaintainService.CHILD_FOR_USER_PERMISSION_LEVEL_EQUALS,
                new Object[]{accountKey, Constants.PERMISSION_LEVEL_OWNER},
                pagingInfo
        );
        List<DispPbSet> dispPbSets = new ArrayList<>();
        for (Popb popb : lookup.getData()) {
            dispPbSets.add(dispPbSetFromPopb(popb, accountKey));
        }
        return new PagedData<>(
                lookup.getCurrentPage(), lookup.getTotalPages(), lookup.getRows(), lookup.getCount(), dispPbSets
        );
    }

    private DispPbSet dispPbSetFromPopb(Popb popb, StringIdKey inspectAccountKey) throws ServiceException {
        PbSet pbSet = pbSetMaintainService.get(new LongIdKey(popb.getKey().getPbLongId()));
        List<Popb> relatedPopbs = popbMaintainService.lookup(
                PopbMaintainService.CHILD_FOR_PB_SET, new Object[]{pbSet.getKey()}
        ).getData();
        Popb ownerPopb = relatedPopbs.stream().filter(
                p -> Objects.equals(p.getPermissionLevel(), Constants.PERMISSION_LEVEL_OWNER)
        ).findFirst().orElse(null);
        DispAccount ownerAccount = null;
        if (Objects.nonNull(ownerPopb)) {
            ownerAccount = accountResponseService.getDisp(
                    new StringIdKey(ownerPopb.getKey().getUserStringId()), inspectAccountKey
            );
        }
        Integer permissionLevel = popb.getPermissionLevel();
        return DispPbSet.of(pbSet, ownerAccount, permissionLevel);
    }

    @Override
    public LongIdKey createPbSet(StringIdKey userKey, PbSetCreateInfo pbSetCreateInfo)
            throws ServiceException {
        return pbSetOperateService.createPbSet(userKey, pbSetCreateInfo);
    }

    @Override
    public void updatePbSet(StringIdKey userKey, PbSetUpdateInfo pbSetUpdateInfo)
            throws ServiceException {
        pbSetOperateService.updatePbSet(userKey, pbSetUpdateInfo);
    }

    @Override
    public void removePbSet(StringIdKey userKey, LongIdKey pbSetKey) throws ServiceException {
        pbSetOperateService.removePbSet(userKey, pbSetKey);
    }

    @Override
    public void upsertPermission(StringIdKey userKey, PbSetPermissionUpsertInfo permissionUpsertInfo)
            throws ServiceException {
        pbSetOperateService.upsertPermission(userKey, permissionUpsertInfo);
    }

    @Override
    public void removePermission(StringIdKey userKey, PbSetPermissionRemoveInfo permissionRemoveInfo)
            throws ServiceException {
        pbSetOperateService.removePermission(userKey, permissionRemoveInfo);
    }
}
