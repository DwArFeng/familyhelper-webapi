package com.dwarfeng.familyhelper.webapi.stack.service.audit;

import com.dwarfeng.familyhelper.webapi.stack.bean.audit.dto.AuditRecordInfo;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.service.Service;

/**
 * 审计记录响应服务。
 *
 * @author DwArFeng
 * @since 2.2.0
 */
public interface AuditRecordResponseService extends Service {

    /**
     * 记录。
     *
     * @param auditRecordInfo 记录信息。
     * @throws ServiceException 服务异常。
     */
    void record(AuditRecordInfo auditRecordInfo) throws ServiceException;
}
