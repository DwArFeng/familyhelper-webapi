package com.dwarfeng.familyhelper.webapi.impl.service.life;

import com.dwarfeng.familyhelper.life.stack.bean.entity.ActivityTemplateDriverInfo;
import com.dwarfeng.familyhelper.life.stack.service.ActivityTemplateDriverInfoMaintainService;
import com.dwarfeng.familyhelper.webapi.stack.service.life.ActivityTemplateDriverInfoResponseService;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class ActivityTemplateDriverInfoResponseServiceImpl implements ActivityTemplateDriverInfoResponseService {

    private final ActivityTemplateDriverInfoMaintainService activityTemplateDriverInfoMaintainService;

    public ActivityTemplateDriverInfoResponseServiceImpl(
            @Qualifier("familyhelperLifeActivityTemplateDriverInfoMaintainService")
            ActivityTemplateDriverInfoMaintainService activityTemplateDriverInfoMaintainService
    ) {
        this.activityTemplateDriverInfoMaintainService = activityTemplateDriverInfoMaintainService;
    }

    @Override
    public boolean exists(LongIdKey key) throws ServiceException {
        return activityTemplateDriverInfoMaintainService.exists(key);
    }

    @Override
    public ActivityTemplateDriverInfo get(LongIdKey key) throws ServiceException {
        return activityTemplateDriverInfoMaintainService.get(key);
    }

    @Override
    public LongIdKey insert(ActivityTemplateDriverInfo activityTemplateDriverInfo) throws ServiceException {
        return activityTemplateDriverInfoMaintainService.insert(activityTemplateDriverInfo);
    }

    @Override
    public void update(ActivityTemplateDriverInfo activityTemplateDriverInfo) throws ServiceException {
        activityTemplateDriverInfoMaintainService.update(activityTemplateDriverInfo);
    }

    @Override
    public void delete(LongIdKey key) throws ServiceException {
        activityTemplateDriverInfoMaintainService.delete(key);
    }

    @Override
    public PagedData<ActivityTemplateDriverInfo> childForActivityTemplate(
            LongIdKey accountBookKey, PagingInfo pagingInfo
    ) throws ServiceException {
        return activityTemplateDriverInfoMaintainService.lookup(
                ActivityTemplateDriverInfoMaintainService.CHILD_FOR_ACTIVITY_TEMPLATE,
                new Object[]{accountBookKey},
                pagingInfo
        );
    }
}
