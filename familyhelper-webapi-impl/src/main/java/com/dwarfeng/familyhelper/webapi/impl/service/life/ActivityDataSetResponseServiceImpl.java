package com.dwarfeng.familyhelper.webapi.impl.service.life;

import com.dwarfeng.familyhelper.life.sdk.util.Constants;
import com.dwarfeng.familyhelper.life.stack.bean.dto.ActivityDataSetCreateInfo;
import com.dwarfeng.familyhelper.life.stack.bean.dto.ActivityDataSetPermissionRemoveInfo;
import com.dwarfeng.familyhelper.life.stack.bean.dto.ActivityDataSetPermissionUpsertInfo;
import com.dwarfeng.familyhelper.life.stack.bean.dto.ActivityDataSetUpdateInfo;
import com.dwarfeng.familyhelper.life.stack.bean.entity.ActivityDataSet;
import com.dwarfeng.familyhelper.life.stack.bean.entity.Poad;
import com.dwarfeng.familyhelper.life.stack.service.ActivityDataSetMaintainService;
import com.dwarfeng.familyhelper.life.stack.service.ActivityDataSetOperateService;
import com.dwarfeng.familyhelper.life.stack.service.PoadMaintainService;
import com.dwarfeng.familyhelper.webapi.stack.bean.disp.life.DispActivityDataSet;
import com.dwarfeng.familyhelper.webapi.stack.bean.disp.system.DispAccount;
import com.dwarfeng.familyhelper.webapi.stack.service.life.ActivityDataSetResponseService;
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
public class ActivityDataSetResponseServiceImpl implements ActivityDataSetResponseService {

    private final ActivityDataSetMaintainService activityDataSetMaintainService;
    private final PoadMaintainService poadMaintainService;
    private final ActivityDataSetOperateService activityDataSetOperateService;

    private final AccountResponseService accountResponseService;

    public ActivityDataSetResponseServiceImpl(
            @Qualifier("familyhelperLifeActivityDataSetMaintainService")
            ActivityDataSetMaintainService activityDataSetMaintainService,
            @Qualifier("familyhelperLifePoadMaintainService") PoadMaintainService poadMaintainService,
            @Qualifier("familyhelperLifeActivityDataSetOperateService")
            ActivityDataSetOperateService activityDataSetOperateService,
            AccountResponseService accountResponseService
    ) {
        this.activityDataSetMaintainService = activityDataSetMaintainService;
        this.poadMaintainService = poadMaintainService;
        this.activityDataSetOperateService = activityDataSetOperateService;
        this.accountResponseService = accountResponseService;
    }

    @Override
    public boolean exists(LongIdKey key) throws ServiceException {
        return activityDataSetMaintainService.exists(key);
    }

    @Override
    public ActivityDataSet get(LongIdKey key) throws ServiceException {
        return activityDataSetMaintainService.get(key);
    }

    @Override
    public PagedData<ActivityDataSet> all(PagingInfo pagingInfo) throws ServiceException {
        return activityDataSetMaintainService.lookup(pagingInfo);
    }

    @Override
    public DispActivityDataSet getDisp(LongIdKey key, StringIdKey inspectAccountKey) throws ServiceException {
        ActivityDataSet activityDataSet = activityDataSetMaintainService.get(key);
        return dispActivityDataSetFromActivityDataSet(activityDataSet, inspectAccountKey);
    }

    private DispActivityDataSet dispActivityDataSetFromActivityDataSet(
            ActivityDataSet activityDataSet, StringIdKey inspectAccountKey
    ) throws ServiceException {
        List<Poad> relatedPoads = poadMaintainService.lookup(
                PoadMaintainService.CHILD_FOR_ACTIVITY_DATA_SET, new Object[]{activityDataSet.getKey()}
        ).getData();
        Poad ownerPoad = relatedPoads.stream().filter(
                p -> Objects.equals(p.getPermissionLevel(), Constants.PERMISSION_LEVEL_OWNER)
        ).findFirst().orElse(null);
        Poad myPoad = relatedPoads.stream().filter(
                p -> Objects.equals(p.getKey().getUserStringId(), inspectAccountKey.getStringId())
        ).findFirst().orElse(null);
        DispAccount ownerAccount = null;
        if (Objects.nonNull(ownerPoad)) {
            ownerAccount = accountResponseService.getDisp(
                    inspectAccountKey, new StringIdKey(ownerPoad.getKey().getUserStringId())
            );
        }
        Integer permissionLevel = Optional.ofNullable(myPoad).map(Poad::getPermissionLevel).orElse(null);
        return DispActivityDataSet.of(activityDataSet, ownerAccount, permissionLevel);
    }

    @SuppressWarnings("DuplicatedCode")
    @Override
    public PagedData<DispActivityDataSet> allPermittedDisp(StringIdKey accountKey, PagingInfo pagingInfo)
            throws ServiceException {
        PagedData<Poad> lookup = poadMaintainService.lookup(
                PoadMaintainService.CHILD_FOR_USER, new Object[]{accountKey}, pagingInfo
        );
        List<DispActivityDataSet> dispActivityDataSets = new ArrayList<>();
        for (Poad poad : lookup.getData()) {
            dispActivityDataSets.add(dispActivityDataSetFromPoad(poad, accountKey));
        }
        return new PagedData<>(
                lookup.getCurrentPage(), lookup.getTotalPages(), lookup.getRows(), lookup.getCount(),
                dispActivityDataSets
        );
    }

    @SuppressWarnings("DuplicatedCode")
    @Override
    public PagedData<DispActivityDataSet> allOwnedDisp(StringIdKey accountKey, PagingInfo pagingInfo)
            throws ServiceException {
        PagedData<Poad> lookup = poadMaintainService.lookup(
                PoadMaintainService.CHILD_FOR_USER_PERMISSION_LEVEL_EQUALS,
                new Object[]{accountKey, Constants.PERMISSION_LEVEL_OWNER},
                pagingInfo
        );
        List<DispActivityDataSet> dispActivityDataSets = new ArrayList<>();
        for (Poad poad : lookup.getData()) {
            dispActivityDataSets.add(dispActivityDataSetFromPoad(poad, accountKey));
        }
        return new PagedData<>(
                lookup.getCurrentPage(), lookup.getTotalPages(), lookup.getRows(), lookup.getCount(),
                dispActivityDataSets
        );
    }

    private DispActivityDataSet dispActivityDataSetFromPoad(
            Poad poad, StringIdKey inspectAccountKey
    ) throws ServiceException {
        ActivityDataSet activityDataSet = activityDataSetMaintainService.get(
                new LongIdKey(poad.getKey().getActivityDataSetLongId())
        );
        List<Poad> relatedPoads = poadMaintainService.lookup(
                PoadMaintainService.CHILD_FOR_ACTIVITY_DATA_SET, new Object[]{activityDataSet.getKey()}
        ).getData();
        Poad ownerPoad = relatedPoads.stream().filter(
                p -> Objects.equals(p.getPermissionLevel(), Constants.PERMISSION_LEVEL_OWNER)
        ).findFirst().orElse(null);
        DispAccount ownerAccount = null;
        if (Objects.nonNull(ownerPoad)) {
            ownerAccount = accountResponseService.getDisp(
                    new StringIdKey(ownerPoad.getKey().getUserStringId()), inspectAccountKey
            );
        }
        Integer permissionLevel = poad.getPermissionLevel();
        return DispActivityDataSet.of(activityDataSet, ownerAccount, permissionLevel);
    }

    @Override
    public LongIdKey createActivityDataSet(StringIdKey userKey, ActivityDataSetCreateInfo activityDataSetCreateInfo)
            throws ServiceException {
        return activityDataSetOperateService.createActivityDataSet(userKey, activityDataSetCreateInfo);
    }

    @Override
    public void updateActivityDataSet(StringIdKey userKey, ActivityDataSetUpdateInfo activityDataSetUpdateInfo)
            throws ServiceException {
        activityDataSetOperateService.updateActivityDataSet(userKey, activityDataSetUpdateInfo);
    }

    @Override
    public void removeActivityDataSet(StringIdKey userKey, LongIdKey activityDataSetKey) throws ServiceException {
        activityDataSetOperateService.removeActivityDataSet(userKey, activityDataSetKey);
    }

    @Override
    public void upsertPermission(StringIdKey userKey, ActivityDataSetPermissionUpsertInfo permissionUpsertInfo)
            throws ServiceException {
        activityDataSetOperateService.upsertPermission(userKey, permissionUpsertInfo);
    }

    @Override
    public void removePermission(StringIdKey userKey, ActivityDataSetPermissionRemoveInfo permissionRemoveInfo)
            throws ServiceException {
        activityDataSetOperateService.removePermission(userKey, permissionRemoveInfo);
    }
}
