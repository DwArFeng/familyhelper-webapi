package com.dwarfeng.familyhelper.webapi.stack.service.life;

import com.dwarfeng.familyhelper.life.stack.bean.dto.ActivityTemplateDataInfoCreateInfo;
import com.dwarfeng.familyhelper.life.stack.bean.dto.ActivityTemplateDataInfoUpdateInfo;
import com.dwarfeng.familyhelper.life.stack.bean.entity.ActivityTemplateDataInfo;
import com.dwarfeng.familyhelper.webapi.stack.bean.disp.life.DispActivityTemplateDataInfo;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.service.Service;

/**
 * 活动类型响应服务。
 *
 * @author DwArFeng
 * @since 1.0.10
 */
public interface ActivityTempalteDataInfoResponseService extends Service {

    boolean exists(LongIdKey key) throws ServiceException;

    ActivityTemplateDataInfo get(LongIdKey key) throws ServiceException;

    PagedData<ActivityTemplateDataInfo> all(PagingInfo pagingInfo) throws ServiceException;

    PagedData<ActivityTemplateDataInfo> childForActivityTemplate(LongIdKey activityTemplateKey, PagingInfo pagingInfo)
            throws ServiceException;

    DispActivityTemplateDataInfo getDisp(LongIdKey key, StringIdKey inspectAccountKey) throws ServiceException;

    PagedData<DispActivityTemplateDataInfo> allDisp(StringIdKey inspectAccountKey, PagingInfo pagingInfo)
            throws ServiceException;

    PagedData<DispActivityTemplateDataInfo> childForActivityTemplateDisp(
            StringIdKey inspectAccountKey, LongIdKey activityTemplateKey, PagingInfo pagingInfo
    ) throws ServiceException;

    LongIdKey create(StringIdKey userKey, ActivityTemplateDataInfoCreateInfo createInfo) throws ServiceException;

    void update(StringIdKey userKey, ActivityTemplateDataInfoUpdateInfo updateInfo) throws ServiceException;

    void remove(StringIdKey userKey, LongIdKey key) throws ServiceException;
}
