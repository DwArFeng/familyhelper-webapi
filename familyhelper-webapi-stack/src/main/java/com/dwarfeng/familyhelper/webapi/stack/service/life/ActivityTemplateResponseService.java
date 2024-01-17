package com.dwarfeng.familyhelper.webapi.stack.service.life;

import com.dwarfeng.familyhelper.life.stack.bean.dto.*;
import com.dwarfeng.familyhelper.life.stack.bean.entity.ActivityTemplate;
import com.dwarfeng.familyhelper.webapi.stack.bean.disp.life.DispActivityTemplate;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.service.Service;

/**
 * 活动模板参与者响应服务。
 *
 * @author DwArFeng
 * @since 1.0.10
 */
public interface ActivityTemplateResponseService extends Service {

    boolean exists(LongIdKey key) throws ServiceException;

    ActivityTemplate get(LongIdKey key) throws ServiceException;

    PagedData<ActivityTemplate> all(PagingInfo pagingInfo) throws ServiceException;

    DispActivityTemplate getDisp(LongIdKey key, StringIdKey inspectAccountKey) throws ServiceException;

    PagedData<DispActivityTemplate> allPermittedDisp(StringIdKey accountKey, PagingInfo pagingInfo)
            throws ServiceException;

    PagedData<DispActivityTemplate> allOwnedDisp(StringIdKey accountKey, PagingInfo pagingInfo) throws ServiceException;

    LongIdKey createActivityTemplate(StringIdKey userKey, ActivityTemplateCreateInfo activityTemplateCreateInfo)
            throws ServiceException;

    void updateActivityTemplate(StringIdKey userKey, ActivityTemplateUpdateInfo activityTemplateUpdateInfo)
            throws ServiceException;

    void removeActivityTemplate(StringIdKey userKey, LongIdKey activityTemplateKey) throws ServiceException;

    void upsertPermission(StringIdKey userKey, ActivityTemplatePermissionUpsertInfo permissionUpsertInfo)
            throws ServiceException;

    void removePermission(StringIdKey userKey, ActivityTemplatePermissionRemoveInfo permissionRemoveInfo)
            throws ServiceException;

    void upsertActivityPermission(
            StringIdKey userKey, ActivityTemplateActivityPermissionUpsertInfo permissionUpsertInfo
    ) throws ServiceException;

    void removeActivityPermission(
            StringIdKey userKey, ActivityTemplateActivityPermissionRemoveInfo permissionRemoveInfo
    ) throws ServiceException;

    void createActivity(StringIdKey userKey, ActivityTemplateActivityCreateInfo activityCreateInfo)
            throws ServiceException;

    void createActivityForTest(StringIdKey userKey, ActivityTemplateActivityCreateInfo activityCreateInfo)
            throws ServiceException;
}
