package com.dwarfeng.familyhelper.webapi.impl.service.life;

import com.dwarfeng.familyhelper.life.stack.bean.dto.ActivityParticipantCreateInfo;
import com.dwarfeng.familyhelper.life.stack.bean.dto.ActivityParticipantRemoveInfo;
import com.dwarfeng.familyhelper.life.stack.bean.dto.ActivityParticipantUpdateInfo;
import com.dwarfeng.familyhelper.life.stack.bean.entity.ActivityParticipant;
import com.dwarfeng.familyhelper.life.stack.bean.key.ActivityParticipantKey;
import com.dwarfeng.familyhelper.life.stack.service.ActivityParticipantMaintainService;
import com.dwarfeng.familyhelper.life.stack.service.ActivityParticipantOperateService;
import com.dwarfeng.familyhelper.webapi.stack.bean.life.disp.DispActivity;
import com.dwarfeng.familyhelper.webapi.stack.bean.life.disp.DispActivityParticipant;
import com.dwarfeng.familyhelper.webapi.stack.bean.system.disp.DispAccount;
import com.dwarfeng.familyhelper.webapi.stack.service.life.ActivityParticipantResponseService;
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

@Service
public class ActivityParticipantResponseServiceImpl implements ActivityParticipantResponseService {

    private final ActivityParticipantMaintainService activityParticipantMaintainService;
    private final ActivityParticipantOperateService activityParticipantOperateService;

    private final ActivityResponseService activityResponseService;
    private final AccountResponseService accountResponseService;

    public ActivityParticipantResponseServiceImpl(
            @Qualifier("familyhelperLifeActivityParticipantMaintainService")
            ActivityParticipantMaintainService activityParticipantMaintainService,
            @Qualifier("familyhelperLifeActivityParticipantOperateService")
            ActivityParticipantOperateService activityParticipantOperateService,
            ActivityResponseService activityResponseService,
            AccountResponseService accountResponseService
    ) {
        this.activityParticipantMaintainService = activityParticipantMaintainService;
        this.activityParticipantOperateService = activityParticipantOperateService;
        this.activityResponseService = activityResponseService;
        this.accountResponseService = accountResponseService;
    }

    @Override
    public boolean exists(ActivityParticipantKey key) throws ServiceException {
        return activityParticipantMaintainService.exists(key);
    }

    @Override
    public ActivityParticipant get(ActivityParticipantKey key) throws ServiceException {
        return activityParticipantMaintainService.get(key);
    }

    @Override
    public PagedData<ActivityParticipant> all(PagingInfo pagingInfo) throws ServiceException {
        return activityParticipantMaintainService.lookup(pagingInfo);
    }

    @Override
    public PagedData<ActivityParticipant> childForActivity(
            LongIdKey activityKey, PagingInfo pagingInfo
    ) throws ServiceException {
        return activityParticipantMaintainService.lookup(
                ActivityParticipantMaintainService.CHILD_FOR_ACTIVITY,
                new Object[]{activityKey},
                pagingInfo
        );
    }

    @Override
    public DispActivityParticipant getDisp(ActivityParticipantKey key, StringIdKey inspectAccountKey)
            throws ServiceException {
        ActivityParticipant activityParticipant = get(key);
        return toDisp(activityParticipant, inspectAccountKey);
    }

    @Override
    public PagedData<DispActivityParticipant> allDisp(StringIdKey inspectAccountKey, PagingInfo pagingInfo)
            throws ServiceException {
        PagedData<ActivityParticipant> lookup = all(pagingInfo);
        return toDispPagedData(lookup, inspectAccountKey);
    }

    @Override
    public PagedData<DispActivityParticipant> childForActivityDisp(
            StringIdKey inspectAccountKey, LongIdKey activityKey, PagingInfo pagingInfo
    ) throws ServiceException {
        PagedData<ActivityParticipant> lookup = childForActivity(activityKey, pagingInfo);
        return toDispPagedData(lookup, inspectAccountKey);
    }

    private DispActivityParticipant toDisp(
            ActivityParticipant activityParticipant, StringIdKey inspectAccountKey
    ) throws ServiceException {
        DispActivity activity = null;
        if (Objects.nonNull(activityParticipant)) {
            activity = activityResponseService.getDisp(
                    new LongIdKey(activityParticipant.getKey().getActivityLongId()), inspectAccountKey
            );
        }

        DispAccount userAccount = null;
        if (Objects.nonNull(activityParticipant)) {
            userAccount = accountResponseService.getDisp(
                    new StringIdKey(activityParticipant.getKey().getUserStringId()), inspectAccountKey
            );
        }

        return DispActivityParticipant.of(activityParticipant, activity, userAccount);
    }

    private PagedData<DispActivityParticipant> toDispPagedData(
            PagedData<ActivityParticipant> lookup, StringIdKey inspectAccountKey
    ) throws ServiceException {
        List<DispActivityParticipant> dispActivityParticipants = new ArrayList<>();
        for (ActivityParticipant activityParticipant : lookup.getData()) {
            dispActivityParticipants.add(toDisp(activityParticipant, inspectAccountKey));
        }
        return new PagedData<>(
                lookup.getCurrentPage(), lookup.getTotalPages(), lookup.getRows(), lookup.getCount(),
                dispActivityParticipants
        );
    }

    @Override
    public ActivityParticipantKey create(
            StringIdKey userKey, ActivityParticipantCreateInfo createInfo
    ) throws ServiceException {
        return activityParticipantOperateService.create(userKey, createInfo);
    }

    @Override
    public void update(StringIdKey userKey, ActivityParticipantUpdateInfo updateInfo) throws ServiceException {
        activityParticipantOperateService.update(userKey, updateInfo);
    }

    @Override
    public void remove(StringIdKey userKey, ActivityParticipantRemoveInfo removeInfo) throws ServiceException {
        activityParticipantOperateService.remove(userKey, removeInfo);
    }
}
