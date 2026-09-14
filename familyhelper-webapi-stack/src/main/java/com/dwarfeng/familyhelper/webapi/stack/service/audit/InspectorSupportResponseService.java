package com.dwarfeng.familyhelper.webapi.stack.service.audit;

import com.dwarfeng.audit.stack.bean.entity.InspectorSupport;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.service.Service;

/**
 * 审计器支持响应服务。
 *
 * @author DwArFeng
 * @since 2.2.0
 */
public interface InspectorSupportResponseService extends Service {

    boolean exists(StringIdKey key) throws ServiceException;

    InspectorSupport get(StringIdKey key) throws ServiceException;

    PagedData<InspectorSupport> all(PagingInfo pagingInfo) throws ServiceException;
}
