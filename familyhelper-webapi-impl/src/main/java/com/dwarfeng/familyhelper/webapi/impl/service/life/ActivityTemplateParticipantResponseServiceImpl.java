package com.dwarfeng.familyhelper.webapi.impl.service.life;

import com.dwarfeng.familyhelper.life.stack.bean.dto.ActivityTemplateParticipantCreateInfo;
import com.dwarfeng.familyhelper.life.stack.bean.dto.ActivityTemplateParticipantRemoveInfo;
import com.dwarfeng.familyhelper.life.stack.bean.dto.ActivityTemplateParticipantUpdateInfo;
import com.dwarfeng.familyhelper.life.stack.bean.entity.ActivityTemplateParticipant;
import com.dwarfeng.familyhelper.life.stack.bean.key.ActivityTemplateParticipantKey;
import com.dwarfeng.familyhelper.life.stack.service.ActivityTemplateParticipantMaintainService;
import com.dwarfeng.familyhelper.life.stack.service.ActivityTemplateParticipantOperateService;
import com.dwarfeng.familyhelper.webapi.stack.bean.life.disp.DispActivityTemplate;
import com.dwarfeng.familyhelper.webapi.stack.bean.life.disp.DispActivityTemplateParticipant;
import com.dwarfeng.familyhelper.webapi.stack.bean.system.disp.DispAccount;
import com.dwarfeng.familyhelper.webapi.stack.service.life.ActivityTemplateParticipantResponseService;
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

@Service
public class ActivityTemplateParticipantResponseServiceImpl implements ActivityTemplateParticipantResponseService {

    private final ActivityTemplateParticipantMaintainService activityTemplateParticipantMaintainService;
    private final ActivityTemplateParticipantOperateService activityTemplateParticipantOperateService;

    private final ActivityTemplateResponseService activityTemplateResponseService;
    private final AccountResponseService accountResponseService;

    public ActivityTemplateParticipantResponseServiceImpl(
            @Qualifier("familyhelperLifeActivityTemplateParticipantMaintainService")
            ActivityTemplateParticipantMaintainService activityTemplateParticipantMaintainService,
            @Qualifier("familyhelperLifeActivityTemplateParticipantOperateService")
            ActivityTemplateParticipantOperateService activityTemplateParticipantOperateService,
            ActivityTemplateResponseService activityTemplateResponseService,
            AccountResponseService accountResponseService
    ) {
        this.activityTemplateParticipantMaintainService = activityTemplateParticipantMaintainService;
        this.activityTemplateParticipantOperateService = activityTemplateParticipantOperateService;
        this.activityTemplateResponseService = activityTemplateResponseService;
        this.accountResponseService = accountResponseService;
    }

    @Override
    public boolean exists(ActivityTemplateParticipantKey key) throws ServiceException {
        return activityTemplateParticipantMaintainService.exists(key);
    }

    @Override
    public ActivityTemplateParticipant get(ActivityTemplateParticipantKey key) throws ServiceException {
        return activityTemplateParticipantMaintainService.get(key);
    }

    @Override
    public PagedData<ActivityTemplateParticipant> all(PagingInfo pagingInfo) throws ServiceException {
        return activityTemplateParticipantMaintainService.lookup(pagingInfo);
    }

    @Override
    public PagedData<ActivityTemplateParticipant> childForActivityTemplate(
            LongIdKey activityTemplateKey, PagingInfo pagingInfo
    ) throws ServiceException {
        return activityTemplateParticipantMaintainService.lookup(
                ActivityTemplateParticipantMaintainService.CHILD_FOR_ACTIVITY_TEMPLATE,
                new Object[]{activityTemplateKey},
                pagingInfo
        );
    }

    @Override
    public DispActivityTemplateParticipant getDisp(ActivityTemplateParticipantKey key, StringIdKey inspectAccountKey)
            throws ServiceException {
        ActivityTemplateParticipant activityTemplateParticipant = get(key);
        return toDisp(activityTemplateParticipant, inspectAccountKey);
    }

    @Override
    public PagedData<DispActivityTemplateParticipant> allDisp(StringIdKey inspectAccountKey, PagingInfo pagingInfo)
            throws ServiceException {
        PagedData<ActivityTemplateParticipant> lookup = all(pagingInfo);
        return toDispPagedData(lookup, inspectAccountKey);
    }

    @Override
    public PagedData<DispActivityTemplateParticipant> childForActivityTemplateDisp(
            StringIdKey inspectAccountKey, LongIdKey activityTemplateKey, PagingInfo pagingInfo
    ) throws ServiceException {
        PagedData<ActivityTemplateParticipant> lookup = childForActivityTemplate(activityTemplateKey, pagingInfo);
        return toDispPagedData(lookup, inspectAccountKey);
    }

    private DispActivityTemplateParticipant toDisp(
            ActivityTemplateParticipant activityTemplateParticipant, StringIdKey inspectAccountKey
    ) throws ServiceException {
        DispActivityTemplate activityTemplate = null;
        if (Objects.nonNull(activityTemplateParticipant)) {
            activityTemplate = activityTemplateResponseService.getDisp(
                    new LongIdKey(activityTemplateParticipant.getKey().getActivityTemplateLongId()), inspectAccountKey
            );
        }

        DispAccount userAccount = null;
        if (Objects.nonNull(activityTemplateParticipant)) {
            userAccount = accountResponseService.getDisp(
                    new StringIdKey(activityTemplateParticipant.getKey().getUserStringId()), inspectAccountKey
            );
        }

        return DispActivityTemplateParticipant.of(activityTemplateParticipant, activityTemplate, userAccount);
    }

    private PagedData<DispActivityTemplateParticipant> toDispPagedData(
            PagedData<ActivityTemplateParticipant> lookup, StringIdKey inspectAccountKey
    ) throws ServiceException {
        List<DispActivityTemplateParticipant> dispActivityTemplateParticipants = new ArrayList<>();
        for (ActivityTemplateParticipant activityTemplateParticipant : lookup.getData()) {
            dispActivityTemplateParticipants.add(toDisp(activityTemplateParticipant, inspectAccountKey));
        }
        return new PagedData<>(
                lookup.getCurrentPage(), lookup.getTotalPages(), lookup.getRows(), lookup.getCount(),
                dispActivityTemplateParticipants
        );
    }

    @Override
    public ActivityTemplateParticipantKey create(
            StringIdKey userKey, ActivityTemplateParticipantCreateInfo createInfo
    ) throws ServiceException {
        return activityTemplateParticipantOperateService.create(userKey, createInfo);
    }

    @Override
    public void update(StringIdKey userKey, ActivityTemplateParticipantUpdateInfo updateInfo) throws ServiceException {
        activityTemplateParticipantOperateService.update(userKey, updateInfo);
    }

    @Override
    public void remove(StringIdKey userKey, ActivityTemplateParticipantRemoveInfo removeInfo) throws ServiceException {
        activityTemplateParticipantOperateService.remove(userKey, removeInfo);
    }
}
