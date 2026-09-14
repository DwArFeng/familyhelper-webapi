package com.dwarfeng.familyhelper.webapi.impl.service.audit;

import com.dwarfeng.audit.stack.bean.entity.AuditCategory;
import com.dwarfeng.audit.stack.service.AuditCategoryMaintainService;
import com.dwarfeng.familyhelper.webapi.stack.service.audit.AuditCategoryResponseService;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class AuditCategoryResponseServiceImpl implements AuditCategoryResponseService {

    private final AuditCategoryMaintainService auditCategoryMaintainService;

    public AuditCategoryResponseServiceImpl(
            @Qualifier("auditAuditCategoryMaintainService") AuditCategoryMaintainService auditCategoryMaintainService
    ) {
        this.auditCategoryMaintainService = auditCategoryMaintainService;
    }

    @Override
    public boolean exists(StringIdKey key) throws ServiceException {
        return auditCategoryMaintainService.exists(key);
    }

    @Override
    public AuditCategory get(StringIdKey key) throws ServiceException {
        return auditCategoryMaintainService.get(key);
    }

    @Override
    public StringIdKey insert(AuditCategory auditCategory) throws ServiceException {
        return auditCategoryMaintainService.insert(auditCategory);
    }

    @Override
    public void update(AuditCategory auditCategory) throws ServiceException {
        auditCategoryMaintainService.update(auditCategory);
    }

    @Override
    public void delete(StringIdKey key) throws ServiceException {
        auditCategoryMaintainService.delete(key);
    }

    @Override
    public PagedData<AuditCategory> all(PagingInfo pagingInfo) throws ServiceException {
        return auditCategoryMaintainService.lookup(pagingInfo);
    }
}
