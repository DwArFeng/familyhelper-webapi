package com.dwarfeng.familyhelper.webapi.stack.service.logicengine;

import com.dwarfeng.logicengine.stack.bean.entity.DriverSupport;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.service.Service;

/**
 * 驱动器支持响应服务。
 *
 * @author DwArFeng
 * @since 2.4.0
 */
public interface DriverSupportResponseService extends Service {

    boolean exists(StringIdKey key) throws ServiceException;

    DriverSupport get(StringIdKey key) throws ServiceException;

    PagedData<DriverSupport> all(PagingInfo pagingInfo) throws ServiceException;

    PagedData<DriverSupport> idLike(String pattern, PagingInfo pagingInfo) throws ServiceException;

    PagedData<DriverSupport> labelLike(String pattern, PagingInfo pagingInfo) throws ServiceException;
}
