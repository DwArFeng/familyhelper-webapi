package com.dwarfeng.familyhelper.webapi.impl.service.life;

import com.dwarfeng.familyhelper.life.stack.bean.dto.ActivityTemplateDataInfoCreateInfo;
import com.dwarfeng.familyhelper.life.stack.bean.dto.ActivityTemplateDataInfoUpdateInfo;
import com.dwarfeng.familyhelper.life.stack.bean.entity.ActivityTemplateDataInfo;
import com.dwarfeng.familyhelper.life.stack.service.ActivityTemplateDataInfoMaintainService;
import com.dwarfeng.familyhelper.life.stack.service.ActivityTemplateDataInfoOperateService;
import com.dwarfeng.familyhelper.webapi.stack.bean.disp.life.DispActivityDataItem;
import com.dwarfeng.familyhelper.webapi.stack.bean.disp.life.DispActivityTemplate;
import com.dwarfeng.familyhelper.webapi.stack.bean.disp.life.DispActivityTemplateDataInfo;
import com.dwarfeng.familyhelper.webapi.stack.service.life.ActivityDataItemResponseService;
import com.dwarfeng.familyhelper.webapi.stack.service.life.ActivityTempalteDataInfoResponseService;
import com.dwarfeng.familyhelper.webapi.stack.service.life.ActivityTemplateResponseService;
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
public class ActivityTemplateDataInfoResponseServiceImpl implements ActivityTempalteDataInfoResponseService {

    private final ActivityTemplateDataInfoMaintainService activityTemplateDataInfoMaintainService;
    private final ActivityTemplateDataInfoOperateService activityTemplateDataInfoOperateService;

    private final ActivityTemplateResponseService activityTemplateResponseService;
    private final ActivityDataItemResponseService activityDataItemResponseService;

    public ActivityTemplateDataInfoResponseServiceImpl(
            @Qualifier("familyhelperLifeActivityTemplateDataInfoMaintainService")
            ActivityTemplateDataInfoMaintainService activityTemplateDataInfoMaintainService,
            @Qualifier("familyhelperLifeActivityTemplateDataInfoOperateService")
            ActivityTemplateDataInfoOperateService activityTemplateDataInfoOperateService,
            ActivityTemplateResponseService activityTemplateResponseService,
            ActivityDataItemResponseService activityDataItemResponseService
    ) {
        this.activityTemplateDataInfoMaintainService = activityTemplateDataInfoMaintainService;
        this.activityTemplateDataInfoOperateService = activityTemplateDataInfoOperateService;
        this.activityTemplateResponseService = activityTemplateResponseService;
        this.activityDataItemResponseService = activityDataItemResponseService;
    }

    @Override
    public boolean exists(LongIdKey key) throws ServiceException {
        return activityTemplateDataInfoMaintainService.exists(key);
    }

    @Override
    public ActivityTemplateDataInfo get(LongIdKey key) throws ServiceException {
        return activityTemplateDataInfoMaintainService.get(key);
    }

    @Override
    public PagedData<ActivityTemplateDataInfo> all(PagingInfo pagingInfo) throws ServiceException {
        return activityTemplateDataInfoMaintainService.lookup(pagingInfo);
    }

    @Override
    public PagedData<ActivityTemplateDataInfo> childForActivityTemplate(
            LongIdKey activityTemplateKey, PagingInfo pagingInfo
    ) throws ServiceException {
        return activityTemplateDataInfoMaintainService.lookup(
                ActivityTemplateDataInfoMaintainService.CHILD_FOR_ACTIVITY_TEMPLATE,
                new Object[]{activityTemplateKey},
                pagingInfo
        );
    }

    @Override
    public DispActivityTemplateDataInfo getDisp(LongIdKey key, StringIdKey inspectAccountKey)
            throws ServiceException {
        ActivityTemplateDataInfo activityTemplateDataInfo = get(key);
        return toDisp(activityTemplateDataInfo, inspectAccountKey);
    }

    @Override
    public PagedData<DispActivityTemplateDataInfo> allDisp(StringIdKey inspectAccountKey, PagingInfo pagingInfo)
            throws ServiceException {
        PagedData<ActivityTemplateDataInfo> lookup = all(pagingInfo);
        return toDispPagedData(lookup, inspectAccountKey);
    }

    @Override
    public PagedData<DispActivityTemplateDataInfo> childForActivityTemplateDisp(
            StringIdKey inspectAccountKey, LongIdKey activityTemplateKey, PagingInfo pagingInfo
    ) throws ServiceException {
        PagedData<ActivityTemplateDataInfo> lookup = childForActivityTemplate(activityTemplateKey, pagingInfo);
        return toDispPagedData(lookup, inspectAccountKey);
    }

    private DispActivityTemplateDataInfo toDisp(
            ActivityTemplateDataInfo activityTemplateDataInfo, StringIdKey inspectAccountKey
    ) throws ServiceException {
        DispActivityTemplate activityTemplate = null;
        if (Objects.nonNull(activityTemplateDataInfo)) {
            activityTemplate = activityTemplateResponseService.getDisp(
                    activityTemplateDataInfo.getActivityTemplateKey(), inspectAccountKey
            );
        }

        DispActivityDataItem activityDataItem = null;
        if (Objects.nonNull(activityTemplateDataInfo)) {
            activityDataItem = activityDataItemResponseService.getDisp(
                    activityTemplateDataInfo.getActivityDataItemKey(), inspectAccountKey
            );
        }

        return DispActivityTemplateDataInfo.of(activityTemplateDataInfo, activityTemplate, activityDataItem);
    }

    private PagedData<DispActivityTemplateDataInfo> toDispPagedData(
            PagedData<ActivityTemplateDataInfo> lookup, StringIdKey inspectAccountKey
    ) throws ServiceException {
        List<DispActivityTemplateDataInfo> dispActivityTemplateDataInfos = new ArrayList<>();
        for (ActivityTemplateDataInfo activityTemplateDataInfo : lookup.getData()) {
            dispActivityTemplateDataInfos.add(toDisp(activityTemplateDataInfo, inspectAccountKey));
        }
        return new PagedData<>(
                lookup.getCurrentPage(), lookup.getTotalPages(), lookup.getRows(), lookup.getCount(),
                dispActivityTemplateDataInfos
        );
    }

    @Override
    public LongIdKey create(StringIdKey userKey, ActivityTemplateDataInfoCreateInfo createInfo)
            throws ServiceException {
        return activityTemplateDataInfoOperateService.create(userKey, createInfo);
    }

    @Override
    public void update(StringIdKey userKey, ActivityTemplateDataInfoUpdateInfo updateInfo) throws ServiceException {
        activityTemplateDataInfoOperateService.update(userKey, updateInfo);
    }

    @Override
    public void remove(StringIdKey userKey, LongIdKey key) throws ServiceException {
        activityTemplateDataInfoOperateService.remove(userKey, key);
    }
}
