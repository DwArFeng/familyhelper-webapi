package com.dwarfeng.familyhelper.webapi.stack.service.audit;

import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.service.Service;

/**
 * 重置响应服务。
 *
 * @author DwArFeng
 * @since 2.2.0
 */
public interface ResetResponseService extends Service {

    void resetAuditRecord() throws ServiceException;

    void resetInspectionSupervise() throws ServiceException;

    void resetInspectionJob() throws ServiceException;
}
