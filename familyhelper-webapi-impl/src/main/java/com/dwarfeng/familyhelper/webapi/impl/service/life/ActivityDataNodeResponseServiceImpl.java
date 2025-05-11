package com.dwarfeng.familyhelper.webapi.impl.service.life;

import com.dwarfeng.familyhelper.life.stack.bean.dto.ActivityDataNodeCreateInfo;
import com.dwarfeng.familyhelper.life.stack.bean.dto.ActivityDataNodeUpdateInfo;
import com.dwarfeng.familyhelper.life.stack.bean.entity.ActivityDataItem;
import com.dwarfeng.familyhelper.life.stack.bean.entity.ActivityDataNode;
import com.dwarfeng.familyhelper.life.stack.service.ActivityDataItemMaintainService;
import com.dwarfeng.familyhelper.life.stack.service.ActivityDataNodeMaintainService;
import com.dwarfeng.familyhelper.life.stack.service.ActivityDataNodeOperateService;
import com.dwarfeng.familyhelper.webapi.stack.bean.life.disp.DispActivityDataNode;
import com.dwarfeng.familyhelper.webapi.stack.bean.life.disp.DispActivityDataSet;
import com.dwarfeng.familyhelper.webapi.stack.service.life.ActivityDataNodeResponseService;
import com.dwarfeng.familyhelper.webapi.stack.service.life.ActivityDataSetResponseService;
import com.dwarfeng.subgrade.sdk.bean.dto.PagingUtil;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ActivityDataNodeResponseServiceImpl implements ActivityDataNodeResponseService {

    private final ActivityDataNodeMaintainService activityDataNodeMaintainService;
    private final ActivityDataNodeOperateService activityDataNodeOperateService;
    private final ActivityDataItemMaintainService activityDataItemMaintainService;

    private final ActivityDataSetResponseService activityDataSetResponseService;

    public ActivityDataNodeResponseServiceImpl(
            @Qualifier("familyhelperLifeActivityDataNodeMaintainService")
            ActivityDataNodeMaintainService activityDataNodeMaintainService,
            @Qualifier("familyhelperLifeActivityDataNodeOperateService")
            ActivityDataNodeOperateService activityDataNodeOperateService,
            @Qualifier("familyhelperLifeActivityDataItemMaintainService")
            ActivityDataItemMaintainService activityDataItemMaintainService,
            ActivityDataSetResponseService activityDataSetResponseService
    ) {
        this.activityDataNodeMaintainService = activityDataNodeMaintainService;
        this.activityDataNodeOperateService = activityDataNodeOperateService;
        this.activityDataItemMaintainService = activityDataItemMaintainService;
        this.activityDataSetResponseService = activityDataSetResponseService;
    }

    @Override
    public boolean exists(LongIdKey key) throws ServiceException {
        return activityDataNodeMaintainService.exists(key);
    }

    @Override
    public ActivityDataNode get(LongIdKey key) throws ServiceException {
        return activityDataNodeMaintainService.get(key);
    }

    @Override
    public PagedData<ActivityDataNode> all(PagingInfo pagingInfo) throws ServiceException {
        return activityDataNodeMaintainService.lookup(pagingInfo);
    }

    @Override
    public PagedData<ActivityDataNode> childForActivityDataSet(LongIdKey activityDataSetKey, PagingInfo pagingInfo)
            throws ServiceException {
        return activityDataNodeMaintainService.lookup(
                ActivityDataNodeMaintainService.CHILD_FOR_SET, new Object[]{activityDataSetKey}, pagingInfo
        );
    }

    @Override
    public PagedData<ActivityDataNode> childForActivityDataSetRoot(LongIdKey activityDataSetKey, PagingInfo pagingInfo)
            throws ServiceException {
        return activityDataNodeMaintainService.lookup(
                ActivityDataNodeMaintainService.CHILD_FOR_SET_ROOT, new Object[]{activityDataSetKey}, pagingInfo
        );
    }

    @Override
    public PagedData<ActivityDataNode> childForParent(LongIdKey parentKey, PagingInfo pagingInfo)
            throws ServiceException {
        return activityDataNodeMaintainService.lookup(
                ActivityDataNodeMaintainService.CHILD_FOR_PARENT, new Object[]{parentKey}, pagingInfo
        );
    }

    @Override
    public PagedData<ActivityDataNode> childForActivityDataSetNameLike(
            LongIdKey activityDataSetKey, String pattern, PagingInfo pagingInfo
    ) throws ServiceException {
        return activityDataNodeMaintainService.lookup(
                ActivityDataNodeMaintainService.CHILD_FOR_SET_NAME_LIKE,
                new Object[]{activityDataSetKey, pattern},
                pagingInfo
        );
    }

    @Override
    public DispActivityDataNode getDisp(LongIdKey key, StringIdKey inspectAccountKey) throws ServiceException {
        ActivityDataNode activityDataNode = activityDataNodeMaintainService.get(key);
        return toDisp(activityDataNode, inspectAccountKey);
    }

    @Override
    public PagedData<DispActivityDataNode> allDisp(StringIdKey accountKey, PagingInfo pagingInfo)
            throws ServiceException {
        PagedData<ActivityDataNode> lookup = all(pagingInfo);
        return toDispPagedData(lookup, accountKey);
    }

    @Override
    public PagedData<DispActivityDataNode> childForActivityDataSetDisp(
            StringIdKey accountKey, LongIdKey activityDataSetKey, PagingInfo pagingInfo
    ) throws ServiceException {
        PagedData<ActivityDataNode> lookup = childForActivityDataSet(activityDataSetKey, pagingInfo);
        return toDispPagedData(lookup, accountKey);
    }

    @Override
    public PagedData<DispActivityDataNode> childForActivityDataSetRootDisp(
            StringIdKey accountKey, LongIdKey activityDataSetKey, PagingInfo pagingInfo
    ) throws ServiceException {
        PagedData<ActivityDataNode> lookup = childForActivityDataSetRoot(activityDataSetKey, pagingInfo);
        return toDispPagedData(lookup, accountKey);
    }

    @Override
    public PagedData<DispActivityDataNode> childForParentDisp(
            StringIdKey accountKey, LongIdKey parentKey, PagingInfo pagingInfo
    ) throws ServiceException {
        PagedData<ActivityDataNode> lookup = childForParent(parentKey, pagingInfo);
        return toDispPagedData(lookup, accountKey);
    }

    @Override
    public PagedData<DispActivityDataNode> childForActivityDataSetNameLikeDisp(
            StringIdKey accountKey, LongIdKey activityDataSetKey, String pattern, PagingInfo pagingInfo
    ) throws ServiceException {
        PagedData<ActivityDataNode> lookup = childForActivityDataSetNameLike(activityDataSetKey, pattern, pagingInfo);
        return toDispPagedData(lookup, accountKey);
    }

    @Override
    public PagedData<ActivityDataNode> nodePathFromRoot(LongIdKey key) throws ServiceException {
        // 获取当前的笔记节点作为锚点。
        ActivityDataNode anchor = activityDataNodeMaintainService.get(key);

        // 定义结果列表。
        List<ActivityDataNode> result = new ArrayList<>();

        // 如果锚点的父键不为 null，则循环。
        while (Objects.nonNull(anchor.getParentKey())) {
            // 获取锚点的父键对应的权限组。
            anchor = activityDataNodeMaintainService.get(anchor.getParentKey());
            // 将锚点加入结果列表。
            result.add(anchor);
        }

        // 将结果列表反转。
        Collections.reverse(result);

        // 返回结果。
        return PagingUtil.pagedData(result);
    }

    @Override
    public PagedData<DispActivityDataNode> nodePathFromRootDisp(StringIdKey accountKey, LongIdKey key)
            throws ServiceException {
        PagedData<ActivityDataNode> pathFromRoot = nodePathFromRoot(key);
        return toDispPagedData(pathFromRoot, accountKey);
    }

    @Override
    public PagedData<ActivityDataNode> itemPathFromRoot(LongIdKey itemKey) throws ServiceException {
        // 获取当前的笔记项目。
        ActivityDataItem activityDataItem = activityDataItemMaintainService.get(itemKey);

        // 如果项目的节点键为 null，则返回空的分页数据。
        if (Objects.isNull(activityDataItem.getNodeKey())) {
            return PagingUtil.pagedData(Collections.emptyList());
        }

        // 获取当前的个人最佳节点作为锚点。
        ActivityDataNode anchor = activityDataNodeMaintainService.get(activityDataItem.getNodeKey());

        // 定义结果列表。
        List<ActivityDataNode> result = new ArrayList<>();
        result.add(anchor);

        // 如果锚点的父键不为 null，则循环。
        while (Objects.nonNull(anchor.getParentKey())) {
            // 获取锚点的父键对应的权限组。
            anchor = activityDataNodeMaintainService.get(anchor.getParentKey());
            // 将锚点加入结果列表。
            result.add(anchor);
        }

        // 将结果列表反转。
        Collections.reverse(result);

        // 返回结果。
        return PagingUtil.pagedData(result);
    }

    @Override
    public PagedData<DispActivityDataNode> itemPathFromRootDisp(StringIdKey accountKey, LongIdKey itemKey)
            throws ServiceException {
        PagedData<ActivityDataNode> pathFromRoot = itemPathFromRoot(itemKey);
        return toDispPagedData(pathFromRoot, accountKey);
    }

    @SuppressWarnings("DuplicatedCode")
    private DispActivityDataNode toDisp(
            ActivityDataNode activityDataNode, StringIdKey inspectAccountKey
    ) throws ServiceException {
        DispActivityDataSet set = null;
        if (Objects.nonNull(activityDataNode.getSetKey())) {
            set = activityDataSetResponseService.getDisp(activityDataNode.getSetKey(), inspectAccountKey);
        }

        boolean hasNoChild = activityDataNodeMaintainService.lookup(
                ActivityDataNodeMaintainService.CHILD_FOR_PARENT, new Object[]{activityDataNode.getKey()},
                new PagingInfo(0, 1)
        ).getCount() <= 0;
        if (hasNoChild) {
            hasNoChild = activityDataItemMaintainService.lookup(
                    ActivityDataItemMaintainService.CHILD_FOR_NODE, new Object[]{activityDataNode.getKey()},
                    new PagingInfo(0, 1)
            ).getCount() <= 0;
        }

        return DispActivityDataNode.of(activityDataNode, set, hasNoChild);
    }

    @SuppressWarnings("DuplicatedCode")
    private DispActivityDataNode toDispWithCache(
            ActivityDataNode activityDataNode, StringIdKey inspectAccountKey,
            Map<LongIdKey, DispActivityDataSet> cachedActivityDataSetMap
    ) throws ServiceException {
        DispActivityDataSet set = toDispActivityDataSetWithCache(
                activityDataNode, inspectAccountKey, cachedActivityDataSetMap
        );
        boolean hasNoChild = activityDataNodeMaintainService.lookup(
                ActivityDataNodeMaintainService.CHILD_FOR_PARENT, new Object[]{activityDataNode.getKey()},
                new PagingInfo(0, 1)
        ).getCount() <= 0;
        if (hasNoChild) {
            hasNoChild = activityDataItemMaintainService.lookup(
                    ActivityDataItemMaintainService.CHILD_FOR_NODE, new Object[]{activityDataNode.getKey()},
                    new PagingInfo(0, 1)
            ).getCount() <= 0;
        }
        return DispActivityDataNode.of(activityDataNode, set, hasNoChild);
    }

    private DispActivityDataSet toDispActivityDataSetWithCache(
            ActivityDataNode activityDataNode, StringIdKey inspectAccountKey,
            Map<LongIdKey, DispActivityDataSet> cachedActivityDataSetMap
    ) throws ServiceException {
        LongIdKey setKey = activityDataNode.getSetKey();
        if (Objects.isNull(setKey)) {
            return null;
        }
        DispActivityDataSet dispActivityDataSet = cachedActivityDataSetMap.getOrDefault(setKey, null);
        if (Objects.isNull(dispActivityDataSet)) {
            dispActivityDataSet = activityDataSetResponseService.getDisp(setKey, inspectAccountKey);
            cachedActivityDataSetMap.put(setKey, dispActivityDataSet);
        }
        return dispActivityDataSet;
    }

    private PagedData<DispActivityDataNode> toDispPagedData(
            PagedData<ActivityDataNode> lookup, StringIdKey inspectAccountKey
    ) throws ServiceException {
        List<DispActivityDataNode> dispActivityDataNodes = new ArrayList<>();
        Map<LongIdKey, DispActivityDataSet> cachedActivityDataSetMap = new HashMap<>();
        for (ActivityDataNode activityDataNode : lookup.getData()) {
            dispActivityDataNodes.add(toDispWithCache(
                    activityDataNode, inspectAccountKey, cachedActivityDataSetMap
            ));
        }
        return new PagedData<>(
                lookup.getCurrentPage(), lookup.getTotalPages(), lookup.getRows(), lookup.getCount(),
                dispActivityDataNodes
        );
    }

    @Override
    public LongIdKey createActivityDataNode(
            StringIdKey userKey, ActivityDataNodeCreateInfo activityDataNodeCreateInfo
    ) throws ServiceException {
        return activityDataNodeOperateService.createActivityDataNode(userKey, activityDataNodeCreateInfo);
    }

    @Override
    public void updateActivityDataNode(StringIdKey userKey, ActivityDataNodeUpdateInfo activityDataNodeUpdateInfo)
            throws ServiceException {
        activityDataNodeOperateService.updateActivityDataNode(userKey, activityDataNodeUpdateInfo);
    }

    @Override
    public void removeActivityDataNode(StringIdKey userKey, LongIdKey activityDataNodeKey) throws ServiceException {
        activityDataNodeOperateService.removeActivityDataNode(userKey, activityDataNodeKey);
    }
}
