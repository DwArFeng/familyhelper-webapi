package com.dwarfeng.familyhelper.webapi.impl.service.life;

import com.dwarfeng.familyhelper.life.stack.bean.dto.ActivityDataItemCreateInfo;
import com.dwarfeng.familyhelper.life.stack.bean.dto.ActivityDataItemUpdateInfo;
import com.dwarfeng.familyhelper.life.stack.bean.entity.ActivityDataItem;
import com.dwarfeng.familyhelper.life.stack.service.ActivityDataItemMaintainService;
import com.dwarfeng.familyhelper.life.stack.service.ActivityDataItemOperateService;
import com.dwarfeng.familyhelper.webapi.stack.bean.disp.life.DispActivityDataItem;
import com.dwarfeng.familyhelper.webapi.stack.bean.disp.life.DispActivityDataNode;
import com.dwarfeng.familyhelper.webapi.stack.service.life.ActivityDataItemResponseService;
import com.dwarfeng.familyhelper.webapi.stack.service.life.ActivityDataNodeResponseService;
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
public class ActivityDataItemResponseServiceImpl implements ActivityDataItemResponseService {

    private final ActivityDataItemMaintainService activityDataItemMaintainService;
    private final ActivityDataItemOperateService activityDataItemOperateService;

    private final ActivityDataNodeResponseService activityDataNodeResponseService;

    public ActivityDataItemResponseServiceImpl(
            @Qualifier("familyhelperLifeActivityDataItemMaintainService")
            ActivityDataItemMaintainService activityDataItemMaintainService,
            @Qualifier("familyhelperLifeActivityDataItemOperateService")
            ActivityDataItemOperateService activityDataItemOperateService,
            ActivityDataNodeResponseService activityDataNodeResponseService
    ) {
        this.activityDataItemMaintainService = activityDataItemMaintainService;
        this.activityDataItemOperateService = activityDataItemOperateService;
        this.activityDataNodeResponseService = activityDataNodeResponseService;
    }

    @Override
    public boolean exists(LongIdKey key) throws ServiceException {
        return activityDataItemMaintainService.exists(key);
    }

    @Override
    public ActivityDataItem get(LongIdKey key) throws ServiceException {
        return activityDataItemMaintainService.get(key);
    }

    @Override
    public PagedData<ActivityDataItem> all(PagingInfo pagingInfo) throws ServiceException {
        return activityDataItemMaintainService.lookup(pagingInfo);
    }

    @Override
    public PagedData<ActivityDataItem> childForActivityDataNode(LongIdKey activityDataNodeKey, PagingInfo pagingInfo)
            throws ServiceException {
        return activityDataItemMaintainService.lookup(
                ActivityDataItemMaintainService.CHILD_FOR_NODE, new Object[]{activityDataNodeKey}, pagingInfo
        );
    }

    @Override
    public PagedData<ActivityDataItem> childForActivityDataSetRoot(LongIdKey activityDataSetKey, PagingInfo pagingInfo)
            throws ServiceException {
        return activityDataItemMaintainService.lookup(
                ActivityDataItemMaintainService.CHILD_FOR_SET_ROOT, new Object[]{activityDataSetKey}, pagingInfo
        );
    }

    @Override
    public PagedData<ActivityDataItem> childForActivityDataSetNameLike(
            LongIdKey activityDataSetKey, String pattern, PagingInfo pagingInfo
    ) throws ServiceException {
        return activityDataItemMaintainService.lookup(
                ActivityDataItemMaintainService.CHILD_FOR_SET_NAME_LIKE,
                new Object[]{activityDataSetKey, pattern},
                pagingInfo
        );
    }

    @Override
    public DispActivityDataItem getDisp(LongIdKey key, StringIdKey inspectAccountKey) throws ServiceException {
        ActivityDataItem activityDataItem = activityDataItemMaintainService.get(key);
        return toDisp(activityDataItem, inspectAccountKey);
    }

    @Override
    public PagedData<DispActivityDataItem> allDisp(StringIdKey accountKey, PagingInfo pagingInfo)
            throws ServiceException {
        PagedData<ActivityDataItem> lookup = all(pagingInfo);
        return toDispPagedData(lookup, accountKey);
    }

    @Override
    public PagedData<DispActivityDataItem> childForActivityDataNodeDisp(
            StringIdKey accountKey, LongIdKey activityDataNodeKey, PagingInfo pagingInfo
    ) throws ServiceException {
        PagedData<ActivityDataItem> lookup = childForActivityDataNode(activityDataNodeKey, pagingInfo);
        return toDispPagedData(lookup, accountKey);
    }

    @Override
    public PagedData<DispActivityDataItem> childForActivityDataSetRootDisp(
            StringIdKey accountKey, LongIdKey activityDataSetKey, PagingInfo pagingInfo
    ) throws ServiceException {
        PagedData<ActivityDataItem> lookup = childForActivityDataSetRoot(activityDataSetKey, pagingInfo);
        return toDispPagedData(lookup, accountKey);
    }

    @Override
    public PagedData<DispActivityDataItem> childForActivityDataSetNameLikeDisp(
            StringIdKey accountKey, LongIdKey activityDataSetKey, String pattern, PagingInfo pagingInfo
    ) throws ServiceException {
        PagedData<ActivityDataItem> lookup = childForActivityDataSetNameLike(activityDataSetKey, pattern, pagingInfo);
        return toDispPagedData(lookup, accountKey);
    }

    private DispActivityDataItem toDisp(ActivityDataItem activityDataItem, StringIdKey inspectAccountKey)
            throws ServiceException {
        DispActivityDataNode node = null;
        if (Objects.nonNull(activityDataItem.getNodeKey())) {
            node = activityDataNodeResponseService.getDisp(activityDataItem.getNodeKey(), inspectAccountKey);
        }

        return DispActivityDataItem.of(activityDataItem, node);
    }

    private PagedData<DispActivityDataItem> toDispPagedData(
            PagedData<ActivityDataItem> lookup, StringIdKey inspectAccountKey
    ) throws ServiceException {
        List<DispActivityDataItem> dispActivityDataItems = new ArrayList<>();
        for (ActivityDataItem activityDataItem : lookup.getData()) {
            dispActivityDataItems.add(toDisp(activityDataItem, inspectAccountKey));
        }
        return new PagedData<>(
                lookup.getCurrentPage(), lookup.getTotalPages(), lookup.getRows(), lookup.getCount(),
                dispActivityDataItems
        );
    }

    @Override
    public LongIdKey createActivityDataItem(
            StringIdKey userKey, ActivityDataItemCreateInfo activityDataItemCreateInfo
    ) throws ServiceException {
        return activityDataItemOperateService.createActivityDataItem(userKey, activityDataItemCreateInfo);
    }

    @Override
    public void updateActivityDataItem(StringIdKey userKey, ActivityDataItemUpdateInfo activityDataItemUpdateInfo)
            throws ServiceException {
        activityDataItemOperateService.updateActivityDataItem(userKey, activityDataItemUpdateInfo);
    }

    @Override
    public void removeActivityDataItem(StringIdKey userKey, LongIdKey activityDataItemKey) throws ServiceException {
        activityDataItemOperateService.removeActivityDataItem(userKey, activityDataItemKey);
    }
}
