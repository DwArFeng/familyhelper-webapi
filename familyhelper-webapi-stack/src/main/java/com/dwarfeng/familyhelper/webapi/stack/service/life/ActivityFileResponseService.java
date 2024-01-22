package com.dwarfeng.familyhelper.webapi.stack.service.life;

import com.dwarfeng.familyhelper.life.stack.bean.dto.ActivityFile;
import com.dwarfeng.familyhelper.life.stack.bean.dto.ActivityFileUpdateInfo;
import com.dwarfeng.familyhelper.life.stack.bean.dto.ActivityFileUploadInfo;
import com.dwarfeng.familyhelper.life.stack.bean.entity.ActivityFileInfo;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.service.Service;

/**
 * 活动文件响应服务。
 *
 * @author DwArFeng
 * @since 1.0.10
 */
public interface ActivityFileResponseService extends Service {

    boolean exists(LongIdKey key) throws ServiceException;

    ActivityFileInfo get(LongIdKey key) throws ServiceException;

    PagedData<ActivityFileInfo> childForActivity(LongIdKey ActivityKey, PagingInfo pagingInfo) throws ServiceException;

    PagedData<ActivityFileInfo> childForActivityInspectedDateDesc(LongIdKey ActivityKey, PagingInfo pagingInfo)
            throws ServiceException;

    PagedData<ActivityFileInfo> childForActivityModifiedDateDesc(LongIdKey ActivityKey, PagingInfo pagingInfo)
            throws ServiceException;

    PagedData<ActivityFileInfo> childForActivityOriginNameAsc(LongIdKey ActivityKey, PagingInfo pagingInfo)
            throws ServiceException;

    PagedData<ActivityFileInfo> childForActivityCreatedDateAsc(LongIdKey ActivityKey, PagingInfo pagingInfo)
            throws ServiceException;

    ActivityFile downloadActivityFile(StringIdKey userKey, LongIdKey ActivityFileKey) throws ServiceException;

    void uploadActivityFile(StringIdKey userKey, ActivityFileUploadInfo ActivityFileUploadInfo) throws ServiceException;

    void updateActivityFile(StringIdKey userKey, ActivityFileUpdateInfo ActivityFileUpdateInfo) throws ServiceException;

    void removeActivityFile(StringIdKey userKey, LongIdKey ActivityFileKey) throws ServiceException;
}
