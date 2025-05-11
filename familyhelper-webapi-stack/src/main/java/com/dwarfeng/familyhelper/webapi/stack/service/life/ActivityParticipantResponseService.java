package com.dwarfeng.familyhelper.webapi.stack.service.life;

import com.dwarfeng.familyhelper.life.stack.bean.dto.ActivityParticipantCreateInfo;
import com.dwarfeng.familyhelper.life.stack.bean.dto.ActivityParticipantRemoveInfo;
import com.dwarfeng.familyhelper.life.stack.bean.dto.ActivityParticipantUpdateInfo;
import com.dwarfeng.familyhelper.life.stack.bean.entity.ActivityParticipant;
import com.dwarfeng.familyhelper.life.stack.bean.key.ActivityParticipantKey;
import com.dwarfeng.familyhelper.webapi.stack.bean.life.disp.DispActivityParticipant;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.service.Service;

/**
 * 活动参与者响应服务。
 *
 * @author DwArFeng
 * @since 1.0.10
 */
public interface ActivityParticipantResponseService extends Service {

    boolean exists(ActivityParticipantKey key) throws ServiceException;

    ActivityParticipant get(ActivityParticipantKey key) throws ServiceException;

    PagedData<ActivityParticipant> all(PagingInfo pagingInfo) throws ServiceException;

    PagedData<ActivityParticipant> childForActivity(
            LongIdKey activityKey, PagingInfo pagingInfo
    ) throws ServiceException;

    DispActivityParticipant getDisp(ActivityParticipantKey key, StringIdKey inspectAccountKey) throws ServiceException;

    PagedData<DispActivityParticipant> allDisp(StringIdKey inspectAccountKey, PagingInfo pagingInfo)
            throws ServiceException;

    PagedData<DispActivityParticipant> childForActivityDisp(
            StringIdKey inspectAccountKey, LongIdKey activityKey, PagingInfo pagingInfo
    ) throws ServiceException;

    ActivityParticipantKey create(StringIdKey userKey, ActivityParticipantCreateInfo createInfo)
            throws ServiceException;

    void update(StringIdKey userKey, ActivityParticipantUpdateInfo updateInfo) throws ServiceException;

    void remove(StringIdKey userKey, ActivityParticipantRemoveInfo removeInfo) throws ServiceException;
}
