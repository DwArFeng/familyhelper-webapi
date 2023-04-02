package com.dwarfeng.familyhelper.webapi.stack.service.life;

import com.dwarfeng.familyhelper.life.stack.bean.entity.ActivityTypeIndicator;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.service.Service;

/**
 * 活动类型响应服务。
 *
 * @author DwArFeng
 * @since 1.0.10
 */
public interface ActivityTypeIndicatorResponseService extends Service {

    boolean exists(StringIdKey key) throws ServiceException;

    ActivityTypeIndicator get(StringIdKey key) throws ServiceException;

    StringIdKey insert(ActivityTypeIndicator activityTypeIndicator) throws ServiceException;

    void update(ActivityTypeIndicator activityTypeIndicator) throws ServiceException;

    void delete(StringIdKey key) throws ServiceException;

    PagedData<ActivityTypeIndicator> all(PagingInfo pagingInfo) throws ServiceException;
}
