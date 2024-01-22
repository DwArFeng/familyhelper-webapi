package com.dwarfeng.familyhelper.webapi.stack.service.life;

import com.dwarfeng.familyhelper.life.stack.bean.dto.ActivityDataRecordCreateInfo;
import com.dwarfeng.familyhelper.life.stack.bean.dto.ActivityDataRecordUpdateInfo;
import com.dwarfeng.familyhelper.life.stack.bean.entity.ActivityDataRecord;
import com.dwarfeng.familyhelper.webapi.stack.bean.disp.life.DispActivityDataRecord;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.service.Service;

/**
 * 活动数据记录响应服务。
 *
 * @author DwArFeng
 * @since 1.0.10
 */
public interface ActivityDataRecordResponseService extends Service {

    boolean exists(LongIdKey key) throws ServiceException;

    ActivityDataRecord get(LongIdKey key) throws ServiceException;

    PagedData<ActivityDataRecord> all(PagingInfo pagingInfo) throws ServiceException;

    PagedData<ActivityDataRecord> childForItemRecordedDateAsc(LongIdKey activityDataItemKey, PagingInfo pagingInfo)
            throws ServiceException;

    PagedData<ActivityDataRecord> childForItemRecordedDateDesc(LongIdKey activityDataItemKey, PagingInfo pagingInfo)
            throws ServiceException;

    PagedData<ActivityDataRecord> childForActivityRecordedDateAsc(LongIdKey activityKey, PagingInfo pagingInfo)
            throws ServiceException;

    PagedData<ActivityDataRecord> childForActivityRecordedDateDesc(LongIdKey activityKey, PagingInfo pagingInfo)
            throws ServiceException;

    DispActivityDataRecord getDisp(LongIdKey key, StringIdKey inspectAccountKey) throws ServiceException;

    PagedData<DispActivityDataRecord> allDisp(StringIdKey accountKey, PagingInfo pagingInfo) throws ServiceException;

    PagedData<DispActivityDataRecord> childForItemRecordedDateAscDisp(
            StringIdKey accountKey, LongIdKey activityDataItemKey, PagingInfo pagingInfo
    ) throws ServiceException;

    PagedData<DispActivityDataRecord> childForItemRecordedDateDescDisp(
            StringIdKey accountKey, LongIdKey activityDataItemKey, PagingInfo pagingInfo
    ) throws ServiceException;

    PagedData<DispActivityDataRecord> childForActivityRecordedDateAscDisp(
            StringIdKey accountKey, LongIdKey activityKey, PagingInfo pagingInfo
    ) throws ServiceException;

    PagedData<DispActivityDataRecord> childForActivityRecordedDateDescDisp(
            StringIdKey accountKey, LongIdKey activityKey, PagingInfo pagingInfo
    ) throws ServiceException;

    LongIdKey create(StringIdKey userKey, ActivityDataRecordCreateInfo createInfo) throws ServiceException;

    void update(StringIdKey userKey, ActivityDataRecordUpdateInfo updateInfo) throws ServiceException;

    void remove(StringIdKey userKey, LongIdKey key) throws ServiceException;
}
