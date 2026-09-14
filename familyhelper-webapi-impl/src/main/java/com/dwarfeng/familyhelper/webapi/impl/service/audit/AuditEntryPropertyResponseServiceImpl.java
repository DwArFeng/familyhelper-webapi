package com.dwarfeng.familyhelper.webapi.impl.service.audit;

import com.dwarfeng.audit.stack.bean.entity.AuditEntry;
import com.dwarfeng.audit.stack.bean.entity.AuditEntryProperty;
import com.dwarfeng.audit.stack.bean.entity.AuditPropertyIndicator;
import com.dwarfeng.audit.stack.bean.key.AuditEntryPropertyKey;
import com.dwarfeng.audit.stack.bean.key.AuditPropertyIndicatorKey;
import com.dwarfeng.audit.stack.service.AuditEntryMaintainService;
import com.dwarfeng.audit.stack.service.AuditEntryPropertyMaintainService;
import com.dwarfeng.audit.stack.service.AuditPropertyIndicatorMaintainService;
import com.dwarfeng.familyhelper.webapi.stack.bean.audit.disp.DispAuditEntryProperty;
import com.dwarfeng.familyhelper.webapi.stack.service.audit.AuditEntryPropertyResponseService;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class AuditEntryPropertyResponseServiceImpl implements AuditEntryPropertyResponseService {

    private final AuditEntryPropertyMaintainService auditEntryPropertyMaintainService;
    private final AuditEntryMaintainService auditEntryMaintainService;
    private final AuditPropertyIndicatorMaintainService auditPropertyIndicatorMaintainService;

    public AuditEntryPropertyResponseServiceImpl(
            @Qualifier("auditAuditEntryPropertyMaintainService")
            AuditEntryPropertyMaintainService auditEntryPropertyMaintainService,
            @Qualifier("auditAuditEntryMaintainService") AuditEntryMaintainService auditEntryMaintainService,
            @Qualifier("auditAuditPropertyIndicatorMaintainService")
            AuditPropertyIndicatorMaintainService auditPropertyIndicatorMaintainService
    ) {
        this.auditEntryPropertyMaintainService = auditEntryPropertyMaintainService;
        this.auditEntryMaintainService = auditEntryMaintainService;
        this.auditPropertyIndicatorMaintainService = auditPropertyIndicatorMaintainService;
    }

    @Override
    public boolean exists(AuditEntryPropertyKey key) throws ServiceException {
        return auditEntryPropertyMaintainService.exists(key);
    }

    @Override
    public AuditEntryProperty get(AuditEntryPropertyKey key) throws ServiceException {
        return auditEntryPropertyMaintainService.get(key);
    }

    @Override
    public PagedData<AuditEntryProperty> all(PagingInfo pagingInfo) throws ServiceException {
        return auditEntryPropertyMaintainService.lookup(pagingInfo);
    }

    @Override
    public PagedData<AuditEntryProperty> childForAuditEntry(LongIdKey auditEntryKey, PagingInfo pagingInfo)
            throws ServiceException {
        return auditEntryPropertyMaintainService.lookup(
                AuditEntryPropertyMaintainService.CHILD_FOR_AUDIT_ENTRY,
                new Object[]{auditEntryKey},
                pagingInfo
        );
    }

    @Override
    public DispAuditEntryProperty getDisp(AuditEntryPropertyKey key) throws ServiceException {
        AuditEntryProperty auditEntryProperty = auditEntryPropertyMaintainService.get(key);
        return toDisp(auditEntryProperty);
    }

    @Override
    public PagedData<DispAuditEntryProperty> allDisp(PagingInfo pagingInfo) throws ServiceException {
        PagedData<AuditEntryProperty> lookup = auditEntryPropertyMaintainService.lookup(pagingInfo);
        return toDispPagedData(lookup);
    }

    @Override
    public PagedData<DispAuditEntryProperty> childForAuditEntryDisp(LongIdKey auditEntryKey, PagingInfo pagingInfo)
            throws ServiceException {
        PagedData<AuditEntryProperty> lookup = auditEntryPropertyMaintainService.lookup(
                AuditEntryPropertyMaintainService.CHILD_FOR_AUDIT_ENTRY,
                new Object[]{auditEntryKey},
                pagingInfo
        );
        return toDispPagedData(lookup);
    }

    private DispAuditEntryProperty toDisp(AuditEntryProperty auditEntryProperty) throws ServiceException {
        if (Objects.isNull(auditEntryProperty)) {
            return null;
        }
        AuditEntryPropertyKey key = auditEntryProperty.getKey();
        LongIdKey auditEntryKey = Objects.nonNull(key) && Objects.nonNull(key.getAuditEntryLongId())
                ? new LongIdKey(key.getAuditEntryLongId())
                : null;
        AuditEntry auditEntry = null;
        if (Objects.nonNull(auditEntryKey)) {
            auditEntry = auditEntryMaintainService.getIfExists(auditEntryKey);
        }
        AuditPropertyIndicator auditPropertyIndicator = null;
        if (Objects.nonNull(key) && Objects.nonNull(key.getPropertyStringId())
                && Objects.nonNull(auditEntry) && Objects.nonNull(auditEntry.getCategoryKey())) {
            AuditPropertyIndicatorKey indicatorKey = new AuditPropertyIndicatorKey(
                    auditEntry.getCategoryKey().getStringId(), key.getPropertyStringId()
            );
            auditPropertyIndicator = auditPropertyIndicatorMaintainService.getIfExists(indicatorKey);
        }
        return DispAuditEntryProperty.of(auditEntryProperty, auditEntry, auditPropertyIndicator);
    }

    private PagedData<DispAuditEntryProperty> toDispPagedData(PagedData<AuditEntryProperty> pagedData)
            throws ServiceException {
        List<DispAuditEntryProperty> dispAuditEntryProperties = new ArrayList<>(pagedData.getData().size());
        for (AuditEntryProperty auditEntryProperty : pagedData.getData()) {
            dispAuditEntryProperties.add(toDisp(auditEntryProperty));
        }
        return new PagedData<>(
                pagedData.getCurrentPage(), pagedData.getTotalPages(), pagedData.getRows(), pagedData.getCount(),
                dispAuditEntryProperties
        );
    }
}
