package com.dwarfeng.familyhelper.webapi.stack.service.life;

import com.dwarfeng.familyhelper.life.stack.bean.dto.ActivityDataNodeCreateInfo;
import com.dwarfeng.familyhelper.life.stack.bean.dto.ActivityDataNodeUpdateInfo;
import com.dwarfeng.familyhelper.life.stack.bean.entity.ActivityDataNode;
import com.dwarfeng.familyhelper.webapi.stack.bean.life.disp.DispActivityDataNode;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.service.Service;

/**
 * 活动数据节点响应服务。
 *
 * @author DwArFeng
 * @since 1.0.10
 */
public interface ActivityDataNodeResponseService extends Service {

    boolean exists(LongIdKey key) throws ServiceException;

    ActivityDataNode get(LongIdKey key) throws ServiceException;

    PagedData<ActivityDataNode> all(PagingInfo pagingInfo) throws ServiceException;

    PagedData<ActivityDataNode> childForActivityDataSet(LongIdKey activityDataSetKey, PagingInfo pagingInfo)
            throws ServiceException;

    PagedData<ActivityDataNode> childForActivityDataSetRoot(LongIdKey activityDataSetKey, PagingInfo pagingInfo)
            throws ServiceException;

    PagedData<ActivityDataNode> childForParent(LongIdKey parentKey, PagingInfo pagingInfo) throws ServiceException;

    PagedData<ActivityDataNode> childForActivityDataSetNameLike(
            LongIdKey activityDataSetKey, String pattern, PagingInfo pagingInfo
    ) throws ServiceException;

    DispActivityDataNode getDisp(LongIdKey key, StringIdKey inspectAccountKey) throws ServiceException;

    PagedData<DispActivityDataNode> allDisp(StringIdKey accountKey, PagingInfo pagingInfo) throws ServiceException;

    PagedData<DispActivityDataNode> childForActivityDataSetDisp(
            StringIdKey accountKey, LongIdKey activityDataSetKey, PagingInfo pagingInfo
    ) throws ServiceException;

    PagedData<DispActivityDataNode> childForActivityDataSetRootDisp(
            StringIdKey accountKey, LongIdKey activityDataSetKey, PagingInfo pagingInfo
    ) throws ServiceException;

    PagedData<DispActivityDataNode> childForParentDisp(
            StringIdKey accountKey, LongIdKey parentKey, PagingInfo pagingInfo
    ) throws ServiceException;

    PagedData<DispActivityDataNode> childForActivityDataSetNameLikeDisp(
            StringIdKey accountKey, LongIdKey activityDataSetKey, String pattern, PagingInfo pagingInfo
    ) throws ServiceException;

    PagedData<ActivityDataNode> nodePathFromRoot(LongIdKey key) throws ServiceException;

    PagedData<DispActivityDataNode> nodePathFromRootDisp(StringIdKey accountKey, LongIdKey key)
            throws ServiceException;

    PagedData<ActivityDataNode> itemPathFromRoot(LongIdKey itemKey) throws ServiceException;

    PagedData<DispActivityDataNode> itemPathFromRootDisp(StringIdKey accountKey, LongIdKey itemKey)
            throws ServiceException;

    LongIdKey createActivityDataNode(StringIdKey userKey, ActivityDataNodeCreateInfo activityDataNodeCreateInfo)
            throws ServiceException;

    void updateActivityDataNode(StringIdKey userKey, ActivityDataNodeUpdateInfo activityDataNodeUpdateInfo)
            throws ServiceException;

    void removeActivityDataNode(StringIdKey userKey, LongIdKey activityDataNodeKey) throws ServiceException;
}
