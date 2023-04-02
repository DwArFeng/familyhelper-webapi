package com.dwarfeng.familyhelper.webapi.impl.service.life;

import com.dwarfeng.familyhelper.life.stack.bean.entity.ActivityTypeIndicator;
import com.dwarfeng.familyhelper.life.stack.service.ActivityTypeIndicatorMaintainService;
import com.dwarfeng.familyhelper.webapi.stack.service.life.ActivityTypeIndicatorResponseService;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class ActivityTypeIndicatorResponseServiceImpl implements ActivityTypeIndicatorResponseService {

    private final ActivityTypeIndicatorMaintainService activityTypeIndicatorMaintainService;

    public ActivityTypeIndicatorResponseServiceImpl(
            @Qualifier("familyhelperLifeActivityTypeIndicatorMaintainService")
            ActivityTypeIndicatorMaintainService activityTypeIndicatorMaintainService
    ) {
        this.activityTypeIndicatorMaintainService = activityTypeIndicatorMaintainService;
    }

    @Override
    public boolean exists(StringIdKey key) throws ServiceException {
        return activityTypeIndicatorMaintainService.exists(key);
    }

    @Override
    public ActivityTypeIndicator get(StringIdKey key) throws ServiceException {
        return activityTypeIndicatorMaintainService.get(key);
    }

    @Override
    public StringIdKey insert(ActivityTypeIndicator activityTypeIndicator) throws ServiceException {
        return activityTypeIndicatorMaintainService.insert(activityTypeIndicator);
    }

    @Override
    public void update(ActivityTypeIndicator activityTypeIndicator) throws ServiceException {
        activityTypeIndicatorMaintainService.update(activityTypeIndicator);
    }

    @Override
    public void delete(StringIdKey key) throws ServiceException {
        activityTypeIndicatorMaintainService.delete(key);
    }

    @Override
    public PagedData<ActivityTypeIndicator> all(PagingInfo pagingInfo) throws ServiceException {
        return activityTypeIndicatorMaintainService.lookup(pagingInfo);
    }
}
