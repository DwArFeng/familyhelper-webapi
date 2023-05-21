package com.dwarfeng.familyhelper.webapi.stack.service.life;

import com.dwarfeng.familyhelper.life.stack.bean.dto.ActivityDataItemCreateInfo;
import com.dwarfeng.familyhelper.life.stack.bean.dto.ActivityDataItemUpdateInfo;
import com.dwarfeng.familyhelper.life.stack.bean.entity.ActivityDataItem;
import com.dwarfeng.familyhelper.webapi.stack.bean.disp.life.DispActivityDataItem;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.service.Service;

/**
 * 活动数据项目响应服务。
 *
 * @author DwArFeng
 * @since 1.0.10
 */
public interface ActivityDataItemResponseService extends Service {

    boolean exists(LongIdKey key) throws ServiceException;

    ActivityDataItem get(LongIdKey key) throws ServiceException;

    PagedData<ActivityDataItem> all(PagingInfo pagingInfo) throws ServiceException;

    PagedData<ActivityDataItem> childForActivityDataNode(LongIdKey activityDataNodeKey, PagingInfo pagingInfo)
            throws ServiceException;

    PagedData<ActivityDataItem> childForActivityDataSetRoot(LongIdKey activityDataSetKey, PagingInfo pagingInfo)
            throws ServiceException;

    PagedData<ActivityDataItem> childForActivityDataSetNameLike(
            LongIdKey activityDataSetKey, String pattern, PagingInfo pagingInfo
    ) throws ServiceException;

    DispActivityDataItem getDisp(LongIdKey key, StringIdKey inspectAccountKey) throws ServiceException;

    PagedData<DispActivityDataItem> allDisp(StringIdKey accountKey, PagingInfo pagingInfo) throws ServiceException;

    PagedData<DispActivityDataItem> childForActivityDataNodeDisp(
            StringIdKey accountKey, LongIdKey activityDataNodeKey, PagingInfo pagingInfo
    ) throws ServiceException;

    PagedData<DispActivityDataItem> childForActivityDataSetRootDisp(
            StringIdKey accountKey, LongIdKey activityDataSetKey, PagingInfo pagingInfo
    ) throws ServiceException;

    PagedData<DispActivityDataItem> childForActivityDataSetNameLikeDisp(
            StringIdKey accountKey, LongIdKey activityDataSetKey, String pattern, PagingInfo pagingInfo
    ) throws ServiceException;

    LongIdKey createActivityDataItem(StringIdKey userKey, ActivityDataItemCreateInfo activityDataItemCreateInfo)
            throws ServiceException;

    void updateActivityDataItem(StringIdKey userKey, ActivityDataItemUpdateInfo activityDataItemUpdateInfo)
            throws ServiceException;

    void removeActivityDataItem(StringIdKey userKey, LongIdKey activityDataItemKey) throws ServiceException;
}
