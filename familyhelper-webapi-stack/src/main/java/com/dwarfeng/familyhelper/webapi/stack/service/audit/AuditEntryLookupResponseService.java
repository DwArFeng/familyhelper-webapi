package com.dwarfeng.familyhelper.webapi.stack.service.audit;

import com.dwarfeng.audit.stack.bean.dto.AuditEntryLookupResult;
import com.dwarfeng.familyhelper.webapi.stack.bean.audit.dto.AuditEntryCompositeLookupInfo;
import com.dwarfeng.familyhelper.webapi.stack.bean.audit.dto.AuditEntryGroupedLookupInfo;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.service.Service;

/**
 * 审计条目查询响应服务。
 *
 * @author DwArFeng
 * @since 2.2.0
 */
public interface AuditEntryLookupResponseService extends Service {

    /**
     * 执行组合查询。
     *
     * @param info 组合查询信息。
     * @return 查询结果。
     * @throws ServiceException 服务异常。
     */
    AuditEntryLookupResult lookupComposite(AuditEntryCompositeLookupInfo info) throws ServiceException;

    /**
     * 执行分组查询。
     *
     * @param info 分组查询信息。
     * @return 查询结果。
     * @throws ServiceException 服务异常。
     */
    AuditEntryLookupResult lookupGrouped(AuditEntryGroupedLookupInfo info) throws ServiceException;
}
