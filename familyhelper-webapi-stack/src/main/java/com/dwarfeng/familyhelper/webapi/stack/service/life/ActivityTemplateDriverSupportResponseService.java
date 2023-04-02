package com.dwarfeng.familyhelper.webapi.stack.service.life;

import com.dwarfeng.familyhelper.life.stack.bean.entity.ActivityTemplateDriverSupport;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.service.Service;

/**
 * 活动模板驱动器支持响应服务。
 *
 * @author DwArFeng
 * @since 1.0.10
 */
public interface ActivityTemplateDriverSupportResponseService extends Service {

    boolean exists(StringIdKey key) throws ServiceException;

    ActivityTemplateDriverSupport get(StringIdKey key) throws ServiceException;

    PagedData<ActivityTemplateDriverSupport> all(PagingInfo pagingInfo) throws ServiceException;

    PagedData<ActivityTemplateDriverSupport> idLike(String pattern, PagingInfo pagingInfo) throws ServiceException;
}
