package com.dwarfeng.familyhelper.webapi.impl.service.life;

import com.dwarfeng.familyhelper.life.stack.bean.dto.ActivityDataRecordCreateInfo;
import com.dwarfeng.familyhelper.life.stack.bean.dto.ActivityDataRecordUpdateInfo;
import com.dwarfeng.familyhelper.life.stack.bean.entity.ActivityDataRecord;
import com.dwarfeng.familyhelper.life.stack.service.ActivityDataRecordMaintainService;
import com.dwarfeng.familyhelper.life.stack.service.ActivityDataRecordOperateService;
import com.dwarfeng.familyhelper.webapi.stack.bean.disp.life.DispActivity;
import com.dwarfeng.familyhelper.webapi.stack.bean.disp.life.DispActivityDataItem;
import com.dwarfeng.familyhelper.webapi.stack.bean.disp.life.DispActivityDataRecord;
import com.dwarfeng.familyhelper.webapi.stack.service.life.ActivityDataItemResponseService;
import com.dwarfeng.familyhelper.webapi.stack.service.life.ActivityDataRecordResponseService;
import com.dwarfeng.familyhelper.webapi.stack.service.life.ActivityResponseService;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
public class ActivityDataRecordResponseServiceImpl implements ActivityDataRecordResponseService {

    private final ActivityDataRecordMaintainService activityDataRecordMaintainService;
    private final ActivityDataRecordOperateService activityDataRecordOperateService;

    private final ActivityDataItemResponseService activityDataItemResponseService;
    private final ActivityResponseService activityResponseService;

    public ActivityDataRecordResponseServiceImpl(
            @Qualifier("familyhelperLifeActivityDataRecordMaintainService")
            ActivityDataRecordMaintainService activityDataRecordMaintainService,
            @Qualifier("familyhelperLifeActivityDataRecordOperateService")
            ActivityDataRecordOperateService activityDataRecordOperateService,
            ActivityDataItemResponseService activityDataItemResponseService,
            ActivityResponseService activityResponseService
    ) {
        this.activityDataRecordMaintainService = activityDataRecordMaintainService;
        this.activityDataRecordOperateService = activityDataRecordOperateService;
        this.activityDataItemResponseService = activityDataItemResponseService;
        this.activityResponseService = activityResponseService;
    }

    @Override
    public boolean exists(LongIdKey key) throws ServiceException {
        return activityDataRecordMaintainService.exists(key);
    }

    @Override
    public ActivityDataRecord get(LongIdKey key) throws ServiceException {
        return activityDataRecordMaintainService.get(key);
    }

    @Override
    public PagedData<ActivityDataRecord> all(PagingInfo pagingInfo) throws ServiceException {
        return activityDataRecordMaintainService.lookup(pagingInfo);
    }

    @Override
    public PagedData<ActivityDataRecord> childForItemRecordedDateAsc(
            LongIdKey activityDataItemKey, PagingInfo pagingInfo
    ) throws ServiceException {
        return activityDataRecordMaintainService.lookup(
                ActivityDataRecordMaintainService.CHILD_FOR_ITEM_RECORDED_DATE_ASC,
                new Object[]{activityDataItemKey},
                pagingInfo
        );
    }

    @Override
    public PagedData<ActivityDataRecord> childForItemRecordedDateDesc(
            LongIdKey activityDataItemKey, PagingInfo pagingInfo
    ) throws ServiceException {
        return activityDataRecordMaintainService.lookup(
                ActivityDataRecordMaintainService.CHILD_FOR_ITEM_RECORDED_DATE_DESC,
                new Object[]{activityDataItemKey},
                pagingInfo
        );
    }

    @Override
    public PagedData<ActivityDataRecord> childForActivityRecordedDateAsc(LongIdKey activityKey, PagingInfo pagingInfo)
            throws ServiceException {
        return activityDataRecordMaintainService.lookup(
                ActivityDataRecordMaintainService.CHILD_FOR_ACTIVITY_RECORDED_DATE_ASC,
                new Object[]{activityKey},
                pagingInfo
        );
    }

    @Override
    public PagedData<ActivityDataRecord> childForActivityRecordedDateDesc(LongIdKey activityKey, PagingInfo pagingInfo)
            throws ServiceException {
        return activityDataRecordMaintainService.lookup(
                ActivityDataRecordMaintainService.CHILD_FOR_ACTIVITY_RECORDED_DATE_DESC,
                new Object[]{activityKey},
                pagingInfo
        );
    }

    @Override
    public DispActivityDataRecord getDisp(LongIdKey key, StringIdKey inspectAccountKey) throws ServiceException {
        ActivityDataRecord activityDataRecord = get(key);
        return toDisp(activityDataRecord, inspectAccountKey);
    }

    @Override
    public PagedData<DispActivityDataRecord> allDisp(StringIdKey accountKey, PagingInfo pagingInfo)
            throws ServiceException {
        PagedData<ActivityDataRecord> lookup = all(pagingInfo);
        return toDispPagedData(lookup, accountKey);
    }

    @Override
    public PagedData<DispActivityDataRecord> childForItemRecordedDateAscDisp(
            StringIdKey accountKey, LongIdKey activityDataItemKey, PagingInfo pagingInfo
    ) throws ServiceException {
        PagedData<ActivityDataRecord> lookup = childForItemRecordedDateAsc(activityDataItemKey, pagingInfo);
        return toDispPagedData(lookup, accountKey);
    }

    @Override
    public PagedData<DispActivityDataRecord> childForItemRecordedDateDescDisp(
            StringIdKey accountKey, LongIdKey activityDataItemKey, PagingInfo pagingInfo
    ) throws ServiceException {
        PagedData<ActivityDataRecord> lookup = childForItemRecordedDateDesc(activityDataItemKey, pagingInfo);
        return toDispPagedData(lookup, accountKey);
    }

    @Override
    public PagedData<DispActivityDataRecord> childForActivityRecordedDateAscDisp(
            StringIdKey accountKey, LongIdKey activityKey, PagingInfo pagingInfo
    ) throws ServiceException {
        PagedData<ActivityDataRecord> lookup = childForActivityRecordedDateAsc(activityKey, pagingInfo);
        return toDispPagedData(lookup, accountKey);
    }

    @Override
    public PagedData<DispActivityDataRecord> childForActivityRecordedDateDescDisp(
            StringIdKey accountKey, LongIdKey activityKey, PagingInfo pagingInfo
    ) throws ServiceException {
        PagedData<ActivityDataRecord> lookup = childForActivityRecordedDateDesc(activityKey, pagingInfo);
        return toDispPagedData(lookup, accountKey);
    }

    private DispActivityDataRecord toDisp(ActivityDataRecord activityDataRecord, StringIdKey inspectAccountKey)
            throws ServiceException {
        DispActivityDataItem item = null;
        if (Objects.nonNull(activityDataRecord.getItemKey())) {
            item = activityDataItemResponseService.getDisp(activityDataRecord.getItemKey(), inspectAccountKey);
        }
        DispActivity activity = null;
        if (Objects.nonNull(activityDataRecord.getActivityKey())) {
            activity = activityResponseService.getDisp(activityDataRecord.getActivityKey(), inspectAccountKey);
        }
        return DispActivityDataRecord.of(activityDataRecord, item, activity);
    }

    private PagedData<DispActivityDataRecord> toDispPagedData(
            PagedData<ActivityDataRecord> lookup, StringIdKey inspectAccountKey
    ) throws ServiceException {
        List<DispActivityDataRecord> dispActivityDataRecords = new ArrayList<>();
        for (ActivityDataRecord activityDataRecord : lookup.getData()) {
            dispActivityDataRecords.add(toDisp(activityDataRecord, inspectAccountKey));
        }
        return new PagedData<>(
                lookup.getCurrentPage(), lookup.getTotalPages(), lookup.getRows(), lookup.getCount(),
                dispActivityDataRecords
        );
    }

    @Override
    public LongIdKey create(StringIdKey userKey, ActivityDataRecordCreateInfo createInfo) throws ServiceException {
        return activityDataRecordOperateService.create(userKey, createInfo);
    }

    @Override
    public void update(StringIdKey userKey, ActivityDataRecordUpdateInfo updateInfo) throws ServiceException {
        activityDataRecordOperateService.update(userKey, updateInfo);
    }

    @Override
    public void remove(StringIdKey userKey, LongIdKey key) throws ServiceException {
        activityDataRecordOperateService.remove(userKey, key);
    }
}
