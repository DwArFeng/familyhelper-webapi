package com.dwarfeng.familyhelper.webapi.stack.service.audit;

import com.dwarfeng.audit.stack.bean.entity.AuditEntry;
import com.dwarfeng.familyhelper.webapi.stack.bean.audit.disp.DispAuditEntry;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.service.Service;

/**
 * 审计条目响应服务。
 *
 * @author DwArFeng
 * @since 2.2.0
 */
public interface AuditEntryResponseService extends Service {

    boolean exists(LongIdKey key) throws ServiceException;

    AuditEntry get(LongIdKey key) throws ServiceException;

    PagedData<AuditEntry> all(PagingInfo pagingInfo) throws ServiceException;

    PagedData<AuditEntry> createdDateDesc(PagingInfo pagingInfo) throws ServiceException;

    PagedData<AuditEntry> childForAuditCategory(StringIdKey auditCategoryKey, PagingInfo pagingInfo)
            throws ServiceException;

    DispAuditEntry getDisp(LongIdKey key) throws ServiceException;

    PagedData<DispAuditEntry> allDisp(PagingInfo pagingInfo) throws ServiceException;

    PagedData<DispAuditEntry> createdDateDescDisp(PagingInfo pagingInfo) throws ServiceException;

    PagedData<DispAuditEntry> childForAuditCategoryDisp(StringIdKey auditCategoryKey, PagingInfo pagingInfo)
            throws ServiceException;
}
