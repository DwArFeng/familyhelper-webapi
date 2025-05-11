package com.dwarfeng.familyhelper.webapi.stack.service.life;

import com.dwarfeng.familyhelper.life.stack.bean.dto.ActivityCreateInfo;
import com.dwarfeng.familyhelper.life.stack.bean.dto.ActivityPermissionRemoveInfo;
import com.dwarfeng.familyhelper.life.stack.bean.dto.ActivityPermissionUpsertInfo;
import com.dwarfeng.familyhelper.life.stack.bean.dto.ActivityUpdateInfo;
import com.dwarfeng.familyhelper.life.stack.bean.entity.Activity;
import com.dwarfeng.familyhelper.webapi.stack.bean.life.disp.DispActivity;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.service.Service;

/**
 * 活动响应服务。
 *
 * @author DwArFeng
 * @since 1.0.10
 */
public interface ActivityResponseService extends Service {

    boolean exists(LongIdKey key) throws ServiceException;

    Activity get(LongIdKey key) throws ServiceException;

    PagedData<Activity> all(PagingInfo pagingInfo) throws ServiceException;

    DispActivity getDisp(LongIdKey key, StringIdKey inspectAccountKey) throws ServiceException;

    PagedData<DispActivity> allPermittedDisp(StringIdKey accountKey, PagingInfo pagingInfo)
            throws ServiceException;

    PagedData<DispActivity> allOwnedDisp(StringIdKey accountKey, PagingInfo pagingInfo) throws ServiceException;

    LongIdKey createActivity(StringIdKey userKey, ActivityCreateInfo activityCreateInfo)
            throws ServiceException;

    void updateActivity(StringIdKey userKey, ActivityUpdateInfo activityUpdateInfo)
            throws ServiceException;

    void removeActivity(StringIdKey userKey, LongIdKey activityKey) throws ServiceException;

    void upsertPermission(StringIdKey userKey, ActivityPermissionUpsertInfo permissionUpsertInfo)
            throws ServiceException;

    void removePermission(StringIdKey userKey, ActivityPermissionRemoveInfo permissionRemoveInfo)
            throws ServiceException;

    void lockActivity(StringIdKey userKey, LongIdKey activityKey) throws ServiceException;
}
