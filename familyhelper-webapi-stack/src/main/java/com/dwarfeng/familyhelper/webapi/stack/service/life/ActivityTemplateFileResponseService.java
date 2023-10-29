package com.dwarfeng.familyhelper.webapi.stack.service.life;

import com.dwarfeng.familyhelper.life.stack.bean.dto.ActivityTemplateFile;
import com.dwarfeng.familyhelper.life.stack.bean.dto.ActivityTemplateFileUpdateInfo;
import com.dwarfeng.familyhelper.life.stack.bean.dto.ActivityTemplateFileUploadInfo;
import com.dwarfeng.familyhelper.life.stack.bean.entity.ActivityTemplateFileInfo;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.service.Service;

/**
 * 活动模板文件响应服务。
 *
 * @author DwArFeng
 * @since 1.0.10
 */
public interface ActivityTemplateFileResponseService extends Service {

    boolean exists(LongIdKey key) throws ServiceException;

    ActivityTemplateFileInfo get(LongIdKey key) throws ServiceException;

    PagedData<ActivityTemplateFileInfo> childForActivityTemplate(
            LongIdKey ActivityTemplateKey, PagingInfo pagingInfo
    ) throws ServiceException;

    PagedData<ActivityTemplateFileInfo> childForActivityTemplateInspectedDateDesc(
            LongIdKey ActivityTemplateKey, PagingInfo pagingInfo
    ) throws ServiceException;

    PagedData<ActivityTemplateFileInfo> childForActivityTemplateModifiedDateDesc(
            LongIdKey ActivityTemplateKey, PagingInfo pagingInfo
    ) throws ServiceException;

    PagedData<ActivityTemplateFileInfo> childForActivityTemplateOriginNameAsc(
            LongIdKey ActivityTemplateKey, PagingInfo pagingInfo
    ) throws ServiceException;

    PagedData<ActivityTemplateFileInfo> childForActivityTemplateCreatedDateAsc(
            LongIdKey ActivityTemplateKey, PagingInfo pagingInfo
    ) throws ServiceException;

    ActivityTemplateFile downloadActivityTemplateFile(
            StringIdKey userKey, LongIdKey ActivityTemplateFileKey
    ) throws ServiceException;

    void uploadActivityTemplateFile(
            StringIdKey userKey, ActivityTemplateFileUploadInfo ActivityTemplateFileUploadInfo
    ) throws ServiceException;

    void updateActivityTemplateFile(
            StringIdKey userKey, ActivityTemplateFileUpdateInfo ActivityTemplateFileUpdateInfo
    ) throws ServiceException;

    void removeActivityTemplateFile(
            StringIdKey userKey, LongIdKey ActivityTemplateFileKey
    ) throws ServiceException;
}
