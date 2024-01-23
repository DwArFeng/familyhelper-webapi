package com.dwarfeng.familyhelper.webapi.impl.service.life;

import com.dwarfeng.familyhelper.life.stack.bean.dto.ActivityTemplateCover;
import com.dwarfeng.familyhelper.life.stack.bean.dto.ActivityTemplateCoverOrderUpdateInfo;
import com.dwarfeng.familyhelper.life.stack.bean.dto.ActivityTemplateCoverUploadInfo;
import com.dwarfeng.familyhelper.life.stack.bean.entity.ActivityTemplateCoverInfo;
import com.dwarfeng.familyhelper.life.stack.service.ActivityTemplateCoverInfoMaintainService;
import com.dwarfeng.familyhelper.life.stack.service.ActivityTemplateCoverOperateService;
import com.dwarfeng.familyhelper.webapi.stack.service.life.ActivityTemplateCoverResponseService;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class ActivityTemplateCoverResponseServiceImpl implements ActivityTemplateCoverResponseService {

    private final ActivityTemplateCoverInfoMaintainService activityTemplateCoverInfoMaintainService;
    private final ActivityTemplateCoverOperateService activityTemplateCoverOperateService;

    public ActivityTemplateCoverResponseServiceImpl(
            @Qualifier("familyhelperLifeActivityTemplateCoverInfoMaintainService")
            ActivityTemplateCoverInfoMaintainService activityTemplateCoverInfoMaintainService,
            @Qualifier("familyhelperLifeActivityTemplateCoverOperateService")
            ActivityTemplateCoverOperateService activityTemplateCoverOperateService
    ) {
        this.activityTemplateCoverInfoMaintainService = activityTemplateCoverInfoMaintainService;
        this.activityTemplateCoverOperateService = activityTemplateCoverOperateService;
    }

    @Override
    public boolean exists(LongIdKey key) throws ServiceException {
        return activityTemplateCoverInfoMaintainService.exists(key);
    }

    @Override
    public ActivityTemplateCoverInfo get(LongIdKey key) throws ServiceException {
        return activityTemplateCoverInfoMaintainService.get(key);
    }

    @Override
    public PagedData<ActivityTemplateCoverInfo> childForActivityTemplate(
            LongIdKey activityTemplateKey, PagingInfo pagingInfo
    ) throws ServiceException {
        return activityTemplateCoverInfoMaintainService.lookup(
                ActivityTemplateCoverInfoMaintainService.CHILD_FOR_ACTIVITY_TEMPLATE_INDEX_ASC,
                new Object[]{activityTemplateKey},
                pagingInfo
        );
    }

    @Override
    public ActivityTemplateCover download(StringIdKey userKey, LongIdKey coverKey) throws ServiceException {
        return activityTemplateCoverOperateService.download(userKey, coverKey);
    }

    @Override
    public void upload(StringIdKey userKey, ActivityTemplateCoverUploadInfo coverUploadInfo) throws ServiceException {
        activityTemplateCoverOperateService.upload(userKey, coverUploadInfo);
    }

    @Override
    public void remove(StringIdKey userKey, LongIdKey coverKey) throws ServiceException {
        activityTemplateCoverOperateService.remove(userKey, coverKey);
    }

    @Override
    public void updateOrder(StringIdKey userKey, ActivityTemplateCoverOrderUpdateInfo coverUpdateInfo)
            throws ServiceException {
        activityTemplateCoverOperateService.updateOrder(userKey, coverUpdateInfo);
    }
}
