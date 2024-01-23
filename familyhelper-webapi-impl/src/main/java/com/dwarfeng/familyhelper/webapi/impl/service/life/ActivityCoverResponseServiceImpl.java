package com.dwarfeng.familyhelper.webapi.impl.service.life;

import com.dwarfeng.familyhelper.life.stack.bean.dto.ActivityCover;
import com.dwarfeng.familyhelper.life.stack.bean.dto.ActivityCoverOrderUpdateInfo;
import com.dwarfeng.familyhelper.life.stack.bean.dto.ActivityCoverUploadInfo;
import com.dwarfeng.familyhelper.life.stack.bean.entity.ActivityCoverInfo;
import com.dwarfeng.familyhelper.life.stack.service.ActivityCoverInfoMaintainService;
import com.dwarfeng.familyhelper.life.stack.service.ActivityCoverOperateService;
import com.dwarfeng.familyhelper.webapi.stack.service.life.ActivityCoverResponseService;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class ActivityCoverResponseServiceImpl implements ActivityCoverResponseService {

    private final ActivityCoverInfoMaintainService activityCoverInfoMaintainService;
    private final ActivityCoverOperateService activityCoverOperateService;

    public ActivityCoverResponseServiceImpl(
            @Qualifier("familyhelperLifeActivityCoverInfoMaintainService")
            ActivityCoverInfoMaintainService activityCoverInfoMaintainService,
            @Qualifier("familyhelperLifeActivityCoverOperateService")
            ActivityCoverOperateService activityCoverOperateService
    ) {
        this.activityCoverInfoMaintainService = activityCoverInfoMaintainService;
        this.activityCoverOperateService = activityCoverOperateService;
    }

    @Override
    public boolean exists(LongIdKey key) throws ServiceException {
        return activityCoverInfoMaintainService.exists(key);
    }

    @Override
    public ActivityCoverInfo get(LongIdKey key) throws ServiceException {
        return activityCoverInfoMaintainService.get(key);
    }

    @Override
    public PagedData<ActivityCoverInfo> childForActivity(LongIdKey activityKey, PagingInfo pagingInfo)
            throws ServiceException {
        return activityCoverInfoMaintainService.lookup(
                ActivityCoverInfoMaintainService.CHILD_FOR_ACTIVITY_INDEX_ASC,
                new Object[]{activityKey},
                pagingInfo
        );
    }

    @Override
    public ActivityCover download(StringIdKey userKey, LongIdKey coverKey) throws ServiceException {
        return activityCoverOperateService.download(userKey, coverKey);
    }

    @Override
    public void upload(StringIdKey userKey, ActivityCoverUploadInfo coverUploadInfo) throws ServiceException {
        activityCoverOperateService.upload(userKey, coverUploadInfo);
    }

    @Override
    public void remove(StringIdKey userKey, LongIdKey coverKey) throws ServiceException {
        activityCoverOperateService.remove(userKey, coverKey);
    }

    @Override
    public void updateOrder(StringIdKey userKey, ActivityCoverOrderUpdateInfo coverUpdateInfo)
            throws ServiceException {
        activityCoverOperateService.updateOrder(userKey, coverUpdateInfo);
    }
}
