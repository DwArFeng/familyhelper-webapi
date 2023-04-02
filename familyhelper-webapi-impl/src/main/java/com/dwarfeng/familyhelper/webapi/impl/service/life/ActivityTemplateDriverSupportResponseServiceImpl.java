package com.dwarfeng.familyhelper.webapi.impl.service.life;

import com.dwarfeng.familyhelper.life.stack.bean.entity.ActivityTemplateDriverSupport;
import com.dwarfeng.familyhelper.life.stack.service.ActivityTemplateDriverSupportMaintainService;
import com.dwarfeng.familyhelper.webapi.stack.service.life.ActivityTemplateDriverSupportResponseService;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class ActivityTemplateDriverSupportResponseServiceImpl implements ActivityTemplateDriverSupportResponseService {

    private final ActivityTemplateDriverSupportMaintainService activityTemplateDriverSupportMaintainService;

    public ActivityTemplateDriverSupportResponseServiceImpl(
            @Qualifier("familyhelperLifeActivityTemplateDriverSupportMaintainService")
            ActivityTemplateDriverSupportMaintainService activityTemplateDriverSupportMaintainService
    ) {
        this.activityTemplateDriverSupportMaintainService = activityTemplateDriverSupportMaintainService;
    }

    @Override
    public boolean exists(StringIdKey key) throws ServiceException {
        return activityTemplateDriverSupportMaintainService.exists(key);
    }

    @Override
    public ActivityTemplateDriverSupport get(StringIdKey key) throws ServiceException {
        return activityTemplateDriverSupportMaintainService.get(key);
    }

    @Override
    public PagedData<ActivityTemplateDriverSupport> all(PagingInfo pagingInfo) throws ServiceException {
        return activityTemplateDriverSupportMaintainService.lookup(pagingInfo);
    }

    @Override
    public PagedData<ActivityTemplateDriverSupport> idLike(String pattern, PagingInfo pagingInfo) throws ServiceException {
        return activityTemplateDriverSupportMaintainService.lookup(
                ActivityTemplateDriverSupportMaintainService.ID_LIKE, new Object[]{pattern}, pagingInfo
        );
    }
}
