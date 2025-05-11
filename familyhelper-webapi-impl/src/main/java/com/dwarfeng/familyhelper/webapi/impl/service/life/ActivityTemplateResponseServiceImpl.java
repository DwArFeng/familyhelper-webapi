package com.dwarfeng.familyhelper.webapi.impl.service.life;

import com.dwarfeng.familyhelper.life.sdk.util.Constants;
import com.dwarfeng.familyhelper.life.stack.bean.dto.*;
import com.dwarfeng.familyhelper.life.stack.bean.entity.ActivityTemplate;
import com.dwarfeng.familyhelper.life.stack.bean.entity.ActivityTypeIndicator;
import com.dwarfeng.familyhelper.life.stack.bean.entity.Poat;
import com.dwarfeng.familyhelper.life.stack.service.ActivityTemplateMaintainService;
import com.dwarfeng.familyhelper.life.stack.service.ActivityTemplateOperateService;
import com.dwarfeng.familyhelper.life.stack.service.ActivityTypeIndicatorMaintainService;
import com.dwarfeng.familyhelper.life.stack.service.PoatMaintainService;
import com.dwarfeng.familyhelper.webapi.stack.bean.life.disp.DispActivityTemplate;
import com.dwarfeng.familyhelper.webapi.stack.bean.system.disp.DispAccount;
import com.dwarfeng.familyhelper.webapi.stack.service.life.ActivityTemplateResponseService;
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
public class ActivityTemplateResponseServiceImpl implements ActivityTemplateResponseService {

    private final ActivityTemplateMaintainService activityTemplateMaintainService;
    private final PoatMaintainService poatMaintainService;
    private final ActivityTemplateOperateService activityTemplateOperateService;
    private final ActivityTypeIndicatorMaintainService activityTypeIndicatorMaintainService;

    private final AccountResponseService accountResponseService;

    public ActivityTemplateResponseServiceImpl(
            @Qualifier("familyhelperLifeActivityTemplateMaintainService")
            ActivityTemplateMaintainService activityTemplateMaintainService,
            @Qualifier("familyhelperLifePoatMaintainService")
            PoatMaintainService poatMaintainService,
            @Qualifier("familyhelperLifeActivityTemplateOperateService")
            ActivityTemplateOperateService activityTemplateOperateService,
            @Qualifier("familyhelperLifeActivityTypeIndicatorMaintainService")
            ActivityTypeIndicatorMaintainService activityTypeIndicatorMaintainService,
            AccountResponseService accountResponseService
    ) {
        this.activityTemplateMaintainService = activityTemplateMaintainService;
        this.poatMaintainService = poatMaintainService;
        this.activityTemplateOperateService = activityTemplateOperateService;
        this.activityTypeIndicatorMaintainService = activityTypeIndicatorMaintainService;
        this.accountResponseService = accountResponseService;
    }

    @Override
    public boolean exists(LongIdKey key) throws ServiceException {
        return activityTemplateMaintainService.exists(key);
    }

    @Override
    public ActivityTemplate get(LongIdKey key) throws ServiceException {
        return activityTemplateMaintainService.get(key);
    }

    @Override
    public PagedData<ActivityTemplate> all(PagingInfo pagingInfo) throws ServiceException {
        return activityTemplateMaintainService.lookup(pagingInfo);
    }

    @Override
    public DispActivityTemplate getDisp(LongIdKey key, StringIdKey inspectAccountKey) throws ServiceException {
        ActivityTemplate activityTemplate = get(key);
        return toDispFromActivityTemplate(activityTemplate, inspectAccountKey);
    }

    @Override
    public PagedData<DispActivityTemplate> allPermittedDisp(StringIdKey accountKey, PagingInfo pagingInfo)
            throws ServiceException {
        PagedData<Poat> lookup = poatMaintainService.lookup(
                PoatMaintainService.CHILD_FOR_USER, new Object[]{accountKey}, pagingInfo
        );
        return toDispPagedDataFromPoat(lookup, accountKey);
    }

    @Override
    public PagedData<DispActivityTemplate> allOwnedDisp(StringIdKey accountKey, PagingInfo pagingInfo)
            throws ServiceException {
        PagedData<Poat> lookup = poatMaintainService.lookup(
                PoatMaintainService.CHILD_FOR_USER_PERMISSION_LEVEL_EQUALS,
                new Object[]{accountKey, Constants.PERMISSION_LEVEL_OWNER},
                pagingInfo
        );
        return toDispPagedDataFromPoat(lookup, accountKey);
    }

    @SuppressWarnings("DuplicatedCode")
    private DispActivityTemplate toDispFromActivityTemplate(
            ActivityTemplate activityTemplate, StringIdKey inspectAccountKey
    ) throws ServiceException {
        List<Poat> relatedPoats = poatMaintainService.lookup(
                PoatMaintainService.CHILD_FOR_ACTIVITY_TEMPLATE, new Object[]{activityTemplate.getKey()}
        ).getData();
        Poat ownerPoat = relatedPoats.stream().filter(
                p -> Objects.equals(p.getPermissionLevel(), Constants.PERMISSION_LEVEL_OWNER)
        ).findFirst().orElse(null);
        Poat myPoat = relatedPoats.stream().filter(
                p -> Objects.equals(p.getKey().getUserStringId(), inspectAccountKey.getStringId())
        ).findFirst().orElse(null);
        DispAccount ownerAccount = null;
        if (Objects.nonNull(ownerPoat)) {
            ownerAccount = accountResponseService.getDisp(
                    inspectAccountKey, new StringIdKey(ownerPoat.getKey().getUserStringId())
            );
        }
        Integer permissionLevel = Optional.ofNullable(myPoat).map(Poat::getPermissionLevel).orElse(null);
        ActivityTypeIndicator activityTypeIndicator = null;
        if (Objects.nonNull(activityTemplate.getActivityType())) {
            activityTypeIndicator = activityTypeIndicatorMaintainService.getIfExists(
                    new StringIdKey(activityTemplate.getActivityType())
            );
        }
        return DispActivityTemplate.of(activityTemplate, ownerAccount, permissionLevel, activityTypeIndicator);
    }

    @SuppressWarnings("DuplicatedCode")
    private DispActivityTemplate toDispFromPoat(Poat poat, StringIdKey inspectAccountKey) throws ServiceException {
        ActivityTemplate activityTemplate = activityTemplateMaintainService.get(
                new LongIdKey(poat.getKey().getActivityTemplateLongId())
        );
        List<Poat> relatedPoats = poatMaintainService.lookup(
                PoatMaintainService.CHILD_FOR_ACTIVITY_TEMPLATE, new Object[]{activityTemplate.getKey()}
        ).getData();
        Poat ownerPoat = relatedPoats.stream().filter(
                p -> Objects.equals(p.getPermissionLevel(), Constants.PERMISSION_LEVEL_OWNER)
        ).findFirst().orElse(null);
        DispAccount ownerAccount = null;
        if (Objects.nonNull(ownerPoat)) {
            ownerAccount = accountResponseService.getDisp(
                    new StringIdKey(ownerPoat.getKey().getUserStringId()), inspectAccountKey
            );
        }
        Integer permissionLevel = poat.getPermissionLevel();
        ActivityTypeIndicator activityTypeIndicator = null;
        if (Objects.nonNull(activityTemplate.getActivityType())) {
            activityTypeIndicator = activityTypeIndicatorMaintainService.getIfExists(
                    new StringIdKey(activityTemplate.getActivityType())
            );
        }
        return DispActivityTemplate.of(activityTemplate, ownerAccount, permissionLevel, activityTypeIndicator);
    }

    private PagedData<DispActivityTemplate> toDispPagedDataFromPoat(
            PagedData<Poat> lookup, StringIdKey inspectAccountKey
    ) throws ServiceException {
        List<DispActivityTemplate> dispActivityTemplates = new ArrayList<>();
        for (Poat poat : lookup.getData()) {
            dispActivityTemplates.add(toDispFromPoat(poat, inspectAccountKey));
        }
        return new PagedData<>(
                lookup.getCurrentPage(), lookup.getTotalPages(), lookup.getRows(), lookup.getCount(),
                dispActivityTemplates
        );
    }

    @Override
    public LongIdKey createActivityTemplate(StringIdKey userKey, ActivityTemplateCreateInfo activityTemplateCreateInfo)
            throws ServiceException {
        return activityTemplateOperateService.createActivityTemplate(userKey, activityTemplateCreateInfo);
    }

    @Override
    public void updateActivityTemplate(StringIdKey userKey, ActivityTemplateUpdateInfo activityTemplateUpdateInfo)
            throws ServiceException {
        activityTemplateOperateService.updateActivityTemplate(userKey, activityTemplateUpdateInfo);
    }

    @Override
    public void removeActivityTemplate(StringIdKey userKey, LongIdKey activityTemplateKey) throws ServiceException {
        activityTemplateOperateService.removeActivityTemplate(userKey, activityTemplateKey);
    }

    @Override
    public void upsertPermission(StringIdKey userKey, ActivityTemplatePermissionUpsertInfo permissionUpsertInfo)
            throws ServiceException {
        activityTemplateOperateService.upsertPermission(userKey, permissionUpsertInfo);
    }

    @Override
    public void removePermission(StringIdKey userKey, ActivityTemplatePermissionRemoveInfo permissionRemoveInfo)
            throws ServiceException {
        activityTemplateOperateService.removePermission(userKey, permissionRemoveInfo);
    }

    @Override
    public void upsertActivityPermission(
            StringIdKey userKey, ActivityTemplateActivityPermissionUpsertInfo permissionUpsertInfo
    ) throws ServiceException {
        activityTemplateOperateService.upsertActivityPermission(userKey, permissionUpsertInfo);
    }

    @Override
    public void removeActivityPermission(
            StringIdKey userKey, ActivityTemplateActivityPermissionRemoveInfo permissionRemoveInfo
    ) throws ServiceException {
        activityTemplateOperateService.removeActivityPermission(userKey, permissionRemoveInfo);
    }

    @Override
    public void createActivity(StringIdKey userKey, ActivityTemplateActivityCreateInfo activityCreateInfo)
            throws ServiceException {
        activityTemplateOperateService.createActivity(userKey, activityCreateInfo);
    }

    @Override
    public void createActivityForTest(StringIdKey userKey, ActivityTemplateActivityCreateInfo activityCreateInfo)
            throws ServiceException {
        activityTemplateOperateService.createActivityForTest(userKey, activityCreateInfo);
    }
}
