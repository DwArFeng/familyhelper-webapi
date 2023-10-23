package com.dwarfeng.familyhelper.webapi.stack.service.life;

import com.dwarfeng.familyhelper.life.stack.bean.dto.ActivityTemplateParticipantCreateInfo;
import com.dwarfeng.familyhelper.life.stack.bean.dto.ActivityTemplateParticipantRemoveInfo;
import com.dwarfeng.familyhelper.life.stack.bean.dto.ActivityTemplateParticipantUpdateInfo;
import com.dwarfeng.familyhelper.life.stack.bean.entity.ActivityTemplateParticipant;
import com.dwarfeng.familyhelper.life.stack.bean.key.ActivityTemplateParticipantKey;
import com.dwarfeng.familyhelper.webapi.stack.bean.disp.life.DispActivityTemplateParticipant;
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
public interface ActivityTemplateParticipantResponseService extends Service {

    boolean exists(ActivityTemplateParticipantKey key) throws ServiceException;

    ActivityTemplateParticipant get(ActivityTemplateParticipantKey key) throws ServiceException;

    PagedData<ActivityTemplateParticipant> all(PagingInfo pagingInfo) throws ServiceException;

    PagedData<ActivityTemplateParticipant> childForActivityTemplate(
            LongIdKey activityTemplateKey, PagingInfo pagingInfo
    ) throws ServiceException;

    DispActivityTemplateParticipant getDisp(ActivityTemplateParticipantKey key, StringIdKey inspectAccountKey)
            throws ServiceException;

    PagedData<DispActivityTemplateParticipant> allDisp(StringIdKey inspectAccountKey, PagingInfo pagingInfo)
            throws ServiceException;

    PagedData<DispActivityTemplateParticipant> childForActivityTemplateDisp(
            StringIdKey inspectAccountKey, LongIdKey activityTemplateKey, PagingInfo pagingInfo
    ) throws ServiceException;

    ActivityTemplateParticipantKey create(StringIdKey userKey, ActivityTemplateParticipantCreateInfo createInfo)
            throws ServiceException;

    void update(StringIdKey userKey, ActivityTemplateParticipantUpdateInfo updateInfo) throws ServiceException;

    void remove(StringIdKey userKey, ActivityTemplateParticipantRemoveInfo removeInfo) throws ServiceException;
}
