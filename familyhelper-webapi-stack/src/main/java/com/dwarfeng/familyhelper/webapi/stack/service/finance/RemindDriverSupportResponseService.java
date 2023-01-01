package com.dwarfeng.familyhelper.webapi.stack.service.finance;

import com.dwarfeng.familyhelper.finance.stack.bean.entity.RemindDriverSupport;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.service.Service;

/**
 * 提醒驱动器支持响应服务。
 *
 * @author DwArFeng
 * @since 1.0.7
 */
public interface RemindDriverSupportResponseService extends Service {

    boolean exists(StringIdKey key) throws ServiceException;

    RemindDriverSupport get(StringIdKey key) throws ServiceException;

    PagedData<RemindDriverSupport> all(PagingInfo pagingInfo) throws ServiceException;

    PagedData<RemindDriverSupport> idLike(String pattern, PagingInfo pagingInfo) throws ServiceException;
}
