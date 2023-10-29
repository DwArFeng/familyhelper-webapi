package com.dwarfeng.familyhelper.webapi.impl.service.life;

import com.dwarfeng.familyhelper.life.stack.bean.dto.ActivityTemplateFile;
import com.dwarfeng.familyhelper.life.stack.bean.dto.ActivityTemplateFileUpdateInfo;
import com.dwarfeng.familyhelper.life.stack.bean.dto.ActivityTemplateFileUploadInfo;
import com.dwarfeng.familyhelper.life.stack.bean.entity.ActivityTemplateFileInfo;
import com.dwarfeng.familyhelper.life.stack.service.ActivityTemplateFileInfoMaintainService;
import com.dwarfeng.familyhelper.life.stack.service.ActivityTemplateFileOperateService;
import com.dwarfeng.familyhelper.webapi.stack.service.life.ActivityTemplateFileResponseService;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class ActivityTemplateFileResponseServiceImpl implements ActivityTemplateFileResponseService {

    private final ActivityTemplateFileInfoMaintainService activityTemplateFileInfoMaintainService;
    private final ActivityTemplateFileOperateService activityTemplateFileOperateService;

    public ActivityTemplateFileResponseServiceImpl(
            @Qualifier("familyhelperLifeActivityTemplateFileInfoMaintainService")
            ActivityTemplateFileInfoMaintainService activityTemplateFileInfoMaintainService,
            @Qualifier("familyhelperLifeActivityTemplateFileOperateService")
            ActivityTemplateFileOperateService activityTemplateFileOperateService
    ) {
        this.activityTemplateFileInfoMaintainService = activityTemplateFileInfoMaintainService;
        this.activityTemplateFileOperateService = activityTemplateFileOperateService;
    }

    @Override
    public boolean exists(LongIdKey key) throws ServiceException {
        return activityTemplateFileInfoMaintainService.exists(key);
    }

    @Override
    public ActivityTemplateFileInfo get(LongIdKey key) throws ServiceException {
        return activityTemplateFileInfoMaintainService.get(key);
    }

    @Override
    public PagedData<ActivityTemplateFileInfo> childForActivityTemplate(
            LongIdKey activityTemplateKey, PagingInfo pagingInfo
    ) throws ServiceException {
        return activityTemplateFileInfoMaintainService.lookup(
                ActivityTemplateFileInfoMaintainService.CHILD_FOR_ACTIVITY_TEMPLATE,
                new Object[]{activityTemplateKey},
                pagingInfo
        );
    }

    @Override
    public PagedData<ActivityTemplateFileInfo> childForActivityTemplateInspectedDateDesc(
            LongIdKey activityTemplateKey, PagingInfo pagingInfo
    ) throws ServiceException {
        return activityTemplateFileInfoMaintainService.lookup(
                ActivityTemplateFileInfoMaintainService.CHILD_FOR_ACTIVITY_TEMPLATE_INSPECTED_DATE_DESC,
                new Object[]{activityTemplateKey},
                pagingInfo
        );
    }

    @Override
    public PagedData<ActivityTemplateFileInfo> childForActivityTemplateModifiedDateDesc(
            LongIdKey activityTemplateKey, PagingInfo pagingInfo
    ) throws ServiceException {
        return activityTemplateFileInfoMaintainService.lookup(
                ActivityTemplateFileInfoMaintainService.CHILD_FOR_ACTIVITY_TEMPLATE_MODIFIED_DATE_DESC,
                new Object[]{activityTemplateKey},
                pagingInfo
        );
    }

    @Override
    public PagedData<ActivityTemplateFileInfo> childForActivityTemplateOriginNameAsc(
            LongIdKey activityTemplateKey, PagingInfo pagingInfo
    ) throws ServiceException {
        return activityTemplateFileInfoMaintainService.lookup(
                ActivityTemplateFileInfoMaintainService.CHILD_FOR_ACTIVITY_TEMPLATE_ORIGIN_NAME_ASC,
                new Object[]{activityTemplateKey},
                pagingInfo
        );
    }

    @Override
    public PagedData<ActivityTemplateFileInfo> childForActivityTemplateCreatedDateAsc(
            LongIdKey activityTemplateKey, PagingInfo pagingInfo
    ) throws ServiceException {
        return activityTemplateFileInfoMaintainService.lookup(
                ActivityTemplateFileInfoMaintainService.CHILD_FOR_ACTIVITY_TEMPLATE_CREATED_DATE_ASC,
                new Object[]{activityTemplateKey},
                pagingInfo
        );
    }

    @Override
    public ActivityTemplateFile downloadActivityTemplateFile(
            StringIdKey userKey, LongIdKey activityTemplateFileKey
    ) throws ServiceException {
        return activityTemplateFileOperateService.downloadActivityTemplateFile(userKey, activityTemplateFileKey);
    }

    @Override
    public void uploadActivityTemplateFile(
            StringIdKey userKey, ActivityTemplateFileUploadInfo activityTemplateFileUploadInfo
    ) throws ServiceException {
        activityTemplateFileOperateService.uploadActivityTemplateFile(userKey, activityTemplateFileUploadInfo);
    }

    @Override
    public void updateActivityTemplateFile(
            StringIdKey userKey, ActivityTemplateFileUpdateInfo activityTemplateFileUpdateInfo
    ) throws ServiceException {
        activityTemplateFileOperateService.updateActivityTemplateFile(userKey, activityTemplateFileUpdateInfo);
    }

    @Override
    public void removeActivityTemplateFile(
            StringIdKey userKey, LongIdKey activityTemplateFileKey
    ) throws ServiceException {
        activityTemplateFileOperateService.removeActivityTemplateFile(userKey, activityTemplateFileKey);
    }
}
