package com.dwarfeng.familyhelper.webapi.stack.service.audit;

import com.dwarfeng.audit.stack.bean.entity.AuditPropertyIndicator;
import com.dwarfeng.audit.stack.bean.key.AuditPropertyIndicatorKey;
import com.dwarfeng.familyhelper.webapi.stack.bean.audit.disp.DispAuditPropertyIndicator;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.service.Service;

/**
 * 审计属性指示器响应服务。
 *
 * @author DwArFeng
 * @since 2.2.0
 */
public interface AuditPropertyIndicatorResponseService extends Service {

    boolean exists(AuditPropertyIndicatorKey key) throws ServiceException;

    AuditPropertyIndicator get(AuditPropertyIndicatorKey key) throws ServiceException;

    AuditPropertyIndicatorKey insert(AuditPropertyIndicator auditPropertyIndicator) throws ServiceException;

    void update(AuditPropertyIndicator auditPropertyIndicator) throws ServiceException;

    void delete(AuditPropertyIndicatorKey key) throws ServiceException;

    PagedData<AuditPropertyIndicator> all(PagingInfo pagingInfo) throws ServiceException;

    PagedData<AuditPropertyIndicator> childForAuditCategory(StringIdKey auditCategoryKey, PagingInfo pagingInfo)
            throws ServiceException;

    DispAuditPropertyIndicator getDisp(AuditPropertyIndicatorKey key) throws ServiceException;

    PagedData<DispAuditPropertyIndicator> allDisp(PagingInfo pagingInfo) throws ServiceException;

    PagedData<DispAuditPropertyIndicator> childForAuditCategoryDisp(
            StringIdKey auditCategoryKey, PagingInfo pagingInfo
    ) throws ServiceException;
}
