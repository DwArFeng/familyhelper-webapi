package com.dwarfeng.familyhelper.webapi.impl.service.life;

import com.dwarfeng.familyhelper.life.stack.bean.dto.ActivityFile;
import com.dwarfeng.familyhelper.life.stack.bean.dto.ActivityFileUpdateInfo;
import com.dwarfeng.familyhelper.life.stack.bean.dto.ActivityFileUploadInfo;
import com.dwarfeng.familyhelper.life.stack.bean.entity.ActivityFileInfo;
import com.dwarfeng.familyhelper.life.stack.service.ActivityFileInfoMaintainService;
import com.dwarfeng.familyhelper.life.stack.service.ActivityFileOperateService;
import com.dwarfeng.familyhelper.webapi.stack.service.life.ActivityFileResponseService;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class ActivityFileResponseServiceImpl implements ActivityFileResponseService {

    private final ActivityFileInfoMaintainService activityFileInfoMaintainService;
    private final ActivityFileOperateService activityFileOperateService;

    public ActivityFileResponseServiceImpl(
            @Qualifier("familyhelperLifeActivityFileInfoMaintainService")
            ActivityFileInfoMaintainService activityFileInfoMaintainService,
            @Qualifier("familyhelperLifeActivityFileOperateService")
            ActivityFileOperateService activityFileOperateService
    ) {
        this.activityFileInfoMaintainService = activityFileInfoMaintainService;
        this.activityFileOperateService = activityFileOperateService;
    }

    @Override
    public boolean exists(LongIdKey key) throws ServiceException {
        return activityFileInfoMaintainService.exists(key);
    }

    @Override
    public ActivityFileInfo get(LongIdKey key) throws ServiceException {
        return activityFileInfoMaintainService.get(key);
    }

    @Override
    public PagedData<ActivityFileInfo> childForActivity(
            LongIdKey activityKey, PagingInfo pagingInfo
    ) throws ServiceException {
        return activityFileInfoMaintainService.lookup(
                ActivityFileInfoMaintainService.CHILD_FOR_ACTIVITY,
                new Object[]{activityKey},
                pagingInfo
        );
    }

    @Override
    public PagedData<ActivityFileInfo> childForActivityInspectedDateDesc(
            LongIdKey activityKey, PagingInfo pagingInfo
    ) throws ServiceException {
        return activityFileInfoMaintainService.lookup(
                ActivityFileInfoMaintainService.CHILD_FOR_ACTIVITY_INSPECTED_DATE_DESC,
                new Object[]{activityKey},
                pagingInfo
        );
    }

    @Override
    public PagedData<ActivityFileInfo> childForActivityModifiedDateDesc(
            LongIdKey activityKey, PagingInfo pagingInfo
    ) throws ServiceException {
        return activityFileInfoMaintainService.lookup(
                ActivityFileInfoMaintainService.CHILD_FOR_ACTIVITY_MODIFIED_DATE_DESC,
                new Object[]{activityKey},
                pagingInfo
        );
    }

    @Override
    public PagedData<ActivityFileInfo> childForActivityOriginNameAsc(
            LongIdKey activityKey, PagingInfo pagingInfo
    ) throws ServiceException {
        return activityFileInfoMaintainService.lookup(
                ActivityFileInfoMaintainService.CHILD_FOR_ACTIVITY_ORIGIN_NAME_ASC,
                new Object[]{activityKey},
                pagingInfo
        );
    }

    @Override
    public PagedData<ActivityFileInfo> childForActivityCreatedDateAsc(
            LongIdKey activityKey, PagingInfo pagingInfo
    ) throws ServiceException {
        return activityFileInfoMaintainService.lookup(
                ActivityFileInfoMaintainService.CHILD_FOR_ACTIVITY_CREATED_DATE_ASC,
                new Object[]{activityKey},
                pagingInfo
        );
    }

    @Override
    public ActivityFile downloadActivityFile(
            StringIdKey userKey, LongIdKey activityFileKey
    ) throws ServiceException {
        return activityFileOperateService.downloadActivityFile(userKey, activityFileKey);
    }

    @Override
    public void uploadActivityFile(
            StringIdKey userKey, ActivityFileUploadInfo activityFileUploadInfo
    ) throws ServiceException {
        activityFileOperateService.uploadActivityFile(userKey, activityFileUploadInfo);
    }

    @Override
    public void updateActivityFile(
            StringIdKey userKey, ActivityFileUpdateInfo activityFileUpdateInfo
    ) throws ServiceException {
        activityFileOperateService.updateActivityFile(userKey, activityFileUpdateInfo);
    }

    @Override
    public void removeActivityFile(
            StringIdKey userKey, LongIdKey activityFileKey
    ) throws ServiceException {
        activityFileOperateService.removeActivityFile(userKey, activityFileKey);
    }
}
