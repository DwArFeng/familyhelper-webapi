package com.dwarfeng.familyhelper.webapi.stack.service.project;

import com.dwarfeng.familyhelper.project.stack.bean.dto.TimePointCreateInfo;
import com.dwarfeng.familyhelper.project.stack.bean.dto.TimePointUpdateInfo;
import com.dwarfeng.familyhelper.project.stack.bean.entity.TimePoint;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.service.Service;

/**
 * 时间点响应服务。
 *
 * @author DwArFeng
 * @since 1.0.6
 */
public interface TimePointResponseService extends Service {

    boolean exists(LongIdKey key) throws ServiceException;

    TimePoint get(LongIdKey key) throws ServiceException;

    PagedData<TimePoint> all(PagingInfo pagingInfo) throws ServiceException;

    PagedData<TimePoint> childForTask(LongIdKey taskKey, PagingInfo pagingInfo) throws ServiceException;

    LongIdKey createTimePoint(StringIdKey userKey, TimePointCreateInfo timePointCreateInfo) throws ServiceException;

    void updateTimePoint(StringIdKey userKey, TimePointUpdateInfo timePointUpdateInfo) throws ServiceException;

    void removeTimePoint(StringIdKey userKey, LongIdKey timePointKey) throws ServiceException;

    void finishTimePoint(StringIdKey userKey, LongIdKey timePointKey) throws ServiceException;
}
