package com.dwarfeng.familyhelper.webapi.stack.service.audit;

import com.dwarfeng.audit.stack.bean.entity.AuditCategory;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.service.Service;

/**
 * 审计类别响应服务。
 *
 * @author DwArFeng
 * @since 2.2.0
 */
public interface AuditCategoryResponseService extends Service {

    boolean exists(StringIdKey key) throws ServiceException;

    AuditCategory get(StringIdKey key) throws ServiceException;

    StringIdKey insert(AuditCategory auditCategory) throws ServiceException;

    void update(AuditCategory auditCategory) throws ServiceException;

    void delete(StringIdKey key) throws ServiceException;

    PagedData<AuditCategory> all(PagingInfo pagingInfo) throws ServiceException;
}
