package com.dwarfeng.familyhelper.webapi.stack.service.life;

import com.dwarfeng.familyhelper.life.stack.bean.dto.ActivityTemplateCover;
import com.dwarfeng.familyhelper.life.stack.bean.dto.ActivityTemplateCoverOrderUpdateInfo;
import com.dwarfeng.familyhelper.life.stack.bean.dto.ActivityTemplateCoverUploadInfo;
import com.dwarfeng.familyhelper.life.stack.bean.entity.ActivityTemplateCoverInfo;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.service.Service;

/**
 * 活动模板封面响应服务。
 *
 * @author DwArFeng
 * @since 1.0.10
 */
public interface ActivityTemplateCoverResponseService extends Service {

    boolean exists(LongIdKey key) throws ServiceException;

    ActivityTemplateCoverInfo get(LongIdKey key) throws ServiceException;

    PagedData<ActivityTemplateCoverInfo> childForActivityTemplate(LongIdKey activityTemplateKey, PagingInfo pagingInfo)
            throws ServiceException;

    ActivityTemplateCover download(StringIdKey userKey, LongIdKey coverKey) throws ServiceException;

    void upload(StringIdKey userKey, ActivityTemplateCoverUploadInfo coverUploadInfo) throws ServiceException;

    void remove(StringIdKey userKey, LongIdKey coverKey) throws ServiceException;

    void updateOrder(StringIdKey userKey, ActivityTemplateCoverOrderUpdateInfo coverUpdateInfo) throws ServiceException;
}
