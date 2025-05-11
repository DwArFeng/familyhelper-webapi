package com.dwarfeng.familyhelper.webapi.impl.service.life;

import com.dwarfeng.familyhelper.life.sdk.util.Constants;
import com.dwarfeng.familyhelper.life.stack.bean.dto.ActivityCreateInfo;
import com.dwarfeng.familyhelper.life.stack.bean.dto.ActivityPermissionRemoveInfo;
import com.dwarfeng.familyhelper.life.stack.bean.dto.ActivityPermissionUpsertInfo;
import com.dwarfeng.familyhelper.life.stack.bean.dto.ActivityUpdateInfo;
import com.dwarfeng.familyhelper.life.stack.bean.entity.Activity;
import com.dwarfeng.familyhelper.life.stack.bean.entity.ActivityTypeIndicator;
import com.dwarfeng.familyhelper.life.stack.bean.entity.Poac;
import com.dwarfeng.familyhelper.life.stack.service.ActivityMaintainService;
import com.dwarfeng.familyhelper.life.stack.service.ActivityOperateService;
import com.dwarfeng.familyhelper.life.stack.service.ActivityTypeIndicatorMaintainService;
import com.dwarfeng.familyhelper.life.stack.service.PoacMaintainService;
import com.dwarfeng.familyhelper.webapi.stack.bean.life.disp.DispActivity;
import com.dwarfeng.familyhelper.webapi.stack.bean.system.disp.DispAccount;
import com.dwarfeng.familyhelper.webapi.stack.service.life.ActivityResponseService;
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
public class ActivityResponseServiceImpl implements ActivityResponseService {

    private final ActivityMaintainService activityMaintainService;
    private final PoacMaintainService poacMaintainService;
    private final ActivityOperateService activityOperateService;
    private final ActivityTypeIndicatorMaintainService activityTypeIndicatorMaintainService;

    private final AccountResponseService accountResponseService;

    public ActivityResponseServiceImpl(
            @Qualifier("familyhelperLifeActivityMaintainService")
            ActivityMaintainService activityMaintainService,
            @Qualifier("familyhelperLifePoacMaintainService")
            PoacMaintainService poacMaintainService,
            @Qualifier("familyhelperLifeActivityOperateService")
            ActivityOperateService activityOperateService,
            @Qualifier("familyhelperLifeActivityTypeIndicatorMaintainService")
            ActivityTypeIndicatorMaintainService activityTypeIndicatorMaintainService,
            AccountResponseService accountResponseService
    ) {
        this.activityMaintainService = activityMaintainService;
        this.poacMaintainService = poacMaintainService;
        this.activityOperateService = activityOperateService;
        this.activityTypeIndicatorMaintainService = activityTypeIndicatorMaintainService;
        this.accountResponseService = accountResponseService;
    }

    @Override
    public boolean exists(LongIdKey key) throws ServiceException {
        return activityMaintainService.exists(key);
    }

    @Override
    public Activity get(LongIdKey key) throws ServiceException {
        return activityMaintainService.get(key);
    }

    @Override
    public PagedData<Activity> all(PagingInfo pagingInfo) throws ServiceException {
        return activityMaintainService.lookup(pagingInfo);
    }

    @Override
    public DispActivity getDisp(LongIdKey key, StringIdKey inspectAccountKey) throws ServiceException {
        Activity activity = get(key);
        return toDispFromActivity(activity, inspectAccountKey);
    }

    @Override
    public PagedData<DispActivity> allPermittedDisp(StringIdKey accountKey, PagingInfo pagingInfo)
            throws ServiceException {
        PagedData<Poac> lookup = poacMaintainService.lookup(
                PoacMaintainService.CHILD_FOR_USER, new Object[]{accountKey}, pagingInfo
        );
        return toDispPagedDataFromPoac(lookup, accountKey);
    }

    @Override
    public PagedData<DispActivity> allOwnedDisp(StringIdKey accountKey, PagingInfo pagingInfo)
            throws ServiceException {
        PagedData<Poac> lookup = poacMaintainService.lookup(
                PoacMaintainService.CHILD_FOR_USER_PERMISSION_LEVEL_EQUALS,
                new Object[]{accountKey, Constants.PERMISSION_LEVEL_OWNER},
                pagingInfo
        );
        return toDispPagedDataFromPoac(lookup, accountKey);
    }

    @SuppressWarnings("DuplicatedCode")
    private DispActivity toDispFromActivity(
            Activity activity, StringIdKey inspectAccountKey
    ) throws ServiceException {
        List<Poac> relatedPoacs = poacMaintainService.lookup(
                PoacMaintainService.CHILD_FOR_ACTIVITY, new Object[]{activity.getKey()}
        ).getData();
        Poac ownerPoac = relatedPoacs.stream().filter(
                p -> Objects.equals(p.getPermissionLevel(), Constants.PERMISSION_LEVEL_OWNER)
        ).findFirst().orElse(null);
        Poac myPoac = relatedPoacs.stream().filter(
                p -> Objects.equals(p.getKey().getUserStringId(), inspectAccountKey.getStringId())
        ).findFirst().orElse(null);
        DispAccount ownerAccount = null;
        if (Objects.nonNull(ownerPoac)) {
            ownerAccount = accountResponseService.getDisp(
                    inspectAccountKey, new StringIdKey(ownerPoac.getKey().getUserStringId())
            );
        }
        Integer permissionLevel = Optional.ofNullable(myPoac).map(Poac::getPermissionLevel).orElse(null);
        ActivityTypeIndicator activityTypeIndicator = null;
        if (Objects.nonNull(activity.getActivityType())) {
            activityTypeIndicator = activityTypeIndicatorMaintainService.getIfExists(
                    new StringIdKey(activity.getActivityType())
            );
        }
        return DispActivity.of(activity, ownerAccount, permissionLevel, activityTypeIndicator);
    }

    @SuppressWarnings("DuplicatedCode")
    private DispActivity toDispFromPoac(Poac poac, StringIdKey inspectAccountKey) throws ServiceException {
        Activity activity = activityMaintainService.get(
                new LongIdKey(poac.getKey().getActivityLongId())
        );
        List<Poac> relatedPoacs = poacMaintainService.lookup(
                PoacMaintainService.CHILD_FOR_ACTIVITY, new Object[]{activity.getKey()}
        ).getData();
        Poac ownerPoac = relatedPoacs.stream().filter(
                p -> Objects.equals(p.getPermissionLevel(), Constants.PERMISSION_LEVEL_OWNER)
        ).findFirst().orElse(null);
        DispAccount ownerAccount = null;
        if (Objects.nonNull(ownerPoac)) {
            ownerAccount = accountResponseService.getDisp(
                    new StringIdKey(ownerPoac.getKey().getUserStringId()), inspectAccountKey
            );
        }
        Integer permissionLevel = poac.getPermissionLevel();
        ActivityTypeIndicator activityTypeIndicator = null;
        if (Objects.nonNull(activity.getActivityType())) {
            activityTypeIndicator = activityTypeIndicatorMaintainService.getIfExists(
                    new StringIdKey(activity.getActivityType())
            );
        }
        return DispActivity.of(activity, ownerAccount, permissionLevel, activityTypeIndicator);
    }

    private PagedData<DispActivity> toDispPagedDataFromPoac(
            PagedData<Poac> lookup, StringIdKey inspectAccountKey
    ) throws ServiceException {
        List<DispActivity> dispActivities = new ArrayList<>();
        for (Poac poac : lookup.getData()) {
            dispActivities.add(toDispFromPoac(poac, inspectAccountKey));
        }
        return new PagedData<>(
                lookup.getCurrentPage(), lookup.getTotalPages(), lookup.getRows(), lookup.getCount(),
                dispActivities
        );
    }

    @Override
    public LongIdKey createActivity(StringIdKey userKey, ActivityCreateInfo activityCreateInfo)
            throws ServiceException {
        return activityOperateService.createActivity(userKey, activityCreateInfo);
    }

    @Override
    public void updateActivity(StringIdKey userKey, ActivityUpdateInfo activityUpdateInfo)
            throws ServiceException {
        activityOperateService.updateActivity(userKey, activityUpdateInfo);
    }

    @Override
    public void removeActivity(StringIdKey userKey, LongIdKey activityKey) throws ServiceException {
        activityOperateService.removeActivity(userKey, activityKey);
    }

    @Override
    public void upsertPermission(StringIdKey userKey, ActivityPermissionUpsertInfo permissionUpsertInfo)
            throws ServiceException {
        activityOperateService.upsertPermission(userKey, permissionUpsertInfo);
    }

    @Override
    public void removePermission(StringIdKey userKey, ActivityPermissionRemoveInfo permissionRemoveInfo)
            throws ServiceException {
        activityOperateService.removePermission(userKey, permissionRemoveInfo);
    }

    @Override
    public void lockActivity(StringIdKey userKey, LongIdKey activityKey) throws ServiceException {
        activityOperateService.lockActivity(userKey, activityKey);
    }
}
