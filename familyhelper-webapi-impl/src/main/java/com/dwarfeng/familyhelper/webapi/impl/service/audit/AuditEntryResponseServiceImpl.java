package com.dwarfeng.familyhelper.webapi.impl.service.audit;

import com.dwarfeng.audit.stack.bean.entity.AuditCategory;
import com.dwarfeng.audit.stack.bean.entity.AuditEntry;
import com.dwarfeng.audit.stack.service.AuditCategoryMaintainService;
import com.dwarfeng.audit.stack.service.AuditEntryMaintainService;
import com.dwarfeng.familyhelper.webapi.stack.bean.audit.disp.DispAuditEntry;
import com.dwarfeng.familyhelper.webapi.stack.service.audit.AuditEntryResponseService;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class AuditEntryResponseServiceImpl implements AuditEntryResponseService {

    private final AuditEntryMaintainService auditEntryMaintainService;
    private final AuditCategoryMaintainService auditCategoryMaintainService;

    public AuditEntryResponseServiceImpl(
            @Qualifier("auditAuditEntryMaintainService") AuditEntryMaintainService auditEntryMaintainService,
            @Qualifier("auditAuditCategoryMaintainService") AuditCategoryMaintainService auditCategoryMaintainService
    ) {
        this.auditEntryMaintainService = auditEntryMaintainService;
        this.auditCategoryMaintainService = auditCategoryMaintainService;
    }

    @Override
    public boolean exists(LongIdKey key) throws ServiceException {
        return auditEntryMaintainService.exists(key);
    }

    @Override
    public AuditEntry get(LongIdKey key) throws ServiceException {
        return auditEntryMaintainService.get(key);
    }

    @Override
    public PagedData<AuditEntry> all(PagingInfo pagingInfo) throws ServiceException {
        return auditEntryMaintainService.lookup(pagingInfo);
    }

    @Override
    public PagedData<AuditEntry> createdDateDesc(PagingInfo pagingInfo) throws ServiceException {
        return auditEntryMaintainService.lookup(
                "created_date_desc", new Object[]{}, pagingInfo
        );
    }

    @Override
    public PagedData<AuditEntry> childForAuditCategory(StringIdKey auditCategoryKey, PagingInfo pagingInfo)
            throws ServiceException {
        return auditEntryMaintainService.lookup(
                AuditEntryMaintainService.CHILD_FOR_AUDIT_CATEGORY,
                new Object[]{auditCategoryKey},
                pagingInfo
        );
    }

    @Override
    public DispAuditEntry getDisp(LongIdKey key) throws ServiceException {
        AuditEntry auditEntry = auditEntryMaintainService.get(key);
        return toDisp(auditEntry);
    }

    @Override
    public PagedData<DispAuditEntry> allDisp(PagingInfo pagingInfo) throws ServiceException {
        PagedData<AuditEntry> lookup = auditEntryMaintainService.lookup(pagingInfo);
        return toDispPagedData(lookup);
    }

    @Override
    public PagedData<DispAuditEntry> createdDateDescDisp(PagingInfo pagingInfo) throws ServiceException {
        return toDispPagedData(createdDateDesc(pagingInfo));
    }

    @Override
    public PagedData<DispAuditEntry> childForAuditCategoryDisp(StringIdKey auditCategoryKey, PagingInfo pagingInfo)
            throws ServiceException {
        PagedData<AuditEntry> lookup = auditEntryMaintainService.lookup(
                AuditEntryMaintainService.CHILD_FOR_AUDIT_CATEGORY,
                new Object[]{auditCategoryKey},
                pagingInfo
        );
        return toDispPagedData(lookup);
    }

    private DispAuditEntry toDisp(AuditEntry auditEntry) throws ServiceException {
        if (Objects.isNull(auditEntry)) {
            return null;
        }
        StringIdKey auditCategoryKey = auditEntry.getCategoryKey();
        AuditCategory auditCategory = null;
        if (Objects.nonNull(auditCategoryKey)) {
            auditCategory = auditCategoryMaintainService.getIfExists(auditCategoryKey);
        }
        return DispAuditEntry.of(auditEntry, auditCategory);
    }

    private PagedData<DispAuditEntry> toDispPagedData(PagedData<AuditEntry> pagedData) throws ServiceException {
        List<DispAuditEntry> dispAuditEntries = new ArrayList<>(pagedData.getData().size());
        for (AuditEntry auditEntry : pagedData.getData()) {
            dispAuditEntries.add(toDisp(auditEntry));
        }
        return new PagedData<>(
                pagedData.getCurrentPage(), pagedData.getTotalPages(), pagedData.getRows(), pagedData.getCount(),
                dispAuditEntries
        );
    }
}
