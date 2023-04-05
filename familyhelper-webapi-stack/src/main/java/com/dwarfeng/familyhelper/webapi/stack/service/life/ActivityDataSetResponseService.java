package com.dwarfeng.familyhelper.webapi.stack.service.life;

import com.dwarfeng.familyhelper.life.stack.bean.dto.ActivityDataSetCreateInfo;
import com.dwarfeng.familyhelper.life.stack.bean.dto.ActivityDataSetPermissionRemoveInfo;
import com.dwarfeng.familyhelper.life.stack.bean.dto.ActivityDataSetPermissionUpsertInfo;
import com.dwarfeng.familyhelper.life.stack.bean.dto.ActivityDataSetUpdateInfo;
import com.dwarfeng.familyhelper.life.stack.bean.entity.ActivityDataSet;
import com.dwarfeng.familyhelper.webapi.stack.bean.disp.life.DispActivityDataSet;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.service.Service;

/**
 * 活动数据集合响应服务。
 *
 * @author DwArFeng
 * @since 1.0.10
 */
public interface ActivityDataSetResponseService extends Service {

    boolean exists(LongIdKey key) throws ServiceException;

    ActivityDataSet get(LongIdKey key) throws ServiceException;

    PagedData<ActivityDataSet> all(PagingInfo pagingInfo) throws ServiceException;

    DispActivityDataSet getDisp(LongIdKey key, StringIdKey inspectAccountKey) throws ServiceException;

    PagedData<DispActivityDataSet> allPermittedDisp(StringIdKey accountKey, PagingInfo pagingInfo)
            throws ServiceException;

    PagedData<DispActivityDataSet> allOwnedDisp(StringIdKey accountKey, PagingInfo pagingInfo) throws ServiceException;

    LongIdKey createActivityDataSet(StringIdKey userKey, ActivityDataSetCreateInfo activityDataSetCreateInfo)
            throws ServiceException;

    void updateActivityDataSet(StringIdKey userKey, ActivityDataSetUpdateInfo activityDataSetUpdateInfo)
            throws ServiceException;

    void removeActivityDataSet(StringIdKey userKey, LongIdKey activityDataSetKey) throws ServiceException;

    void upsertPermission(StringIdKey userKey, ActivityDataSetPermissionUpsertInfo permissionUpsertInfo)
            throws ServiceException;

    void removePermission(StringIdKey userKey, ActivityDataSetPermissionRemoveInfo permissionRemoveInfo)
            throws ServiceException;
}
