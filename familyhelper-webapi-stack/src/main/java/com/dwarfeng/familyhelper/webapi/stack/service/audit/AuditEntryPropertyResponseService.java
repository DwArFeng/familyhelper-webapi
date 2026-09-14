package com.dwarfeng.familyhelper.webapi.stack.service.audit;

import com.dwarfeng.audit.stack.bean.entity.AuditEntryProperty;
import com.dwarfeng.audit.stack.bean.key.AuditEntryPropertyKey;
import com.dwarfeng.familyhelper.webapi.stack.bean.audit.disp.DispAuditEntryProperty;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.service.Service;

/**
 * 审计条目属性响应服务。
 *
 * @author DwArFeng
 * @since 2.2.0
 */
public interface AuditEntryPropertyResponseService extends Service {

    boolean exists(AuditEntryPropertyKey key) throws ServiceException;

    AuditEntryProperty get(AuditEntryPropertyKey key) throws ServiceException;

    PagedData<AuditEntryProperty> all(PagingInfo pagingInfo) throws ServiceException;

    PagedData<AuditEntryProperty> childForAuditEntry(LongIdKey auditEntryKey, PagingInfo pagingInfo)
            throws ServiceException;

    DispAuditEntryProperty getDisp(AuditEntryPropertyKey key) throws ServiceException;

    PagedData<DispAuditEntryProperty> allDisp(PagingInfo pagingInfo) throws ServiceException;

    PagedData<DispAuditEntryProperty> childForAuditEntryDisp(LongIdKey auditEntryKey, PagingInfo pagingInfo)
            throws ServiceException;
}
