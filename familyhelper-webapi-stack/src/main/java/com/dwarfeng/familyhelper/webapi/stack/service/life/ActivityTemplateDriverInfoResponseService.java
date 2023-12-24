package com.dwarfeng.familyhelper.webapi.stack.service.life;

import com.dwarfeng.familyhelper.life.stack.bean.entity.ActivityTemplateDriverInfo;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.service.Service;

/**
 * 活动模板驱动信息响应服务。
 *
 * @author DwArFeng
 * @since 1.0.10
 */
public interface ActivityTemplateDriverInfoResponseService extends Service {

    boolean exists(LongIdKey key) throws ServiceException;

    ActivityTemplateDriverInfo get(LongIdKey key) throws ServiceException;

    LongIdKey insert(ActivityTemplateDriverInfo activityTemplateDriverInfo) throws ServiceException;

    void update(ActivityTemplateDriverInfo activityTemplateDriverInfo) throws ServiceException;

    void delete(LongIdKey key) throws ServiceException;

    PagedData<ActivityTemplateDriverInfo> childForActivityTemplate(LongIdKey accountBookKey, PagingInfo pagingInfo)
            throws ServiceException;
}
