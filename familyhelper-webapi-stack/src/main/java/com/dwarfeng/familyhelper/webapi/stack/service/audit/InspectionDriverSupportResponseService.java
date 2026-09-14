package com.dwarfeng.familyhelper.webapi.stack.service.audit;

import com.dwarfeng.audit.stack.bean.entity.InspectionDriverSupport;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.service.Service;

/**
 * 自动审计驱动器支持响应服务。
 *
 * @author DwArFeng
 * @since 2.2.0
 */
public interface InspectionDriverSupportResponseService extends Service {

    boolean exists(StringIdKey key) throws ServiceException;

    InspectionDriverSupport get(StringIdKey key) throws ServiceException;

    PagedData<InspectionDriverSupport> all(PagingInfo pagingInfo) throws ServiceException;
}
