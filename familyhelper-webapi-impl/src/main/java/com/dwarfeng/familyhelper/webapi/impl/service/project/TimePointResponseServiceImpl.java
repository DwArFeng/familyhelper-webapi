package com.dwarfeng.familyhelper.webapi.impl.service.project;

import com.dwarfeng.familyhelper.project.stack.bean.dto.TimePointCreateInfo;
import com.dwarfeng.familyhelper.project.stack.bean.dto.TimePointUpdateInfo;
import com.dwarfeng.familyhelper.project.stack.bean.entity.TimePoint;
import com.dwarfeng.familyhelper.project.stack.service.TimePointMaintainService;
import com.dwarfeng.familyhelper.project.stack.service.TimePointOperateService;
import com.dwarfeng.familyhelper.webapi.stack.service.project.TimePointResponseService;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class TimePointResponseServiceImpl implements TimePointResponseService {

    private final TimePointMaintainService timePointMaintainService;
    private final TimePointOperateService timePointOperateService;

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    public TimePointResponseServiceImpl(
            @Qualifier("familyhelperTimePointMaintainService") TimePointMaintainService timePointMaintainService,
            @Qualifier("familyhelperTimePointOperateService") TimePointOperateService timePointOperateService
    ) {
        this.timePointMaintainService = timePointMaintainService;
        this.timePointOperateService = timePointOperateService;
    }

    @Override
    public boolean exists(LongIdKey key) throws ServiceException {
        return timePointMaintainService.exists(key);
    }

    @Override
    public TimePoint get(LongIdKey key) throws ServiceException {
        return timePointMaintainService.get(key);
    }

    @Override
    public PagedData<TimePoint> all(PagingInfo pagingInfo) throws ServiceException {
        return timePointMaintainService.lookup(pagingInfo);
    }

    @Override
    public PagedData<TimePoint> childForTask(LongIdKey taskKey, PagingInfo pagingInfo) throws ServiceException {
        return timePointMaintainService.lookup(
                TimePointMaintainService.CHILD_FOR_TASK_EXPECTED_FINISHED_DATE_DESC, new Object[]{taskKey}, pagingInfo
        );
    }

    @Override
    public LongIdKey createTimePoint(StringIdKey userKey, TimePointCreateInfo timePointCreateInfo)
            throws ServiceException {
        return timePointOperateService.createTimePoint(userKey, timePointCreateInfo);
    }

    @Override
    public void updateTimePoint(StringIdKey userKey, TimePointUpdateInfo timePointUpdateInfo) throws ServiceException {
        timePointOperateService.updateTimePoint(userKey, timePointUpdateInfo);
    }

    @Override
    public void removeTimePoint(StringIdKey userKey, LongIdKey timePointKey) throws ServiceException {
        timePointOperateService.removeTimePoint(userKey, timePointKey);
    }

    @Override
    public void finishTimePoint(StringIdKey userKey, LongIdKey timePointKey) throws ServiceException {
        timePointOperateService.finishTimePoint(userKey, timePointKey);
    }
}
